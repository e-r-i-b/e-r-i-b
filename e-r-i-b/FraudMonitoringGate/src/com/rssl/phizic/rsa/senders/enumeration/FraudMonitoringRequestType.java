package com.rssl.phizic.rsa.senders.enumeration;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 * ��� ������� � ������� ����-�����������
 */
public enum  FraudMonitoringRequestType
{
	UPDATE_ACTIVITY,    //������ �� ��������� �������� �� ���������� � ActivityEngine
	BY_DOCUMENT,        //������ �� ���������� �� ������ �������/������� � AdaptiveAuthentication
	ANALYZE_BY_EVENT,   //������ �� ������ �� ����������-������� � AdaptiveAuthentication
	NOTIFY_BY_EVENT     //������-���������� �� ����������-������� � AdaptiveAuthentication
}
