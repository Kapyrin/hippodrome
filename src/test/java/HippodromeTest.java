import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {
    @Test
    void whenHippodromeConstructorIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    void whenHippodromeConstructorIsEmpty() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    void hippodromeReturnSameHorsesInConstructor() {
        List<Horse> horses = new ArrayList<Horse>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("one of many horses", i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void testMoveHippodromeWithFiftyHorses() {

        List<Horse> horses = new ArrayList<Horse>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        new Hippodrome(horses).move();


        for (Horse horse : horses) {
            verify(horse).move();
        }

    }

    @Test
    void horseWithMostDistance() {
        Horse lessDistance = new Horse("less", 19, 10);
        Horse maxDistance = new Horse("max", 19, 30);
        Horse middleDistance = new Horse("middle", 19, 20);

        Hippodrome hippodrome = new Hippodrome(List.of(lessDistance, maxDistance, middleDistance));

        assertSame(maxDistance, hippodrome.getWinner());
    }


}
