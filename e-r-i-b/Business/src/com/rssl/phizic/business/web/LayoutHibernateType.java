package com.rssl.phizic.business.web;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Erkin
 * @ created 24.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Hibernate-маппер для Layout
 * @see Layout
 */
public class LayoutHibernateType implements UserType
{
	///////////////////////////////////////////////////////////////////////////

	public int[] sqlTypes()
	{
		return new int[] { Types.VARCHAR };
	}

	public Class returnedClass()
	{
		return Layout.class;
	}

	public boolean equals(Object x, Object y)
	{
		return ObjectUtils.equals(x, y);
	}

	public int hashCode(Object x)
	{
		return ObjectUtils.hashCode(x);
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String layoutAsString = rs.getString(names[0]);
		if (rs.wasNull())
			return null;

		try
		{
			return Layout.valueOf(layoutAsString);
		}
		catch (RuntimeException e)
		{
			throw new HibernateException(e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		try
		{
			if (value == null)
				st.setNull(index, Types.VARCHAR);
			else st.setString(index, value.toString());
		}
		catch (RuntimeException e)
		{
			throw new HibernateException(e);
		}
	}

	public Object deepCopy(Object value)
	{
		return value;
	}

	public boolean isMutable()
	{
		return true;
	}

	public Serializable disassemble(Object value)
	{
		// поскольку immutable, метод не должен вызываться
		throw new UnsupportedOperationException();
	}

	public Object assemble(Serializable cached, Object owner)
	{
		// поскольку immutable, метод не должен вызываться
		throw new UnsupportedOperationException();
	}

	public Object replace(Object original, Object target, Object owner)
	{
		return original;
	}
}
