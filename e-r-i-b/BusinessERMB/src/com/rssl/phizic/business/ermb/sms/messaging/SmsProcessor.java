package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.sms.inbox.SmsDublicateFilter;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ermb.ErmbSmsContext;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.synchronization.types.SMSRq;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 08.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class SmsProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private static final String UNKNOWN_ERROR_MESSAGE = "Операция временно недоступна. Пожалуйста, повторите попытку позже.";
	private static final ErmbProfileBusinessService ermbProfileService = new ErmbProfileBusinessService();

	SmsProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		SMSRq smsXmlRequest = xmlRequest.getSmsRq();
		ErmbSmsContext.setIncomingSMSRequestUID(smsXmlRequest.getRqUID());

		try
		{
			updateRequestTime(smsXmlRequest.getPhone());

			if (!checkDuplicate(smsXmlRequest))
				return;
			// 2. Создаем реквизиты выполнителя команд
			ExecutorRequisites requisites = new SmsCommandRequisites(smsXmlRequest, getModule());
			// 3. Создаем выполнитель команд
			CommandExecutor executor = new CommandExecutor(requisites);
			// 4. Выполняем команду
			executor.run();
		}
		finally
		{
            ErmbSmsContext.clear();
		}
	}

	private void updateRequestTime(PhoneNumber phoneNumber)
	{
		try
		{
			Calendar previousTime = ermbProfileService.getLastRequestTime(phoneNumber);
			ErmbSmsContext.setPreviousSmsTime(previousTime);

			Calendar currentTime = Calendar.getInstance();
			ErmbSmsContext.setCurrentSmsTime(currentTime);

			ermbProfileService.updateLastRequestTime(phoneNumber, currentTime);
		}
		catch (BusinessException e)
		{
			ErmbSmsContext.setCurrentSmsTime(Calendar.getInstance());
			log.error("Ошибка обновления даты запроса", e);
		}
	}

	private boolean checkDuplicate(SMSRq xmlRequest)
	{
		SmsDublicateFilter filter = new SmsDublicateFilter();
		try
		{
			filter.execute(xmlRequest);
			return true;
		}
		catch (UserErrorException e)
		{
			// Ошибка, готовая для показа пользователю (произошло дублирование записей)
			log.debug(e);
			if (xmlRequest != null)
				SmsSenderUtils.notifyClientMessage(xmlRequest.getPhone(), e.getTextMessage());
		}
		catch (Exception e)
		{
			// Неизвестная ошибка, пишем в лог и отправляем сообщение клиенту
			log.error(UNKNOWN_ERROR_MESSAGE, e);
			if (xmlRequest != null)
				SmsSenderUtils.notifyClientMessage(xmlRequest.getPhone(), new TextMessage(UNKNOWN_ERROR_MESSAGE));
		}
		return false;
	}

	public boolean writeToLog()
	{
		ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
		return config.isSmsLoggerUse();
	}
}
