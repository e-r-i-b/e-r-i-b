package com.rssl.phizic.auth.modes;

/**
 * @author gulov
 * @ created 23.09.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * �����, ������� �������� ���������� � ���������� ������������� �������
 */
public class ConfirmStrategyResult
{
	/**
	 * true - ���� ������������� ���� �� ����� ������, false - � ������ �������
	 */
	private boolean isCardStrategy = false;

	public ConfirmStrategyResult()
	{
	}

	public ConfirmStrategyResult(boolean isCardStrategy)
	{
		this.isCardStrategy = isCardStrategy;
	}

	public boolean isCardStrategy()
	{
		return isCardStrategy;
	}

	public void setCardStrategy(boolean cardStrategy)
	{
		isCardStrategy = cardStrategy;
	}
}
