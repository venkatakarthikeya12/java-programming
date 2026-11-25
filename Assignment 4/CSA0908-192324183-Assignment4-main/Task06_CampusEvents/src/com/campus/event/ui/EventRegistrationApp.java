package com.campus.event.ui;

import com.campus.event.dao.*;
import com.campus.event.model.Registration;
import com.campus.event.service.RegistrationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EventRegistrationApp extends JFrame {
    private final JTextField studentId = new JTextField(12);
    private final JTextField studentName = new JTextField(18);
    private final JComboBox<String> event =
            new JComboBox<>(new String[]{"Cultural Fest", "Code Marathon", "Music Night"});
    private final JComboBox<String> filter =
            new JComboBox<>(new String[]{"All", "Cultural Fest", "Code Marathon", "Music Night"});
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Student ID", "Name", "Event", "Registered At"}, 0);
    private final JTable table = new JTable(tableModel);
    private final JLabel status = new JLabel("Loading registrations...");
    private final RegistrationService service;
    private int pageOffset;
    private static final int PAGE_SIZE = 25;

    public EventRegistrationApp() {
        super("Campus Event Registration");
        RegistrationDao dao;
        try {
            dao = new JdbcRegistrationDao();
            dao.filter("All", 0, 1);
        } catch (Exception jdbcUnavailable) {
            dao = new InMemoryRegistrationDao();
            status.setText("In-memory demonstration mode (MySQL unavailable).");
        }
        service = new RegistrationService(dao);

        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Register Student"));
        form.add(new JLabel("Student ID"));
        form.add(studentId);
        form.add(new JLabel("Student Name"));
        form.add(studentName);
        form.add(new JLabel("Event"));
        form.add(event);
        JButton register = new JButton("Register");
        register.addActionListener(e -> register());
        form.add(new JLabel());
        form.add(register);

        JPanel filterRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterRow.add(new JLabel("Filter"));
        filterRow.add(filter);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadPage());
        JButton previous = new JButton("Previous");
        previous.addActionListener(e -> {
            pageOffset = Math.max(0, pageOffset - PAGE_SIZE);
            loadPage();
        });
        JButton next = new JButton("Next");
        next.addActionListener(e -> {
            pageOffset += PAGE_SIZE;
            loadPage();
        });
        filterRow.add(refresh);
        filterRow.add(previous);
        filterRow.add(next);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.WEST);
        top.add(filterRow, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadPage();
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem export = new JMenuItem("Export CSV");
        export.addActionListener(e -> exportCsv());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> dispose());
        file.add(export);
        file.addSeparator();
        file.add(exit);
        bar.add(file);
        JMenu help = new JMenu("Help");
        help.add(new JMenuItem("Campus Event Registration"));
        bar.add(help);
        return bar;
    }

    private void register() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return service.register(studentId.getText(), studentName.getText(),
                        event.getSelectedItem().toString());
            }
            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                try {
                    if (get()) {
                        JOptionPane.showMessageDialog(EventRegistrationApp.this,
                                "Registration successful.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        studentId.setText("");
                        studentName.setText("");
                        loadPage();
                    } else {
                        JOptionPane.showMessageDialog(EventRegistrationApp.this,
                                "Duplicate registration.", "Duplicate",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    String message = ex.getCause() == null
                            ? ex.getMessage() : ex.getCause().getMessage();
                    JOptionPane.showMessageDialog(EventRegistrationApp.this,
                            message, "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void loadPage() {
        status.setText("Loading registrations...");
        SwingWorker<List<Registration>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Registration> doInBackground() throws Exception {
                return service.filter(filter.getSelectedItem().toString(),
                        pageOffset, PAGE_SIZE);
            }
            @Override
            protected void done() {
                try {
                    tableModel.setRowCount(0);
                    for (Registration row : get()) {
                        tableModel.addRow(new Object[]{row.id(), row.studentId(),
                                row.studentName(), row.eventName(), row.registeredAt()});
                    }
                    status.setText("Showing " + tableModel.getRowCount()
                            + " rows from offset " + pageOffset);
                } catch (Exception ex) {
                    status.setText("Unable to load registrations: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file),
                        StandardCharsets.UTF_8))) {
            writer.println("ID,Student ID,Name,Event,Registered At");
            for (Registration row : service.all()) {
                writer.printf("%d,%s,%s,%s,%s%n",
                        row.id(), csv(row.studentId()), csv(row.studentName()),
                        csv(row.eventName()), csv(row.registeredAt()));
            }
            JOptionPane.showMessageDialog(this, "Exported " + file.getName(),
                    "Export", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistrationApp().setVisible(true));
    }
}
