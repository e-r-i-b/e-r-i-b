package com.rssl.phizic.business.security;

/**
 * Калькулятор проверки прав
 *
 * @author khudyakov
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PermissionCalculator
{
	/**
	 * Проверка на доступность операции
	 * @param serviceKey сервис
	 * @param operationKey операция
	 * @param rigid
	 * @return true - доступен
	 */
	boolean impliesOperation(String serviceKey, String operationKey, boolean rigid);

	/**
	 * Проверка на доступность сервиса
	 * @param serviceKey сервис
	 * @param rigid
	 * @return true - доступен
	 */
	boolean impliesService(String serviceKey, boolean rigid);

	/**
	 * Проверка на доступность схемы
	 * @param schemeKey схема
	 * @return true - доступна
	 */
	boolean impliesAccessScheme(String schemeKey);
}
