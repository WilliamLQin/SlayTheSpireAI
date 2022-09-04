package combatai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import helpers.*;
import inputapi.*;

public class CombatAutoPlayer {

    public static boolean enabled = false;

    public static void autoPlayCombat() {
        ArrayList<AbstractMonster> monsters = (AbstractDungeon.getCurrRoom()).monsters.monsters;
        if (monsters.isEmpty()) {
            System.out.println("No monsters in combat!");
            return;
        }

        if (AbstractDungeon.actionManager.phase != GameActionManager.Phase.WAITING_ON_USER
                || !AbstractDungeon.actionManager.cardQueue.isEmpty()) {
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
        if (AbstractDungeon.overlayMenu.endTurnButton.enabled) {
            AbstractDungeon.overlayMenu.endTurnButton.disable(true);
        }
    }

}