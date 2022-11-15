package champ.cards;

import champ.ChampMod;
import champ.stances.AbstractChampStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SwordThrow extends AbstractChampCard {
    public final static String ID = makeID("SwordThrow");

    public SwordThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.OPENER);
       
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);

        berserkOpen();
        if (AbstractDungeon.player.stance instanceof AbstractChampStance) {
            ((AbstractChampStance) AbstractDungeon.player.stance).upgradeStance();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(3);
    }
}