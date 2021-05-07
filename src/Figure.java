import java.awt.*;

public class Figure {

    private FiguresEnum figureId;
    private Color color;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private boolean isMain;
    private Sprite parent;

    public Figure(FiguresEnum figureId, int x1, int y1, int x2, int y2, boolean isMain, Color color, Sprite parent) {
        this.figureId = figureId;
        this.color = color;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.isMain = isMain;
        this.parent = parent;
    }
    //геттеры
    public FiguresEnum getFigureId() {
        return figureId;
    }

    public Color getColor() {
        return color;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }



    public boolean isMain() {
        return isMain;
    }

    //сеттеры

    public void setX1(int x1) {
        int old = getX1();
        this.x1 = x1;
        if (getFigureId() == FiguresEnum.LINE) {
            setX2(getX2() + (x1 - old));
        }
    }

    public void setY1(int y1) {
        int old = getY1();
        this.y1 = y1;
        if (getFigureId() == FiguresEnum.LINE) {
            setY2(getY2() + (y1 - old));
        }
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public Sprite getParent() {
        return parent;
    }
}
