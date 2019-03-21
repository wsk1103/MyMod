package com.wsk.cards.proficiency;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.wsk.patches.ArmsEnum;
import com.wsk.utils.CommonUtil;
import com.wsk.utils.ProficiencyUtil;

/**
 * @author wsk1103
 * @date 2019/3/20
 * @description 熟练度相关的卡牌
 */
public abstract class AbstractProficiencyCard extends CustomCard {

    /**
     * 兵器类型
     */
    public ArmsEnum arms;

    /**
     * 熟练度/技能点
     */
    public float proficiency = 0;

    public AbstractProficiencyCard(String id, String name, String imgUrl, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target, ArmsEnum arms) {
        super(id, name, imgUrl, cost, rawDescription, type, color, rarity, target);
        this.arms = arms;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        String extended = "技能点不足！";
        if (!"zhs".equals(CommonUtil.getLanguage())) {
            extended = "Not enough SkillPoint!";
        }
        this.cantUseMessage = extended;
        return ProficiencyUtil.isProficiency(this);
    }
}
