package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.vfx.MegaSpeechBubble;
import downfall.downfallMod;
import downfall.powers.NextTurnGainSlimeCrush;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

public class PrepareCrushBossCard extends AbstractExpansionCard {
    public final static String ID = makeID("PrepareCrush");


    private static final int BLOCK = 10;
    private static final int UPGRADE_BLOCK = 5;
    private static final int MAGIC = 3;

    public PrepareCrushBossCard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_slime.png", "expansioncontentResources/images/1024/bg_boss_slime.png");

        tags.add(downfallMod.STUDY_SLIMEBOSS);
        tags.add(downfallMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        cardsToPreview = new SlimeCrush();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new MegaSpeechBubble(p.hb.cX, p.hb.cY, 1.0F, SlimeBoss.DIALOG[0], true));
        atb(new ShakeScreenAction(0.3F, ScreenShake.ShakeDur.MED, ScreenShake.ShakeIntensity.LOW));

        atb(new GainBlockAction(p, p, this.block));
        atb(new ApplyPowerAction(p, p, new EnergizedPower(p, magicNumber), magicNumber));
        atb(new ApplyPowerAction(p, p, new NextTurnGainSlimeCrush(p, p, 1, false), 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }

}
