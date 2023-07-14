package com.rssl.phizic.business.dictionaries.synchronization.processors.exception;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.exception.ExceptionEntryService;
import com.rssl.phizic.business.exception.ExceptionMapping;
import com.rssl.phizic.business.exception.ExceptionMappingRestriction;
import com.rssl.phizic.business.exception.uuid.ExceptionMappingUUID;

import java.util.ArrayList;

/**
 * @author osminin
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации маппинга ошибок
 */
public class ExceptionMappingProcessor extends ProcessorBase<ExceptionMapping>
{
	private static ExceptionEntryService exceptionEntryService = new ExceptionEntryService();

	@Override
	protected Class<ExceptionMapping> getEntityClass()
	{
		return ExceptionMapping.class;
	}

	@Override
	protected ExceptionMapping getEntity(String uuid) throws BusinessException
	{
		ExceptionMappingUUID mappingUUID = new ExceptionMappingUUID(uuid);

		return exceptionEntryService.getByHashAndGroup(mappingUUID.getHash(), mappingUUID.getGroup());
	}

	@Override
	protected ExceptionMapping getNewEntity()
	{
		return new ExceptionMapping();
	}

	@Override
	protected void update(ExceptionMapping source, ExceptionMapping destination) throws BusinessException
	{
		destination.setHash(source.getHash());
		destination.setGroup(source.getGroup());
		destination.setMessage(source.getMessage());
		destination.setUuid(source.getUuid());
		destination.setRestrictions(new ArrayList<ExceptionMappingRestriction>(source.getRestrictions()));
	}
}
