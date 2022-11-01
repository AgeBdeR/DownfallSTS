package slimebound.ui;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.vfx.campfire.CampfireTokeEffect;
import slimebound.SlimeboundMod;


public class ScrapBonfireOption extends AbstractCampfireOption {
    public static final String[] DESCRIPTIONS;
    private static final RelicStrings relicStrings;

    static {
        relicStrings = CardCrawlGame.languagePack.getRelicStrings("Slimebound:ScrapOozeRelic");
        DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    }

    //private ArrayList<String> idleMessages;
    public ScrapBonfireOption(boolean active) {
        //this.idleMessages = new ArrayList();
        //this.idleMessages.add("~Treasure!");
        //this.idleMessages.add("~Feed ~me!");
        //this.idleMessages.add("~Give ~shiny!");
        //this.idleMessages.add("~More ~scrap!");
        this.label = DESCRIPTIONS[3];
        //this.description = "Remove a card from your deck. Increase the Scrap Ooze's damage by 2.";
        this.usable = active;
        if (active) {
            this.description = DESCRIPTIONS[1];
            this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/ui/scrapcampfire.png");

        } else {
            this.description = DESCRIPTIONS[2];
            this.img = ImageMaster.loadImage("slimeboundResources/SlimeboundImages/ui/scrapcampfiredisabled.png");
        }
    }

    @Override
    public void useOption() {
        if (this.usable) {
            SlimeboundMod.scrapping = true;
            AbstractDungeon.effectList.add(new CampfireTokeEffect());


        }
    }

    @Override
    public void update() {
        float hackScale = ReflectionHacks.getPrivate(this, AbstractCampfireOption.class, "scale");

        if (this.hb.hovered) {

            if (!this.hb.clickStarted) {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, Settings.scale));

            } else {
                ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));

            }
        } else {
            ReflectionHacks.setPrivate(this, AbstractCampfireOption.class, "scale", MathHelper.scaleLerpSnap(hackScale, 0.9F * Settings.scale));
        }
        super.update();
    }
}
