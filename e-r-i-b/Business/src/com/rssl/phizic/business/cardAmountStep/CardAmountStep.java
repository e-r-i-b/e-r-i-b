package com.rssl.phizic.business.cardAmountStep;

import com.rssl.phizic.common.types.Money;

/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 10:38:41
 * Лимиты сумм для предодобренных предложений.
 * Используются для выбора сотрудниками банка.
 */
public class CardAmountStep {
    /*
    * Идентификатор
    */
    private Long id;
    /**
     * Значение
     */
    private Money value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }
}
