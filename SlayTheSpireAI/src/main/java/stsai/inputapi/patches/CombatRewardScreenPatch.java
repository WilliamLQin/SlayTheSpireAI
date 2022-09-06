package stsai.inputapi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.rewards.*;
import stsai.*;

@SpirePatch(clz = CombatRewardScreen.class, method = "update")
public class CombatRewardScreenPatch {

    public static void Prefix(CombatRewardScreen __instance) {
        if (SlayTheSpireAI.enabled) {
            for (RewardItem r : __instance.rewards) {
                System.out.println("Claiming reward " + r.text);
                r.isDone = true;
            }

            if (__instance.hasTakenAll) {
                System.out.println("All rewards claimed, proceeding.");
                CInputActionSet.proceed.justPressed = true;
            }
        }
    }

}