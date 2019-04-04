package com.wsk.relics.share;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.wsk.reward.CombatRewardScreenPatch;
import com.wsk.utils.CommonUtil;

/**
 * @author wsk1103
 * @date 2019/3/20
 * @description 描述
 */
public class RareGoldArmorRelic extends CustomRelic {

    public static final String ID = "LagranYue:RareGoldArmorRelic";
public static final String IMG = "relics/r19.png";
    public static final String OUTLINE = "relics/r20.png";

    private boolean more = true;

    public RareGoldArmorRelic() {
        super(ID, new Texture(CommonUtil.getResourcePath(IMG)), new Texture(CommonUtil.getResourcePath(OUTLINE)), RelicTier.RARE, LandingSound.FLAT);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RareGoldArmorRelic();
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {

        if (more && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            counter += damageAmount;
        }
        if (counter >= 40) {
            CombatRewardScreenPatch.rareGoldArmor = true;
            more = false;
        }
    }

    @Override
    public void atBattleStart() {
        CombatRewardScreenPatch.rareGoldArmor = true;
    }

    @Override
    public void atTurnStart() {
        if (counter >= 40) {
            more = false;
        } else {
            flash();
            more = true;
            counter = 0;
        }
    }
}