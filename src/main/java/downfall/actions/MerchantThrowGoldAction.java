package downfall.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import downfall.patches.GoldSoundPatch;
import downfall.vfx.ThrowGoldEffect;

public class MerchantThrowGoldAction extends AbstractGameAction {
    private final int goldAmount;
    private AbstractGameEffect effect;
    private final boolean waitForEffect;

    public MerchantThrowGoldAction(AbstractCreature target, AbstractCreature source, int goldAmount, boolean waitForEffect) {
        setValues(target, source, goldAmount);
        this.goldAmount = goldAmount;
        this.waitForEffect = waitForEffect;
    }

    @Override
    public void update() {
        if (effect == null) {
            GoldSoundPatch.active = false;
            CardCrawlGame.sound.play("GOLD_JINGLE");
            GoldSoundPatch.active = true;
            float stagger = 0;
            for (int i = 0; i < goldAmount; ++i) {
                effect = new ThrowGoldEffect(source, source.hb.cX, source.hb.cY, target.hb.cX, target.hb.cY, stagger);
                AbstractDungeon.effectList.add(effect);
                stagger += 0.15f;
            }
        }

        if (!waitForEffect || effect == null || effect.isDone) {
            isDone = true;
        }
    }
}