package com.rssl.phizic.messaging.mail.rsalarm;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.TranslitMode;

import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: erkin $
 * @ $Revision: 73843 $
 */
public class RSAlarmTransport extends Transport
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String MAIL_RSALARM_HOST   = "mail.rsalarm.host";
	private static final String MAIL_RSALARM_PORT   = "mail.rsalarm.port";
	private static final String MAIL_RSALARM_ENTITY = "mail.rsalarm.entity";

	private String host;
	private int    port;
	private String entyty;
	private String carrier;
	private Sender sender;

	/**
	 * ctor
	 * @param session Session object for this Transport.
	 * @param urlName URLName object to be used for this Transport
	 */
	public RSAlarmTransport(Session session, URLName urlName) throws Throwable
	{
		super(session, urlName);

		try
		{
			initialize(session, urlName);
		}
		catch (Throwable e)
		{
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	private void initialize(Session session, URLName urlName) throws RSAlarmTransportException
	{
		host   = session.getProperty(MAIL_RSALARM_HOST);
		port   = Integer.parseInt(session.getProperty(MAIL_RSALARM_PORT));
		entyty = session.getProperty(MAIL_RSALARM_ENTITY);
		String protocol = urlName.getProtocol();
		carrier = session.getProperty("mail." + protocol + ".carrier");

		if (protocol.equals("rsalarm-smpp"))
			sender = new SMPPSender();
		else if (protocol.equals("rsalarm-smtp"))
			sender = new SMTPSender();
		else
			throw new RSAlarmTransportException("Unsupported protocol");
	}

	/**
	 * 	do nothing
	 */
	public void connect() throws MessagingException
	{
	}

	/**
	 * 	do nothing
	 */
	public void connect(String name, int i, String name1, String name2) throws MessagingException
	{
	}

	/**
	 * 	do nothing
	 */
	public void connect(String name, String name1) throws MessagingException
	{
	}

	/**
	 * 	do nothing
	 */
	public void connect(String name, String name1, String name2) throws MessagingException
	{
	}

	public void sendMessage(Message message, Address[] addresses) throws MessagingException
	{
		RSAlarmConnect rsaconn = null;

		try
		{
			rsaconn = getConnection();
			sender.send(rsaconn, message, addresses);
		}
		finally
		{
			if(rsaconn != null)
			{
				try
				{
					rsaconn.shutdown();
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
		}
	}

	private RSAlarmConnect getConnection()
	{
		return new RSAlarmConnect(host, port);
	}

	private abstract class Sender
	{
		abstract void send(RSAlarmConnect connect, Message message, Address[] addresses) throws MessagingException;

		protected void sendMessage(RSAlarmConnect rsaconn, String to, String path, String content, String subj) throws MessagingException
		{
			RSAlarmConnect.Properties results = null;
			try
			{
				results = rsaconn.Submit(entyty, to, path, content, subj);
			}
			catch (IOException e)
			{
				log.error("Ошибка при отсылке сообщения", e);
				throw new RSAlarmTransportException("Ошибка при отсылке сообщения", e);
			}

			RSAlarmConnect.Property result = results.getProperty(RSAlarmConnect.RSALARM_PROP_RESULT, RSAlarmConnect.RSALARM_TYPE_INTEGER);
			int i = result.toNumber();

			if (i != 0) {
				log.error("При отправке сообщения возникла ошибка. Код ошибки из RS-Alarm'a: " + i);
				throw new RSAlarmTransportException("При отправке сообщения возникла ошибка. Код ошибки из RS-Alarm'a: " + i);
			}
		}

		protected String messageToString(Message message, String carrierName) throws MessagingException
		{
			try
			{
				String content = "";
				if ("SMPP".equals(carrierName)) {
					content = (String)message.getContent();
				} else if ("SMTP".equals(carrierName)) {
					MimeMultipart messageMM = (MimeMultipart)(message.getContent());
					int count = messageMM.getCount();
					for (int i = 0; i < count; i++) {
						content += messageMM.getBodyPart(i).getContent();
					}
				} else {
					throw new RSAlarmTransportException("Неизвестная среда передачи сообщений. Должна быть либо SMTP, либо SMPP (получено '"+carrierName+"')");
				}

				if (!(content instanceof String))
					throw new RSAlarmTransportException("message.content must be java.lang.String");

				return content;
			}
			catch (IOException e)
			{
				throw new RSAlarmTransportException("Ошибка получения текста сообщения", e);
			}
		}
	}

	private class SMPPSender extends RSAlarmTransport.Sender
	{
		public void send(RSAlarmConnect connect, Message message, Address[] addresses) throws MessagingException
		{
			String text = messageToString(message, "SMPP");

			for (Address address : addresses)
			{
				TranslitMode translit = ((PhoneAddress) address).getTranslit();
				switch (translit)
				{
					case DEFAULT:
						sendMessage(connect, address.toString() + ",,unicode", carrier, text, null);
						break;
					case TRANSLIT:
						sendMessage(connect, address.toString() + ",,translit", carrier, text, null);
						break;
					default:
						throw new UnsupportedOperationException("Неизвестный режим транслитерации: " + translit);
				}
			}
		}
	}

	private class SMTPSender extends RSAlarmTransport.Sender
	{
		public void send(RSAlarmConnect connect,  Message message, Address[] addresses) throws MessagingException
		{
			String text = messageToString(message, "SMTP");

			for (Address address : addresses)
			{
				sendMessage(connect, address.toString(), carrier, text, message.getSubject());
			}
		}


	}
}