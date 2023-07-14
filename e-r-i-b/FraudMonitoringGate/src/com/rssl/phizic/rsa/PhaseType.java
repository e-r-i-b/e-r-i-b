package com.rssl.phizic.rsa;

/**
 * ������ �������� �� ��
 *
 * @author khudyakov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */
public enum PhaseType
{
	SENDING_REQUEST,            //���� �������� ������� �� �� (��� ������������ ��������������)
	WAITING_FOR_RESPONSE,       //���� �������� ������ (��� ������������ ��������������)
	CONTINUOUS_INTERACTION,     //����������� ��������� (��� ����������� ��������������)
	OFF_LINE                    //off-line
}
