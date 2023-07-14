package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.UserPrincipal;

/**
 * User: Moshenko
 * Date: 22.05.12
 * Time: 12:06
 * интерфейс поставщика стратегий подтверждения
 */
public interface ConfirmStrategyProvider
{
	/**
	 * метод для получения стратегии для заданного пользователя
	 * @return стратегия подтверждения
	 */
	ConfirmStrategy getStrategy();

	/**
	 * @return UserPrincipal
	 */
	UserPrincipal getPrincipal();
}
