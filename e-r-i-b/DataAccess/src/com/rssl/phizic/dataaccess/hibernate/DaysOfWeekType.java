package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.Day;
import com.rssl.phizic.common.types.DaysOfWeek;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

/**
 * User: moshenko
 * Date: 03.10.12
 * Time: 18:51
 */
public class DaysOfWeekType implements UserType
{
	private static final int[] SQL_TYPES = {Types.VARCHAR};

	public int[] sqlTypes()
	{
		return SQL_TYPES.clone();
	}

	public Class returnedClass()
	{
		return com.rssl.phizic.common.types.DaysOfWeek.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		if (x == null && y == null)
			return true;

		if (x == null)
			return false;

		return x.equals(y);
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String value = rs.getString(names[0]);
		if (value != null && value != "")
		{
			Set<Day> days = new HashSet<Day>();
			String[] daysString = value.split(",");
			for (int i = 0; i < daysString.length; i++)
			{
				String day = daysString[i];
				days.add(Day.fromValue(day));
			}
			return new DaysOfWeek(days);
		}
		else
			return null;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		DaysOfWeek daysOfWeek = (DaysOfWeek) value;
		if (daysOfWeek == null || daysOfWeek.isEmpty())
		{
			st.setNull(index, Types.VARCHAR);
		}
		else
		{
			String result = StringUtils.join(daysOfWeek.getStringDays(), ",");
			st.setString(index,result);
		}
	}

	public Object deepCopy(Object value) throws HibernateException
	{
		DaysOfWeek daysOfWeek = (DaysOfWeek) value;
		return daysOfWeek == null ? null : new DaysOfWeek(daysOfWeek.getDays());
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
		return deepCopy(cached);
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
