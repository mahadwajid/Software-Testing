import org.example.Main;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testRemoveDuplicates_EmptyArray() {
        int[] array = {};
        int[] expectedArray = {};
        int expectedLength = 0;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }

    @Test
    public void testRemoveDuplicates_SingleElementArray() {
        int[] array = {1};
        int[] expectedArray = {1};
        int expectedLength = 1;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }

    @Test
    public void testRemoveDuplicates_NoDuplicates() {
        int[] array = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};
        int expectedLength = 5;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }

    @Test
    public void testRemoveDuplicates_ConsecutiveDuplicates() {
        int[] array = {1, 1, 2, 2, 3, 4, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};
        int expectedLength = 5;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }

    @Test
    public void testRemoveDuplicates_AllElementsSame() {
        int[] array = {1, 1, 1, 1, 1};
        int[] expectedArray = {1};
        int expectedLength = 1;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }

    @Test
    public void testRemoveDuplicates_ScatteredDuplicates() {
        int[] array = {1, 2, 2, 3, 4, 4, 5, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};
        int expectedLength = 5;
        int result = Main.removeDuplicates(array); // Call removeDuplicates from Main class
        assertArrayEquals(expectedArray, Arrays.copyOf(array, result));
        assertEquals(expectedLength, result);
    }
}
