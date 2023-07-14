package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ��������� �����. � ���� �������� ������, ����������� ������ ��� ���������� ���������� �����.
 */
interface InternalQuery extends Query
{

	/**
	 * �������� ������. � ������ ������� ������ ����������� ��� ������ �� ��������� ������
	 * @param <T> ��� ������ �����
	 * @return ������
	 * @throws DataAccessException
	 */
	public <T> List<T> executeListInternal() throws DataAccessException;

	/**
	 * ���������� ��������� ���������� ������
	 * @param parameters - ��������� ����������
	 * @return ������� �����
	 */
	public Query setOrderParameterList(List<OrderParameter> parameters);

}
