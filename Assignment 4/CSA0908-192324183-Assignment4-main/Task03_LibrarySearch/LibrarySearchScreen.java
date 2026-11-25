import javax.swing.*;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {
    public LibrarySearchScreen() {
        super("Campus Library Search");
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Campus Library Search", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.add(title);

        JTextField search = new JTextField(22);
        JComboBox<String> category = new JComboBox<>(
                new String[]{"All", "Computer Science", "Engineering", "Literature"});
        JButton searchButton = new JButton("Search");
        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        searchRow.add(search);
        searchRow.add(category);
        searchRow.add(searchButton);

        JTable results = new JTable(
                new Object[][]{
                        {"Java: A Beginner's Guide", "Computer Science", "Available"},
                        {"Clean Code", "Computer Science", "Reserved"},
                        {"Data Structures", "Engineering", "Available"}},
                new Object[]{"Title", "Category", "Status"});
        JPanel centre = new JPanel(new BorderLayout());
        centre.add(new JScrollPane(results), BorderLayout.CENTER);
        centre.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.add(new JButton("Prev"));
        bottom.add(new JButton("Next"));
        bottom.add(new JButton("Reserve"));

        JLabel status = new JLabel("Ready.");
        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusRow.add(status);

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.NORTH);
        north.add(searchRow, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(bottom, BorderLayout.CENTER);
        south.add(statusRow, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);
        add(centre, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearchScreen().setVisible(true));
    }
}
