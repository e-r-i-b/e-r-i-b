package com.rssl.phizic.messaging.mail.rsalarm;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.mobilebank.MessageInfo;
import com.rssl.phizic.gate.mobilebank.SendMessageError;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.mail.SmsMessage;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author eMakarov
 * @ created 24.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class RSAlarmSmsTransportService implements SmsTransportService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	Session session;

	/**
	 * ctor
	 */
	public RSAlarmSmsTransportService()
	{
		createSession();
	}

	public void sendSms(String toPhone, String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		if(StringHelper.isEmpty(toPhone))
			throw new IllegalArgumentException("Не задан номер");
		RSAlarmMessage msg = new RSAlarmMessage(session);
		PhoneAddress phoneAddress = new PhoneAddress(toPhone);

		try
		{
			msg.setText(text);
			Transport.send(msg, new Address[]{phoneAddress});
		}
		catch(Exception ex)  //т.к. MesageException не отлавливается
		{
			log.error("Ошибка при отправке сообщения", ex);
			throw new IKFLMessagingException(ex.toString(), ex);
		}

	}

	public void sendSms(String toPhone, TranslitMode translit, String text, String textToLog, Long priority) throws IKFLMessagingException
	{
		RSAlarmMessage msg = new RSAlarmMessage(session);
		toPhone = "+" + PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(toPhone);
		PhoneAddress phoneAddress = new PhoneAddress(toPhone, translit);

		try
		{
			msg.setText(text);
			Transport.send(msg, new Address[]{phoneAddress});
		}
		catch(Exception ex)  //т.к. MesageException не отлавливается
		{
			log.error("Ошибка при отправке сообщения", ex);
			throw new IKFLMessagingException("Не удалось отправить смс-сообщение", ex);
		}
	}

	public Map<String, SendMessageError> sendSmsWithIMSICheck(MessageInfo messageInfo, Long priority, String... toPhones) throws IKFLMessagingException
	{
		throw new UnsupportedOperationException("Отправка смс с проверкой IMSI не поддерживается в RS-Alarm.");
	}

	public SmsMessage receive() throws IKFLMessagingException
	{
		String host    = session.getProperty("mail.rsalarm.host");
		int port       = Integer.parseInt(session.getProperty("mail.rsalarm.port"));
		String carrier = session.getProperty("mail.rsalarm-smpp.carrier");

		SmsMessage smsMessage = null;
		String messageId = null;
		String messageCarrier = null;
		try
		{
			RSAlarmConnect rsaconn = new RSAlarmConnect(host, port);
			RSAlarmConnect.Properties properties;

			do
			{
				properties = rsaconn.Accept(null, messageId);

				if(properties.getProperty(RSAlarmConnect.RSALARM_PROP_RESULT, RSAlarmConnect.RSALARM_TYPE_INTEGER).toNumber() != 0)
				{
					rsaconn.shutdown();
					// сообщений нет
					return null;
				}

				messageCarrier = properties.getProperty(RSAlarmConnect.RSALARM_PROP_CARRIER_NAME, RSAlarmConnect.RSALARM_TYPE_STRING).toString();
				messageId      = properties.getProperty(RSAlarmConnect.RSALARM_PROP_MESSAGE_ID, RSAlarmConnect.RSALARM_TYPE_STRING).toString();
			}
			while (!carrier.equals(messageCarrier));

			String from = properties.getProperty(RSAlarmConnect.RSALARM_PROP_MESSAGE_ADDR, RSAlarmConnect.RSALARM_TYPE_STRING).toString();
			String text = properties.getProperty(RSAlarmConnect.RSALARM_PROP_MESSAGE_TEXT, RSAlarmConnect.RSALARM_TYPE_STRING).toString();

			smsMessage = new SmsMessage(from, text);

			rsaconn.Delete(messageId);

			rsaconn.shutdown();
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении сообщения из RS-Alarm.", ex);
			throw new IKFLMessagingException("Ошибка при получении сообщения из RS-Alarm.", ex);
		}
		catch (Throwable ex)
		{
			log.error("Ошибка при получении сообщения из RS-Alarm.", ex);
			throw new IKFLMessagingException("Ошибка при получении сообщения из RS-Alarm.");
		}

		return smsMessage;
	}

	/*
	 * создаем сессию
	 * читаем свойства из messaging.properties и подгружаем инормацию из javamail.address.map
	 */
	private void createSession()
	{
		PropertyReader config = ConfigFactory.getReaderByFileName("messaging.properties");
		session = Session.getInstance(config.getAllProperties());
	}
}
