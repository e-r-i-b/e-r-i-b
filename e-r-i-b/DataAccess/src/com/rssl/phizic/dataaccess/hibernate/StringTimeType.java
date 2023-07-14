package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

/**
 * @author Evgrafov
 * @ created 01.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1821 $
 */

public class StringTimeType implements UserType
{
    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes()
    {
        return StringTimeType.SQL_TYPES.clone();
    }

    public Class returnedClass()
    {
        return Time.class;
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
	    Time time = (Time) value;
	    return time == null ? null : time.clone();
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException
    {
        String value = resultSet.getString(names[0]);
        return value == null ? null : Time.valueOf(value);
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            statement.setNull(index, Types.VARCHAR);
        }
        else
        {
            Time time = (Time) value;
            statement.setString(index, time.toString());
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