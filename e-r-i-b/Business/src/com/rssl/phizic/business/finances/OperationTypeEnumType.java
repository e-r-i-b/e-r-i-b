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
 * Класс для хибернейт маппинга Енума OperationType.
 * Необходим из-за проблем в оракле с дефолтными значениями. CHG063597: Параметры поля CHECK_LOGIN_COUNT таблицы USERS
 */
public class OperationTypeEnumType extends EnumType
{
	/** При простановке значения в бин, смотрим, а не null ли оно. Если null, то ставим дефолтное значение */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
		Object value = super.nullSafeGet(rs, names, owner);
		if(value == null)
			value = OperationType.BY_CARD;
		return value;
	}

	/** При получении значения из бина, смотрим, а не дефолтное ли оно. Если дефолтное, то ставим null */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		if(value == OperationType.BY_CARD)
			//noinspection AssignmentToMethodParameter
			value = null;
		super.nullSafeSet(st, value, index);
	}
}
