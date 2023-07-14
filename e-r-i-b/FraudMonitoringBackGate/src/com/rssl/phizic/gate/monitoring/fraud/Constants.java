package com.rssl.phizic.gate.monitoring.fraud;

/**
 * @author khudyakov
 * @ created 12.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface Constants
{
	//Queue
	String GENERAL_QUEUE_NAME                                               = "jms/monitoring/fraud/GeneralRequestQueue";
	String BLOCKING_CLIENT_QUEUE_NAME                                       = "jms/monitoring/fraud/BlockingClientRequestQueue";

	String GENERAL_QUEUE_FACTORY_NAME                                       = "jms/monitoring/fraud/GeneralRequestQCF";
	String BLOCKING_CLIENT_QUEUE_FACTORY_NAME                               = "jms/monitoring/fraud/BlockingClientRequestQCF";

	//���� ���������
	int PROCESSING_REQUEST_ERROR_CODE                                       = 500;
	int PROCESSING_REQUEST_SUCCESS_CODE                                     = 0;

	//���� �������
	int DEFAULT_REASON_CODE                                                 = 1001;

	//�������� ���� ��������
	String PROCESSING_REQUEST_ERROR_CODE_DESCRIPTION                        = "������ ��� ��������� �������.";
	String PROCESSING_REQUEST_SUCCESS_CODE_DESCRIPTION                      = "������ ������ � ���������.";

	//��������� ������/���������
	String PROCESSING_REQUEST_SUCCESS_MESSAGE                               = "��������� ������� clientTransactionId = %s ������� ���������.";
	String PROCESSING_REQUEST_ERROR_MESSAGE                                 = "������ ��� ��������� ������� clientTransactionId = %s.";
	String SAVE_REQUEST_ERROR_MESSAGE                                       = "������ ��� ���������� ������� clientTransactionId = %s.";
	String TRANSFORM_REQUEST_ERROR_MESSAGE                                  = "������ ��� jaxb �������������� ������� clientTransactionId = %s.";
	String ROLLBACK_ERROR_MESSAGE                                           = "������ ��� ������ ��������� processor = %s";
	String PLACED_IN_QUEUE_MESSAGE                                          = "������ � ClientTransactionId = %s ������� � ������� %s";
}
