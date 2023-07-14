package com.rssl.phizic.messaging.push;

import com.rssl.phizic.gate.messaging.impl.InnerSerializer;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPrivacyType;
import com.rssl.phizic.messaging.mail.messagemaking.push.PushPublicityType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.mail.MessagingException;
import javax.xml.parsers.ParserConfigurationException;

/**
 * создатель xml сообщений для пушей
 * @author basharin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */

public class PushXmlCreator
{

	/**
	 * получить xml для push сообщения
	 * @param messageText
	 * @param lifetimeMessage
	 * @param isSmsBackup
	 * @param privacyType
	 * @param publicityType
	 * @param eventType
	 * @param attributes
	 * @return
	 * @throws Exception
	 */
	public static String createXml(String messageText, String attributes, int lifetimeMessage, boolean isSmsBackup, PushPrivacyType privacyType, PushPublicityType publicityType, int eventType) throws Exception
	{
		return createXml(messageText, attributes, lifetimeMessage, isSmsBackup,privacyType, publicityType, eventType, true);
	}

	/**
	 * получить xml для push сообщения
	 * @param messageText
	 * @param lifetimeMessage
	 * @param isSmsBackup
	 * @param privacyType
	 * @param publicityType
	 * @param eventType
	 * @param attributes
	 * @param useStopTime
	 * @return
	 * @throws Exception
	 */
	public static String createXml(String messageText, String attributes, int lifetimeMessage, boolean isSmsBackup, PushPrivacyType privacyType, PushPublicityType publicityType, int eventType, boolean useStopTime) throws Exception
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String smsBackup = isSmsBackup ? "reserveSMS" :"notReserveSMS";
		Calendar startTime = Calendar.getInstance();
		Calendar stopTime = Calendar.getInstance();
		stopTime.add(Calendar.SECOND, lifetimeMessage);

		StringWriter writer = new StringWriter();
		InnerSerializer serializer = new InnerSerializer(writer);
		Attributes emptyAttributes = new AttributesImpl();
		try
		{
			serializer.startDocument();
			serializer.startElement("", "", "message", emptyAttributes);

			serializer.startElement("", "", "text", emptyAttributes);
			writeTextToXml(serializer, messageText);
			serializer.endElement("", "", "text");

			serializer.startElement("", "", "parameters", emptyAttributes);

			serializer.startElement("", "", "privacyType", emptyAttributes);
			writeTextToXml(serializer, privacyType.toString().toLowerCase());
			serializer.endElement("", "", "privacyType");

			serializer.startElement("", "", "publicityType", emptyAttributes);
			writeTextToXml(serializer, publicityType.toString().toLowerCase());
			serializer.endElement("", "", "publicityType");

			serializer.startElement("", "", "smsBackup", emptyAttributes);
			writeTextToXml(serializer, smsBackup);
			serializer.endElement("", "", "smsBackup");

			serializer.startElement("", "", "notificationType", emptyAttributes);
			writeTextToXml(serializer, String.valueOf(eventType));
			serializer.endElement("", "", "notificationType");

			serializer.startElement("", "", "startTime", emptyAttributes);
			writeTextToXml(serializer, dateFormat.format(startTime.getTime()));
			serializer.endElement("", "", "startTime");

			if (useStopTime)
			{
				serializer.startElement("", "", "stopTime", emptyAttributes);
				writeTextToXml(serializer, dateFormat.format(stopTime.getTime()));
				serializer.endElement("", "", "stopTime");
			}

			serializer.endElement("", "", "parameters");

			serializeAttributes(serializer, attributes, emptyAttributes);

			serializer.endElement("", "", "message");
			serializer.endDocument();
		}
		catch (SAXException e)
		{
			throw new Exception("ошибка при создании xml для push-уведомления",e);
		}
		return writer.toString();
	}


	private static void serializeAttributes(InnerSerializer serializer, String attributes, Attributes emptyAttributes) throws MessagingException
	{
		try
		{
			if (StringHelper.isEmpty(attributes))
			{
				serializer.startElement("", "", "attributes", emptyAttributes);
				serializer.endElement("", "", "attributes");
			}
			else
			{
				Document domAttributes = XmlHelper.parse("<attributes>" + attributes + "</attributes>");
				serializer.serializeElementOnly(domAttributes.getDocumentElement());
			}
		}
		catch (SAXException e)
		{
			throw new MessagingException("ошибка при создании xml для push-уведомления", e);
		}
		catch (ParserConfigurationException e)
		{
			throw new MessagingException("ошибка при формировании атрибутов для push-уведомления", e);
		}
		catch (IOException e)
		{
			throw new MessagingException("ошибка при формировании атрибутов для push-уведомления", e);
		}
	}

	private static void writeTextToXml(InnerSerializer serializer, String text) throws SAXException
	{
		char[] arr = text.toCharArray();
		serializer.characters(arr, 0, arr.length);
	}
}
