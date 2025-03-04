import ro.mpp.model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {

    @Test
    @DisplayName("First Test")
    public void name() {
        ComputerRepairRequest request = new ComputerRepairRequest();
        assertEquals(0, request.getID());
        assertEquals("", request.getOwnerName());
        assertEquals("", request.getOwnerAddress());
        assertEquals("", request.getPhoneNumber());
        assertEquals("", request.getModel());
        assertEquals("", request.getDate());
        assertEquals("", request.getProblemDescription());
    }

    @Test
    void testParameterizedConstructor() {
        ComputerRepairRequest request = new ComputerRepairRequest(1, "Toda Emanuela",
                "166 Brancusi", "751803048",
                "Dell XPS", "2024-03-04",
                "Won't turn on");

        assertEquals(1, request.getID());
        assertEquals("Toda Emanuela", request.getOwnerName());
        assertEquals("166 Brancusi", request.getOwnerAddress());
        assertEquals("751803048", request.getPhoneNumber());
        assertEquals("Dell XPS", request.getModel());
        assertEquals("2024-03-04", request.getDate());
        assertEquals("Won't turn on", request.getProblemDescription());
    }
}
