package com.rssl.phizicgate.rsV51.hibernate;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV51.money.CurrencyHelper;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Evgrafov
 * @ created 01.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1821 $
 */

public class RSRetailCurrencyType implements UserType
{
    private static final int[] SQL_TYPES = {Types.BIGINT};
	private static final CurrencyHelper RSRetailCurrencyHelper = new CurrencyHelper();

	public int[] sqlTypes()
    {
        return SQL_TYPES.clone();
    }

    public Class returnedClass()
    {
        return Currency.class;
    }

    public boolean equals(Object x, Object y)
    {
        if( x == null && y == null)
            return true;

        if( x == null)
            return false;

        return x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    public Object deepCopy(Object value) throws HibernateException
    {
	    return value;
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException
    {
        Long currencyId = resultSet.getLong(names[0]);
	    try
	    {
		    String code = RSRetailCurrencyHelper.getCurrencyCode(currencyId);

		    CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		    return currencyId == null ? null : currencyService.findByAlphabeticCode(code);
	    }
	    catch (GateException e)
	    {
		    throw new HibernateException(e);
	    }
    }

	public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            statement.setNull(index, Types.BIGINT);
        }
        else
        {
	        Currency currency = (Currency) value;
	        Long currencyId = RSRetailCurrencyHelper.getCurrencyId(currency.getCode());

	        statement.setLong(index, currencyId);
        }
    }

	public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return deepCopy(cached);
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }
}