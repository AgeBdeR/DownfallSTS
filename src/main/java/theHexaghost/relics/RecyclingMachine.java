package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class RecyclingMachine extends CustomRelic {

    public static final String ID = HexaMod.makeID("RecyclingMachine");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("RecyclingMachine.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("RecyclingMachine.png"));

    public boolean activated = false;

    public RecyclingMachine() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        activated = false;
        beginLongPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.isEthereal && card.type != AbstractCard.CardType.STATUS &&
                card.type != AbstractCard.CardType.CURSE && !activated) {
            flash();
            activated = true;
            stopPulse();
            AbstractCard copy = card.makeCopy();
            copy.freeToPlayOnce = true;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copy));
        }
    }
}
