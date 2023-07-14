package com.rssl.phizic.messaging.mail.rsalarm;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.messaging.TranslitMode;
import junit.framework.TestCase;

import java.util.Enumeration;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 64708 $
 */
@SuppressWarnings({"JavaDoc"})
public class MessageSendingTest extends TestCase
{
	private java.util.Properties properties;
	private String toEmail;
	private String toPhone;

	private Session session;

	protected void setUp() throws Exception
	{
		super.setUp();
		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("messaging.properties");
		properties = propertyReader.getAllProperties();
		properties.setProperty("mail.debug", "true");  // JUST TURN DEBUG ON
		toEmail = properties.getProperty("mail.smtp.user");
		toPhone = properties.getProperty("mail.phone.user");
		toPhone = toPhone == null ? "000" : toPhone;
		// мой телефон 000. ты не думай что это пароль - звони
		//                                          (c) Пикник

		session = Session.getInstance(properties);

		PropertyReader addressMap = ConfigFactory.getReaderByFileName("javamail.address.map");
		java.util.Properties addressMapProps = addressMap.getAllProperties();

		for(Enumeration<?> enumeration = addressMapProps.propertyNames(); enumeration.hasMoreElements();)
		{
			String addressType = (String) enumeration.nextElement();
			String protocol = addressMapProps.getProperty(addressType);
			session.setProtocolForAddress(addressType, protocol);
		}
	}

	protected void tearDown() throws Exception
	{
		properties = null;
		toEmail = null;
		toPhone = null;
		super.tearDown();
	}

	public void sendEmail() throws Exception
	{
		assertNotNull("Задайте системное свойство -Dmail.smtp.user=ievgrafov@softlab.ru (тока плиз свое мыло :))", toEmail);

		MimeMessage msg = new MimeMessage(session);

		Address address = new InternetAddress(toEmail);
		msg.setFrom(address);
		msg.setRecipient(Message.RecipientType.TO, address);

		msg.setSubject("проверка", "utf-8");
		msg.setText("<p><font size=\"9\">\u06de<font><p><table border=\"1\"><tr><td>1</td><td>1</td></tr></table>", "utf-8", "html");
		
		Transport.send(msg);
	}

	public void sendSms() throws Exception
	{
		RSAlarmMessage msg = new RSAlarmMessage(session);
		PhoneAddress phoneAddress = new PhoneAddress(toPhone, TranslitMode.DEFAULT);

		msg.setText("sms\nсмс");

		Transport.send(msg, new Address[]{phoneAddress});
	}

	public void sendEmailAlarm() throws Throwable
	{
		String host = properties.getProperty("mail.rsalarm.host");
		int    port = Integer.parseInt(properties.getProperty("mail.rsalarm.port"));

		String resource = properties.getProperty("mail.rsalarm.entity");
		String path     = properties.getProperty("mail.rsalarm-smtp.carrier");
		String text     = "sample пример <br/> <hr/>";

		RSAlarmConnect conn = new RSAlarmConnect(host, port);
		
		conn.Submit(resource, toEmail, path, text, null);
	}

	public void test()
	{
	}
}