package com.rssl.phizic.business.finances;

import com.rssl.phizic.dataaccess.hibernate.EnumType;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mihaylov
 * @ created 21.02.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ��������� �������� ����� OperationType.
 * ��������� ��-�� ������� � ������ � ���������� ����������. CHG063597: ��������� ���� CHECK_LOGIN_COUNT ������� USERS
 */
public class OperationTypeEnumType extends EnumType
{
	/** ��� ����������� �������� � ���, �������, � �� null �� ���. ���� null, �� ������ ��������� �������� */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		Object value = super.nullSafeGet(rs, names, owner);
		if(value == null)
			value = OperationType.BY_CARD;
		return value;
	}

	/** ��� ��������� �������� �� ����, �������, � �� ��������� �� ���. ���� ���������, �� ������ null */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		if(value == OperationType.BY_CARD)
			//noinspection AssignmentToMethodParameter
			value = null;
		super.nullSafeSet(st, value, index);
	}
}
