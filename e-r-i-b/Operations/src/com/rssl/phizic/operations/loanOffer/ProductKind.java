package com.rssl.phizic.operations.loanOffer;

/**
 * User: Moshenko
 * Date: 22.06.2011
 * Time: 12:27:36
 */
public enum ProductKind
{
	LOAN_OFFER("PR"),             //�������� �� �������������� ���������� �������
	LOAN_CARD_OFFER("PK"),        //��������� �� �������������� ��������� �����
	LOAN_PRODUCT("KR"),           //�������� �� ��������� �������.
	LOAN_CARD_PRODUCT("KK"),      //�������� �� ��������� ��������� �����.
	VIRTUAL_CARD(""),             //������ �� ����������� �����
	PREAPPROVED_LOAN_CARD_CLAIM(""),     //������ �� �������������� ��������� �����(����� ��������)
	LOAN_CARD_CLAIM("");                 //������ �� ��������� �����(����� ��������)

	private String value;

	ProductKind(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }


}
