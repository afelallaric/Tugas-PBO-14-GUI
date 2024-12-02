import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private OFImage image;

    public void setImage(OFImage image) {
        this.image = image;
        repaint();
    }

    public void clearImage() {
        this.image = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
