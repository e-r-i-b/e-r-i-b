package com.rssl.phizic.utils.test;

import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

/**
 * @author Roshka
 * @ created 07.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConfigurationAnnotationHelper
{
	/**
	 * Проверяет удовлетворяет ли конфигурация критерию includeTest
	 * @param configuration имя конфигурации
	 * @param includeTest критерий
	 * @return true/false
	 * @see com.rssl.phizic.utils.test.annotation.IncludeTest
	 */
	public boolean match(String configuration, IncludeTest includeTest)
	{
		if (includeTest == null)
			return true;

		String[] configurations = includeTest.configurations();
		if (configurations.length == 0)
			return true;

		if (StringUtils.contains(configurations, configuration))
			return true;

		return false;
	}

	/**
	 * Проверяет удовлетворяет ли конфигурация критерию excludeTest
	 * @param configuration имя конфигурации
	 * @param excludeTest критерий
	 * @return true/false
	 * @see com.rssl.phizic.utils.test.annotation.ExcludeTest
	 */
	public boolean match(String configuration, ExcludeTest excludeTest)
	{
		if (excludeTest == null)
			return true;

		String[] configurations = excludeTest.configurations();
		if (configurations.length == 0)
			return false;

		if (!StringUtils.contains(configurations, configuration))
			return true;

		return false;
	}
}