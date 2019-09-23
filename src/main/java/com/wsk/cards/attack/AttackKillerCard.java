package com.wsk.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.wsk.actions.ActionUtil;
import com.wsk.patches.AbstractCardEnum;
import com.wsk.powers.base.VictoryPower;
import com.wsk.utils.CommonUtil;

/**
 * @author wsk1103
 * @date 2019/2/28
 * @desc 一句话说明
 */
public class AttackKillerCard extends CustomCard {
    public static final String ID = "LagranYue:AttackKillerCard";
    private static final String NAME /*= "来自WSK的攻击"*/;

    private static final String DESCRIPTION /*= "造成 !D! 点伤害。"*/;
    private static final String UPGRADED_DESCRIPTION;
    private static final CardStrings cardStrings;
    private static final String IMG = "cards/AttackKillerCard.png";
    //例：img/cards/claw/attack/BloodSuckingClaw_Orange.png  详细情况请根据自己项目的路径布置进行填写。

    private static final int COST = 1;

    public AttackKillerCard() {
        super(ID, NAME, CommonUtil.getResourcePath(IMG), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.LagranYue,
                CardRarity.UNCOMMON, CardTarget.ENEMY);
        //上一行为继承basemod的CustomCard类里的构造方法。五个参数（ID、NAME、IMG、COST、DESCRIPTION）为上方已声明出的变量，如果不在上方声明，可以在此处对应位置直接填写内容。
        this.baseDamage = 9;//基础伤害值，除升级以外无任何其他加成. this.damage为有力量、钢笔尖等加成的伤害值.
        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = false;
        this.exhaust = true;
        this.isInnate = false;//固有属性，false不固有，true固有。可在该类里调用改变。不固有就可以赋值为false或者删掉这一行
    }

    public AbstractCard makeCopy() {
        return new AttackKillerCard();
    }//用于显示在卡牌一览里。同时也是诸多卡牌复制效果所需要调用的基本方法，用来获得一张该卡的原始模板修改后加入手牌/抽牌堆/弃牌堆/牌组。

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();//升级名称。必带。
            this.upgradeDamage(4);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }//注：该部分为升级的效果部分，此处展示的代码为只能升级一次的代码，如需无限升级，卡牌代码有些许不同但不便于例出，请自行查看灼热攻击源码。

    //以上为卡牌的必备内容，不可缺少。
    public void use(AbstractPlayer p, AbstractMonster m) {//局部变量：p-玩家，m敌人。
        //注：以下注释里：执行者 指动作效果生效的目标。给予者 指产生动作效果的来源。
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        int num = 0;
        if (p.hasPower(VictoryPower.POWER_ID)) {
            num = p.getPower(VictoryPower.POWER_ID).amount;
            if (num <= 0) {
                num = 0;
            }
        }
        if (upgraded) {
            if (num == 0) {
                ActionUtil.victoryPower(p, this.magicNumber);
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
//                        new VictoryPower(p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.POISON));
            } else {
                ActionUtil.victoryPower(p, num);
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
//                        new VictoryPower(p, num), num, true, AbstractGameAction.AttackEffect.POISON));
            }
        } else {
            if (num > 0) {
                ActionUtil.victoryPower(p, num);
//                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
//                        new VictoryPower(p, num), num, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }//注：卡牌效果的diy区。

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

}
