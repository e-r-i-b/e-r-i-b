package com.rssl.phizic.operations.loanOffer;

/**
 * @author Moshenko
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 */
public enum CardOfferLoadState
{
	OK("�������� ��������"),
	PARTLY_ERROR("���� �� ����������� ������"),
	LOAD_ERROR("������ ��������"),
	FILE_NOT_FOUND("���� �������� �� ������"),
	BD_ERROR("������ � �������� ������ � ��");

	private String returnMsg;

	CardOfferLoadState(String returnMsg)
	{
		this.returnMsg = returnMsg;
	}

	public String toValue()
	{
		return returnMsg;
	}

	public String getValue()
	{
		return returnMsg;
	}
}
