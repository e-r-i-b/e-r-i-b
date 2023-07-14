package com.rssl.phizic.gate;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Шлюз
 */
public interface Gate
{
    /**
     * @return фабрка для создания объектов гейта
     */
    GateFactory getFactory();

    /**
     * Этот метод вызывается первым до обращения к каким либо иным методам
     */
    void initialize() throws GateException;
}
