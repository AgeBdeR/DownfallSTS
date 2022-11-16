package automaton.cards;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TinkerersToolbox extends AbstractBronzeCard {

    public final static String ID = makeID("TinkerersToolbox");

    //stupid intellij stuff skill, self, rare

    private float rotationTimer;
    private int previewIndex;
    private final ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public TinkerersToolbox() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        selfRetain = true;
        // this.tags.add(downfallMod.BANNEDFORSNECKO);
        cardsList.add(new Debug());
        cardsList.add(new Batch());
        cardsList.add(new Decompile());
        cardsList.add(new ByteShift());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsCenteredAction(cardsList, 1, masterUI.TEXT[8], (cards) -> addToTop(new MakeTempCardInHandAction(cards.get(0).makeCopy(), true))));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardsList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardsList.get(previewIndex);
                }
                if (previewIndex == cardsList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
}