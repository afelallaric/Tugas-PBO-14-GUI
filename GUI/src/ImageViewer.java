import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * ImageViewer is the main class of the image viewer application. It builds and
 * displays the application GUI and initializes all other components.
 *
 * To start the application, create an object of this class.
 *
 * @author Michael Kolling and David J Barnes
 * @version 1.0
 */
public class ImageViewer {
    // Static fields
    private static final String VERSION = "Version 1.0";
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    // Instance fields
    private JFrame frame;
    private ImagePanel imagePanel;
    private JLabel filenameLabel;
    private JLabel statusLabel;
    private OFImage currentImage;

    /**
     * Main constructor. Create an ImageViewer and show it on screen.
     */
    public ImageViewer() {
        makeFrame();
    }

    /**
     * Open a file dialog to load an image file.
     */
    private void openFile() {
        int returnVal = fileChooser.showOpenDialog(frame);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return; // cancelled
        }

        File selectedFile = fileChooser.getSelectedFile();
        currentImage = ImageFileManager.loadImage(selectedFile);
        if (currentImage == null) {
            JOptionPane.showMessageDialog(frame,
                    "The file was not in a recognized image file format.",
                    "Image Load Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        imagePanel.setImage(currentImage);
        showFilename(selectedFile.getPath());
        showStatus("File loaded.");
        frame.pack();
    }

    /**
     * Close the currently loaded image.
     */
    private void close() {
        currentImage = null;
        imagePanel.clearImage();
        showFilename(null);
        showStatus("Image closed.");
    }

    /**
     * Quit the application.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Apply a "darker" filter to the image.
     */
    private void makeDarker() {
        if (currentImage != null) {
            currentImage.darker();
            frame.repaint();
            showStatus("Applied: darker");
        } else {
            showStatus("No image loaded.");
        }
    }

    /**
     * Apply a "lighter" filter to the image.
     */
    private void makeLighter() {
        if (currentImage != null) {
            currentImage.lighter();
            frame.repaint();
            showStatus("Applied: lighter");
        } else {
            showStatus("No image loaded.");
        }
    }

    /**
     * Apply a "threshold" filter to the image.
     */
    private void threshold() {
        if (currentImage != null) {
            currentImage.threshold();
            frame.repaint();
            showStatus("Applied: threshold");
        } else {
            showStatus("No image loaded.");
        }
    }

    /**
     * Show an "About" dialog.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(frame,
                "ImageViewer\n" + VERSION,
                "About ImageViewer",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ---- Support Methods ----

    /**
     * Display a file name on the filename label.
     *
     * @param filename The file name to be displayed.
     */
    private void showFilename(String filename) {
        if (filename == null) {
            filenameLabel.setText("No file displayed.");
        } else {
            filenameLabel.setText("File: " + filename);
        }
    }

    /**
     * Display a status message in the status bar.
     *
     * @param text The status message to be displayed.
     */
    private void showStatus(String text) {
        statusLabel.setText(text);
    }

    // ---- GUI Construction ----

    /**
     * Create the Swing frame and its content.
     */
    private void makeFrame() {
        frame = new JFrame("ImageViewer");
        makeMenuBar(frame);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout(6, 6));

        filenameLabel = new JLabel("No file displayed.");
        contentPane.add(filenameLabel, BorderLayout.NORTH);

        imagePanel = new ImagePanel();
        contentPane.add(imagePanel, BorderLayout.CENTER);

        statusLabel = new JLabel(VERSION);
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }

    /**
     * Create the main frame's menu bar.
     *
     * @param frame The frame that the menu bar should be added to.
     */
    private void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu menu;
        JMenuItem item;

        // File menu
        menu = new JMenu("File");
        menubar.add(menu);

        item = new JMenuItem("Open");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        item.addActionListener(e -> openFile());
        menu.add(item);

        item = new JMenuItem("Close");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        item.addActionListener(e -> close());
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem("Quit");
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        item.addActionListener(e -> quit());
        menu.add(item);

        // Filter menu
        menu = new JMenu("Filter");
        menubar.add(menu);

        item = new JMenuItem("Darker");
        item.addActionListener(e -> makeDarker());
        menu.add(item);

        item = new JMenuItem("Lighter");
        item.addActionListener(e -> makeLighter());
        menu.add(item);

        item = new JMenuItem("Threshold");
        item.addActionListener(e -> threshold());
        menu.add(item);

        // Help menu
        menu = new JMenu("Help");
        menubar.add(menu);

        item = new JMenuItem("About ImageViewer...");
        item.addActionListener(e -> showAbout());
        menu.add(item);
    }

    public static void main(String[] args) {
        new ImageViewer();
    }
}
