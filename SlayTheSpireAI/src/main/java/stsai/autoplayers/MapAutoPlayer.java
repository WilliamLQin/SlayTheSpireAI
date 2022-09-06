package stsai.autoplayers;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.map.*;
import java.util.ArrayList;

import stsai.inputapi.*;
import stsai.*;

public class MapAutoPlayer {

    private static final MapRoomNode BOSS_NODE = new MapRoomNode(3, 16);
    private static final MapRoomNode ENDING_BOSS_NODE = new MapRoomNode(3, 4);

    public static void autoplay() {
        if (!SlayTheSpireAI.enabled
                || AbstractDungeon.actionManager.phase != GameActionManager.Phase.WAITING_ON_USER
                || !AbstractDungeon.isScreenUp
                || !AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMPLETE)
                || AbstractDungeon.screen != AbstractDungeon.CurrentScreen.MAP) {
            return;
        }

        ArrayList<MapRoomNode> nodes = ChooseMapNode.getAvailableNodes();
        if (!nodes.isEmpty()) {
            MapRoomNode targetNode = selectNode(nodes);

            // Set the next node to targetNode
            ChooseMapNode.chooseNextNode(targetNode);
        } else if ((AbstractDungeon.getCurrMapNode()).y == 14) {
            ChooseMapNode.chooseNextNode(BOSS_NODE);
        } else if ((AbstractDungeon.id.equals("TheEnding") && (AbstractDungeon.getCurrMapNode()).y == 2)) {
            ChooseMapNode.chooseNextNode(ENDING_BOSS_NODE);
        }
    }

    // AI part - which node to select?
    public static MapRoomNode selectNode(ArrayList<MapRoomNode> nodes) {
        return nodes.get(0);
    }

}