import java.awt.event.*;
import javax.swing.*;

public class AnonymousInnerConverter extends ConversionUI {
    public AnonymousInnerConverter() {
        super("Currency Converter - Anonymous Inner");
        ActionListener handler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convert();
            }
        };
        convertButton.addActionListener(handler);
        amount.addActionListener(handler);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnonymousInnerConverter().setVisible(true));
    }
}
