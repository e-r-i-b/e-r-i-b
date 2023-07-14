package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author mihaylov
 * @ created 25.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �������� ��������� �� eMail
 */
public class EmailMessagingService
{

	/**
	 * ��������� ������ �� eMail
	 * @param eMailAddress - ������ �� ������� ���������� ��������� ���������
	 * @param subject - ���� ������
	 * @param text - ����� ������
	 * @throws IKFLMessagingException
	 */
	public void sendPlainEmail(String eMailAddress, String subject, String text) throws IKFLMessagingException
	{
		try
		{
			Session mailSession = MessageBuilderFactory.getMailSession();
			MimeMessage message = new MimeMessage(mailSession);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(eMailAddress));
			message.setSubject(subject, "utf-8");

			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(text, "text/html;\r\n\tcharset=\"utf-8\"");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);
			Transport.send(message, message.getRecipients(Message.RecipientType.TO));
		}
		catch (MessagingException e)
		{
			throw new IKFLMessagingException("�� ������� ��������� eMail �� ����� " + eMailAddress,e);
		}
	}


}
