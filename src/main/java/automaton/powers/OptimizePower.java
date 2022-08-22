package automaton.powers;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OptimizePower extends AbstractAutomatonPower implements OnAddToFuncPower {
    public static final String NAME = "Optimize";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public OptimizePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }

    @Override
    public void receiveAddToFunc(AbstractCard addition) {
        for (int i = 0; i < amount; i++) {
            ((AbstractBronzeCard)addition).fineTune(false, true);
        }
    }
}
