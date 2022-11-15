package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static champ.ChampMod.loadJokeCardImage;

public class Challenge extends AbstractChampCard {

    public final static String ID = makeID("Challenge");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public Challenge() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;

       
        loadJokeCardImage(this, "Challenge.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (m != null) {
            atb(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        if (m.hasPower(StrengthPower.POWER_ID) && m.getPower(StrengthPower.POWER_ID).amount > 0 && !this.purgeOnUse) {
            blck();
            if (m != null) {
                atb(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
            }
            dmg(m, AbstractGameAction.AttackEffect.NONE);
        }
    }

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void triggerOnGlowCheck() {
        boolean hasStr = false;
        if (isInCombat()) {
            for (AbstractMonster m : monsterList()) {
                if (m.hasPower(StrengthPower.POWER_ID)) {
                    hasStr = true;
                    break;
                }
            }

            glowColor = (hasStr) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}