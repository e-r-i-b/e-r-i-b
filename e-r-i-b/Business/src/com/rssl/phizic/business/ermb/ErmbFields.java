package com.rssl.phizic.business.ermb;

/**
 * User: Moshenko
 * Date: 11.10.13
 * Time: 14:32
 * ��������� �� ����.
 */
public enum  ErmbFields
{
    //�������� ����� ������� ����
    SUPPRESS_ADVERTISING("������� ������� ��������� ��������."),
    CLIENT_PHONES("�������� �������."),
    INTERNET_CLIENT_SERVICE("��������, ��������� � ��������-����������."),
    MOBILE_CLIENT_SERVICE("��������, ��������� � ��������� ����������."),
    ATM_CLIENT_SERVICE("��������, ��������� � ����������� ����������������."),
    SMS_CLIENT_SERVICE("��������, ��������� � ���-������."),
    INFORM_RESOURCES("�������� �������, �� ������� ������ ������������ ����������."),
    SERVICE_STATUS("��������� ������"),
    CLIENT_BLOCKED("���������� �����������"),
    PAYMENT_BLOCKED("����������� �� �� ������"),
    START_SERVICE_DATE("���� ������� ����������� ������."),
    END_SERVICE_DATE("���� ��������� �������������� ������."),
    TARIFF("�������� ���� ������."),
    QUICK_SERVICES("������� ��������� ��� ������� ����������� ������ ������ �������� � ��������� �� ������ ��������."),
    ACTIVE_PHONE("������� �������, �� ������� ������ ������������ ����������."),
    CHARHE_CARD("����� ������������ ����� ��� �������� ����������� �����."),
    NEW_PRODUCT_NOTIFICATION("������ ����������� ����������� �� ����� ������������ ��������/."),
	NEW_PRODUCT_SHOW_IN_SMS("������ ���������� �������� � ���-������"),
    INFORM_PERION("��������� ���������, � ������� ��������� ���������� �����������."),
    INFORM_PERION_BEGIN("����� ������ ������� �����������"),
    INFORM_PERION_END("����� ��������� ������� �����������"),
    INFORM_PERION_DAY("���� ������ ������� �����������"),
    INFORM_PERION_TIME_ZONE("������� ���� �������");

    private String value;

    ErmbFields (String value)
    {
        this.value = value;
    }

    public String toValue() { return value; }

}
