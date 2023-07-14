package com.rssl.phizic.dataaccess.common.counters;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * Генератор id. Если id уже присутствует в сущности, то оно и остаётся.
 * Если id == null, то берётся новое значение из соответствующей последовательности.
 *********************************************************
 * Данный генератор НЕ РАБОТАЕТ с методом saveOrUpdate!!!*
 * Если у сущности есть id, но её нет в базе,            *
 * то она(сущность) не добавится.                        *
 *********************************************************
 * Корректно работает только с методами save и update.
 * @author komarov
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class AssignedOrSequenceGenerator extends SequenceGenerator
{
	private String entityName;

	public void configure(Type type, Properties params, Dialect dialect) throws MappingException
	{
		super.configure(type, params, dialect);

		entityName = params.getProperty(ENTITY_NAME);
		if (entityName==null)
		{
			throw new MappingException("no entity name");
		}
	}

	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException
	{
		final Serializable id = session.getEntityPersister( entityName, obj )
				                .getIdentifier( obj, session.getEntityMode() );
		if (id==null)
		{
			return super.generate(session, obj);
		}
		return id;
	}
}
