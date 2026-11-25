import javax.swing.*;
import java.awt.*;

abstract class ConversionUI extends JFrame {
    protected final JTextField amount = new JTextField(10);
    protected final JComboBox<String> target =
            new JComboBox<>(new String[]{"USD", "EUR"});
    protected final JLabel result = new JLabel(" ");
    protected final JButton convertButton = new JButton("Convert");

    protected ConversionUI(String title) {
        super(title);
        setLayout(new FlowLayout());
        add(new JLabel("INR"));
        add(amount);
        add(target);
        add(convertButton);
        add(result);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    protected void convert() {
        try {
            double inr = Double.parseDouble(amount.getText().trim());
            if (inr < 0) {
                throw new NumberFormatException();
            }
            double rate = target.getSelectedItem().equals("USD") ? 0.012 : 0.011;
            String text = String.format("%.2f %s", inr * rate, target.getSelectedItem());
            result.setText(text);
            JOptionPane.showMessageDialog(this, text, "Conversion", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount", "Conversion",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
