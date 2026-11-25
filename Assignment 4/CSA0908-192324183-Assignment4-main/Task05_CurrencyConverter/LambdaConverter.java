import javax.swing.*;

public class LambdaConverter extends ConversionUI {
    public LambdaConverter() {
        super("Currency Converter - Lambda");
        convertButton.addActionListener(e -> convert());
        amount.addActionListener(e -> convert());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LambdaConverter().setVisible(true));
    }
}
