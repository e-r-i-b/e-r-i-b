package com.rssl.phizic.rsa;

/**
 * @author khudyakov
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface Constants
{
	//Queue
	String GENERAL_QUEUE_NAME                                               = "jms/monitoring/fraud/GeneralRequestQueue";
	String BLOCKING_CLIENT_QUEUE_NAME                                       = "jms/monitoring/fraud/BlockingClientRequestQueue";
	String CSA_BACK_TRANSPORT_QUEUE_NAME                                    = "jms/monitoring/fraud/CSABackTransportQueue";

	String GENERAL_QUEUE_FACTORY_NAME                                       = "jms/monitoring/fraud/GeneralRequestQCF";
	String BLOCKING_CLIENT_QUEUE_FACTORY_NAME                               = "jms/monitoring/fraud/BlockingClientRequestQCF";
	String CSA_BACK_TRANSPORT_QUEUE_FACTORY_NAME                            = "jms/monitoring/fraud/CSABackTransportQueueQCF";

	//���������
	String PROFILE_BLOCKED_ERROR_MESSAGE                                    = "��� ������� � ������� �������� ������ ������������ � ����� ���������� ������������� ��������. ��� ������ ���������� ���������� � ���������� ����� ����� �� ���������, ����������� �� �������� ������� ����� ����� ���� � ����� ������������� �����.";
	String PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE                      = "�������� �������� ������ �� ���������� � �������������. ���������� � ���������� ����� ����� �� ���������, ����������� �� �������� ������� ����� ����� ���� � ����� ������������� �����.";
	String REQUIRED_ADDITIONAL_CONFIRM_DEFAULT_ERROR_MESSAGE                = "���������� �������������� ������������� �������� � ���������� ������ �����.";
}
