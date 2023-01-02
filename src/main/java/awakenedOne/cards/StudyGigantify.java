package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.cards.tokens.spells.GreaterGigantify;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;
import static awakenedOne.util.Wiz.atb;

public class StudyGigantify extends AbstractAwakenedCard {
    public final static String ID = makeID(StudyGigantify.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 1, 1

    public StudyGigantify() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new GreaterGigantify();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new DexterityPower(p, magicNumber));
        atb(new AddSpellCardAction(new GreaterGigantify()));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}