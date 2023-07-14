package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 *
 * Операция для просмотра смс шаблонов
 *
 * @author  Balovtsev
 * @version 22.03.13 10:43
 */
public class SmsSettingsListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final SMSResourcesService resourcesService = new SMSResourcesService();

	/**
	 * @param type тип канала, если null - возвращает вообще все шаблоны
	 * @return
	 * @throws BusinessException
	 */
	public List<? extends MessageResource> getSmsResources(final ChannelType type) throws BusinessException
	{
		try
		{
			return resourcesService.getSMSResources(type);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
