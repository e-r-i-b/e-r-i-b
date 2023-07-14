package com.rssl.phizic.business.hibernate;

import com.rssl.phizic.common.types.documents.State;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Evgrafov
 * @ created 29.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 49015 $
 */

public class StateType implements CompositeUserType
{
	private static final String[] PROPERTY_NAMES = {"code", "description"};
	private static final Type[]   PROPERTY_TYPES = {Hibernate.STRING, Hibernate.STRING};

	public Class returnedClass()
	{
		return State.class;
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
		State from = (State) x;
		return new State(from.getCode(), from.getDescription());
	}

	public boolean isMutable()
	{
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException
	{
		String name = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
		String description = (String) Hibernate.STRING.nullSafeGet(rs, names[1]);

		return newInstance(name, description);
	}

	private Object newInstance(String name, String description) throws HibernateException
	{
		if (name == null)
			throw new HibernateException("code amount cannot be null");

		return new State(name, description);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
	{
		State state = (State) value;

		if (value == null)
			throw new HibernateException("State can not be null");

		Hibernate.STRING.nullSafeSet(st, state.getCode(), index);
		Hibernate.STRING.nullSafeSet(st, state.getDescription(), index + 1);
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
		State state = (State) component;

		switch (propertyIndex)
		{
			case 0:
				return state.getCode();
			case 1:
				return state.getDescription();
			default:
				throw new RuntimeException("unknown property index");
		}
	}

	public void setPropertyValue(Object component, int propertyIndex, Object value)
	{
		/// Ээээ я же сказал immutable :)
		throw new UnsupportedOperationException("immutable!");
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner)
	{
		Serializable[] arr = (Serializable[]) cached;
		String name = (String) arr[0];
		String description = (String) arr[1];

		return newInstance(name, description);
	}

	public Serializable disassemble(Object value, SessionImplementor session)
	{
		State state = (State) value;
		return new Serializable[]{state.getCode(), state.getDescription()};
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException
	{
		return original; // immutable
	}

}