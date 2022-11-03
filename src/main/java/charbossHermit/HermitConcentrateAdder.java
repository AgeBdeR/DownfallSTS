//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbossHermit;

import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HermitConcentrateAdder extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:HermitConcentrateAdder";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public HermitConcentrateAdder(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, HermitConcentrationPower.POWER_ID));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new HermitConcentrationPower(this.owner), 10));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
