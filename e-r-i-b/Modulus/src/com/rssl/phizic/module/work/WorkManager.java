package com.rssl.phizic.module.work;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.utils.store.Store;

/**
 * @author Erkin
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������� �������� (������)
 * �������� ������ - ��������� ���������� ������ ��� ���������� ������������ ������
 * �����! �������� �������� ������ � "�������" �������
 */
@ThreadSafe
public interface WorkManager
{
	/**
	 * ������ ������ � �������
	 */
	void beginWork();

	/**
	 * ���������� ������ � �����
	 * @param session - ������ (can be null)
	 */
	void setSession(Store session);

	/**
	 * ������� ������ ������
	 * @return ������ ��� null, ���� ���/�� ������������ �������
	 */
	Store getSession();

	/**
	 * ��������� ������ � �������
	 */
	void endWork();
}
