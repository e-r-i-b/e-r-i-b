package com.rssl.phizic.cache;

/**
 * �������� ���������� �������� �� ���������������� �� ���������
 *
 * @author khudyakov
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface CacheAction<T>
{
	/**
	 * �������� ���������� �������� �� ���������������� �� ���������
	 * @return ���������� ��������
	 * @throws Exception
	 */
	T getEntity() throws Exception;
}
