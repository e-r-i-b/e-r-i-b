package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Операция просмотра текстов смс-шаблонов ЕРМБ
 * @author Rtischeva
 * @created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class ERMBSmsSettingsListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final SMSResourcesService resourcesService = new SMSResourcesService();

	/**
	 * @return смс-шаблоны ЕРМБ-канала
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<? extends MessageResource> getERMBSmsResources() throws BusinessException
	{
		try
		{
			return resourcesService.getSMSResources(ChannelType.ERMB_SMS);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
