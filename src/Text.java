import java.awt.*;

public class Text extends Figure {
    private String string;
    private StringEnum type;

    public Text(FiguresEnum figureId, int x1, int y1, int x2, int y2, boolean isMain, Color color, String string, StringEnum type) {
        super(figureId, x1, y1, x2, y2, isMain, color, null);
        this.string = string;
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public StringEnum getType() {
        return type;
    }
}
