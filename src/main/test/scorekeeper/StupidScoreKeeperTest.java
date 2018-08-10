package scorekeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StupidScoreKeeperTest {

    private static int PODIUM_SIZE = 3;

    private StupidScoreKeeper<String> underTest;

    @BeforeEach
    public void setupEach() {
        underTest = new StupidScoreKeeper<>(PODIUM_SIZE);
    }

    @Test
    public void zeroDecrease() {
        boolean changed = underTest.decrease("a");
        assertFalse(changed);
    }

    @Test
    public void zeroIncrease() {
        boolean changed;
        List<String> podium;
        for (int i = 0; i < PODIUM_SIZE; i++) {
            changed = underTest.increase("" + i);
            assertTrue(changed);

            podium = underTest.getPodium();
            assertEquals(PODIUM_SIZE, podium.size());
            podium.removeIf(Objects::isNull);
            assertEquals(i + 1, podium.size());
        }
    }

    @Test
    public void overtakeIncrease() {
        boolean changed;
        List<String> podium;
        for (int i = 0; i < PODIUM_SIZE; i++) {
            underTest.increase("" + i);
        }
        changed = underTest.increase("1");
        assertTrue(changed);

        podium = underTest.getPodium();
        assertEquals(PODIUM_SIZE, podium.size());
        assertEquals("1", podium.get(0));

        for (int i = 0; i < PODIUM_SIZE; i++) {
            if (i != 2) {
                assertTrue(podium.contains("" + i));
            }
        }
    }

    @Test
    public void overtakeDecrease() {
        boolean changed;
        List<String> podium;
        for (int i = 0; i < PODIUM_SIZE; i++) {
            underTest.increase("" + i);
            underTest.increase("" + i);
        }
        changed = underTest.decrease("1");
        assertTrue(changed);

        podium = underTest.getPodium();
        assertEquals(PODIUM_SIZE, podium.size());

        assertEquals("1", podium.get(PODIUM_SIZE - 1));

        for (int i = 0; i < PODIUM_SIZE; i++) {
            if (i != 2) {
                assertTrue(podium.contains("" + i));
            }
        }
    }

}