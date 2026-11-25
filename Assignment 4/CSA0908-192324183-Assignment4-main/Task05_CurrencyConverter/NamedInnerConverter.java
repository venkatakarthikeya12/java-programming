import java.awt.event.*;
import javax.swing.*;

public class NamedInnerConverter extends ConversionUI {
    private class ConvertHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            convert();
        }
    }

    public NamedInnerConverter() {
        super("Currency Converter - Named Inner");
        ConvertHandler handler = new ConvertHandler();
        convertButton.addActionListener(handler);
        amount.addActionListener(handler);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NamedInnerConverter().setVisible(true));
    }
}
