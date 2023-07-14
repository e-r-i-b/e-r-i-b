package com.rssl.phizic.business.ermb.sms.inbox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.synchronization.types.SMSRq;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author Gulov
 * @ created 11.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализует логику защиты от дублирования одинаковых СМС.
 */
public class SmsDublicateFilter
{
	private static final SmsInBoxService service = new SmsInBoxService();

	private static final String EXIST_SMS_MESSAGE = "Операция не выполнена. Получен повторный запрос." +
			" Для проведения операции измените текст запроса или направьте его повторно через %s минут.";

	///////////////////////////////////////////////////////////////////////////

	public void execute(SMSRq request)
	{
		SmsInBox current = createSmsInbox(request);
		SmsInBox old = findInbox(current);
		if (old == null)
		{
			SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
			save(current, smsConfig.getDublicateExpireInterval());
		}
		else
			throw new UserErrorException(new TextMessage(String.format(EXIST_SMS_MESSAGE, getRemainingInterval(old, current))));
	}

	/**
	 * Возвращает количество минут, оставшееся до следующей отправки СМС-команды.
	 * @param old - старая СМС-команда
	 * @param current - новая СМС-команда
	 * @return - количество минут
	 */
	private long getRemainingInterval(SmsInBox old, SmsInBox current)
	{
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		Date date = DateUtils.addMinutes(old.getTime().getTime(), smsConfig.getDublicateExpireInterval());
		long result = (date.getTime() - current.getTime().getTimeInMillis()) / (1000 * 60);
		return result == 0 ? 1 : result;
	}

	private SmsInBox findInbox(SmsInBox current)
	{
		try
		{
			return service.findInbox(current);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка получения последней СМС", e);
		}
	}

	private void save(SmsInBox smsInBox, int remainingInterval)
	{
		try
		{
//			Сохранить новую СМС
			service.save(smsInBox);
		}
		catch (DublicateSmsInBoxException e)
		{
//			отправить сообщение клиенту о дублировании СМС
			throw new UserErrorException(new TextMessage(String.format(EXIST_SMS_MESSAGE, remainingInterval)), e);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка сохранения новой СМС " + smsInBox + " в ERMB_SMS_INBOX", e);
		}
	}

	private SmsInBox createSmsInbox(SMSRq request)
	{
		SmsInBox result = new SmsInBox();
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		result.setUuid(request.getRqUID());
		result.setTime(request.getRqTime());
		result.setPhone(request.getPhone());
		result.setText(StringUtils.left(request.getText(), smsConfig.getSmsMaxLength()).toUpperCase());
		return result;
	}
}
