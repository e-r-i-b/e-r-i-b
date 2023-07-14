package com.rssl.phizic.web.blockingrules;

/**
 * @author osminin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Константы для редактирования правил блокировки
 */
public interface EditConstants
{
	String DATESTAMP_FORMAT = "dd.MM.yyyy";
	String TIMESTAMP_FORMAT = "HH:mm";

	String WRONG_FROM_DATETIME_PUBLISH = "Укажите корректное значение даты и времени публикации в формате ДД.ММ.ГГГГ; ЧЧ:ММ.";
	String WRONG_FROM_TIME_PUBLISH     = "Укажите корректное значение времени публикации";
	String WRONG_TO_DATETIME_PUBLISH   = "Укажите корректное значение даты и времени снятия с публикации в формате ДД.ММ.ГГГГ; ЧЧ:ММ.";
	String WRONG_TO_TIME_PUBLISH       = "Укажите корректное значение времени снятия с публикации";

	String USE_ERMB_MESSAGE_FIELD   = "useErmbMessage";
	String USE_MAPI_MESSAGE_FIELD   = "useMapiMessage";
	String USE_ATM_MESSAGE_FIELD    = "useAtmMessage";
	String APPLY_TO_ERIB_FIELD      = "applyToERIB";
	String APPLY_TO_MAPI_FIELD      = "applyToMAPI";
	String APPLY_TO_ERMB_FIELD      = "applyToERMB";
	String APPLY_TO_ATM_FIELD       = "applyToATM";
	String ERIB_MESSAGE_FIELD       = "eribMessage";
	String MAPI_MESSAGE_FIELD       = "mapiMessage";
	String ERMB_MESSAGE_FIELD       = "ermbMessage";
	String ATM_MESSAGE_FIELD        = "atmMessage";
	String NAME_FIELD               = "name";
	String DEPARTMENTS_FIELD        = "departments";
	String STATE_FIELD              = "state";
	String RESUMING_TIME_FIELD      = "resumingTime";
	String RESUMING_DATE_FIELD      = "resumingDate";
	String CONFIGURE_NOTIFICATION_FIELD     = "configureNotification";
	String FROM_PUBLISH_FIELD               = "fromPublish";
	String FROM_PUBLISH_DATE_FIELD          = "fromPublishDate";
	String FROM_PUBLISH_TIME_FIELD          = "fromPublishTime";
	String TO_PUBLISH_FIELD                 = "toPublish";
	String TO_PUBLISH_DATE_FIELD            = "toPublishDate";
	String TO_PUBLISH_TIME_FIELD            = "toPublishTime";
	String FROM_REGISTRATION_DATE_FIELD     = "fromRestrictionDate";
	String FROM_REGISTRATION_TIME_FIELD     = "fromRestrictionTime";
	String TO_REGISTRATION_DATE_FIELD       = "toRestrictionDate";
	String TO_REGISTRATION_TIME_FIELD       = "toRestrictionTime";
}
