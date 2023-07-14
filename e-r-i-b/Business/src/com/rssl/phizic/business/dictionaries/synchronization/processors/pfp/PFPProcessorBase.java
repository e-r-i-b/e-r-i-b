package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый процессор справочников ПФП
 */

public abstract class PFPProcessorBase<R extends MultiBlockDictionaryRecordBase> extends ProcessorBase<R>
{
	private static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";

	@Override
	protected void update(R source, R destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
	}

	protected final <T extends MultiBlockDictionaryRecordBase> T getLocalVersionByGlobal(T source) throws BusinessException
	{
		return getLocalVersionByGlobal(source, MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	protected final <T extends MultiBlockDictionaryRecordBase> List<T> getLocalVersionByGlobal(List<T> source) throws BusinessException
	{
		return getLocalVersionByGlobal(source, MULTI_BLOCK_RECORD_ID_FIELD_NAME);
	}

	protected R getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.<R>findSingle(criteria);
	}
}
