package com.rssl.phizic.business.finances;

/**
 * �������� ��� ���������� ������� �� �������� "��������" (�������� � �����������)
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CashAndCashlessOperationsGraphAbstract extends CardOperationAbstract
{
	private boolean cash; // �������� �� ��������

	public boolean getCash()
	{
		return cash;
	}

	public void setCash(boolean cash)
	{
		this.cash = cash;
	}
}
