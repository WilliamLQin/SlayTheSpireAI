package stsai.helpers;

import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;

public class Helpers {

    public static boolean hasPower(AbstractCreature creature, String targetID) {
        for (AbstractPower p : creature.powers) {
            if (p.ID.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCardQueued(AbstractCard card) {
        for (CardQueueItem item : AbstractDungeon.actionManager.cardQueue) {
            if (item.card == card) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMonsterTargetable(AbstractMonster target) {
        if (target.isDying || target.isEscaping || target.currentHealth <= 0) {
            return false;
        }
        return true;
    }

}