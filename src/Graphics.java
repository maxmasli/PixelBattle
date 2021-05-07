import javax.swing.*;
import java.awt.*;

public class Graphics extends JPanel {
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.black);

        try {
            for (Figure figure : GameScene.figures) { // отображает фигуры
                switch (figure.getFigureId()) { //можно потом сделать чтобы если фигура за границе окна, то не рисовать ее лол
                    case PIXEL:
                        g2d.setColor(figure.getColor());
                        g2d.fillRect(figure.getX1(), figure.getY1(), figure.getX2(), figure.getY2());
                        break;
                    case LINE:
                        g2d.setColor(figure.getColor());
                        g2d.drawLine(figure.getX1(), figure.getY1(), figure.getX2(), figure.getY2());
                        break;
                    case RECT:
                        g2d.setColor(figure.getColor());
                        g2d.setStroke(new BasicStroke(10.0f));
                        g2d.fillRect(figure.getX1(), figure.getY1(), figure.getX2(), figure.getY2());
                        break;
                }
            }
            for (Text text : GameScene.texts) { // отображает строки
                if (text.getFigureId() == FiguresEnum.STRING) {
                    g2d.setColor(text.getColor());
                    g2d.setFont(new Font("SansSerif", Font.PLAIN, 18));
                    g2d.drawString(text.getString(), text.getX1(), text.getY1());
                }
            }
        } catch (Exception e) {
            System.err.println("ошибка");
        }

        repaint();

    }
}
