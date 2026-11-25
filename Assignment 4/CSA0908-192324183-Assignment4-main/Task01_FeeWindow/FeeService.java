public class FeeService {
    public PaymentResult pay(String registerNumber, String amountText) {
        if (registerNumber == null || registerNumber.trim().isEmpty()) {
            return PaymentResult.failure("Register number is required.");
        }
        if (amountText == null || amountText.trim().isEmpty()) {
            return PaymentResult.failure("Amount is required.");
        }
        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                return PaymentResult.failure("Amount must be greater than zero.");
            }
            return PaymentResult.success("Payment recorded for " + registerNumber.trim()
                    + ": Rs. " + String.format("%.2f", amount));
        } catch (NumberFormatException ex) {
            return PaymentResult.failure("Amount must be numeric.");
        }
    }

    public PaymentResult receipt(String registerNumber) {
        if (registerNumber == null || registerNumber.trim().isEmpty()) {
            return PaymentResult.failure("Enter a register number for the receipt.");
        }
        return PaymentResult.success("Receipt ready for " + registerNumber.trim());
    }

    public record PaymentResult(boolean success, String message) {
        public static PaymentResult success(String message) {
            return new PaymentResult(true, message);
        }
        public static PaymentResult failure(String message) {
            return new PaymentResult(false, message);
        }
    }
}
