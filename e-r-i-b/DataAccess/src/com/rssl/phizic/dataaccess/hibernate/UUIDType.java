package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.UUID;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Gulov
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
/**
 * Меппер объекта UUID на базу
 * Объект UUID пишется в строковое поле базы
 * При чтении из базы объект UUID инстанцируется с помощью static метода.
 */
public class UUIDType implements UserType
{
	public int[] sqlTypes()
	{
		return new int[] { Types.VARCHAR };
	}

	public Class returnedClass()
	{
		return UUID.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		return ObjectUtils.equals(x, y);
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String value = rs.getString(names[0]);
        if (rs.wasNull())
            return null;
        return getResult(value);
	}

	private UUID getResult(String value)
	{
		return UUID.fromValue(value);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		UUID uuid = (UUID) value;
		String result = uuid != null ? StringUtils.trimToNull(uuid.toString()) : null;
		if (result == null)
			st.setNull(index, Types.VARCHAR);
		else
			st.setString(index, result);
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
		// поскольку immutable, метод не должен вызываться
		throw new UnsupportedOperationException();
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		// поскольку immutable, метод не должен вызываться
		throw new UnsupportedOperationException();
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
