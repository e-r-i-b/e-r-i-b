package com.rssl.phizic.auth.modes;

/**
 * @author gulov
 * @ created 23.09.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Класс, который содержит информацию о результате подтверждения платежа
 */
public class ConfirmStrategyResult
{
	/**
	 * true - если подтверждение было по карте ключей, false - в других случаях
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
