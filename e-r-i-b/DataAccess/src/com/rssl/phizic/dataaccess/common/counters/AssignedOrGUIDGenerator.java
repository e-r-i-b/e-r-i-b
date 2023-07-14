package com.rssl.phizic.dataaccess.common.counters;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.GUIDGenerator;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.02.14
 * @ $Author$
 * @ $Revision$
 *
 * Генератор идентификатора. Идентификатор генерируется только в случае его незаполненности в сущности.
 */
public class AssignedOrGUIDGenerator extends GUIDGenerator
{
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException
	{
		final Serializable guid = session.getEntityPersister(null, obj).getIdentifier(obj, session.getEntityMode());

		return guid == null ? super.generate(session, obj) : guid;
	}
}
