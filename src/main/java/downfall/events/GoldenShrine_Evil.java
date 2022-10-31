package downfall.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.downfallMod;

public class GoldenShrine_Evil extends AbstractImageEvent {
    public static final String ID = "downfall:SolShrine";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final int GOLD_AMT = 100;
    private static final int CURSE_GOLD_AMT = 275;
    private static final int A_2_GOLD_AMT = 50;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private static final String IGNORE;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Golden Shrine");
        NAME = CardCrawlGame.languagePack.getEventString(ID).NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
        IGNORE = DESCRIPTIONS[3];
    }

    private int goldAmt = 0;
    private CUR_SCREEN screen;

    public GoldenShrine_Evil() {
        super(NAME, DIALOG_1, (downfallMod.assetPath("images/events/soulShrine.png")));
        this.screen = CUR_SCREEN.INTRO;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldAmt = 50;
        } else {
            this.goldAmt = 100;
        }

        this.imageEventText.setDialogOption(OPTIONS[0] + this.goldAmt + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2], CardLibrary.getCopy("Regret"));
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }

    public void onEnterRoom() {
        CardCrawlGame.music.playTempBGM("SHRINE");
    }

    public void update() {
        super.update();
        if (!RoomEventDialog.waitForInput) {
            this.buttonEffect(this.roomEventText.getSelectedOption());
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        this.screen = CUR_SCREEN.COMPLETE;
                        logMetricGainGold(ID, "Pray", this.goldAmt);
                        this.imageEventText.updateBodyText(DIALOG_2);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldAmt));
                        AbstractDungeon.player.gainGold(this.goldAmt);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.screen = CUR_SCREEN.COMPLETE;
                        AbstractCard curse = new Regret();
                        logMetricGainGoldAndCard(ID, "Desecrate", curse, 275);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(275));
                        AbstractDungeon.player.gainGold(275);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        this.screen = CUR_SCREEN.COMPLETE;
                        logMetricIgnored(ID);
                        this.imageEventText.updateBodyText(IGNORE);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        return;
                }
            case COMPLETE:
                this.openMap();
        }

    }

    private enum CUR_SCREEN {
        INTRO,
        COMPLETE;

        CUR_SCREEN() {
        }
    }
}
