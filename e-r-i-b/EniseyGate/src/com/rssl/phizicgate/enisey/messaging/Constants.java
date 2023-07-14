package com.rssl.phizicgate.enisey.messaging;

/**
 * @author mihaylov
 * @ created 12.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class Constants
{
	public static final String OUTGOING_MESSAGES_CONFIG = "com/rssl/phizicgate/enisey/messaging/enisey-messaging-config.cfg.xml";

	public static final String BILLING_INTERACTION_ERROR        = "ѕроизошла ошибка при взаимодействии с биллинговой системой";
	public static final String PAYMENT_TYPE_ERROR               = "Ќеверный тип платежа - ожидаетс€ AccountPaymentSystemPayment";
	public static final String COMISSION_TAG                    = "Comission";
	public static final String DEBT_SUM_TAG                     = "Debt";
	public static final String NAMEBS_TAG                       = "NameBS";
	public static final String RECIPIENT_NAME_TAG               = "Name";
	public static final String CODE_RECIPIENT_TAG               = "CodeRecipient";
	public static final String CODE_SERVICE_TAG                 = "CodeService";
	public static final String TOTAL_SUM_TAG                    = "TotalSum";
	public static final String ATTRIBUTES_TAG                   = "Attributes";
	public static final String ATTRIBUTE_TAG                    = "Attribute";
	public static final String CODE_TAG                         = "NameBS";
	public static final String VALUE_TAG                        = "Value";
	public static final String ERROR_CODE_TAG                   = "ErrorCode";
	public static final String ERROR_DESCRIPTION_TAG            = "ErrorDescription";
	public static final String PAYMENT_ID_TAG                   = "PaymentID";

	public static final String ATTRIBUTES_REQUEST               = "requestBillingAttr_q";
	public static final String EXECUTE_REQUEST                  = "executeBillingPayment_q";
	public static final String PREPARE_REQUEST                  = "prepareBillingPayment_q";
	public static final String REVOKE_REQUEST                   = "revokeBillingPayment_q";

	public static final String AMOUNT_FIELD_NAME                = "q3eb2fZ70";  //поле сумма
	public static final String DEBT_FIELD_NAME                  = "q3eb2fZ68";  //поле задолженность

	public static final String STEP_1                           = "step_1";
	public static final String STEP_2                           = "step_2";
	public static final String STEP_3                           = "step_3";
	public static final String STEP_4                           = "step_4";

	public static final int MAX_COUNT_TRANSACTIONS              = 20; //максимальное количество прверок при получении комиссии
}
