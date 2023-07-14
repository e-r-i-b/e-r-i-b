package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.utils.StringHelper;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.hibernate.util.EqualsHelper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author akrenev
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * Тип boolean если значение null возвращается false
 */

public class FalseIfNullBooleanType implements UserType
{
	public static final int SQL_TYPE = Types.BIT;

	private Boolean getDefaultValue()
	{
		return Boolean.FALSE;
	}

	public int[] sqlTypes()
	{
		return new int[] {SQL_TYPE};
	}

	public Class returnedClass()
	{
		return boolean.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		return EqualsHelper.equals(x, y);
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String name = names[0];
		String value = rs.getString(name);
		if (StringHelper.isEmpty(value))
			return getDefaultValue();

		return rs.getBoolean(name);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		st.setBoolean(index, value == null? getDefaultValue() : (Boolean) value);
	}

	public Object deepCopy(Object value) throws HibernateException
	{
		return value;
	}

	public boolean isMutable()
	{
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException
	{
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
