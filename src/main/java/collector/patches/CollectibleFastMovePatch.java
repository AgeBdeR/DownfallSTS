package collector.patches;

import basemod.ReflectionHacks;
import collector.CollectorCollection;
import collector.cards.Collectibles.AbstractCollectibleCard;
import collector.cards.Collectibles.SentryCore;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

@SpirePatch(
        clz = FastCardObtainEffect.class,
        method = "update"
)
public class CollectibleFastMovePatch {
    public static void Postfix(FastCardObtainEffect __instance) {
        AbstractCard q = ReflectionHacks.getPrivate(__instance, FastCardObtainEffect.class, "card");
        if (q instanceof AbstractCollectibleCard && __instance.duration < 0.0F) {
            AbstractDungeon.player.masterDeck.removeCard(q);
            CollectorCollection.collection.addToBottom(q);
            if (q instanceof SentryCore && AbstractDungeon.player.maxOrbs < 3) {
                AbstractDungeon.player.maxOrbs += 3;
            }
        }
    }
}
