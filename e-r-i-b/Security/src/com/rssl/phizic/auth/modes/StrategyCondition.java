package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;

/**
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public interface StrategyCondition
{
	/**
	 * Получить сообщение об ошибке, призванное обосновать причину отрицательного результата при проверки объекта подтверждения
	 * @return String
	 */
	public String getWarning();

	/**
	 * Проверка объекта подтверждения
	 * @param object - объект подтверждения
	 * @return boolean - true при прохождении проверки объектом подтверждения
	 */
	public boolean checkCondition(ConfirmableObject object);
}
