package charbossHermit.Hermit;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.cards.Showdown;

public class EnShowdown extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Showdown";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Showdown.ID);

    public EnShowdown() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/showdown.png", 2, cardStrings.DESCRIPTION, CardType.ATTACK, downfallMod.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 8;
        //baseMagicNumber = magicNumber = 2;
        //  this.isMultiDamage = true;
        selfRetain = true;
        exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        //  this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void onSpecificTrigger() {
        for (AbstractCard q : AbstractCharBoss.boss.hand.group) {
            if (q instanceof EnFreeStrikeHermit) {
                q.setCostForTurn(1);
                q.isCostModified = false;
                q.isCostModifiedForTurn = false;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnShowdown();
    }
}
