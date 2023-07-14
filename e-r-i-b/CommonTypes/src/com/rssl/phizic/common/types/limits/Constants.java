package com.rssl.phizic.common.types.limits;

/**
 * @author osminin
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Константы для работы с лимитами
 */
public interface Constants
{
	String LIMIT_DELIMITER              = ";"; //разделитель лимитов для свойства limitsInfo сущности TransactionsJournal
	String LIMIT_PROPERTY_DELIMITER     = ","; //резделитель параметров лимита

	String ADD_TRANSACTION_REQUEST_NAME         = "addTransactionRq";
	String ROLLBACK_TRANSACTION_REQUEST_NAME    = "rollbackTransactionRq";
	String SAVE_PERSON_SETTINGS_REQUEST_NAME    = "savePersonSettingsRq";

	String PROFILE_INFO_TAG                     = "profileInfo";
	String BIRTH_DATE_TAG                       = "birthDate";
	String FIRST_NAME_TAG                       = "firstName";
	String SUR_NAME_TAG                         = "surName";
	String PATR_NAME_TAG                        = "patrName";
	String PASSPORT_NAME_TAG                    = "passport";
	String TB_TAG                               = "tb";

	String TRANSACTION_TAG                      = "transaction";
	String AMOUNT_TAG                           = "amount";
	String AMOUNT_VALUE_TAG                     = "amountValue";
	String AMOUNT_CUR_TAG                       = "amountCur";
	String OPERATION_DATE_TAG                   = "operationDate";
	String DOCUMENT_EXTERNAL_ID_TAG             = "documentExternalId";
	String EXTERNAL_ID_TAG                      = "externalId";
	String OPERATION_TYPE_TAG                   = "operationType";
	String CHANNEL_TYPE_TAG                     = "channelType";
	String LIMITS_TAG                           = "limits";
	String LIMIT_TAG                            = "limit";
	String LIMIT_TYPE_TAG                       = "limitType";
	String RESTRICTION_TYPE_TAG                 = "restrictionType";
	String EXTERNAL_GROUP_RISK_ID_TAG           = "externalGroupRiskId";

	String PERSON_SETTING_INFORMATION_TYPE_TAG  = "informationType";
	String PERSON_SETTING_DATA_TAG              = "data";

	String QUEUE_NAME                           = "jms/limits/LimitsAppQueue";
	String FACTORY_NAME                         = "jms/limits/LimitsAppQCF";

	String DB_INSTANCE                          = "LimitsApp";
}
