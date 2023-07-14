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

	//текстовки
	String PROFILE_BLOCKED_ERROR_MESSAGE                                    = "Ваш профиль в системе Сбербанк Онлайн заблокирован с целью пресечения мошеннических действий. Для снятия блокировки обратитесь в контактный центр банка по телефонам, приведенным на обратной стороне Вашей карты либо в любое подразделение банка.";
	String PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE                      = "Операция отказана банком по подозрению в мошенничестве. Обратитесь в контактный центр банка по телефонам, приведенным на обратной стороне Вашей карты либо в любое подразделение банка.";
	String REQUIRED_ADDITIONAL_CONFIRM_DEFAULT_ERROR_MESSAGE                = "Необходимо дополнительное подтверждение операции в контактном центре банка.";
}
