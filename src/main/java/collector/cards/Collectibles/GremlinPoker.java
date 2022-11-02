package collector.cards.Collectibles;

import basemod.helpers.CardModifierManager;
import collector.Interfaces.PerpetualCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.PlusDamageAndBlockCardMod;

public class GremlinPoker extends AbstractCollectibleCard implements PerpetualCard {
    public final static String ID = makeID("GremlinPoker");
    int perpetualbonus;

    public GremlinPoker() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        damage = baseDamage = 3;
        perpetualbonus = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CollectorDmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void upp() {
        CardModifierManager.addModifier(this, new PlusDamageAndBlockCardMod(perpetualbonus));
    }

    @Override
    public void PerpetualBonus() {
        baseDamage += perpetualbonus;
    }
}
