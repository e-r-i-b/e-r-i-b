package com.rssl.phizic.operations.messageTranslate;

import com.rssl.phizic.logging.Constants;

/**
 * @author vagin
 * @ created 23.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция списка справочника запрос/ответ для логов ЦСА
 */
public class MessageTranslateCSAListOperation extends MessageTranslateListOperation
{
	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}
}
