package com.rssl.phizic.gate.csa;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������� ��� � ������ ����������� �� ����� � ������� ������� ������
 */

public class ProfileWithFullNodeInfo extends Profile
{
	private Long waitMigrationNodeId;

	/**
	 * @return ������������� ���� ���������� ��������
	 */
	public Long getWaitMigrationNodeId()
	{
		return waitMigrationNodeId;
	}

	/**
	 * ������ ������������� ���� ���������� ��������
	 * @param waitMigrationNodeId ������������� ����
	 */
	public void setWaitMigrationNodeId(Long waitMigrationNodeId)
	{
		this.waitMigrationNodeId = waitMigrationNodeId;
	}
}
