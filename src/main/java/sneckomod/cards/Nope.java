package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Snecko;
import sneckomod.SneckoMod;
import sneckomod.actions.NopeAction;

public class Nope extends AbstractSneckoCard {

    public final static String ID = makeID("Nope");

    //stupid intellij stuff SKILL, SELF, COMMON

    public Nope() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
        SneckoMod.loadJokeCardImage(this, "sneckomodResources/images/betacards/Nope.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new NopeAction(upgraded));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}