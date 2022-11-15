package champ.actions;

import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.actions.AbstractXAction;

public class LariatAction extends AbstractXAction {
    private final int boom;
    private final int boom2;

    public LariatAction(int x, int y) {
        boom = x;
        boom2 = y;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < amount; i++) {
            addToTop(new GainBlockAction(p, boom2));
        }
        this.isDone = true;
    }
}