package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ � ��������
 */

public class ThreadMigrationInfo extends MigrationInfo
{
	private boolean needStop;

	/**
	 * �����������
	 */
	public ThreadMigrationInfo(){}

	/**
	 * @return ���� ���������
	 */
	public boolean isNeedStop()
	{
		return needStop;
	}
}
