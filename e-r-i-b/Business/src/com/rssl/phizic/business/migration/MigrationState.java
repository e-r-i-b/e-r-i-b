package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������
 */

public enum MigrationState
{
	INITIALIZE, // ������������� ����������
	WAIT,       // �������� ������� �������� ��������
	PROCESS,    // ������� �������� �������
	WAIT_STOP,  // �������� ��������� �������� ��������
	STOP        // ������� �������� ����������
}
