package simpleai.combat;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import simpleai.helpers.*;
import simpleai.inputapi.*;
import simpleai.*;

public class CombatAutoPlayer {

    public static void autoPlayCombat() {
        if (!SimpleAI.enabled
                || AbstractDungeon.actionManager.phase != GameActionManager.Phase.WAITING_ON_USER
                || !AbstractDungeon.actionManager.actions.isEmpty()
                || !AbstractDungeon.actionManager.preTurnActions.isEmpty()
                || !AbstractDungeon.actionManager.cardQueue.isEmpty()
                || !AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            return;
        }

        ArrayList<AbstractMonster> monsters = (AbstractDungeon.getCurrRoom()).monsters.monsters;
        if (monsters.isEmpty()) {
            System.out.println("No monsters in combat!");
            return;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.target == AbstractCard.CardTarget.ENEMY
                    || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY) {

                for (AbstractMonster monster : monsters) {
                    if (Helpers.isMonsterTargetable(monster) && card.canUse(AbstractDungeon.player, monster)) {
                        PlayCard.playCard(card, monster);
                        return;
                    }
                }

            } else {
                if (card.canUse(AbstractDungeon.player, null)) {
                    PlayCard.playCard(card, null);
                    return;
                }
            }
        }

        // no cards played, end turn
        AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
    }

}