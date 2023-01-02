package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class SpellMastery extends AbstractAwakenedCard {
    public final static String ID = makeID(SpellMastery.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , , 

    public SpellMastery() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard q : cards) {
                AbstractCard q2 = q.makeStatEquivalentCopy();
                q2.updateCost(-99);
                for (int i = 0; i < 3; i++) {
                    att(new AddSpellCardAction(q2));
                }
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}