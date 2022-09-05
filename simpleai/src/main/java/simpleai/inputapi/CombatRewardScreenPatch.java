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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import simpleai.helpers.*;
import simpleai.combat.*;
import simpleai.*;

@SpirePatch(clz = CombatRewardScreen.class, method = "update")
public class CombatRewardScreenPatch {

    public static void Prefix(CombatRewardScreen __instance) {
        if (SimpleAI.enabled) {
            for (RewardItem r : __instance.rewards) {
                System.out.println("Claiming reward " + r.text);
                r.isDone = true;
            }
        }
    }

}