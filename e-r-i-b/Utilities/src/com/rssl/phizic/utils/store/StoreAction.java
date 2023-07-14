package com.rssl.phizic.utils.store;

/**
 * @author khudyakov
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface StoreAction<T>
{
	/**
	 * �������� �������� �� ���������������� �� ���������
	 * @return ���������� ��������
	 * @throws Exception
	 */
	T getEntity() throws Exception;
}
