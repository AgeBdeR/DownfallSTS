package slimebound.patches;

/*
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.DownfallMetrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SlimeboundMetricsPatch {

    @SpirePatch(clz = DownfallMetrics.class, method = "sendPost", paramtypez = {String.class, String.class})
    public static class SendPostPatch {

        public static void Prefix(DownfallMetrics __instance, @ByRef String[] url, String fileName) {
            if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND) {
                url[0] = "http://slimemetrics.atwebpages.com/Metrics/";
            }
        }

    }

    @SpirePatch(clz = DeathScreen.class, method = "shouldUploadMetricData")
    public static class shouldUploadMetricData {

        public static boolean Postfix(boolean __returnValue) {
            if (AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND) {
                __returnValue = Settings.UPLOAD_DATA;
            }
            return __returnValue;
        }

    }

    @SpirePatch(clz = DownfallMetrics.class, method = "run")
    public static class RunPatch {

        public static void Postfix(DownfallMetrics __instance) {
            if (__instance.type == DownfallMetrics.MetricRequestType.UPLOAD_METRICS && AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND) {
                try {
                    Method m = DownfallMetrics.class.getDeclaredMethod("gatherAllDataAndSend", boolean.class, boolean.class, MonsterGroup.class);
                    m.setAccessible(true);
                    m.invoke(__instance, __instance.death, __instance.trueVictory, __instance.monsters);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

*/