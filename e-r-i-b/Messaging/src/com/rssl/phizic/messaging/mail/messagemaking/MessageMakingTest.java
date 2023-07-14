package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.mail.messagemaking.email.HtmlEmailMessageBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.email.InternetAddressBuilder;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.ResourcePropertyReader;
import freemarker.template.*;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * @author Evgrafov
 * @ created 02.06.2006
 * @ $Author: gladishev $
 * @ $Revision: 61544 $
 */

public class MessageMakingTest extends TestCase
{
	private static final String MESSAGING_TEMPLATES_PATH = "Settings/templates";
	private static final String TEST_TEMPLATE_NAME = "test.template";

	private java.util.Properties properties;
	private String toEmail;
	private String toPhone;
	private TranslitMode translit;
	private MailFormat mailFormat;

	protected void setUp() throws Exception
	{
		super.setUp();
		PropertyReader propertyReader = ConfigFactory.getReaderByFileName("messaging.properties");
		properties = propertyReader.getAllProperties();
		properties.setProperty("mail.debug", "true");  // JUST TURN DEBUG ON
		toEmail = System.getProperty("mail.smtp.user");
		toEmail = toEmail == null ? "000@null.com" : toEmail;  
		toPhone = System.getProperty("mail.phone.user");
		toPhone = toPhone == null ? "000" : toPhone;
		// мой телефон 000. ты не думай что это пароль - звони
		//                                          (c) Пикник
		String encoding = System.getProperty("mail.phone.encoding");
		translit = encoding == null ? TranslitMode.DEFAULT : TranslitMode.valueOf(encoding);
	}

	protected void tearDown() throws Exception
	{
		properties = null;
		toEmail = null;
		toPhone = null;
		super.tearDown();
	}

	public static void testFreeMarker() throws IOException, TemplateException
	{
		Configuration configuration = createConfiguration();
		Template template = configuration.getTemplate(TEST_TEMPLATE_NAME);

		assertNotNull(template);

		Map<String, Object> dataModelMap = new HashMap<String, Object>();
		dataModelMap.put("header", "Заголовок сообщения");
		dataModelMap.put("body", "Тело сообщения");
		dataModelMap.put("footer", "Подпись сообщения");

		StringWriter writer = new StringWriter();
		template.process(dataModelMap, writer);

		assertTrue(writer.toString().length() != 0);
	}

	public void testEmailMessageMaker() throws IOException, MessagingException
	{
		/*Configuration configuration = createConfiguration();
		Template template = configuration.getTemplate(TEST_TEMPLATE_NAME);

		assertNotNull(template);

		Map<String, String> messageBean = new HashMap<String, String>();

		messageBean.put("header", "Заголовог туловища сообщения");
		messageBean.put("body", "Туловищче сообщения");
		messageBean.put("footer", "Футер туловища сообщения");

		HtmlEmailMessageBuilder emailMessageMaker = new HtmlEmailMessageBuilder(template, Session.getInstance(properties), new InternetAddressBuilder());

		MimeMessage message = emailMessageMaker.create
				(
						new MockContactInfo(),
						new MockTransportInfo(),
						messageBean,
						""
				);

		Multipart multiPart = (Multipart) message.getContent();
		assertNotNull(multiPart);

		BodyPart bodyPart = multiPart.getBodyPart(0);
		assertNotNull(bodyPart);

		String content = (String) bodyPart.getContent();
		assertTrue(content.length() != 0);  */
	}

	private static Configuration createConfiguration()
			throws IOException
	{
		Configuration configuration = new Configuration();

		// - Set an error handler that prints errors so they are readable with a HTML browser.
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
		// - Use beans wrapper (recommmended for most applications)
		configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		// - Set the default charset of the template files
		configuration.setDefaultEncoding("windows-1251");
		// - Set the charset of the output. This is actually just a hint, that
		//   templates may require for URL encoding and for generating META element
		//   that uses http-equiv="Content-type".
		configuration.setOutputEncoding("windows-1251");
		// - Set the default locale
		configuration.setLocale(new Locale("ru", "RU"));
		// - Set the directory of the template files
		configuration.setDirectoryForTemplateLoading(new File(MESSAGING_TEMPLATES_PATH));
		return configuration;
	}
}
