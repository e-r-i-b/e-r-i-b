package com.rssl.phizicgate.rsV51.hibernate;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.rsV51.money.CurrencyHelper;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Evgrafov
 * @ created 30.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2963 $
 */
public class RSRetailMoneyType implements CompositeUserType
{
	private static final String[] PROPERTY_NAMES = {"decimal", "currency"};
	private static final Type[]   PROPERTY_TYPES = {Hibernate.BIG_DECIMAL, Hibernate.LONG};

	private static final CurrencyHelper RSRetailCurrencyHelper = new CurrencyHelper();

	public Class returnedClass()
	{
		return Money.class;
	}

	public boolean equals(Object x, Object y)
	{
		if (x == y) return true;
		if (x == null || y == null) return false;
		return x.equals(y);
	}

	public Object deepCopy(Object x)
	{
		if (x == null) return null;
		return new Money((Money) x);
	}

	public boolean isMutable()
	{
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException
	{

		BigDecimal amount       = (BigDecimal) Hibernate.BIG_DECIMAL.nullSafeGet(rs, names[0]);
		Long       currencyCode = (Long) Hibernate.LONG.nullSafeGet(rs, names[1]);

		return newInstance(amount, currencyCode);
	}

	private Object newInstance(BigDecimal amount, Long currencyId)
	{
		if(amount == null ^ currencyId == null)
			throw new RuntimeException("money amount cannot be null");

		if(amount == null & currencyId == null)
			return null;

        try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

			String code = RSRetailCurrencyHelper.getCurrencyCode(currencyId);
			Currency currency = currencyService.findByAlphabeticCode(code);

            return new Money(amount, currency);
		}
		catch (GateException e)
		{
			throw new HibernateException(e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
	{
		BigDecimal amount;
		Long       currencyId;

		Money money = (Money) value;

		if(value == null)
		{
			amount = null;
			currencyId = null;
		}
		else
		{
			amount = money.getDecimal();
			currencyId = RSRetailCurrencyHelper.getCurrencyId(money.getCurrency().getCode());
		}

		Hibernate.BIG_DECIMAL.nullSafeSet(st, amount, index);
		Hibernate.LONG.nullSafeSet(st, currencyId, index + 1);
	}

	public String[] getPropertyNames()
	{
		return PROPERTY_NAMES.clone();
	}

	public Type[] getPropertyTypes()
	{
		return PROPERTY_TYPES.clone();
	}

	public Object getPropertyValue(Object component, int propertyIndex)
	{
		Money money = (Money) component;

		switch(propertyIndex)
		{
			case 0:
				return money.getDecimal();
			case 1:
				return RSRetailCurrencyHelper.getCurrencyId(money.getCurrency().getCode());
			default:
				throw new RuntimeException("unknown property index");
		}
	}

	public void setPropertyValue(Object component, int propertyIndex, Object value)
	{
		/// Ээээ я же сказал immutable :)
		throw new RuntimeException("money is immutable!");
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner)
	{
		return deepCopy(cached);
	}

	public Serializable disassemble(Object value, SessionImplementor session)
	{
		return (Serializable) deepCopy(value);
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException
	{
		return deepCopy(original);
	}
}