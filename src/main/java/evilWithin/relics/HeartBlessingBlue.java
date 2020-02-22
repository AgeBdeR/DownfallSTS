package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import evilWithin.EvilWithinMod;

public class HeartBlessingBlue extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("HeartBlessingBlue");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/HeartBlessingBlue.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/HeartBlessingBlue.png"));

    public HeartBlessingBlue() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(10, true);
    }


}
