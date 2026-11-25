# Task 2 - Hospital Outpatient Appointment Desk

## Event Model

| Source | Event | Listener | Action |
|---|---|---|---|
| Department `JComboBox` | `ItemEvent` | `ItemListener` | Refresh doctor `JComboBox` |
| Patient ID `JTextField` | `ActionEvent` | `ActionListener` | Validate/advance flow |
| Doctor `JComboBox` | `ItemEvent` | `ItemListener` | Update selected doctor |
| Time slot `JComboBox` | `ItemEvent` | `ItemListener` | Update selected slot |
| Book `JButton` | `ActionEvent` | `ActionListener` | Validate and create appointment |
| Window | `WindowEvent` | `WindowListener` | Confirm close if booking is in progress |

## Handler Pseudocode

```text
ON department_changed(selected):
  doctors <- appointmentMap[selected department]
  clear and populate doctorCombo

ON book_clicked():
  IF patientId blank THEN show error
  IF department or doctor or slot missing THEN show error
  IF slot already booked THEN show error
  ELSE add booking, show confirmation, set bookingInProgress false
```
