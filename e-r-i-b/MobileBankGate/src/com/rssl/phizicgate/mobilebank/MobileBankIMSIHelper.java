package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.rssl.phizgate.mobilebank.GatePhoneHelper.hidePhoneNumber;
import static com.rssl.phizicgate.mobilebank.MBKConstants.MBK_PHONE_NUMBER_FORMAT;

/**
 * @author niculichev
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankIMSIHelper
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final int BEFORE_IMSI_CHECK_FIRST_DELAY  = 3000; //количество мс для первой паузы перед проверкой IMSI
	private static final int BEFORE_IMSI_CHECK_DELAY        = 1000; //количество мс для последующих пауз перед проверкой IMSI
	private static final int COUNT_ITERATE_IMSI_CHECK       = 10 + 1; //количество попыток выполнения проверки IMSI

	private JDBCActionExecutor executor;

	public MobileBankIMSIHelper(JDBCActionExecutor executor)
	{
		this.executor = executor;
	}

	/**
	 * Отправка смс с проверкой IMSI
	 * @param messageInfo информация о сообщении
	 * @param phones номера телефонов на которые отправляется сообщение
	 * @return мап ошибок(телефон - ошибка)
	 */
	public Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo,String mbSystemId, String... phones)
	{
		if (ArrayUtils.isEmpty(phones))
			throw new IllegalArgumentException("Аргумент 'toPhone' не может быть пустым");

		if (StringHelper.isEmpty(messageInfo.getText()) || StringUtils.isBlank(messageInfo.getText()))
			throw new IllegalArgumentException("Аргумент 'text' не может быть пустым или blank");

		// номер телефона - тип ошибки
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		//messageId отправленных сообщений
		Map<Integer, String> messageIds = new HashMap<Integer, String>();

		//1. отправляем на все телефоны смс
		for (String phone : phones)
		{
			String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(phone);

			try
			{
				String hidePhone = hidePhoneNumber(phone);
				log.trace("Отправка СМС с проверкой IMSI для номера телефона '" + hidePhone + "' ...");

				Integer messId = executor.execute(new SendSmsWithIMSICheckJDBCAction(phoneMB, messageInfo, mbSystemId));
				messageIds.put(messId, phone);

				log.trace("СМС с проверкой IMSI для номера телефона " + hidePhone + " отправлено в Мобильный Банк");
			}
			catch (SystemException e)
			{
				log.error(e.getMessage(), e);
				errorPhones.put(phone, SendMessageError.error);
			}
		}

		// не получилось отправить ни на один номер
		if (messageIds.isEmpty())
			return errorPhones;

		//2. пытаемся получить результаты проверки IMSI после небольшой паузы
		sleep(BEFORE_IMSI_CHECK_FIRST_DELAY);

		//3. результат проверки IMSI
		errorPhones.putAll(checkIMSI(messageIds));

		return errorPhones;
	}

	private Map<String, SendMessageError> checkIMSI(Map<Integer, String> messageIds)
	{
		// номер телефона - тип ошибки
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		// сообщения которые уже проверены
		Map<Integer, ImsiCheckResult> checkResults = new HashMap<Integer, ImsiCheckResult>();

		// сообщения которые еще не проверены
		List<Integer> notCheckedMessages = new ArrayList<Integer>(messageIds.keySet());

		int checkNumber = 0;

		// спрашиваем у мобильного банка, какие сообщения уже отправлены и имеют результат
		do
		{
			List<Integer> messIdsTmp = new ArrayList<Integer>(notCheckedMessages);
			for (Integer messageId : messIdsTmp)
			{
				ImsiCheckResult result = null;

				try
				{
					log.trace("Получаем результат проверки IMSI для сообщения с ID='" + messageId + "' ...");

					String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(messageIds.get(messageId));
					result = executor.execute(
							new GetCheckIMSIResultJDBCAction(messageId, phoneMB));

					log.trace("Результат проверки IMSI для сообщения с ID=" + messageId + " успешно получено: " + result.getDescription());
				}
				catch (SystemException e)
				{
					log.error(e.getMessage(), e);
					errorPhones.put(messageIds.get(messageId), SendMessageError.error);
				}

				if (result != ImsiCheckResult.yet_not_send)
				{
					checkResults.put(messageId, result);
					notCheckedMessages.remove(messageId);
				}
			}

			if (++checkNumber >= COUNT_ITERATE_IMSI_CHECK)
				break;

			if (!notCheckedMessages.isEmpty()) //делаем паузу перед следующей проверкой
			{
				sleep(BEFORE_IMSI_CHECK_DELAY);
			}

		} while (!notCheckedMessages.isEmpty());


		// наконец все сообщения отправлены, обрабатываем результат, который вернул мобильный банк
		List<String> corruptIMSIPhones = new ArrayList<String>();
		List<String> clientErrorPhones = new ArrayList<String>();

		for (Integer messageId : checkResults.keySet())
		{
			ImsiCheckResult res = checkResults.get(messageId);
			if (res == ImsiCheckResult.imsi_error)
				corruptIMSIPhones.add(messageIds.get(messageId));

			if (res == ImsiCheckResult.client_error)
				clientErrorPhones.add(messageIds.get(messageId));
		}

		//а) если на все телефоны удалось отправить сообщение - всё отлично, делать больше ничего не надо
		if (corruptIMSIPhones.isEmpty() && notCheckedMessages.isEmpty() && errorPhones.isEmpty() && clientErrorPhones.isEmpty())
			return Collections.emptyMap();

		for (String phone : corruptIMSIPhones)
			errorPhones.put(phone, SendMessageError.imsi_error);

		for (String phone : clientErrorPhones)
			errorPhones.put(phone, SendMessageError.error);

		for (Integer id : notCheckedMessages)
			errorPhones.put(messageIds.get(id), SendMessageError.error);

		return errorPhones;
	}

	private void sleep(int delay)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch (InterruptedException e)
		{
			//ничего страшного
		}
	}

	/**
	 * Отправка запроса проверки IMSI без отправки СМС
	 * @param phones номера телефонов на которые отправляется сообщение
	 * @return мап ошибок(телефон - ошибка)
	 */
	public Map<String, SendMessageError> sendIMSICheck(String mbSystemId, String... phones)
	{
		if (ArrayUtils.isEmpty(phones))
			throw new IllegalArgumentException("Аргумент 'toPhone' не может быть пустым");

		// номер телефона - тип ошибки
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		//messageId отправленных сообщений
		Map<Integer, String> messageIds = new HashMap<Integer, String>();

		//1. отправляем на все телефоны смс
		for (String phone : phones)
		{
			String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(phone);

			try
			{
				String hidePhone = hidePhoneNumber(phone);
				log.trace("Отправка запроса проверки IMSI для номера телефона" + hidePhone);

				Integer messId = executor.execute(new CheckIMSIJDBCAction(phoneMB, mbSystemId));
				messageIds.put(messId, phone);

				log.trace("Запроса проверки IMSI для номера телефона " + hidePhone + " отправлен в Мобильный Банк");
			}
			catch (SystemException e)
			{
				log.error(e.getMessage(), e);
				errorPhones.put(phone, SendMessageError.error);
			}
		}

		// не получилось отправить ни на один номер
		if (messageIds.isEmpty())
			return errorPhones;

		//2. пытаемся получить результаты проверки IMSI после небольшой паузы
		sleep(BEFORE_IMSI_CHECK_FIRST_DELAY);

		//3. результат проверки IMSI
		errorPhones.putAll(checkIMSIWithoutSms(messageIds));

		return errorPhones;
	}

	private Map<String, SendMessageError> checkIMSIWithoutSms(Map<Integer, String> messageIds)
	{
		// номер телефона - тип ошибки
		Map<String, SendMessageError> errorPhones = new HashMap<String, SendMessageError>();

		// сообщения которые уже проверены
		Map<Integer, ImsiCheckResult> checkResults = new HashMap<Integer, ImsiCheckResult>();

		// сообщения которые еще не проверены
		List<Integer> notCheckedMessages = new ArrayList<Integer>(messageIds.keySet());

		int checkNumber = 0;

		// спрашиваем у мобильного банка, какие сообщения уже отправлены и имеют результат
		do
		{
			List<Integer> messIdsTmp = new ArrayList<Integer>(notCheckedMessages);
			for (Integer messageId : messIdsTmp)
			{
				ImsiCheckResult result = null;

				try
				{
					log.trace("Получаем результат проверки IMSI для сообщения с ID='" + messageId + "' ...");

					String phoneMB = MBK_PHONE_NUMBER_FORMAT.translate(messageIds.get(messageId));
					result = executor.execute(
							new GetCheckIMSIResultExJDBCAction(messageId, phoneMB));

					log.trace("Результат проверки IMSI для сообщения с ID=" + messageId + " успешно получено: " + result.getDescription());
				}
				catch (SystemException e)
				{
					log.error(e.getMessage(), e);
					errorPhones.put(messageIds.get(messageId), SendMessageError.error);
				}

				if (result != ImsiCheckResult.yet_not_check)
				{
					checkResults.put(messageId, result);
					notCheckedMessages.remove(messageId);
				}
			}

			if (++checkNumber >= COUNT_ITERATE_IMSI_CHECK)
				break;

			if (!notCheckedMessages.isEmpty()) //делаем паузу перед следующей проверкой
			{
				sleep(BEFORE_IMSI_CHECK_DELAY);
			}
		}
		while (!notCheckedMessages.isEmpty());

		// наконец все сообщения отправлены, обрабатываем результат, который вернул мобильный банк
		List<String> corruptIMSIPhones = new ArrayList<String>();
		List<String> clientErrorPhones = new ArrayList<String>();

		for (Integer messageId : checkResults.keySet())
		{
			ImsiCheckResult res = checkResults.get(messageId);
			if (res == ImsiCheckResult.imsi_fail)
				corruptIMSIPhones.add(messageIds.get(messageId));

			if (res == ImsiCheckResult.client_error)
				clientErrorPhones.add(messageIds.get(messageId));
		}

		//а) если на все телефоны удалось отправить сообщение - всё отлично, делать больше ничего не надо
		if (corruptIMSIPhones.isEmpty() && notCheckedMessages.isEmpty() && errorPhones.isEmpty() && clientErrorPhones.isEmpty())
			return Collections.emptyMap();

		for (String phone : corruptIMSIPhones)
			errorPhones.put(phone, SendMessageError.imsi_error);

		for (String phone : clientErrorPhones)
			errorPhones.put(phone, SendMessageError.error);

		for (Integer id : notCheckedMessages)
			errorPhones.put(messageIds.get(id), SendMessageError.error);

		return errorPhones;
	}
}

