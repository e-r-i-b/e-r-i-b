package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.sms.CSASmsResource;
import com.rssl.phizic.business.sms.SMSResourcesService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 *
 * Операция для редактирования смс ресурсов из ЦСА
 *
 * @author  Balovtsev
 * @version 05.04.13 12:24
 */
public class CSASmsSettingsEditOperation extends OperationBase implements EditEntityOperation
{
	private SMSResourcesService resourcesService = new SMSResourcesService();
	private CSASmsResource      csaSmsResource;

	public void initialize(final Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new BusinessException("Не указан идентификатор редактируемого смс-ресурса");
		}

		csaSmsResource = resourcesService.findCSAResourcesById(id, getInstanceName());

		if (csaSmsResource == null)
		{
			throw new BusinessException("Смс-ресурс с идентификатором " + id + " не найден");
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		resourcesService.updateSmsResources(getInstanceName(), csaSmsResource);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return csaSmsResource;
	}

	@Override
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
