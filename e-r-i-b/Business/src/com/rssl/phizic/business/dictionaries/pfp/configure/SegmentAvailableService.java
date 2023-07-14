package com.rssl.phizic.business.dictionaries.pfp.configure;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import org.apache.commons.lang.BooleanUtils;

/**
 * @author akrenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с доступностью ПФП в сегментах клиентов
 */

public class SegmentAvailableService
{
	private static final String SEGMENT_AVAILABLE_PROPERTY_PREFIX = "segment.available.";

	public static String getPropertyKey(SegmentCodeType segment)
	{
		return SEGMENT_AVAILABLE_PROPERTY_PREFIX + SegmentHelper.getSegmentCodeType(segment).name();
	}

	/**
	 * @param segment сегмент
	 * @return доступность (true = доступен)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public boolean isSegmentAvailable(SegmentCodeType segment) throws BusinessException
	{
		try
		{
			return BooleanUtils.toBoolean(ConfigFactory.getConfig(PFPConfigHelper.class).getValue(getPropertyKey(segment)));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * сохранить новое значение доступности сегмента
	 * @param segment сегмент
	 * @param available доступность (true = доступен)
	 */
	public void setSegmentAvailable(SegmentCodeType segment, boolean available)
	{
		DbPropertyService.updateProperty(PFPConfigHelper.PROPERTY_PREFIX.concat(getPropertyKey(segment)), String.valueOf(available));
	}

}
