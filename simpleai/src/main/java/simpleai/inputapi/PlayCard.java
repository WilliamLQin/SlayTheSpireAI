package simpleai.inputapi;

import java.util.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import simpleai.helpers.*;

public class PlayCard {

    public static ArrayList<CardQueueItem> playCardQueue = new ArrayList<>();

    public static void playCard(AbstractCard card, AbstractMonster target) {
        if (!AbstractDungeon.player.hand.contains(card)) {
            System.out.println("Player hand does not have " + card.name);
            return;
        }
        if (target != null) {
            if (!Helpers.isMonsterTargetable(target)) {
                System.out.println("Monster target " + target.name + " is not in combat!");
                return;
            }
        }

        String logMessage = "card " + card.name;
        if (target != null) {
            logMessage += " on target " + target.name;
        }

        if (!card.canUse(AbstractDungeon.player, target)) {
            System.out.println("Cannot use " + logMessage);
            return;
        }

        System.out.println("Playing " + logMessage);
        playCardQueue.add(new CardQueueItem(card, target));
    }

}