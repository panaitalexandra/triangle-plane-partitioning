import javax.swing.*;

public class TestGraphicInterface {
    public static void main(String[] args) {
        var frame = new GraphicInterface();
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
