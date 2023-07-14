package com.rssl.phizic.common.type;

/**
 * User: Moshenko
 * Date: 26.10.13
 * Time: 11:18
 * ������� � ������������ � ������� ����
 */
public enum ErmbResourceType
{
    /**
     * ���� �������
     */
    ACCOUNT("account"),
    /**
     * ����� �������
     */
    CARD("card"),
    /**
     * ������
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
