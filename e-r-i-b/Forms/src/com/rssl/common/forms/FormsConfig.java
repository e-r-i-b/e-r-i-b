package com.rssl.common.forms;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 * Настройки форм.
 */

public class FormsConfig extends Config
{
	private static final String RHINO_OPTIMIZATION_LEVEL_PROPERTY_NAME = "com.rssl.common.forms.FormsConfig.rhino-optimization-level";
	private static final int DEFAULT_RHINO_OPTIMIZATION_LEVEL = -1;
	private int rhinoOptimizationLevel = DEFAULT_RHINO_OPTIMIZATION_LEVEL;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 *
	 * @param reader ридер.
	 */
	public FormsConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		rhinoOptimizationLevel = getIntProperty(RHINO_OPTIMIZATION_LEVEL_PROPERTY_NAME, DEFAULT_RHINO_OPTIMIZATION_LEVEL);
	}

	/**
	 * @return уровень оптимизазации интерпретатора JS выражений rhino.
	 * @see  org.mozilla.javascript.Context#setOptimizationLevel(int)
	 */
	public int getRhinoOptimizationLevel()
	{
		return rhinoOptimizationLevel;
	}
}
