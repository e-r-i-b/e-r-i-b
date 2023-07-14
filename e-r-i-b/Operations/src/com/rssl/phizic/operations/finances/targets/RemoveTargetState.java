package com.rssl.phizic.operations.finances.targets;

/**
 * @author akrenev
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ���������� �������� �������� ����
 */

public enum RemoveTargetState
{
	TARGET_REMOVED,              //���� �������
	CLAIM_COMPLETED,             //������ �� �������� ������ ������������
	CLAIM_ERROR,                 //������ �� �������� ������ �� ������������ (�� �� ��������� �� ������� ���������������)
	CLAIM_REQUIRE_CLIENT_ACTION; //� ���� ���������� ������ �� �������� ������ ������������� �������� �������
}
