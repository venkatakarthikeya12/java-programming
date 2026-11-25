import javax.swing.*;
import java.awt.*;

public class FeeWindow extends JFrame {
    private final JTextField regNo = new JTextField(16);
    private final JTextField amount = new JTextField(12);
    private final JLabel status = new JLabel("Enter payment details.");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.add(new JLabel("Register Number"));
        form.add(regNo);
        form.add(new JLabel("Amount"));
        form.add(amount);

        JPanel actions = new JPanel(new FlowLayout());
        JButton pay = new JButton("Pay");
        JButton clear = new JButton("Clear");
        JButton receipt = new JButton("Receipt");
        actions.add(pay);
        actions.add(clear);
        actions.add(receipt);

        pay.addActionListener(e -> showResult(service.pay(regNo.getText(), amount.getText())));
        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
            status.setText("Fields cleared.");
        });
        receipt.addActionListener(e -> showResult(service.receipt(regNo.getText())));

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.NORTH);
        add(status, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void showResult(FeeService.PaymentResult result) {
        status.setText(result.message());
        status.setForeground(result.success() ? new Color(0, 128, 0) : Color.RED);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true));
    }
}
