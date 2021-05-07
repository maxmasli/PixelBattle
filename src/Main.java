import java.awt.*;

public class Main {
    GameScene scene;
    public Color human1 = Color.WHITE;
    public Color human2 = Color.WHITE;
    public Color tree1 = Color.GREEN;
    public Color tree2 = Color.GREEN;
    public Color stone1 = Color.GRAY;
    public Color stone2 = Color.YELLOW;

    public static void main(String[] args) {
        new Main().startGame();
    }

    public void startGame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = dim.width;
        int screenHeight = dim.height;

        // парсим картинки
        Parser parser = new Parser();
        parser.parse("src/images/human.prof", SpritesEnum.HUMAN, this);
        parser.parse("src/images/tree.prof", SpritesEnum.TREE, this);
        parser.parse("src/images/stone.prof", SpritesEnum.STONE, this);
        // parser.parse("src/images/castle.prof", SpritesEnum.CASTLE, this);



        // начинается прорисовка игры
        scene = new GameScene("PixelBattle", screenWidth, screenHeight, 10); // тут заданы размер окна, чтобы сначала
                                                                             // появлялось это окно
        // а потом переделывалось на весь экран, и белого фона не будет. можно будет
        // потом попробовать поубирать width height хотя не всегда помогает

        // главный перс
        Sprite user = new Sprite(Pictures.man, true, scene.pseudoX, scene.pseudoY, scene, SpritesEnum.HUMAN, 50, human1,
                human2);
        scene.drawSpriteInScene(user, screenWidth / 2 - 50, screenHeight / 2 - 50);

        // камни
        for (int i = 0; i < 10; i++) {
            Sprite stone = new Sprite(Pictures.stone, false, rnd(0, scene.mapWidthSize), rnd(0, scene.mapHeightSize),
                    scene, SpritesEnum.STONE, 10, stone1, stone2);
            scene.drawSpriteInScene(stone, stone.getX() - (scene.pseudoX - (screenWidth / 2)),
                    stone.getY() - (scene.pseudoY - (screenHeight / 2)));
        }

        // деревья
        for (int i = 0; i < 15; i++) {
            Sprite tree = new Sprite(Pictures.tree, false, rnd(0, scene.mapWidthSize), rnd(0, scene.mapHeightSize),
                    scene, SpritesEnum.TREE, 6, tree1, tree2);
            scene.drawSpriteInScene(tree, tree.getX() - (scene.pseudoX - (screenWidth / 2)),
                    tree.getY() - (scene.pseudoY - (screenHeight / 2)));
        }

        // замок
        // Sprite castle = new Sprite(Pictures.castle, false, scene.mapWidthSize - 90,
        // scene.mapHeightSize/2 - 50, scene, SpritesEnum.CASTLE, 100,
        // Color.gray, Color.red, Color.darkGray, Color.red.darker());
        // scene.drawSpriteInScene(castle, castle.getX() - (scene.pseudoX -
        // (screenWidth/2)), castle.getY() - (scene.pseudoY - (screenHeight/2)));

        // это стенки НЕ МЕНЯТЬ иначе все слетит нгафик
        scene.drawLine(-(scene.pseudoX - (screenWidth / 2)), -(scene.pseudoY - (screenHeight / 2)),
                scene.mapWidthSize - (scene.pseudoX - (screenWidth / 2)), -(scene.pseudoY - (screenHeight / 2)), false,
                Color.RED); // top

        scene.drawLine(-(scene.pseudoX - (screenWidth / 2)), -(scene.pseudoY - (screenHeight / 2)),
                -(scene.pseudoX - (screenWidth / 2)), scene.mapHeightSize - (scene.pseudoY - (screenHeight / 2)), false,
                Color.RED); // left

        scene.drawLine(scene.mapWidthSize - (scene.pseudoX - (screenWidth / 2)), -(scene.pseudoY - (screenHeight / 2)),
                scene.mapWidthSize - (scene.pseudoX - (screenWidth / 2)),
                scene.mapHeightSize - (scene.pseudoY - (screenHeight / 2)), false, Color.RED); // right

        scene.drawLine(-(scene.pseudoX - (screenWidth / 2)), scene.mapHeightSize - (scene.pseudoY - (screenHeight / 2)),
                scene.mapWidthSize - (scene.pseudoX - (screenWidth / 2)),
                scene.mapHeightSize - (scene.pseudoY - (screenHeight / 2)), false, Color.RED); // down

        scene.drawString("0 монеток", screenWidth - 110, 50, true, Color.WHITE, 0, StringEnum.MONEY); // надо будет
                                                                                                      // разделить типо
                                                                                                      // чтобы отдельно
                                                                                                      // было 0 и
                                                                                                      // монеток
        scene.drawString("0 дерева", screenWidth - 110, 100, true, Color.WHITE, 1, StringEnum.WOOD); // дерево

        // главный пиксель
        // scene.drawPixel(scene.pseudoX, scene.pseudoY, true, Color.BLUE, user);

        scene.drawAll();
    }

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.ceil(((int) (Math.random() * ++max) + min) / 10)) * 10;
    }

}
