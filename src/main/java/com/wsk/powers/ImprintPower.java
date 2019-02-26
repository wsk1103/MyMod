package com.wsk.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.wsk.utils.CommonUtil;

/**
 * @author wsk1103
 * @date 2019/2/24
 * @desc 死亡印记
 */
public class ImprintPower extends AbstractPower {
    public static final String POWER_ID = "MyMod:ImprintPower";//能力的ID，判断有无能力、能力层数时填写该Id而不是类名。
    public static final String NAME = "死亡印记";//能力的名称。

//    public static final String DESCRIPITON = "攻击伤害增加印记的层数，当层数到达10层的时候，给予100点伤害";//不需要调用变量的文本描叙，例如钢笔尖（PenNibPower）。
    public static final String[] DESCRIPTIONS = {"造成伤害增加","点，当层数到达10层的时候，给予50点伤害"};//需要调用变量的文本描叙，例如力量（Strength）、敏捷（Dexterity）等。

    private static final String IMG = "powers/ritual.png";
    //以上两种文本描叙只需写一个，更新文本方法在第36行。
    private static PowerType POWER_TYPE = PowerType.DEBUFF;

    private AbstractCreature source;

    public ImprintPower(AbstractCreature owner, AbstractCreature source, int amount) {//参数：owner-能力施加对象、amount-施加能力层数。在cards的use里面用ApplyPowerAction调用进行传递。
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.img = new Texture(CommonUtil.getResourcePath(IMG));
        this.source = source;
        updateDescription();//调用该方法（第36行）的文本更新函数,更新一次文本描叙，不可缺少。
        this.type = POWER_TYPE;//能力种类，可以不填写，会默认为PowerType.BUFF。PowerType.BUFF不会被人工制品抵消，PowerType.DEBUFF会被人工制品抵消。
    }

    public void updateDescription() {
//        this.description = DESCRIPITON;//不需要调用变量的文本更新方式。
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        //this.description = (DESCRIPTIONS[0] + 变量1 + DESCRIPTIONS[1] + 变量2 + DESCRIPTIONS[3] + ······);需要调用变量的文本更新方式。
        //例： public static final String[] DESCRIPTIONS = {"在你回合开始时获得","点力量."};
        //   this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        //   通过该方式更新后的文本:在你回合开始时获得amount层力量.
        //   另外一提，除变量this.amount（能力层数对应的变量）外，其他变量被赋值后需要人为调用updateDescription函数进行文本更新。
    }

    //造成伤害时，返回伤害数值
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + this.amount;
        }
        return damage;
    }

    //触发时机：当回合开始时触发。
    public void atStartOfTurn() {
        if (this.amount >= 10) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flashWithoutSound();
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.source, 50, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

//    //触发时机：当玩家攻击时。info.可调用伤害信息。
//    public void onAttack(DamageInfo info, int damageAmount) {//参数： info-伤害信息，damageAmount-伤害数值，target-伤害目标
//        //伤害分为 一般 ，荆棘，失去HP
//        if (info.type == DamageInfo.DamageType.NORMAL) {
//
//        }
//    }
}
