package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.vfx.MyOrb;

public class GreenFlameAction extends AbstractGameAction {

    private final MyOrb orb;

    public GreenFlameAction(MyOrb orb) {
        this.orb = orb;
    }

    public void update() {
        isDone = true;
        orb.charge();
    }
}
