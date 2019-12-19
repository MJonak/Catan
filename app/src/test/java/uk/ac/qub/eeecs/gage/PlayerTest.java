package uk.ac.qub.eeecs.gage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import uk.ac.qub.eeecs.game.catan.Player;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void getPlayerNo(){
        Player player = new Player(4);
        assertEquals("Error - incorrect player number returned",4, player.getPlayerNo());
    }

    @Test
    public void addResources(){
        Player player = new Player(1);
        player.addResource(0, 1);
        assertEquals("Error - incorrect number of resource 0",1, player.getResource(0));
        player.addResource(1, 1);
        assertEquals("Error - incorrect number of resource 1", 1, player.getResource(1));
        player.addResource(2, 1);
        assertEquals("Error - incorrect number of resource 2",1, player.getResource(2));
        player.addResource(3, 1);
        assertEquals("Error - incorrect number of resource 3",1, player.getResource(3));
        player.addResource(4, 1);
        assertEquals("Error - incorrect number of resource 4",1, player.getResource(4));

    }

    //Need  11011
    //      10001
    //      00320
    // =    21332
    //      10321
    //      10001
    @Test
    public void removeResourcesForBuildings(){
        Player player = new Player(2);
        player.addResource(0, 2);
        player.addResource(1, 1);
        player.addResource(2, 3);
        player.addResource(3, 3);
        player.addResource(4, 2);
        assertTrue("Error - player has enough resources for building 1, should be true", player.hasEnoughResourcesFor(1));
        assertTrue("Error - player has enough resources for building 2, should be true", player.hasEnoughResourcesFor(2));
        assertTrue("Error - player has enough resources for building 3, should be true", player.hasEnoughResourcesFor(3));
        player.removeResourcesFor(1);
        assertFalse("Error - player does not have enough resources for building 1, should be false", player.hasEnoughResourcesFor(1));
        assertTrue("Error - player has enough resources for building 2, should be true", player.hasEnoughResourcesFor(2));
        assertTrue("Error - player has enough resources for building 3, should be true", player.hasEnoughResourcesFor(3));
        int resources = player.getResource(0)*10000 + player.getResource(1)*1000 + player.getResource(2)*100+player.getResource(3)*10 + player.getResource(4);
        assertEquals("Error - incorrect number of resources after building settlement",10321,resources);
        player.removeResourcesFor(2);
        assertFalse("Error - player does not have enough resources for building 1, should be false", player.hasEnoughResourcesFor(1));
        assertFalse("Error - player does not have enough resources for building 2, should be false", player.hasEnoughResourcesFor(2));
        assertTrue("Error - player has enough resources for building 3, should be true", player.hasEnoughResourcesFor(3));
        resources = player.getResource(0)*10000 + player.getResource(1)*1000 + player.getResource(2)*100+player.getResource(3)*10 + player.getResource(4);
        assertEquals("Error - incorrect number of resources after building town",10001,resources);
        player.removeResourcesFor(3);
        assertFalse("Error - player does not have enough resources for building 1, should be false", player.hasEnoughResourcesFor(1));
        assertFalse("Error - player does not have enough resources for building 2, should be false", player.hasEnoughResourcesFor(2));
        assertFalse("Error - player does not have enough resources for building 3, should be false", player.hasEnoughResourcesFor(3));
        resources = player.getResource(0) + player.getResource(1) + player.getResource(2) +player.getResource(3) + player.getResource(4);
        assertEquals("Error - incorrect number of resources after building town",0,resources);
    }

    @Test
    public void removeResource(){
        Player player = new Player(3);
        player.addResource(0, 1);
        player.removeResource(0, 1);
        assertEquals("Error - incorrect number of resource 0 after removal", 0, player.getResource(0));

        player.addResource(1, 1);
        player.removeResource(1, 1);
        assertEquals("Error - incorrect number of resource 1 after removal", 0, player.getResource(1));

        player.addResource(2, 1);
        player.removeResource(2, 1);
        assertEquals("Error - incorrect number of resource 2 after removal", 0, player.getResource(2));

        player.addResource(3, 1);
        player.removeResource(3, 1);
        assertEquals("Error - incorrect number of resource 3 after removal", 0, player.getResource(3));

        player.addResource(4, 1);
        player.removeResource(4, 1);
        assertEquals("Error - incorrect number of resource 4 after removal", 0, player.getResource(4));
    }

    @Test
    public void getNoOfResources(){
        Player player = new Player(4);
        player.addResource(0, 3);
        assertEquals("Error - incorrect number of total resource cards given at resource 0", 3, player.getNoOfResources());
        player.addResource(1, 3);
        assertEquals("Error - incorrect number of total resource cards given at resource 1", 6, player.getNoOfResources());
        player.addResource(2, 3);
        assertEquals("Error - incorrect number of total resource cards given at resource 2", 9, player.getNoOfResources());
        player.addResource(3, 3);
        assertEquals("Error - incorrect number of total resource cards given at resource 3", 12, player.getNoOfResources());
        player.addResource(4, 3);
        assertEquals("Error - incorrect number of total resource cards given at resource 4", 15, player.getNoOfResources());

    }

    @Test
    public void addVictoryPoints(){
        Player player = new Player(5);
        player.addVictoryPoints(2);
        assertEquals(2, player.getVictoryPoints());
        player.addVictoryPoints(1);
        assertEquals(3, player.getVictoryPoints());
    }

    @Test
    public void setStartingResources(){
        Player player = new Player(6);
        player.setStartingResources();
        player.removeResourcesFor(1);
        player.removeResourcesFor(1);
        player.removeResourcesFor(3);
        player.removeResourcesFor(3);
        int sum = player.getResource(0) + player.getResource(1) + player.getResource(2) + player.getResource(3) + player.getResource(4);
        assertEquals("Error - sum of player's resources =/= 0 after building 2 settlements & 2 roads",0, sum);
    }



}
