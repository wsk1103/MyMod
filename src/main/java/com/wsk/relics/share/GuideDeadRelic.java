package com.wsk.relics.share;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.wsk.actions.ActionUtil;
import com.wsk.utils.CommonUtil;

/**
 * @author wsk1103
 * @date 2019/3/20
 * @description 描述
 */
public class GuideDeadRelic extends CustomRelic {

    public static final String ID = "LagranYue:GuideDeadRelic";
public static final String IMG = "relics/r13.png";
    public static final String OUTLINE = "relics/r14.png";

    public GuideDeadRelic() {
        super(ID, new Texture(CommonUtil.getResourcePath(IMG)), new Texture(CommonUtil.getResourcePath(OUTLINE)), RelicTier.RARE, LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GuideDeadRelic();
    }

    @Override
    public void atBattleStart() {
        ActionUtil.barricadePower(AbstractDungeon.player);
    }

    @Override
    public void onBlockBroken(AbstractCreature m) {

        ActionUtil.loseHP(AbstractDungeon.player, (int) (AbstractDungeon.player.maxHealth * 0.05));
    }
}
