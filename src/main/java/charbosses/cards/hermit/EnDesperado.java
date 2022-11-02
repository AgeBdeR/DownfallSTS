package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import downfall.downfallMod;
import hermit.cards.Desperado;
import hermit.patches.EnumPatch;

public class EnDesperado extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Desperado";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Desperado.ID);

    public EnDesperado() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/desperado.png", 1, cardStrings.DESCRIPTION, CardType.ATTACK, downfallMod.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 14;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        addToBot(new ApplyPowerAction(m, m, new FrailPower(m, magicNumber, true), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnDesperado();
    }
}
