package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author krenev
 * @ created 18.02.2014
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyType implements UserType
{
	private static final int TYPE = Types.VARCHAR;

	public int[] sqlTypes()
	{
		return new int[]{TYPE};
	}

	public Class returnedClass()
	{
		return Currency.class;
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
		String currencyCode = rs.getString(names[0]);
		if (rs.wasNull())
		{
			return null;
		}
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			return getCurrency(currencyService, currencyCode);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}
	}

	protected Object getCurrency(CurrencyService currencyService, String currencyCode) throws GateException
	{
		return currencyService.findById(currencyCode);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		if (value == null)
		{
			st.setNull(index, TYPE);
		}
		else
		{
			st.setString(index, ((Currency) value).getExternalId());
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
