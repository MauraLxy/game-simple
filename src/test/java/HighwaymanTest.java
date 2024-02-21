import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class HighwaymanTest {
    HighwaymanNPC npc = new HighwaymanNPC();

    @Test
    public void testIntroduction(){
        assertNotNull(npc.getIntroduction());
        npc.setIntroduction("hello");
        assertEquals("hello", npc.getIntroduction());
    }

    @Test
    public void testChoice(){
        Player player = new Player(new Point(1,1));
        assertNotNull(npc.getChoice().getChoices());

        String choose1 = npc.getChoice().choose(1, player);
        String choose2 = npc.getChoice().choose(2,player);
        assertNotEquals(choose1,choose2);

        boolean different = false;
        String prev = choose1;
        for(int i = 0; i < 10 & !different; i++){
            String current = npc.getChoice().choose(1, player);
            if(!current.equals(prev))
                different = true;
        }
        assertTrue(different, "Flakey test: choosing 1 doesn't seem to result in different choices try running again");

        npc.setChoice(new testChoice());
        assertEquals("test getChoices", npc.getChoice().getChoices());
        assertEquals("test choose", npc.getChoice().choose(0, player));
    }

    private class testChoice implements Choice{
        @Override
        public String getChoices() {
            return "test getChoices";
        }

        @Override
        public String choose(int choice,Player player) {
            return "test choose";
        }
    }

}
