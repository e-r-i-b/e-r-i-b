package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.DateSpan;
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
 * @ $Author: balovtsev $
 * @ $Revision: 44573 $
 */

public class DateSpanType implements UserType
{
    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes()
    {
        return SQL_TYPES.clone();
    }

    public Class returnedClass()
    {
        return DateSpan.class;
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
        DateSpan span = (DateSpan) value;
        return span == null ? null : new DateSpan(span.getYears(), span.getMonths(), span.getDays());
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException
    {
        String value = resultSet.getString(names[0]);
        return value == null ? null : new DateSpan(value);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            statement.setNull(index, Types.VARCHAR);
        }
        else
        {
            DateSpan span = (DateSpan) value;
            statement.setString(index, span.toString());
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