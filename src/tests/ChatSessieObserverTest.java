import com.example.aiassistent.utils.ChatsessieCounter;
import com.example.aiassistent.utils.ObserverOndersteuning;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChatSessieObserverTest {
    private ChatsessieCounter chatSessieCounter;
    private ObserverOndersteuning observerOndersteuning;

    @Before
    public void setUp() {
        observerOndersteuning = new ObserverOndersteuning();
        chatSessieCounter = new ChatsessieCounter(observerOndersteuning);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse1_FysiekTestgeval1() {
        // Arrange
        int gebruikerID = 1;
        int chatsessieCount = 4;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse1_FysiekTestgeval2() {
        // Arrange
        int gebruikerID = 1;
        int chatsessieCount = 5;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("Er zijn nu 5 chatsessies aangemaakt.", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse1_FysiekTestgeval3() {
        // Arrange
        int gebruikerID = 1;
        int chatsessieCount = 6;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse2_FysiekTestgeval1() {
        // Arrange
        int gebruikerID = 2;
        int chatsessieCount = 9;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse2_FysiekTestgeval2() {
        // Arrange
        int gebruikerID = 2;
        int chatsessieCount = 10;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("Er zijn nu 10 chatsessies aangemaakt.", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse2_FysiekTestgeval3() {
        // Arrange
        int gebruikerID = 2;
        int chatsessieCount = 11;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse3_FysiekTestgeval1() {
        // Arrange
        int gebruikerID = 3;
        int chatsessieCount = 14;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse3_FysiekTestgeval2() {
        // Arrange
        int gebruikerID = 3;
        int chatsessieCount = 15;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("Er zijn nu 15 chatsessies aangemaakt.", bericht);
    }

    @Test
    public void testGenerateMessage_Equivalentieklasse3_FysiekTestgeval3() {
        // Arrange
        int gebruikerID = 3;
        int chatsessieCount = 16;

        // Act
        String bericht = chatSessieCounter.generateMessage(gebruikerID, chatsessieCount);

        // Assert
        Assert.assertEquals("", bericht);
    }
}
