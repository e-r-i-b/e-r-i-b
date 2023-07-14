package com.rssl.phizic.business.dictionaries.pfp.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.configure.PFPConfigHelper;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author akrenev
 * @ created 28.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class TargetCountService
{
	public static final String TARGET_COUNT_PROPERTY = "target.count";

	/**
	 * @return допустимое количество целей
	 * @throws BusinessException
	 */
	public Long getTargetCount() throws BusinessException
	{
		try
		{
			return NumberUtils.createLong(ConfigFactory.getConfig(PFPConfigHelper.class).getValue(TARGET_COUNT_PROPERTY));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return допустимое количество целей. ≈сли не удалось вытащить из базы - возвращаем дефолтное значение из properties.
	 */
	public static Long getTargetCountSafe()
	{
		return Long.valueOf(ConfigFactory.getConfig(PFPConfigHelper.class).getValueSafe(TARGET_COUNT_PROPERTY));
	}
}
