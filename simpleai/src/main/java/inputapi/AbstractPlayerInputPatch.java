package inputapi;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.input.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import helpers.*;
import combatai.*;

@SpirePatch(clz = AbstractPlayer.class, method = "updateInput")
public class AbstractPlayerInputPatch {

    public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
        if (DevInputActionSet.toggleDebug.isJustPressed()) {
            CombatAutoPlayer.enabled = true;
        }
        if (DevInputActionSet.toggleInfo.isJustPressed()) {
            CombatAutoPlayer.enabled = false;
        }

        if (CombatAutoPlayer.enabled) {
            CombatAutoPlayer.autoPlayCombat();
        }

        if (!PlayCard.playCardQueue.isEmpty()) {
            CardQueueItem item = PlayCard.playCardQueue.get(0);
            PlayCard.playCardQueue.remove(0);

            AbstractCard card = item.card;
            if (card != null && !Helpers.isCardQueued(card) && __instance.hand.contains(card)) {
                System.out.println("Playing card: " + card.name + " (id: " + card.cardID + ")");

                AbstractMonster targetMonster = item.monster;

                // in case no valid monsters or can't use
                if (card.canUse(__instance, targetMonster)) {
                    if (Helpers.hasPower(__instance, "Surrounded") && targetMonster != null) {
                        __instance.flipHorizontal = (targetMonster.drawX < __instance.drawX);
                    }
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, targetMonster));

                    return SpireReturn.Return();
                }
            }
        }

        return SpireReturn.Continue();
    }

}