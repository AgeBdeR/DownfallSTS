package awakenedOne.actions;

import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ConjureAction extends AbstractGameAction {
    private final AbstractGameAction followUpAction;
    public static ArrayList<AbstractCard> conjuredCards = new ArrayList();

    public static int conjuresThisCombat = 0;

    public ConjureAction(int amount) {
        this(amount, null);
    }

    public ConjureAction(int amount, AbstractGameAction action) {
        this.amount = amount;
        followUpAction = action;
    }

    @Override
    public void update() {
        conjuresThisCombat += 1;
        isDone = true;
        conjuredCards.clear();
        if (OrbitingSpells.spellCards.isEmpty()) {
            addToTop(new DrawCardAction(1));
            addToTop(new RefreshSpellsAction());
        } else {
            if (amount == 1 || OrbitingSpells.spellCards.size() == 1) {
                AbstractCard tar = Wiz.getRandomItem(OrbitingSpells.spellCards, AbstractDungeon.cardRandomRng).card.makeStatEquivalentCopy();
                conjuredCards.add(tar);
                endActionWithFollowUp();
                addToTop(new MakeTempCardInHandAction(tar));
                addToTop(new RemoveSpellCardAction(tar));
            } else {
                ArrayList<OrbitingSpells.CardRenderInfo> possCards = new ArrayList<>();
                possCards.addAll(OrbitingSpells.spellCards);
                ArrayList<OrbitingSpells.CardRenderInfo> availableCards = new ArrayList<>();
                while (availableCards.size() < amount && !possCards.isEmpty()) {
                    availableCards.add(possCards.remove(AbstractDungeon.cardRandomRng.random(possCards.size() - 1)));
                }
                ArrayList<AbstractCard> actualChoices = new ArrayList<>();
                availableCards.forEach(q -> actualChoices.add(q.card.makeStatEquivalentCopy()));
                addToTop(new SelectCardsCenteredAction(actualChoices, "Choose a Spell to add into your hand.", (cards) -> {
                    AbstractCard q = cards.get(0);
                    conjuredCards.add(q);
                    endActionWithFollowUp();
                    addToTop(new MakeTempCardInHandAction(q));
                    addToTop(new RemoveSpellCardAction(q));
                })); // TODO: Loc
            }
        }
    }

    private void endActionWithFollowUp() {
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }
    }
}