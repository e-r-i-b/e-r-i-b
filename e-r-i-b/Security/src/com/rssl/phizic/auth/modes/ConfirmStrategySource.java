package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;

/**
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmStrategySource
{
	/**
	 * ѕолучить отфильтрованную стратегию подтверждени€
	 * @param object - объект подтверждени€
	 * @param strategy - исходна€ стратеги€ подтверждени€
	 * @param userChoice - значение типа стратегии подтверждени€ выбранного пользователем (смс, чек)
	 * @return ConfirmStrategy
	*/
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice);
}
