package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.utils.RandomGUID;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;

import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author krenev
 * @ created 17.03.2014
 * @ $Author$
 * @ $Revision$
 * Генератор идентифкаторов на основе RandomGUID
 */

public class GUIDGenerator implements IdentifierGenerator, Configurable
{
	private static final boolean DEFAULT_VALUE = true;
	private static final String NAME = "secure";
	private boolean secure;

	public Serializable generate(SessionImplementor session, Object object) throws HibernateException
	{
		return new RandomGUID(secure).getStringValue();
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException
	{
		secure = PropertiesHelper.getBoolean(NAME, params, DEFAULT_VALUE);
	}
}
