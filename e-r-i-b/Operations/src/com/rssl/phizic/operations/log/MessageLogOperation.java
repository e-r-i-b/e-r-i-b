package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.messages.translate.MessageTranslateService;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 29.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class MessageLogOperation extends LogFilterOperationBase
{
	private static final SimpleService service = new SimpleService();
	protected static final MessageTranslateService messageTranslateService = new MessageTranslateService();

	private MessagingLogEntryBase messagingLogEntry;

	public void initialize(Long id, String type) throws BusinessException
	{
		if (type == null)
			type = "OTHER";
		MessagingLogEntryBase temp;
		if (type.equals("OTHER"))
		{
			temp = service.findById(MessagingLogEntry.class, id, getInstanceName());
		}
		else if(type.equals("GUEST"))
		{
			temp = service.findById(GuestMessagingLogEntry.class, id, getInstanceName());
		}
		else
		{
			temp = service.findById(FinancialMessagingLogEntry.class, id, getInstanceName());
		}
		if (temp == null)
			throw new BusinessException("Ќе установлены параметры операции");

		messagingLogEntry = temp;
	}

	public MessagingLogEntryBase getEntity() throws BusinessException
	{
		return messagingLogEntry;
	}

	public List<String> getSystemList()
	{
		System[] values = System.values();
		List<String> systemList = new ArrayList<String>();
		for(System value : values)       
			systemList.add(value.name());
		
		return systemList;
	}

	/**
	 * ѕолучает список значений-подсказок дл€ "жиового поиска"
	 * @param text - введенна€ строка
	 * @return найденные значени€
	 * @throws BusinessException
	 */
	public List<String> getMessTranslateList(String text, TypeMessageTranslate type) throws BusinessException
	{
		return messageTranslateService.getCodeOrTranslateList(text, type, getInstanceName());
	}
}
