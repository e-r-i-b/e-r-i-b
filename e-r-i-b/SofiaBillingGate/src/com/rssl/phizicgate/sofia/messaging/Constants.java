package com.rssl.phizicgate.sofia.messaging;

/**
 * @author gladishev
 * @ created 07.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String OUTGOING_MESSAGES_CONFIG         = "com/rssl/phizicgate/sofia/messaging/sofia-messaging-config.cfg.xml";
	public static final String ABONENT_ESK                      = "ESK";
	public static final String BILLING_INTERACTION_ERROR        = "Произошла ошибка при взаимодействии с биллинговой системой";
	public static final String ATTRIBUTES_REQUEST               = "requestBillingAttr_q";
	public static final String EXECUTE_REQUEST                  = "executeBillingPayment_q";
	public static final String PREPARE_REQUEST                  = "prepareBillingPayment_q";
	public static final String REVOKE_REQUEST                   = "revokeBillingPayment_q";

	public static final String AMOUNT_FIELD_NAME                = "e3eb2fZ70";  //поле сумма
	public static final String DEBT_FIELD_NAME                  = "e3eb2fZ68";  //поле задолженность

	public static final String STEP_1                           = "step_1";
	public static final String STEP_2                           = "step_2";
	public static final String STEP_3                           = "step_3";
	public static final String STEP_4                           = "step_4";
}
