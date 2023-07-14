package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������� ������ �����
 */
public interface CustomListExecutor<E>
{
	/**
	 * @param parameters - ��������� ���������� ������
	 * @param size - ������ ���������
	 * @param offset - ��������
	 * @param orderParameters - ��������� ���������� ������
	 * @return ������ ��� �����
	 */
	public List<E> getList(Map<String,Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException;
}
