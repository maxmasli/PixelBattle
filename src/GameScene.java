import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends JFrame implements KeyListener {

    private boolean isUp = false;
    private boolean isDown = false;
    private boolean isRight = false;
    private boolean isLeft = false;

    public static int tile; // размер клеточки
    public static List<Figure> figures = new ArrayList<>();
    public static Text[] texts = new Text[2];
    public static List<Sprite> sprites = new ArrayList<>();

    public static int screenWidth;
    public static int screenHeight;

    public int mapWidthSize = 4000;
    public int mapHeightSize = 2000;

    public int pseudoX = mapWidthSize / 2;
    public int pseudoY = mapHeightSize / 2;

    public static int minLengthForBreak;

    public int wood = 0;
    public int maxCostWood = 4;
    public int money = 0;
    public int costMoney = 2;

    //private int tileWidth; //сколько пикселей вмещается в ширину
    //private int tileHeight; //сколько пикселей вмещается в высоту

    public GameScene(String title, int _width, int _height, int tile) {
        setTitle(title);
        screenWidth = _width;
        screenHeight = _height;
        setBounds(0, 0, screenWidth, screenHeight);
        setExtendedState(Frame.MAXIMIZED_BOTH); //на весь экран
        //setUndecorated(true); //чтобы ваще капец на весь экран
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setFocusable(true);

        GameScene.tile = tile;
        GameScene.minLengthForBreak = tile * 6;
        //tileWidth = width / tile;
        //tileHeight = height / tile;

        CheckKeyThread checkKeyThread = new CheckKeyThread();
        checkKeyThread.start();

    }

    //методы для рисования
    public void drawPixel(int x, int y, boolean main, Color color, Sprite parent) {
        figures.add(new Figure(FiguresEnum.PIXEL, x, y, tile, tile, main, color, parent));
    }

    public void drawLine(int x1, int y1, int x2, int y2, boolean main, Color color) {
        figures.add(new Figure(FiguresEnum.LINE, x1, y1, x2, y2, main, color, null));
    }

    public void drawRect(int x1, int y1, int x2, int y2, boolean main, Color color) {
        figures.add(new Figure(FiguresEnum.RECT, x1, y1, x2, y2, main, color, null));
    }

    public void drawString(String string, int x1, int y1, boolean main, Color color, int pos, StringEnum type) {
        texts[pos] = new Text(FiguresEnum.STRING, x1, y1, 0, 0, main, color, string, type);
    }

    // вызывать для перезагрузки экрана
    public void drawAll() {
        add(new Graphics());
    }

    public void addSprite(Sprite sprite) { //добавляется автоматически спрайт
        sprites.add(sprite);
    }

    public void drawSpriteInScene(Sprite sprite, int x, int y) {
        int countY = 0;
        for (int[] row : sprite.getPicture()) {
            int countX = 0;
            for (int i : row) {
                if (i > 0) {
                    drawPixel(x + (countX * tile), y + (countY * tile), sprite.isMain(), sprite.getColors()[i - 1], sprite);
                }
                countX++;
            }
            countY++;
        }
    }

    private void rep() {
        drawAll();
        repaint();
    }

    //методы для логики (несовсем)
    private void addWood() {
        wood += (int) (Math.random() * maxCostWood);
        for (int i = 0; i < texts.length; i++) {
            if (texts[i].getType() == StringEnum.WOOD) {
                texts[i].setString(wood + " дерева");
            }
        }
        spawnTree(); //при каждом ломании спавница дерево
    }

    private void addMoney() {
        money += costMoney;//лох ебаный сука
        for (int i = 0; i < texts.length; i++) {
            if (texts[i].getType() == StringEnum.MONEY) {
                texts[i].setString(money + " монеток");
            }
        }
        spawnStone(); //при каждом ломании спавница каминь
    }

    private void spawnTree() {// адо проследить где спаыница новыые приколы!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Sprite newTree = new Sprite(Pictures.tree, false, Main.rnd(0, mapWidthSize), Main.rnd(0, mapHeightSize), this, SpritesEnum.TREE, 6, Color.green);
        drawSpriteInScene(newTree, newTree.getX() - (pseudoX - (screenWidth / 2)), newTree.getY() - (pseudoY - (screenHeight / 2)));
    }

    private void spawnStone() {// спавница камень
        Sprite newStone = new Sprite(Pictures.stone, false, Main.rnd(0, mapWidthSize), Main.rnd(0, mapHeightSize), this, SpritesEnum.STONE, 10, Color.gray, Color.YELLOW);
        drawSpriteInScene(newStone, newStone.getX() - (pseudoX - (screenWidth / 2)), newStone.getY() - (pseudoY - (screenHeight / 2)));
    }

    private void breakSprite(Sprite spr) { // метод где ломается спрайт
        if (spr.getHealth() > 0) { //если у него есть хп
            spr.setHealth(spr.getHealth() - 1);

        } else { // если нету то удаляется
            for (int i = 0; i < figures.size(); i++) { //проходимся по фигурам и если фигура родителя то убераем ее
                if (figures.get(i).getParent() == null) {
                    continue;
                }

                if (figures.get(i).getParent().equals(spr)) { // почему он только 13 берет у камня?
                    figures.remove(figures.get(i)); // потом переделать говнокод потому что мы сначала проходимся по списку,
                    // и чтобы итерация норм была я заменяю на нулл
                    // а потом от этих нулл очищаю, потому  что если в списке удалять то список сдыинется влево и итерация
                    // нарущится и наверное по этому он удалял только 13
                    figures.add(i, null); //заменяем на нулл вместо удаления

                }
            }
            while (figures.contains(null)) { // очистка от нуллов
                figures.remove(null);
            }

            if (spr.getType() == SpritesEnum.STONE) {//если камень то прибавляется баблище
                addMoney();
            } else if (spr.getType() == SpritesEnum.TREE) {
                addWood();
            }
            sprites.remove(spr);
            System.out.println("удалилось, спрайтов осталось: " + (sprites.size() - 1));
            drawAll();
        }
    }

    private void checkKeys() {
        if (isUp) pressedUp();
        if (isDown) pressedDown();
        if (isRight) pressedRight();
        if (isLeft) pressedLeft();
    }

    private void pressedBreak() {
        int len = screenHeight;

        Sprite spr = null;
        for (Sprite sprite : sprites) {
            if (sprite.isMain()) {
                continue;
            }
            //высчитывается минимальное расстояние от игрока до спрайта
            int deltaX = Math.abs(sprite.getPseudoX() - pseudoX);
            int deltaY = Math.abs(sprite.getPseudoY() - pseudoY);

            int longest = Math.max(deltaX, deltaY);

            if (longest < len) {
                len = longest;
                spr = sprite;
            }
        }

        if (len <= minLengthForBreak) { //расстояние после которого удаляется спрайт
            breakSprite(spr);
        }
    }

    private void pressedUp() {
        if (pseudoY > 0) {
            for (Figure figure : figures) {
                if (!figure.isMain()) {
                    figure.setY1(figure.getY1() + tile);
                }
            }
            pseudoY -= tile;
            drawAll();
        }
    }

    private void pressedDown() {
        if (pseudoY < mapHeightSize) {
            for (Figure figure : figures) {
                if (!figure.isMain()) {
                    figure.setY1(figure.getY1() - tile);///!"!!!!!!!!!!!!!!!!!!!!!!!!!!!!а может быть здесь надо псевдо у спрайтов тоже отнимать?
                }
            }
            pseudoY += tile;
            drawAll();
        }
    }

    private void pressedRight() {
        if (pseudoX < mapWidthSize) {
            for (Figure figure : figures) {
                if (!figure.isMain()) {
                    figure.setX1(figure.getX1() - tile);
                }
            }
            pseudoX += tile;
            drawAll();
        }

    }

    private void pressedLeft() {
        if (pseudoX > 0) {
            for (Figure figure : figures) {
                if (!figure.isMain()) {
                    figure.setX1(figure.getX1() + tile);
                }
            }
            pseudoX -= tile;
            drawAll();
        }
    }

    //слушатель клавиш
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) isLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) isRight = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) isUp = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) isDown = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) isLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) isRight = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) isUp = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) isDown = false;
        if (e.getKeyCode() == KeyEvent.VK_B) pressedBreak();
        if (e.getKeyCode() == KeyEvent.VK_T) spawnTree();
        if (e.getKeyCode() == KeyEvent.VK_S) spawnStone();
        if (e.getKeyCode() == KeyEvent.VK_R) rep();
    }

    private class CheckKeyThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true) {
                checkKeys();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
