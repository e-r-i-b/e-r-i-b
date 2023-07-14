package com.rssl.phizic.business.dictionaries.synchronization.processors.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.CompositeLanguageResourceId;
import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author komarov
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
abstract public class ResourcesProcessorBase<T extends MultiBlockLanguageResources> extends ProcessorBase<T>
{
	private CompositeLanguageResourceId getId(String identifier)
	{
		return CompositeLanguageResourceId.parse(identifier);
	}

	@Override protected T getEntity(String identifier) throws BusinessException
	{
		CompositeLanguageResourceId id = getId(identifier);
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass())
				.add(Expression.eq("uuid", id.getUuid()))
				.add(Expression.eq("localeId", id.getLocaleId()));
		return simpleService.<T>findSingle(criteria);
	}

	@Override
	protected void update(T source, T destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setLocaleId(source.getLocaleId());
	}

	protected void doRemove(String uuid) throws BusinessException, BusinessLogicException
	{
		removeRes(uuid);
	}


	private void removeRes(final String uuid) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(null).execute( new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					/**
					 * Опорный объект: PK_<имя таблицы в которой хранится ресурс>(primary key)
					 * Предикаты доступа:    access("UUID"=:UUID)
					 * Кардинальность: <количество языков в системе>
					 */
					Query query = session.createQuery("delete from "+ getEntityClass().getName()+" resource where resource.uuid = :uuid");
					query.setParameter("uuid", uuid);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
