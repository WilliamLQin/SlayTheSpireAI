package stsai.autoplayers;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.watcher.*;

import java.util.ArrayList;

import stsai.helpers.*;
import stsai.inputapi.*;
import stsai.*;

public class CombatAutoPlayer {

    public static void autoplay() {
        if (!SlayTheSpireAI.enabled
                || AbstractDungeon.actionManager.phase != GameActionManager.Phase.WAITING_ON_USER
                || !AbstractDungeon.actionManager.actions.isEmpty()
                || !AbstractDungeon.actionManager.preTurnActions.isEmpty()
                || !AbstractDungeon.actionManager.cardQueue.isEmpty()
                || !AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            return;
        }

        if ((AbstractDungeon.getCurrRoom()).monsters.monsters.isEmpty()) {
            System.out.println("No monsters in combat!");
            return;
        }

        // end turn if playTurn yields no action
        if (!playTurn()) {
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());
        }
    }

    // AI - what to do next?
    // return true if action taken, return false if no actions taken
    public static boolean playTurn() {
        ArrayList<AbstractMonster> monsters = (AbstractDungeon.getCurrRoom()).monsters.monsters;

        // play the first usable card on the first targetable monster
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.target == AbstractCard.CardTarget.ENEMY
                    || card.target == AbstractCard.CardTarget.SELF_AND_ENEMY) {

                for (AbstractMonster monster : monsters) {
                    if (Helpers.isMonsterTargetable(monster) && card.canUse(AbstractDungeon.player, monster)) {
                        PlayCard.playCard(card, monster);
                        return true;
                    }
                }

            } else {
                if (card.canUse(AbstractDungeon.player, null)) {
                    PlayCard.playCard(card, null);
                    return true;
                }
            }
        }

        return false;
    }

}