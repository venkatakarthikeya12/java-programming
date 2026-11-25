import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class HospitalAppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department =
            new JComboBox<>(new String[]{"Cardiology", "Neurology", "Orthopedics"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> timeSlot =
            new JComboBox<>(new String[]{"09:00", "10:30", "12:00", "15:30"});
    private final JLabel status = new JLabel("Ready.");
    private final Set<String> bookedSlots = new HashSet<>();
    private boolean bookingInProgress;

    private final Map<String, List<String>> doctors = Map.of(
            "Cardiology", List.of("Dr. Aruna", "Dr. Ravi"),
            "Neurology", List.of("Dr. Meena"),
            "Orthopedics", List.of("Dr. Kiran", "Dr. Sara"));

    public HospitalAppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        form.add(new JLabel("Patient ID"));
        form.add(patientId);
        form.add(new JLabel("Department"));
        form.add(department);
        form.add(new JLabel("Doctor"));
        form.add(doctor);
        form.add(new JLabel("Time Slot"));
        form.add(timeSlot);

        JButton book = new JButton("Book Appointment");
        book.addActionListener(e -> bookAppointment());
        department.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) refreshDoctors();
        });

        add(form, BorderLayout.CENTER);
        add(book, BorderLayout.NORTH);
        add(status, BorderLayout.SOUTH);
        refreshDoctors();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (bookingInProgress
                        && JOptionPane.showConfirmDialog(HospitalAppointmentDesk.this,
                        "A booking is in progress. Close anyway?", "Confirm Close",
                        JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                    return;
                }
                dispose();
            }
        });
        pack();
        setLocationRelativeTo(null);
    }

    private void refreshDoctors() {
        doctor.removeAllItems();
        for (String name : doctors.get(department.getSelectedItem())) {
            doctor.addItem(name);
        }
    }

    private void bookAppointment() {
        String id = patientId.getText().trim();
        if (id.isEmpty()) {
            status.setText("Error: Patient ID is required.");
            status.setForeground(Color.RED);
            return;
        }
        String key = doctor.getSelectedItem() + "|" + timeSlot.getSelectedItem();
        bookingInProgress = true;
        if (bookedSlots.contains(key)) {
            status.setText("Error: This doctor/time slot is already booked.");
            status.setForeground(Color.RED);
            bookingInProgress = false;
            return;
        }
        bookedSlots.add(key);
        status.setText("Confirmed: " + id + ", " + doctor.getSelectedItem()
                + ", " + timeSlot.getSelectedItem());
        status.setForeground(new Color(0, 128, 0));
        bookingInProgress = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalAppointmentDesk().setVisible(true));
    }
}
