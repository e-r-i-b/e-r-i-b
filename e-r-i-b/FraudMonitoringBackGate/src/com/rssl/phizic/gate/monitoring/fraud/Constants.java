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

	//коды возвратов
	int PROCESSING_REQUEST_ERROR_CODE                                       = 500;
	int PROCESSING_REQUEST_SUCCESS_CODE                                     = 0;

	//коды причины
	int DEFAULT_REASON_CODE                                                 = 1001;

	//описание кода возврата
	String PROCESSING_REQUEST_ERROR_CODE_DESCRIPTION                        = "Ошибка при обработке запроса.";
	String PROCESSING_REQUEST_SUCCESS_CODE_DESCRIPTION                      = "Запрос принят в обработку.";

	//текстовки ошибок/сообщений
	String PROCESSING_REQUEST_SUCCESS_MESSAGE                               = "Обработка запроса clientTransactionId = %s успешно закончена.";
	String PROCESSING_REQUEST_ERROR_MESSAGE                                 = "Ошибка при обработке запроса clientTransactionId = %s.";
	String SAVE_REQUEST_ERROR_MESSAGE                                       = "Ошибка при сохранении запроса clientTransactionId = %s.";
	String TRANSFORM_REQUEST_ERROR_MESSAGE                                  = "Ошибка при jaxb преобразовании запроса clientTransactionId = %s.";
	String ROLLBACK_ERROR_MESSAGE                                           = "Ошибка при откате обработки processor = %s";
	String PLACED_IN_QUEUE_MESSAGE                                          = "Запрос с ClientTransactionId = %s помещен в очередь %s";
}
