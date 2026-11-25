import java.io.Serializable;

public class Resident implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String registerNumber;
    private transient String password;
    private transient Object databaseConnection;
    private String phoneNumber;

    public Resident(String name, String registerNumber, String password) {
        this.name = name;
        this.registerNumber = registerNumber;
        this.password = password;
    }
}
