package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FaceOfCleric;
import downfall.downfallMod;

public class CBR_MaskCleric extends AbstractCharbossRelic {
    public static final String ID = "CBRFaceOfCleric";
    private int HP = 0;

    public CBR_MaskCleric() {
        super(new FaceOfCleric());
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + this.HP + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[1];
    }

    public void onEquip() {
        int random = AbstractDungeon.cardRng.random(6, 8);
        this.HP = random * AbstractDungeon.actNum;
        this.owner.increaseMaxHp(this.HP, true);

        this.description = getUpdatedDescription();
        refreshDescription();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_MaskCleric();
    }
}
