package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.dataaccess.query.Query;

/**
 * @author Krenev
 * @ created 08.12.2008
 * @ $Author$
 * @ $Revision$
 */
public interface Executor
{
	/**
	 * ��������� ��������, ����� ����������� �������� ����������� ������
	 * � ���� ���� ���������� ����������. ����� ���������� �������� ��� ��� ���� ������� �����������.
	 *
	 * ��� ��������� ������� ���� �� ����� ����������� �������� ������ � ����������
	 * @param action ��������
	 * @return
	 * @throws Exception
	 */
	<T> T execute(HibernateAction<T> action) throws Exception;

	/**
	 * ������ ����������� ������.
	 * @param name ��� �������
	 * @return ������. ��������!! ��������� ������ �� ������� ���������� ������!!!
	 */
	Query getNamedQuery(String name);

	/**
	 * ������� ��� �������� ��, � ������� �������� executor
	 * @return ��� �������� ��, � ������� �������� executor
	 */
	String getInstanceName();
}
