package charbosses.bosses;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.bosses.Silent.NewAge.ArchetypeAct1ShivsNewAge;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;

//Implementation taken from MintySpire
@SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
public class HighlightCards {
    private static Texture shivTexture = new Texture(Gdx.files.internal("downfallResources/images/powers/infinite_blades_marker.png"));
    private static TextureAtlas.AtlasRegion shivRegion = new TextureAtlas.AtlasRegion(shivTexture, 0, 0, shivTexture.getWidth(), shivTexture.getHeight());
    private static Texture biasTexture = new Texture(Gdx.files.internal("downfallResources/images/powers/inverse_bias_marker.png"));
    private static TextureAtlas.AtlasRegion biasRegion = new TextureAtlas.AtlasRegion(biasTexture, 0, 0, biasTexture.getWidth(), biasTexture.getHeight());

    //TODO Work out an elegant way for the glow text to be slightly larger than the original, while maintaining the same origin
    @SpirePostfixPatch
    public static void patch(AbstractCard c, SpriteBatch sb) {
        if (zeroCostChecker(c))
            renderIcon(c, sb, shivRegion);
        else if (powerChecker(c))
            renderIcon(c, sb, biasRegion);
    }

    private static void renderIcon(AbstractCard c, SpriteBatch sb, TextureAtlas.AtlasRegion region) {
        sb.setColor(Color.WHITE);
        renderHelper(sb, region, c.current_x, c.current_y, c);
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, (MathUtils.cosDeg((float) (System.currentTimeMillis() / 5L % 360L)) + 1.25F) / 3.0F));
        renderHelper(sb, region, c.current_x, c.current_y, c);
        sb.setBlendFunction(770, 771);
        sb.setColor(Color.WHITE);
    }


    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, Color.class})
    public static class CardGlowPatch {
        @SpirePostfixPatch
        public static void patch(CardGlowBorder __instance, AbstractCard c, Color col, @ByRef Color[] ___color) {
            if(zeroCostChecker(c) || powerChecker(c)) {
                ___color[0] = Color.RED.cpy();
            }
        }
    }

    //code to render an image on top of all relevant cards taken from Jedi's Ranger
    private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, float drawX, float drawY, AbstractCard C) {
        sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, C.drawScale * Settings.scale, C.drawScale * Settings.scale, C.angle);
    }

    //Overly complicated validation method to check when to draw the double card effect
    private static boolean zeroCostChecker(AbstractCard c) {
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { //This should stop the DoubleImage from rendering if the player has Echo stacks remaining in the card selection screen
            return (AbstractCharBoss.boss != null && AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct1ShivsNewAge && c.cost == 0 && !(c instanceof AbstractBossCard));
        }
        return false;
    }

    private static boolean powerChecker(AbstractCard c) {
        if (AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) { //This should stop the DoubleImage from rendering if the player has Echo stacks remaining in the card selection screen
            return (AbstractCharBoss.boss != null && AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct3OrbsNewAge && c.type == AbstractCard.CardType.POWER && !(c instanceof AbstractBossCard));
        }
        return false;
    }
}