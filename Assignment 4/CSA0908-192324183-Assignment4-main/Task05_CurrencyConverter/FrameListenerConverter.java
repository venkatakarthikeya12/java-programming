import java.awt.event.*;
import javax.swing.*;

public class FrameListenerConverter extends ConversionUI implements ActionListener {
    public FrameListenerConverter() {
        super("Currency Converter - Frame Listener");
        convertButton.addActionListener(this);
        amount.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        convert();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrameListenerConverter().setVisible(true));
    }
}
