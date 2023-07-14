package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.mdm.web.service.generated.RequestData;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * типы сообщений в приложение мдм
 */

public enum MessageType
{
	GetStoredMDMId(new GetStoredMDMIdRequestProcessor()),
	GetExternalSystemMDMId(new GetExternalSystemMDMIdRequestProcessor()),
	unknown(null)
	;

	private final ProcessorBase processor;

	private MessageType(ProcessorBase processor)
	{
		this.processor = processor;
	}

	/**
	 * @return процессор запроса
	 */
	public ProcessorBase getProcessor()
	{
		return processor;
	}

	/**
	 * получить тип запроса
	 * @param requestData данные запроса
	 * @return тип сообщения
	 */
	public static MessageType getType(RequestData requestData)
	{
		if (requestData.getGetStoredMDMIdRq() != null)
			return GetStoredMDMId;
		if (requestData.getGetExternalSystemMDMIdRq() != null)
			return GetExternalSystemMDMId;

		return unknown;
	}
}
