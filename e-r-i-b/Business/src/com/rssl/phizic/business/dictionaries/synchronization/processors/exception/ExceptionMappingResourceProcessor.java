package com.rssl.phizic.business.dictionaries.synchronization.processors.exception;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResources;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResourcesService;
import com.rssl.phizic.business.exception.uuid.ExceptionMappingResourceUUID;

/**
 * @author osminin
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации текстовок маппинга ошибок в зависимости от локалей
 */
public class ExceptionMappingResourceProcessor extends ProcessorBase<ExceptionMappingResources>
{
	private static ExceptionMappingResourcesService resourcesService = new ExceptionMappingResourcesService();

	@Override
	protected Class<ExceptionMappingResources> getEntityClass()
	{
		return ExceptionMappingResources.class;
	}

	@Override
	protected ExceptionMappingResources getEntity(String uuid) throws BusinessException
	{
		ExceptionMappingResourceUUID resourceUUID = new ExceptionMappingResourceUUID(uuid);

		return resourcesService.getByCompositeId(resourceUUID.getHash(), resourceUUID.getGroup(), resourceUUID.getLocaleId());
	}

	@Override
	protected ExceptionMappingResources getNewEntity()
	{
		return new ExceptionMappingResources();
	}

	@Override
	protected void update(ExceptionMappingResources source, ExceptionMappingResources destination) throws BusinessException
	{
		destination.setMessage(source.getMessage());
		destination.setGroup(source.getGroup());
		destination.setHash(source.getHash());
		destination.setLocaleId(source.getLocaleId());
	}
}
