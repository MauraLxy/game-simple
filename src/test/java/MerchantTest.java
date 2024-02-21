import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class MerchantTest {
    MerchantNPC npc = new MerchantNPC();

    @Test
    public void testIntroduction(){
        assertNotNull(npc.getIntroduction());
        npc.setIntroduction("hello");
        assertEquals("hello", npc.getIntroduction());
    }

    @Test
    public void testItem(){
        assertNotNull(this.npc.item);
        assertNotNull(this.npc.item.getValue());
        assertNotNull(this.npc.item.getName());
        assertNotNull(this.npc.item.getDescription());

        int countSame = 0;
        for(int i = 0; i < 10; i++){
            MerchantNPC npc1 = new MerchantNPC();
            MerchantNPC npc2 = new MerchantNPC();

            if(npc1.item.equals(npc2.item))
                countSame++;
        }

        assertTrue(countSame <= 3, "Flakey test: potentially not enough variety in merchant items");
    }

    @Test
    public void testChoice(){
        Player player = new Player(new Point(1,1));
        String initialChoices = npc.getChoice().getChoices();
        assertNotNull(initialChoices);

        String choose1 = npc.getChoice().choose(1, player);
        String choose2 = npc.getChoice().choose(2, player);
        assertNotEquals(choose1, choose2);
        String choose1Again = npc.getChoice().choose(1, player);
        System.out.println(choose1);
        System.out.println(choose1Again);
        assertNotEquals(choose1,choose1Again);

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
