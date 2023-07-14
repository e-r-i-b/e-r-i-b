package com.rssl.phizic.operations.mail.archive;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;
import java.util.HashMap;

/**
 * @author mihaylov
 * @ created 06.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ’ендлер дл€ парсинга XML с телом письма и восстановлени€ письма в базе
 */
public class MailArchiveXMLHandler extends DefaultHandler
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final SecurityService securityService = new SecurityService();
	private static final MailService mailService = new MailService();
	private static final MailSubjectService mailSubjectService = new MailSubjectService();

	private String tagText;
	private Mail mail;
	private boolean isOldArchiveFormat = true;
	private boolean first;
	private boolean mailValid;
	private Recipient recipient;
	private MailUnArcihiveValidator mailValidator;
	private RecipientUnArchiveValidator recipientValidator;
	private Boolean showUnArchivingMailToClient;
	private long recoverMailCounter;

	private static final Map<String, MailType> mailType = new HashMap<String, MailType>();
	static
	{
		for(MailType mType : MailType.values())
			mailType.put(mType.name(), mType);
	}

	public MailArchiveXMLHandler(MailUnArcihiveValidator mailValidator, RecipientUnArchiveValidator recipientValidator, Boolean showUnArchivingMailToClient)
	{
		this.mailValidator = mailValidator;
		this.recipientValidator = recipientValidator;
		this.showUnArchivingMailToClient = showUnArchivingMailToClient;
	}

	public long getRecoverMailCounter()
	{
		return recoverMailCounter;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		if(ArchiveXMLTags.RECORD_TAG_NAME.equals(qName))
		{
			checkObjectState(mail);
			mail = new Mail();
			first = true;
			mailValid = false;
		}
		else if(ArchiveXMLTags.RECIPIENT_TAG_NAME.equals(qName))
		{   			
			checkObjectState(recipient);
			recipient = new Recipient();
		}
		//ƒл€ поддержки старого формата архива.
		else if(ArchiveXMLTags.MAIL_TAG_NAME.equals(qName))//признак старого формата архива.
		{
			isOldArchiveFormat = false;//ѕеред нами архив в старом формате
		}
		else if(ArchiveXMLTags.RECIPIENTS_TAG_NAME.equals(qName) && isOldArchiveFormat)
		{
			try
			{
				mailValid = mailValidator.validate(mail);
			}
			catch (BusinessException e)
			{
				throw new SAXException(e);
			}
		}

		tagText = "";
	}

	private void checkObjectState(Object obj)
	{
		if(obj != null)
			throw new IllegalStateException();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		try
		{
			if(ArchiveXMLTags.MAIL_ID_TAG_NAME.equals(qName))
			{
				mail.setId(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.PARENT_ID_TAG_NAME.equals(qName))
			{
				mail.setParentId(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.NUMBER_TAG_NAME.equals(qName))
			{
				mail.setNum(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.TYPE_TAG_NAME.equals(qName))
			{
				//—тарые типы писем перегон€ем в прочее.
				MailType mType = mailType.get(tagText);
				mail.setType(mType == null ? MailType.OTHER : mType);
			}
			else if(ArchiveXMLTags.STATE_TAG_NAME.equals(qName))
			{
				mail.setState(MailState.valueOf(tagText));
			}
			else if(ArchiveXMLTags.SUBJECT_TAG_NAME.equals(qName))
			{
				mail.setSubject(tagText);
			}
			else if(ArchiveXMLTags.BODY_TAG_NAME.equals(qName))
			{
				mail.setBody(tagText);
			}
			else if(ArchiveXMLTags.DATE_TAG_NAME.equals(qName))
			{
				mail.setDate(XMLDatatypeHelper.parseDateTime(tagText));
			}
			else if(ArchiveXMLTags.SENDER_ID_TAG_NAME.equals(qName))
			{
				Long loginId = Long.valueOf(tagText);
				CommonLogin login = securityService.findById(loginId);
				mail.setSender(login);
			}
			else if(ArchiveXMLTags.EMPLOYEE_ID_TAG_NAME.equals(qName))
			{
				Long loginId = Long.valueOf(tagText);
				CommonLogin login = securityService.findById(loginId);
				mail.setEmployee(login);
			}
			else if(ArchiveXMLTags.DIRECTION_TAG_NAME.equals(qName))
			{
				mail.setDirection(MailDirection.valueOf(tagText));
			}
			else if(ArchiveXMLTags.PHONE_TAG_NAME.equals(qName))
			{
				mail.setPhone(tagText);
			}		
			else if(ArchiveXMLTags.IMPORTANT_TAG_NAME.equals(qName))
			{
				mail.setImportant(Boolean.valueOf(tagText));
			}
			else if(ArchiveXMLTags.ATTACH_NAME_TAG_NAME.equals(qName))
			{
				mail.setFileName(tagText);
			}
			else if(ArchiveXMLTags.ATTACH_TAG_NAME.equals(qName))
			{
				mail.setData(StringUtils.fromHexString(tagText));
			}
			else if(ArchiveXMLTags.DELETED_MAIL_TAG_NAME.equals(qName))
			{
				mail.setDeleted(Boolean.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_ID_MAIL_TAG_NAME.equals(qName))
			{
				mail.setRecipientId(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_NAME_MAIL_TAG_NAME.equals(qName))
			{
				mail.setRecipientName(tagText);
			}
			else if(ArchiveXMLTags.RECIPIENT_TYPE_MAIL_TAG_NAME.equals(qName))
			{
				mail.setRecipientType(RecipientType.valueOf(tagText));
			}
			else if(ArchiveXMLTags.SUBJECT_ID_TAG_NAME.equals(qName))
			{
				setMailSubject(mail, Long.parseLong(tagText));
			}
			else if(ArchiveXMLTags.E_MAIL_TAG_NAME.equals(qName))
			{
				mail.setEmail(tagText);
			}
			else if(ArchiveXMLTags.RESPONSE_METHOD_TAG_NAME.equals(qName))
			{
				mail.setResponseMethod(MailResponseMethod.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RESPONSE_TIME_TAG_NAME.equals(qName))
			{
				mail.setResponseTime(tagText == null ? null : Long.parseLong(tagText));
			}
			else if(ArchiveXMLTags.MAIL_TAG_NAME.equals(qName))
			{
				mailValid = mailValidator.validate(mail);
			}
			else if(ArchiveXMLTags.RECIPIENT_ID_TAG_NAME.equals(qName))
			{
				recipient.setId(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_LOGIN_ID_TAG_NAME.equals(qName))
			{
				recipient.setRecipientId(Long.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_TYPE_TAG_NAME.equals(qName))
			{
				recipient.setRecipientType(RecipientType.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_NAME_TAG_NAME.equals(qName))
			{
				recipient.setRecipientName(tagText);
			}
			else if(ArchiveXMLTags.RECIPIENT_DELETED_TAG_NAME.equals(qName))
			{
				recipient.setDeleted(Boolean.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_STATE_TAG_NAME.equals(qName))
			{
				recipient.setState(RecipientMailState.valueOf(tagText));
			}
			else if(ArchiveXMLTags.RECIPIENT_TAG_NAME.equals(qName))
			{
				//ƒл€ совместимости со старым форматом архива.
				fillMailFields(mail, recipient);

				if(mailValid && recipientValidator.validate(mail, recipient))
				{
					recoverMail(mail, recipient);
					first = false;
				}
				recipient = null;
			}
			else if(ArchiveXMLTags.RECORD_TAG_NAME.equals(qName))
			{
				mail = null;
			}
			else if(ArchiveXMLTags.ARCHIVE_TAG_NAME.equals(qName))
			{}
			else
			{
				throw new SAXException("Ќеверный формат архива.");
			}
		}
		catch (SecurityDbException e)
		{
			throw new SAXException(e);
		}
		catch (BusinessException e)
		{
			throw new SAXException(e);
		}

	}

	private void fillMailFields(Mail mail, Recipient recipient)
	{
		if(mail.getRecipientName() == null)
		{
			mail.setRecipientName(recipient.getRecipientName());
		}

		if(mail.getRecipientType() == null)
		{
			mail.setRecipientType(recipient.getRecipientType());
		}

		if(mail.getRecipientId() == null)
		{
			mail.setRecipientId(recipient.getRecipientId());
		}

		if(mail.getDirection() == MailDirection.ADMIN && mail.getTheme() == null)
		{
			mail.setTheme(MailHelper.getDefaultMailSubject());
		}

		if(recipient.getMailId() == null)
		{
			recipient.setMailId(mail.getId());
		}
	}

	private void setMailSubject(Mail mail, Long subjectId)
	{

		MailSubject mailSubject;
		try
		{
			mailSubject = mailSubjectService.getMailSubjectById(subjectId, null);
		}
		catch (BusinessException be)
		{
			log.error("ќшибка получени€ тематики обращени€", be);
			mailSubject = MailHelper.getDefaultMailSubject();
		}
		mail.setTheme(mailSubject);
	}

	private void recoverMail(Mail mail, Recipient recipient)
	{
		try
		{
			mail.setShow(showUnArchivingMailToClient);
			if(first)//в базу добавл€ем только письмо с первым получателем, далее добавл€ем получателей к письму.
			{
				mailService.insertMailFromArchive(mail);
				recoverMailCounter++;
			}
			//если письмо черновик, то получателей у него нет. ¬се данные есть в письме.
			if(mail.getState() != MailState.EMPLOYEE_DRAFT)
			{
				mailService.insertRecipientFromArchive(recipient);
			}
		}
		catch (BusinessException e)
		{
			//ничего не делаем, письмо уже восстановлено из архива
			log.info("Ќе удалось восстановить письмо id= " + mail.getId()+ " из архива.");
		}
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{
		tagText += new String(ch,start,length);
	}
}
