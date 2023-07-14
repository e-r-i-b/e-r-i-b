package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.Money;
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
 * @ $Author: gladishev $
 * @ $Revision: 58178 $
 */
public abstract class MoneyTypeBase implements CompositeUserType
{
	private static final String[] PROPERTY_NAMES = {"decimal", "currency"};
	private static final Type[]   PROPERTY_TYPES = {Hibernate.BIG_DECIMAL, Hibernate.STRING};

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
		String     currencyCode = (String) Hibernate.STRING.nullSafeGet(rs, names[1]);

		return newInstance(amount, currencyCode);
	}

	protected abstract Object newInstance(BigDecimal amount, String currencyCode);

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
	{
		BigDecimal amount;
		String     currencyCode;

		Money money = (Money) value;

		if(value == null)
		{
			amount = null;
			currencyCode = null;
		}
		else
		{
			amount = money.getDecimal();
			currencyCode = money.getCurrency().getCode();
		}

		Hibernate.BIG_DECIMAL.nullSafeSet(st, amount, index);
		Hibernate.STRING.nullSafeSet(st, currencyCode, index + 1);
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
				return money.getCurrency().getCode();
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
		Serializable[] arr  = (Serializable[]) cached;
		BigDecimal amount   = (BigDecimal) arr[0];
		String currencyCode = (String) arr[1];

		return newInstance(amount, currencyCode);
	}

	public Serializable disassemble(Object value, SessionImplementor session)
	{
		Money money = (Money) value;
		return new Serializable[]{money.getDecimal(), money.getCurrency().getCode()};
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	/**
	 * During merge, replace the existing (target) value in the entity we are merging to with a new (original)
	 * value from the detached entity we are merging. For immutable objects, or null values, it is safe to simply
	 * return the first parameter. For mutable objects, it is safe to return a copy of the first parameter.
	 * However, since composite user types often define component values, it might make sense to recursively
	 * replace component values in the target object.
	 *
	 * @param original
	 * @param target
	 * @param session
	 * @param owner
	 * @return
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException
	{
		return deepCopy(original);
	}
}