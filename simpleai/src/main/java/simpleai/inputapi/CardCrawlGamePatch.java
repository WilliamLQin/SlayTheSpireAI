package simpleai.inputapi;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import simpleai.helpers.*;
import simpleai.combat.*;
import simpleai.*;

@SpirePatch(clz = CardCrawlGame.class, method = "update")
public class CardCrawlGamePatch {

    public static void Prefix(CardCrawlGame __instance) {
        if (DevInputActionSet.toggleDebug.isJustPressed()) {
            SimpleAI.enabled = true;
            System.out.println("Turning on auto AI");
        }
        if (DevInputActionSet.toggleInfo.isJustPressed()) {
            SimpleAI.enabled = false;
            System.out.println("Turning off auto AI");
        }
    }

}