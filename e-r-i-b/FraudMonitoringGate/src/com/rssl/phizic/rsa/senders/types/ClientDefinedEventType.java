package com.rssl.phizic.rsa.senders.types;

/**
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum ClientDefinedEventType
{
	INTERNAL_PAYMENT("INTERNALPAYMENT",                                 "������� ����� ������ ������� � �������"),
	RUR_PAYMENT("RURPAYMENT",                                           "������� �������� ����"),
	RUR_PAY_JUR_SB("RURPAYJURSB",                                       "������ �����"),
	EXTERNAL_PROVIDER_PAYMENT("EXTERNALPROVIDERPAYMENT",                "������ � ������� ������"),
	CREATE_AUTO_PAYMENT("CREATEAUTOPAYMENTPAYMENT",                     "�������� ����������� (���������� ��������)"),
	EDIT_AUTO_PAYMENT("EDITAUTOPAYMENTPAYMENT",                         "�������������� ����������� (���������� ��������)"),
	LOAN_PAYMENT("LOANPAYMENT",                                         "��������� �������"),
	CLOSE_ACCOUNT("CLOSE_ACCOUNT",                                      "�������� �����"),
	CHANGE_PASSWORD("CHANGE",                                           "��������� ������ ��������"),
	RECOVER_PASSWORD("RECOVER",                                         "�������������� ������ ��������"),
	RECOVER_BY_CALL("RECOVER_BY_CALL",                                  "�������������� ������ �������� ����� ������� �����"),
	ISSUE_CARD("CARD",                                                  "������� �����"),
	ISSUE_VIRTUAL_CARD("VIRTUAL_PREPAID_CARD",                          "����������� �����"),
	REISSUE_CARD("CARD",                                                "������� �����"),
	REISSUE_VIRTUAL_CARD("VIRTUAL_PREPAID_CARD",                        "����������� �����"),
	WITH_OTP("WITH_OTP",                                                "���� � ������� � ����������� �������"),
	WITHOUT_OTP("WITHOUT_OTP",                                          "���� � ������� ��� ������������ ������"),
	UNIVERSAL_AGREEMENT("UNIVERSAL_AGREEMENT",                          "����������� ����"),
	NEW_MOB_APPL("NEW_MOB_APPL",                                        "������ ������������ ����� ��������� ����������");


	private String type;
	private String description;

	private ClientDefinedEventType(String type, String description)
	{
		this.type = type;
		this.description = description;
	}

	/**
	 * @return ��� ���������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}
}

