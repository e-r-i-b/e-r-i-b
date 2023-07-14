package com.rssl.phizic.web.common.loanclaim;

/**
 * ��������� ��� ������������ ������� ��� ��������� ������ ������ ������ �������� ���������� ������������
 *
 * @author Balovtsev
 * @since 27.05.2014
 */
public enum ClientAddressRequestType
{
    /**
     * �����/�����
     */
    AREA,

    /**
     * �����
     */
    CITY,

    /**
     * ���������� �����
     */
    LOCALITY,

    /**
     * �����
     */
    STREET,

    /**
     * ���������� ��� ����� ��������
     */
    UNKNOWN;

    /**
     * ���������� ��� �������. ���� ��� ������������ ���������� UNKNOWN
     *
     * @param value ��������� ������������� ���� ������� �� �����
     * @return ClientAddressRequestType
     */
    public static ClientAddressRequestType getTypeByValue(final String value)
    {
        for (ClientAddressRequestType type : ClientAddressRequestType.values())
        {
            if (type.name().equals(value))
            {
                return type;
            }
        }

        return UNKNOWN;
    }
}
