package inputapi;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpirePatch(clz = AbstractPlayer.class, method = "updateInput")
public class AbstractPlayerInputPatch {

    private static boolean hasPower(AbstractPlayer __instance, String targetID) {
        for (AbstractPower p : __instance.powers) {
            if (p.ID.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
        AbstractCard cardFromHotkey = InputHelper.getCardSelectedByHotkey(__instance.hand);
        boolean isCardQueued = false;
        for (CardQueueItem item : AbstractDungeon.actionManager.cardQueue) {
            if (item.card == cardFromHotkey) {
                isCardQueued = true;
            }
        }
        if (cardFromHotkey != null && !isCardQueued) {
            System.out.println("Playing card: " + cardFromHotkey.name + " (id: " + cardFromHotkey.cardID + ")");

            AbstractMonster targetMonster = null;

            if (cardFromHotkey.target == AbstractCard.CardTarget.ENEMY
                    || cardFromHotkey.target == AbstractCard.CardTarget.SELF_AND_ENEMY) { // self and enemy isn't used afaik

                List<AbstractMonster> eligibleMonsters = new ArrayList<>();

                for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!m.isDying && !m.isEscaping && m.currentHealth > 0) {
                        if (cardFromHotkey.canUse(__instance, m)) {
                            eligibleMonsters.add(m);
                        }
                    }
                }

                // rand.nextInt(0) will crash
                if (eligibleMonsters.size() > 0) {
                    Random rand = new Random();
                    targetMonster = eligibleMonsters.get(rand.nextInt(eligibleMonsters.size()));
                }
            }

            // in case no valid monsters or can't use
            if (cardFromHotkey.canUse(__instance, targetMonster)) {
                if (hasPower(__instance, "Surrounded") && targetMonster != null) {
                    __instance.flipHorizontal = (targetMonster.drawX < __instance.drawX);
                }
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardFromHotkey, targetMonster));

                return SpireReturn.Return();
            }
        }

        return SpireReturn.Continue();
    }

}