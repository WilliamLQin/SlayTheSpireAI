package stsai.inputapi;

import java.util.*;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.map.*;
import com.megacrit.cardcrawl.core.*;

public class ChooseMapNode {

    public static MapRoomNode nextNode = null;

    public static void chooseNextNode(MapRoomNode node) {
        if (node == nextNode) {
            return;
        }

        if (isNodeConnectedToCurrentMapNode(node) || (!AbstractDungeon.firstRoomChosen && node.y == 0)) {
            System.out.println("Selected next node: " + node.toString());
            nextNode = node;
        } else {
            System.out.println("Invalid next node: " + node.toString());
        }
    }

    public static boolean isNodeConnectedToCurrentMapNode(MapRoomNode node) {
        return isNodeConnectedToCurrentMapNode(node, false);
    }

    public static boolean isNodeConnectedToCurrentMapNode(MapRoomNode node, boolean excludeWinged) {
        return AbstractDungeon.getCurrMapNode().isConnectedTo(node)
                || (AbstractDungeon.getCurrMapNode().wingedIsConnectedTo(node) && !excludeWinged);
    }

    public static ArrayList<MapRoomNode> getAvailableNodes() {
        return getAvailableNodes(false);
    }

    public static ArrayList<MapRoomNode> getAvailableNodes(boolean excludeWinged) {
        ArrayList<MapRoomNode> ret = new ArrayList<>();
        ArrayList<ArrayList<MapRoomNode>> nodesByRow = CardCrawlGame.dungeon.getMap();

        for (ArrayList<MapRoomNode> rows : nodesByRow) {
            for (MapRoomNode node : rows) {
                if (node.hasEdges() && isNodeConnectedToCurrentMapNode(node, excludeWinged)) {
                    ret.add(node);
                }
            }
        }

        if (ret.isEmpty() && !AbstractDungeon.firstRoomChosen) {
            for (MapRoomNode firstNode : nodesByRow.get(0)) {
                if (firstNode.hasEdges()) {
                    ret.add(firstNode);
                }
            }
        }

        return ret;
    }

}