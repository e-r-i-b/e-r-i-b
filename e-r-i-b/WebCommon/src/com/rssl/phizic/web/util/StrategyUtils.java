package com.rssl.phizic.web.util;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.CompositeConfirmStrategy;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * User: Moshenko
 * Date: 13.06.2012
 * Time: 17:46:08
 */
public class StrategyUtils
{
	/**
	 * Тестируем стратегию на соответствие  заданному типу, если стратегия композитная то на присутствие заданного  типа в ней.
	 * @param confirmStrategy Стратегия подтверждения
	 * @param type Тип стратегии подтверждения
	 * @return Результат проверки
	 */
	public static Boolean isContainStrategy(ConfirmStrategy confirmStrategy,String type)
	{
		ConfirmStrategyType enumType = ConfirmStrategyType.valueOf(type);

		if (confirmStrategy==null)
			return false;

		else if (confirmStrategy instanceof CompositeConfirmStrategy)
		{
			return ((CompositeConfirmStrategy)confirmStrategy).isContainStrategy(type);
		}

		else if (confirmStrategy.getType()==enumType)
			return true;

		return false;
	}
}
