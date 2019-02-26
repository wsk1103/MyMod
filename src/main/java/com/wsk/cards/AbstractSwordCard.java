package com.wsk.cards;

/**
 * @author wsk1103
 * @date 2019/2/25
 * @desc 剑
 */
public abstract class AbstractSwordCard extends AbstractArmsCard {
    public AbstractSwordCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }
}
