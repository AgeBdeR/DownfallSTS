package downfall.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.powers.TotemInvulnerablePower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AbstractTotemMonster extends AbstractMonster {

    public static Float beamOffsetX = 25F * Settings.scale;
    public static Float beamOffsetY = -20F * Settings.scale;
    public static Float beamOffsetX2 = -35F * Settings.scale;
    public static Float beamOffsetY2 = -20F * Settings.scale;
    public Integer baseHP = 50;
    public Integer HPAscBuffed = 10;
    public Intent intentType = Intent.BUFF;
    public Color totemLivingColor;
    private Method refupdateIntent;
    private final boolean wasFalling = false;
    private final boolean spawnedAfterFirst3 = false;


    public AbstractTotemMonster(String name, String ID, String imgPath) {
        super(name, ID, 420, 0.0F, 60F, 200.0F, 200.0F, null, -50.0F, 160.0F);

        //ReflectionHacks.setPrivate(this, AbstractCreature.class,"HB_Y_OFFSET_DIST",-200F);


        try {
            refupdateIntent = AbstractMonster.class.getDeclaredMethod("updateIntent");


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        this.refupdateIntent.setAccessible(true);

        this.type = EnemyType.ELITE;
        this.dialogX = -100.0F * Settings.scale;
        this.dialogY = 10.0F * Settings.scale;
        this.drawY = AbstractDungeon.floorY - 50F * Settings.scale;
        this.drawX = Settings.WIDTH * 0.75F;

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(baseHP + HPAscBuffed);
        } else {
            this.setHp(baseHP);
        }

    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new TotemInvulnerablePower(this)));
        //this.hb.move(this.hb.cX, this.hb.cY - 90F * Settings.scale);
    }

    public void totemAttack() {

    }

    @Override
    public void changeState(String stateName) {
        if ("ATTACK".equals(stateName)) {
            this.state.setAnimation(0, "Attack", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    @Override
    public void healthBarUpdatedEvent() {
        if (this.currentHealth <= 0 && this.hasPower(TotemInvulnerablePower.POWER_ID)) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != this && m.currentHealth > 1 && m instanceof AbstractTotemMonster) { //can't die yet.
                    this.currentHealth = 1;
                    break;
                }
            }
        }
        super.healthBarUpdatedEvent();
    }


    @Override
    public void die() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m != this && m.hasPower(TotemInvulnerablePower.POWER_ID))
                addToTop(new InstantKillAction(m));
        }
        super.die();
    }


    @Override
    public void renderHealth(SpriteBatch sb) {
        this.hb.height = this.hb.height * 1.4F;
        super.renderHealth(sb);
        this.hb.height = this.hb.height / 1.4F;
    }

    public void takeTurn() {
        totemAttack();

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    public void update() {

        super.update();

        try {
            this.intentHb.move(this.hb.cX - 140F * Settings.scale, this.drawY + 170F * Settings.scale);
            refupdateIntent.invoke(this);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    protected void getMove(int num) {
        getUniqueTotemMove();
    }

    public void getUniqueTotemMove() {

    }


}