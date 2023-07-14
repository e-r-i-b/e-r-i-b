package com.rssl.phizic.operations.messageTranslate;

import com.rssl.phizic.logging.Constants;

/**
 * @author vagin
 * @ created 23.10.2012
 * @ $Author$
 * @ $Revision$
 * Операция редактирования справочника запросы/ответы для логов ЦСА
 */
public class MessageTranslateCSAEditOperation extends MessageTranslateEditOperation
{
	protected String getInstanceName()
	{
		return Constants.CSA_DB_LOG_INSTANCE_NAME;
	}
}
