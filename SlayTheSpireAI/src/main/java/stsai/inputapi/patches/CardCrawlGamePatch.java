package stsai.inputapi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.core.*;
import stsai.inputapi.*;
import stsai.*;

@SpirePatch(clz = CardCrawlGame.class, method = "update")
public class CardCrawlGamePatch {

    public static void Prefix(CardCrawlGame __instance) {
        if (DevInputActionSet.toggleDebug.isJustPressed()) {
            SlayTheSpireAI.enabled = true;
            System.out.println("Turning on auto AI");
        }
        if (DevInputActionSet.toggleInfo.isJustPressed()) {
            SlayTheSpireAI.enabled = false;
            System.out.println("Turning off auto AI");
        }
        if (DevInputActionSet.gainGold.isJustPressed()) {
            InputHelper.mX = Math.round(ChooseMapNode.nextNode.hb.cX);
            InputHelper.mY = Math.round(ChooseMapNode.nextNode.hb.cY);
        }
    }

    public static void Postfix(CardCrawlGame __instance) {
        if (SlayTheSpireAI.enabled) {
            CInputHelper.updateLast();
        }
    }

}