import com.example.aiassistent.model.Gebruiker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class inlogTest {

    @Test
    public void inlogTest() {
        Gebruiker gebruiker = new Gebruiker(1, "test", "test@gmail.com", "test123", "Nederlands");

        Assertions.assertTrue(gebruiker.testInlog(true, true, true));
        Assertions.assertFalse(gebruiker.testInlog(true, true, false));
        Assertions.assertFalse(gebruiker.testInlog(true, false, true));
        Assertions.assertFalse(gebruiker.testInlog(false, true, false));
    }
}
