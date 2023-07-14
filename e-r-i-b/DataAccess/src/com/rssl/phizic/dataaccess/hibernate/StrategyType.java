package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.hibernate.usertype.ParameterizedType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������-��������� �� ����
 * ������-��������� ������� � ��������� ���� ���� � ���� ������� ����� ������ ���������
 * ��� ������ �� ���� ������-��������� ��������������� � ������� ������������ ��� ����������
 * ���������:
 * strategyInterface - ������ ��� ���������� ��������� (������������ ��������)
 * �����! ������-��������� �� ������ ����� ���������� ���������, �.�. ��� �� ������� � ����
 */
public class StrategyType implements UserType, ParameterizedType
{
	private static final String STRATEGY_INTERFACE_PARAM_NAME = "strategyInterface";

	private Class strategyInterface;

	///////////////////////////////////////////////////////////////////////////

	public void setParameterValues(Properties parameters)
	{
		String interfaceName = parameters.getProperty(STRATEGY_INTERFACE_PARAM_NAME);
		if (StringHelper.isEmpty(interfaceName))
			throw new IllegalArgumentException("� .hbm.xml �� ������ �������� " + STRATEGY_INTERFACE_PARAM_NAME);

		try
		{
			strategyInterface = ClassHelper.loadClass(interfaceName);
		}
		catch (ClassNotFoundException e)
		{
			throw new HibernateException("�� ������ ��������� ��������� " + interfaceName, e);
		}
	}

	public int[] sqlTypes()
	{
		return new int[] { Types.VARCHAR };
	}

	public Class returnedClass()
	{
		return strategyInterface;
	}

	public boolean equals(Object x, Object y)
	{
		return x == y;
	}

	public int hashCode(Object x)
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		String className = rs.getString(names[0]);
		if (rs.wasNull())
		    return null;

		try
		{
			return ClassHelper.newInstance(className);
		}
		catch (RuntimeException e)
		{
			throw new HibernateException("�� ������� ������� ��������� ��������� " + className, e);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws SQLException
	{
		if (value == null)
			st.setNull(index, Types.VARCHAR);
		else st.setString(index, value.getClass().getName());
	}

	public Object deepCopy(Object value)
	{
		return value;
	}

	public boolean isMutable()
	{
		return false;
	}

	public Serializable disassemble(Object value)
	{
		// ��������� immutable, ����� �� ������ ����������
		throw new UnsupportedOperationException();
	}

	public Object assemble(Serializable cached, Object owner)
	{
		// ��������� immutable, ����� �� ������ ����������
		throw new UnsupportedOperationException();
	}

	public Object replace(Object original, Object target, Object owner)
	{
		return original;
	}
}
