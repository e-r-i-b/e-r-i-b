package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.lang.ObjectUtils;
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
 * Меппер объекта PhoneNumber на базу
 * Объект PhoneNumber конвертируется с помощью PhoneNumberFormat.SIMPLE_NUMBER и пишется в строковое поле базы.
 * При чтении из базы объект PhoneNumber инстанцируется с помощью static метода.
 * Под номер телефона в базе нужно 10 символов
 */
public class PhoneNumberType implements UserType
{
	public int[] sqlTypes()
	{
		return new int[] { Types.VARCHAR };
	}

	public Class returnedClass()
	{
		return PhoneNumber.class;
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
		return PhoneNumber.fromString(value);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		PhoneNumber phone = (PhoneNumber) value;
		if (phone == null)
			st.setNull(index, Types.VARCHAR);
		else
			st.setString(index, PhoneNumberFormat.SIMPLE_NUMBER.format(phone));
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
