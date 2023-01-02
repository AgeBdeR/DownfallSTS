package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.powers.InResponsePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class InResponse extends AbstractAwakenedCard {
    public final static String ID = makeID(InResponse.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 3, 1

    public InResponse() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseSecondMagic = secondMagic = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ConjureAction(true));
        applyToSelf(new InResponsePower(secondMagic));
    }

    public void upp() {
        upgradeSecondMagic(2);
    }
}