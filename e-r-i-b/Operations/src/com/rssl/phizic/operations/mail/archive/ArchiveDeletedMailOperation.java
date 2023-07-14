package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.mail.Recipient;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XML11Serializer;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ArchiveDeletedMailOperation extends ProcessMailOperationBase
{
	private static final MailService mailService = new MailService();

	/**
	 * архиваци€
	 * @throws com.rssl.phizic.business.BusinessException - ошибка архивации сообщений в службу помощи
	 */
	public void process() throws BusinessException
	{
		//если нет писем дл€ архивации, то нет смысла создавать пустой файл
		if(!mailIterator.hasNext())
			return;
		ZipOutputStream zipOutputStream = null;
		try
		{
			zipOutputStream = new ZipOutputStream(new FileOutputStream(getPath(), false));
			ZipEntry entry = new ZipEntry(getXmlFileName());
			zipOutputStream.putNextEntry(entry);
			archive(zipOutputStream,mailIterator);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			try
			{
				if(zipOutputStream != null)
					zipOutputStream.close();
			}
			catch (IOException e)
			{
				throw new BusinessException(e);
			}
		}
	}

	/**
	 * «аписать в выходной поток XML содержащию письма
	 * @param stream - выходной поток
	 * @param mailIterator - итератор писем
	 * @throws BusinessException
	 */
	private void archive(OutputStream stream, Iterator<Mail> mailIterator) throws BusinessException
	{
		try
		{
			XML11Serializer serializer = new XML11Serializer(stream, new OutputFormat(Method.XML, "windows-1251", true));
			serializer.startDocument();
			serializer.startElement(ArchiveXMLTags.ARCHIVE_TAG_NAME, null);
			for(;mailIterator.hasNext();)
			{
				Mail mail = mailIterator.next();
				Iterator<Recipient> recipients = mailService.getRecipients(mail.getId());
				createRecord(serializer, mail, recipients);
				mailService.removeRecipients(mail);
				mailIterator.remove();
			}
			serializer.endElement(ArchiveXMLTags.ARCHIVE_TAG_NAME);
			serializer.endDocument();
		}
		catch (SAXException e)
		{
			throw new BusinessException("ќшибка при создании документа", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void createRecord(XMLSerializer serializer, Mail mail, Iterator<Recipient> recipients) throws SAXException, IOException
	{
		serializer.startElement(ArchiveXMLTags.RECORD_TAG_NAME, null);
		serializer.startElement(ArchiveXMLTags.MAIL_TAG_NAME, null);
		writeElement(serializer, ArchiveXMLTags.MAIL_ID_TAG_NAME, StringHelper.getEmptyIfNull(mail.getId()));
		writeElement(serializer, ArchiveXMLTags.PARENT_ID_TAG_NAME, StringHelper.getEmptyIfNull(mail.getParentId()));
		writeElement(serializer, ArchiveXMLTags.NUMBER_TAG_NAME, StringHelper.getEmptyIfNull(mail.getNum()));
		writeElement(serializer, ArchiveXMLTags.TYPE_TAG_NAME, StringHelper.getEmptyIfNull(mail.getType()));
		writeElement(serializer, ArchiveXMLTags.STATE_TAG_NAME, StringHelper.getEmptyIfNull(mail.getState()));
		writeElement(serializer, ArchiveXMLTags.SUBJECT_TAG_NAME, mail.getSubject());
		writeElement(serializer, ArchiveXMLTags.BODY_TAG_NAME, mail.getBody());
		writeElement(serializer, ArchiveXMLTags.DATE_TAG_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(mail.getDate()));
		writeElement(serializer, ArchiveXMLTags.SENDER_ID_TAG_NAME, mail.getSender() == null ? null : mail.getSender().getId().toString());
		writeElement(serializer, ArchiveXMLTags.EMPLOYEE_ID_TAG_NAME, mail.getEmployee() == null ? null : mail.getEmployee().getId().toString());
		writeElement(serializer, ArchiveXMLTags.DIRECTION_TAG_NAME, StringHelper.getEmptyIfNull(mail.getDirection()));
		writeElement(serializer, ArchiveXMLTags.PHONE_TAG_NAME, mail.getPhone());
		writeElement(serializer, ArchiveXMLTags.IMPORTANT_TAG_NAME, StringHelper.getEmptyIfNull(mail.getImportant()));
		writeElement(serializer, ArchiveXMLTags.ATTACH_NAME_TAG_NAME, mail.getFileName());
		writeElement(serializer, ArchiveXMLTags.ATTACH_TAG_NAME, mail.getData());
		writeElement(serializer, ArchiveXMLTags.DELETED_MAIL_TAG_NAME, StringHelper.getEmptyIfNull(mail.getDeleted()));
		writeElement(serializer, ArchiveXMLTags.RECIPIENT_TYPE_MAIL_TAG_NAME, StringHelper.getEmptyIfNull(mail.getRecipientType()));
		writeElement(serializer, ArchiveXMLTags.RECIPIENT_NAME_MAIL_TAG_NAME, StringHelper.getEmptyIfNull(mail.getRecipientName()));
		writeElement(serializer, ArchiveXMLTags.RECIPIENT_ID_MAIL_TAG_NAME, StringHelper.getEmptyIfNull(mail.getRecipientId()));
		writeElement(serializer, ArchiveXMLTags.RESPONSE_METHOD_TAG_NAME, StringHelper.getEmptyIfNull(mail.getResponseMethod()));
		writeElement(serializer, ArchiveXMLTags.E_MAIL_TAG_NAME, StringHelper.getEmptyIfNull(mail.getEmail()));
		writeElement(serializer, ArchiveXMLTags.SUBJECT_ID_TAG_NAME, StringHelper.getEmptyIfNull(mail.getTheme() == null ? null : mail.getTheme().getId()));
		writeElement(serializer, ArchiveXMLTags.RESPONSE_TIME_TAG_NAME, StringHelper.getEmptyIfNull(mail.getResponseTime()));

		serializer.endElement(ArchiveXMLTags.MAIL_TAG_NAME);
		writeRecipients(serializer, recipients);

		serializer.endElement(ArchiveXMLTags.RECORD_TAG_NAME);
	}

	private void writeRecipients(XMLSerializer serializer, Iterator<Recipient> recipients) throws SAXException
	{
		while( recipients.hasNext() )
		{
			Recipient recipient = recipients.next();
			serializer.startElement(ArchiveXMLTags.RECIPIENT_TAG_NAME,null);
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_ID_TAG_NAME, StringHelper.getEmptyIfNull(recipient.getId()));
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_LOGIN_ID_TAG_NAME, StringHelper.getEmptyIfNull(recipient.getRecipientId()));
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_TYPE_TAG_NAME, StringHelper.getEmptyIfNull(recipient.getRecipientType()));
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_NAME_TAG_NAME, recipient.getRecipientName());
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_STATE_TAG_NAME, StringHelper.getEmptyIfNull(recipient.getState()));
			writeElement(serializer, ArchiveXMLTags.RECIPIENT_DELETED_TAG_NAME, StringHelper.getEmptyIfNull(recipient.getDeleted()));
			serializer.endElement(ArchiveXMLTags.RECIPIENT_TAG_NAME);
		}		
	}

	private void writeElement(XMLSerializer serializer, String tagName, String tagValue) throws SAXException
	{
		if(!StringHelper.isEmpty(tagValue))
		{
			serializer.startElement(tagName,null);
			char[] chars = tagValue.toCharArray();
			serializer.characters(chars,0,chars.length);
			serializer.endElement(tagName);
		}
	}

	private void writeElement(XMLSerializer serializer, String tagName,  byte[] tagValue) throws SAXException, IOException
	{
		if(tagValue != null && tagValue.length != 0)
		{
			serializer.startElement(tagName,null);
			char[] chars = StringUtils.toHexArray(tagValue);
			serializer.characters(chars,0,chars.length);
			serializer.endElement(tagName);
		}
	}

	private String getPath() throws BusinessException
	{
		try
		{
			return ConfigFactory.getConfig(MailConfig.class).getArchivePath() + "\\" + getArchiveName();
		}
		catch (Exception e)
		{
			throw new BusinessException("Ќе удалось получить путь дл€ архивации писем", e);
		}
	}

	private String getXmlFileName()
	{
		return "mailArchive.xml";
	}

	protected String getArchiveName() throws BusinessException
	{
		return ArchiveXMLTags.ZIP_ARCHIVE_FILE_PREFIX + XMLDatatypeHelper.formatDateWithoutTimeZone(getEndDate()) + ArchiveXMLTags.ZIP_ARCHIVE_FILE_SUFIX;
	}

	protected Integer getLastMonthValue() throws BusinessException
	{
		try
		{
			return ConfigFactory.getConfig(MailConfig.class).getLastMonthDelete();
		}
		catch (Exception e)
		{
			throw new BusinessException("Ќе удалось получить значение параметра 'ќставл€ть записи за...' дл€ архивации списка удаленных писем", e);
		}
	}
}
