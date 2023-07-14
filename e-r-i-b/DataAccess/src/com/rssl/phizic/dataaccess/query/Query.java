package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.utils.ListTransformer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 25.11.2005
 * @ $Author$
 * @ $Rev$
 */

public interface Query
{
    /**
     * ���������� �������� �������
     * @param name   ��� ���������
     * @param value  �������� ���������
     * @return ��� ����
     */
    Query  setParameter(String name, Object value);

    /**
     * ���������� �������� �������
     * @param parameters key - ��������, value - ��������
     * @return ��� ����
     */
    Query setParameters(Map<String,Object> parameters);

	/**
	 * @param name ��� ���������
	 * @return �������� ������� ��� null, ���� �� �� ����������
	 */
	Object getParameter(String name);

	 /**
     * �������� ��������� �������
     * @return ���������� ��������� �������
     */
    public List<String> getNamedParameters()  throws DataAccessException;

	/**
	 * ���������� �������� �������
	 * @param name - ��� ���������
	 * @param vals - ������ ��������
	 * @return ��� ����
	 */
	public Query setParameterList(String name, Collection vals);

	/**
	 * ���������� �������� �������
	 * @param name - ��� ���������
	 * @param  vals - ������ ��������
	 * @return ��� ����
	 */
	Query setParameterList(String name, Object[] vals);

	/**
	 * ���������� ��������� � �������.
	 * ��������������� � ������� count ���������� � ������� prefix+'1', prefix+'2'.. prefix+ count.
	 * �������� ������� �� values. ���� count ������ ���������� �������� values, �� ���������� ��������� ��������������� � null.
	 * ������������� �������������� �������� 'empty_'+prefix, ���������� �� ������ ����� �� ���������.
	 * @param prefix ������� ����������� ����������
	 * @param values �������� ����������
	 * @param count ���������� ����������
	 * @return ��� ����
	 */
	public Query setListParameters(String prefix, Object[] values, int count);

	/**
	 * ���������� ��������� � �������.
	 * ��������������� � ������� count ���������� � ������� prefix+'1', prefix+'2'.. prefix+ count.
	 * �������� ������� �� values. ���� count ������ ���������� �������� values, �� ���������� ��������� ��������������� � null.
	 * ������������� �������������� �������� 'empty_'+prefix, ���������� �� ������ ����� �� ���������.
	 * @param prefix ������� ����������� ����������
	 * @param values �������� ����������
	 * @param count ���������� ����������
	 * @return ��� ����
	 */
	public Query setListParameters(String prefix, List values, int count);
    /**
     * ���������� ������������ ���������� ������������ �������
     * @param val ������������ ���������� ������������ �������
     * @return ��� ����
     */
    Query setMaxResults(int val);

    /**
     * ���������� ������ ������� � ��������
     * @param val ����� ������ ������������ ������
     * @return ��� ����
     */
    Query setFirstResult(int val);

    /**
     * ��������� ������ � ������� ������ �����������
     * @return ������ ����������� ��� ������ ������
     */
    <T> List<T> executeList() throws DataAccessException;

	/**
	 * ��������� ������ � ������� �������� �����������
	 *
	 * @return �������� �����������
	 */
	<T> Iterator<T> executeIterator() throws DataAccessException;

	/**
     * ��������� ������ � ������� ���������
     * @return ��������� ��� null
     */
    <T> T executeUnique() throws DataAccessException;

	/**
	 * TODO ��������� ���������� �������� ������ ��������� � �������� ��������� � ENH035791
	 * ��������� ������ �� ��������� ��.
	 * @return ����� ����������� ����� (�� null)
	 * @throws DataAccessException
	 */
	int executeUpdate() throws DataAccessException;
    /**
     * ���������� ������ ������������ � *.hbm
     * @param filterName - ��� �������
     * @return Filter
     */
    Filter enableFilter(String filterName);

	/**
	 * ����������� �� ������� � ���� �������
	 * @param restriction - ����������� �� ������� � ���� �������
	 */
	void setFilterRestriction(FilterRestriction restriction);

	/**
	 * ���������� ��������������� ����������
	 * @param transformer - ��������������� ������
	 * NOTE: ������ ��������� ������ ������ ���� ����� ������� �������� ������!
	 */
	<Output, Input> void setListTransformer(ListTransformer<Output, Input> transformer);
}
