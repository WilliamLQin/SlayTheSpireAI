package stsai.inputapi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.map.*;
import stsai.autoplayers.*;
import stsai.*;
import stsai.inputapi.*;
import com.badlogic.gdx.Gdx;

@SpirePatch(clz = DungeonMapScreen.class, method = "update")
public class DungeonMapScreenPatch {

    private static MapRoomNode tempCurrNode = null;

    // Need to skip one tick after the maps screen appears before proceeding to the next map node
    private static int waitCounter = 0;
    private static final int COUNTER_TICKS = 5; // Skip a few more just in case

    // For when the map scrolls
    private static float scrollWaitTimer = 5.0F;

    public static void Prefix(DungeonMapScreen __instance) {
        // if the map is opened to scroll twice in a row, this code doesn't work
        // but that shouldn't happen
        if (!__instance.dismissable) {
            if (scrollWaitTimer > 0.0F) {
                scrollWaitTimer -= Gdx.graphics.getDeltaTime();
                return;
            }
        } else {
            scrollWaitTimer = 5.0F;
        }

        if (SlayTheSpireAI.enabled) {
            MapAutoPlayer.autoplay();
        }

        MapRoomNode nextNode = ChooseMapNode.nextNode;
        MapRoomNode currNode = AbstractDungeon.currMapNode;
        if (nextNode != null && nextNode != currNode && nextNode != tempCurrNode) {
            if (waitCounter < COUNTER_TICKS) {
                waitCounter += 1;
            } else {
                waitCounter = 0;

                System.out.println("Clicking on node " + nextNode.toString());

                // "click" on the next node
                if (nextNode.y >= currNode.y + 2) {
                    System.out.println("Clicking on boss node");
                    InputHelper.mX = Math.round(AbstractDungeon.dungeonMapScreen.map.bossHb.cX);
                    InputHelper.mY = Math.round(AbstractDungeon.dungeonMapScreen.map.bossHb.cY);
                } else {
                    InputHelper.mX = Math.round(nextNode.hb.cX);
                    InputHelper.mY = Math.round(nextNode.hb.cY);
                }
                AbstractDungeon.dungeonMapScreen.clicked = true;
                InputHelper.justClickedLeft = true;

                // Prevent clicking more than once
                tempCurrNode = nextNode;
            }
        }
    }

}