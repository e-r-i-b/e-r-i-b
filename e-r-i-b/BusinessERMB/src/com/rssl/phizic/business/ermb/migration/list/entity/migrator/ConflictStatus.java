package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

/**
 * ������ �������� � ��������� ���������� ����������
 * @author Puzikov
 * @ created 24.01.14
 * @ $Author$
 * @ $Revision$
 */

public enum ConflictStatus
{
	UNRESOLVED,             //������������� ��������
	RESOLVED_TO_OWNER,      //�������� ������� (�� id)
	RESOLVED_TO_DELETE      //������� ������� ������
}
