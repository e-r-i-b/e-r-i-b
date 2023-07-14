package com.rssl.phizic.business.ermb.migration.list.task.hibernate;

/**
 * Констансты запроса в архивные базы
 * @author Puzikov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

class ArchiveQueryConstants
{
	//com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findPhonesByClient
	static final int C_PHONE_NUMBER           = 0;
	static final int C_SOURCE                 = 1;
	static final int C_SMS_COUNT              = 2;  //nullable
	static final int C_REGISTRATION_DATE      = 3;  //nullable
	static final int C_LAST_EVENT_CARD_DATE   = 4;  //nullable
	static final int C_UDBO                   = 5;  //nullable
	static final int C_VIP_OR_MVS             = 6;
	static final int C_OSB                    = 7;
	static final int C_VSP                    = 8;
	static final int C_CARD_NUMBER_BASE       = 9;  //nullable
	static final int C_SUR_NAME               = 10; //nullable
	static final int C_FIRST_NAME             = 11; //nullable
	static final int C_PATR_NAME              = 12; //nullable
	static final int C_DOCUMENT               = 13; //nullable
	static final int C_BIRTH_DATE             = 14; //nullable
	static final int C_PAY_CARD               = 15; //nullable
	static final int C_SUR_NAME_BASE          = 16; //nullable
	static final int C_FIRST_NAME_BASE        = 17; //nullable
	static final int C_PATR_NAME_BASE         = 18; //nullable
	static final int C_DOCUMENT_BASE          = 19; //nullable
	static final int C_BIRTH_DATE_BASE        = 20; //nullable
	static final int C_CLIENT_STATE_BASE      = 21; //nullable
	static final int C_LAST_INCOMING_SMS_DATE = 22; //nullable

	//com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.findConflictedByPhone
	static final int P_PHONE_NUMBER           = 0;
	static final int P_SUR_NAME               = 1;
	static final int P_FIRST_NAME             = 2;
	static final int P_PATR_NAME              = 3;  //nullable
	static final int P_DOCUMENT               = 4;
	static final int P_BIRTH_DATE             = 5;
	static final int P_VIP_OR_MVS             = 6;
	static final int P_TER_BANK               = 7;
	static final int P_OSB                    = 8;
	static final int P_VSP                    = 9;
	static final int P_IS_INFO                = 10; //nullable
	static final int P_CARD_NUMBER_BASE       = 11; //nullable
	static final int P_LAST_EVENT_CARD_DATE   = 12; //nullable
	static final int P_LAST_INCOMING_SMS_DATE = 13; //nullable
	static final int P_SMS_COUNT              = 14; //nullable

	//com.rssl.phizic.business.ermb.migration.list.entity.migrator.Archive.countCardRegistration
	static final int R_PHONE_NUMBER           = 0;
	static final int R_CNT                    = 1;
}
