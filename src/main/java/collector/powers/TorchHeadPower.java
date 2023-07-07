package collector.powers;

import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static collector.util.Wiz.applyToEnemyTop;
import static collector.util.Wiz.atb;

public class TorchHeadPower extends AbstractCollectorPower implements NonStackablePower {
    public static final String NAME = "TorchHead";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private int onAttackRandomDoom = 0;
    private int onAttackAOE = 0;
    private int onAttackBlock = 0;

    public TorchHeadPower(int type, int toAdd) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        switch (type) {
            case 0:
                onAttackRandomDoom += toAdd;
                break;
            case 1:
                onAttackAOE += toAdd;
                break;
            case 2:
                onAttackBlock += toAdd;
                break;
            default:
                onAttackRandomDoom += toAdd;
                System.out.println("Incorrect value for torchhead call power! Should be 0-2");
                break;
        }
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && TempHPField.tempHp.get(owner) > 0) {
            if (onAttackRandomDoom > 0 || onAttackAOE > 0 || onAttackBlock > 0) {
                flash();
            }

            if (onAttackRandomDoom > 0) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractMonster tar = AbstractDungeon.getRandomMonster();
                        applyToEnemyTop(tar, new DoomPower(tar, onAttackRandomDoom));
                    }
                });
            }

            if (onAttackAOE > 0) {
                addToBot(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(onAttackAOE, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }

            if (onAttackBlock > 0) {
                atb(new GainBlockAction(owner, onAttackBlock));
            }
        }
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        if (onAttackRandomDoom > 0) {
            sb.append(DESCRIPTIONS[1] + onAttackRandomDoom + DESCRIPTIONS[2]);
            if (onAttackAOE > 0 && onAttackRandomDoom > 0) {
                sb.append(DESCRIPTIONS[3]);
            } else if (onAttackAOE > 0 || onAttackRandomDoom > 0) {
                sb.append(DESCRIPTIONS[9]);
            } else {
                sb.append(LocalizedStrings.PERIOD);
            }
        }
        if (onAttackAOE > 0) {
            sb.append(DESCRIPTIONS[4] + onAttackAOE + DESCRIPTIONS[5]);
            if (onAttackRandomDoom > 0 && onAttackBlock > 0) {
                sb.append(DESCRIPTIONS[6]);
            } else if (onAttackBlock > 0) {
                sb.append(DESCRIPTIONS[9]);
            } else {
                sb.append(LocalizedStrings.PERIOD);
            }
        }
        if (onAttackBlock > 0) {
            sb.append(DESCRIPTIONS[7] + onAttackBlock + DESCRIPTIONS[8]);
        }
        description = sb.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if (power instanceof TorchHeadPower) {
            this.onAttackRandomDoom += ((TorchHeadPower) power).onAttackRandomDoom;
            this.onAttackAOE += ((TorchHeadPower) power).onAttackAOE;
            this.onAttackBlock += ((TorchHeadPower) power).onAttackBlock;
            updateDescription();
        }
        return true;
    }
}