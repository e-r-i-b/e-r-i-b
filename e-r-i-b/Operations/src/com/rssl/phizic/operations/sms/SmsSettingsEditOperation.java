package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * ќпераци€ редактировани€ смс ресурсов
 *
 * @author  Balovtsev
 * @version 22.03.13 11:27
 */
public class SmsSettingsEditOperation extends OperationBase implements EditEntityOperation
{
	private SMSResourcesService             service   = new SMSResourcesService();
	private List<? extends MessageResource> resources = new ArrayList<MessageResource>();

	/**
	 * »нициализаци€ операции
	 *
	 *
	 * @param id редактируемого смс ресурса
	 * @throws BusinessException
	 */
	public void initialize(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new BusinessException("Ќе указан идентификатор редактируемого смс-ресурса");
		}
		resources = service.findSmsResourcesById(id);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.updateSmsResources(null, resources.toArray());
	}

	public List<? extends MessageResource> getEntity() throws BusinessException, BusinessLogicException
	{
		return resources;
	}
}
