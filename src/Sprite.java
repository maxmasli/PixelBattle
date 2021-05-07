import java.awt.*;
import java.util.UUID;

public class Sprite {
    private final int[][] picture;
    private final boolean main;
    private final Color[] colors;
    private int pseudoX;
    private int pseudoY;
    private int x;
    private int y;
    private UUID id;
    private SpritesEnum type;
    private int health;

    public Sprite(int[][] picture, boolean main, int x, int y, GameScene scene, SpritesEnum type,int health,Color... colors) {
        this.picture = picture;
        this.main = main;
        this.x = x;
        this.y = y;
        this.pseudoX = x + (picture[0].length / 2) * GameScene.tile;
        this.pseudoY = y + (picture.length / 2) * GameScene.tile;
        this.colors = colors;
        this.id = UUID.randomUUID();
        this.type = type;
        this.health = health;

        scene.addSprite(this); //добавляется спрайт
    }

    public int[][] getPicture() {
        return picture;
    }

    public boolean isMain() {
        return main;
    }

    public Color[] getColors() {
        return colors;
    }

    public int getPseudoX() {
        return pseudoX;
    }

    public void setPseudoX(int pseudoX) {
        this.pseudoX = pseudoX;
    }

    public int getPseudoY() {
        return pseudoY;
    }

    public void setPseudoY(int pseudoY) {
        this.pseudoY = pseudoY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return getPseudoX() + " " + getPseudoY();
    } //кал метод лучше переписать

    public UUID getId() {
        return id;
    }

    public SpritesEnum getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
