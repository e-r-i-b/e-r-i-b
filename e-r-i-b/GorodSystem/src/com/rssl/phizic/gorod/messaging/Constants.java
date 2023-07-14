package com.rssl.phizic.gorod.messaging;

/**
 * @author Gainanov
 * @ created 16.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String OUTGOING_MESSAGES_CONFIG                         = "com/rssl/phizic/gorod/messaging/gorod-messaging-config.cfg.xml";
	public static final String COUNTER_NAME                                     = "GorodMessages";
	public static final String LOGIC_MESSAGE_PREFIX                             = "PHIZ_GATE_LOGIC_EXCEPTION_START";
	public static final String LOGIC_MESSAGE_SUFFIX                             = "PHIZ_GATE_LOGIC_EXCEPTION_END";
	public static final String ACCOUNT_FIELD_NAME                               = "licAccount";
	public static final String ACCPU_FIELD_NAME                                 = "ACCPU";
	public static final String DEBT_FIELD_NAME                                  = "debtFieldXN";
	public static final String SERVICE_PREFIX                                   = "Service";
	public static final String ADD_SERVICE_PREFIX                               = "AddService";
	public static final String FIO_FIED_NAME                                    = "ФИО";
	public static final String ADDRESS_FIED_NAME                                = "АДРЕС";
	public static final String CALENDAR_FIELD_NAME                              = "payPeriod";
	public static final int MAX_ITERATION_SIZE                                  = 20;                                   //максимальное количество попыток запроса суммы
	public static final Long FIELD_MAX_LENGTH                                   = 40L;
	public static final Long FIELD_MIN_LENGTH                                   = 0L;
	public static final String SS_DELIMITER                                     = "_";                                  //разделитель для построения внешнего идентификатора полей сложных услуг
	public static final String SS_PREFIX                                        = "SS-";                                //префикс для внешнего идетификатора полей сложных услуг
	public static final String SS_AMOUNT_PREFIX                                 = "SSAMOUNT-";                          //префикс для полей сумм подуслуг сложных услуг
	public static final String SS_NAME_PREFIX                                   = "SSNAME-";                            //префикс для полей нзваний подуслуг
	public static final String SS_NAME_FIELD_NAME                               = "Услуга";
	public static final String SS_AMOUNT_FIELD_NAME                             = "Сумма";
	public static final String SERVICE_TYPE_ADD                                 = "ServiceType-ADD";
	public static final String NAME_MAIN_SERVICE_FIELD                          = "fcs4e56v3F";
	public static final String PAYORDER_AMOUNT_FIELD_NAME                       = "amount-field-name";                  //поле суммы, лежащее в PayOrder.
	public static final String MAIN_SUM_NAME_FIELD_NAME                         = "main-sum-name-field";                //поле, в котором храним значение externalId поля с признаком главной суммы.
}
