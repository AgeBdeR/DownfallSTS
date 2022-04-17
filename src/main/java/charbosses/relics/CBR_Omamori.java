package charbosses.relics;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import downfall.downfallMod;

public class CBR_Omamori extends AbstractCharbossRelic {
    public static final String ID = "Omamori";
    private String addedDesc = "";

    @Override
    public String getUpdatedDescription() {

        return this.DESCRIPTIONS[0] + this.addedDesc;
    }


    public CBR_Omamori() {
        super(new Omamori());
        this.counter = 0;
        this.usedUp();
        this.description = getUpdatedDescription();
        refreshDescription();
    }

    public void use(String cardName) {
        this.flash();
        --this.counter;
        this.addedDesc = this.addedDesc + CardCrawlGame.languagePack.getRelicStrings(downfallMod.makeID(ID)).DESCRIPTIONS[0] + cardName + ".";

        if (this.counter == 0) {
            this.usedUp();
        }

        this.description = getUpdatedDescription();
        refreshDescription();

    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Omamori();
    }
}
