package com.rssl.phizic.common.type;

/**
 * User: Moshenko
 * Date: 26.10.13
 * Time: 11:18
 * Ресурсы в фигурирующие в каналах ЕРМБ
 */
public enum ErmbResourceType
{
    /**
     * Счёт клиента
     */
    ACCOUNT("account"),
    /**
     * Карта клиента
     */
    CARD("card"),
    /**
     * Кредит
     */
    LOAN("loan");

    private final String value;

    ErmbResourceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErmbResourceType fromValue(String v) {
        for (ErmbResourceType c: ErmbResourceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
