package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.RecyclingPower;


public class Recycling extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Recycling";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/recycling.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    private static int upgradedamount = 1;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Recycling() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        SlimeboundMod.loadJokeCardImage(this, "Recycling.png");


    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RecyclingPower(p, p, 1), 1));

    }

    public AbstractCard makeCopy() {
        return new Recycling();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            this.isInnate = true;
            //upgradeMagicNumber(1);


        }
    }
}

