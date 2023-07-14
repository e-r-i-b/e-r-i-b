package com.rssl.phizicgate.rsretailV6r20.hibernate;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.Serializable;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.rsretailV6r20.money.CurrencyHelper;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class RSRetailCurrencyType implements UserType
{
    private static final int[] SQL_TYPES = {Types.BIGINT};


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
		    return currencyId == null ? null : getHelper().getGeneralCurrencyById(currencyId);
	    }
	    catch (GateException e)
	    {
		    throw new HibernateException(e);
	    }
	    catch (GateLogicException e)
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
	        try
	        {
				Currency currency = (Currency) value;
				Long currencyId = Long.decode(getHelper().getSpecificCurrencyByCode(currency.getCode()).getExternalId());

				statement.setLong(index, currencyId);
	        }
			catch (GateException e)
			{
				throw new HibernateException(e);
			}
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

	private CurrencyHelper helper;
	//Ленивая инициализация для того чтобы хибернейт поднялся при налиции гейтовых сервисов, юзающих конфиги
	private CurrencyHelper getHelper() {
		if (helper == null){
			helper = new CurrencyHelper();
		}
		return helper;
	}
}
