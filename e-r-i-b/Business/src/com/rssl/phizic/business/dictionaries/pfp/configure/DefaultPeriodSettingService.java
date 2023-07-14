package com.rssl.phizic.business.dictionaries.pfp.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author akrenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � ��������� �������, ������������� ��� ������ �� ��������� � ������� ��� ������ ������� (���� � ��������)
 */
public class DefaultPeriodSettingService
{
	public static final String DEFAULT_PERIOD_PROPERTY = "period.default";

	/**
	 * @return ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Long getDefaultPeriodSettingValue() throws BusinessException
	{
		try
		{
			String propertyValue = ConfigFactory.getConfig(PFPConfigHelper.class).getValue(DEFAULT_PERIOD_PROPERTY);
			if (StringHelper.isEmpty(propertyValue))
				return null;

			return NumberUtils.createLong(propertyValue);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
