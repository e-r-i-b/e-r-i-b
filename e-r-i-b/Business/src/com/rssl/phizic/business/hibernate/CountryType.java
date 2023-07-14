package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.country.CountryCode;
import com.rssl.phizic.business.dictionaries.country.CountryService;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author koptyaev
 * @ created 16.04.14
 * @ $Author$
 * @ $Revision$
 */
public class CountryType implements UserType
{
	private static final int TYPE = Types.VARCHAR;
	private static final CountryService countryService = new CountryService();

	public int[] sqlTypes()
	{
		return new int[]{TYPE};
	}

	public Class returnedClass()
	{
		return CountryCode.class;
	}


	public boolean equals(Object x, Object y) throws HibernateException
	{
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String iso3 = rs.getString(names[0]);
		if (rs.wasNull())
		{
			return null;
		}
		try
		{
			return countryService.getCountryByISO3(iso3);
		}
		catch (BusinessException e)
		{
			throw new HibernateException(e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{

		if (value == null)
		{
			st.setNull(index, TYPE);
		}
		else
		{
			st.setString(index, ((CountryCode) value).getIso3());
		}
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
		throw new UnsupportedOperationException();
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
