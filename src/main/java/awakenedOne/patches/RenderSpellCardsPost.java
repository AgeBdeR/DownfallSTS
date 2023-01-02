package awakenedOne.patches;

import awakenedOne.AwakenedOneChar;
import awakenedOne.ui.OrbitingSpells;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class RenderSpellCardsPost {
    public static void Postfix(AbstractPlayer obj, SpriteBatch sb) {
        if (AbstractDungeon.player.chosenClass.equals(AwakenedOneChar.Enums.AWAKENED_ONE)) {
            OrbitingSpells.postPlayerRender(sb);
        }
    }
}