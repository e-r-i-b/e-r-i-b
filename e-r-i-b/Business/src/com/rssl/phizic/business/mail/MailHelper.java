package com.rssl.phizic.business.mail;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.mail.area.ContactCenterArea;
import com.rssl.phizic.business.mail.area.ContactCenterAreaService;
import com.rssl.phizic.business.messaging.info.EmailTemplateLoader;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderFactory;
import com.rssl.phizic.messaging.mail.messagemaking.email.EmailMessageBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.email.HtmlEmailMessageBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.email.InternetAddressBuilder;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 * @author komarov
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MailHelper
{
	private static final MailService mailService = new MailService();
	private static final MailSubjectService mailSubjectService = new MailSubjectService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final PersonService personService = new PersonService();
	private static final ContactCenterAreaService areaService = new ContactCenterAreaService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Object CONFIGURATION_LOCKER = new Object();
	private static volatile Configuration configuration = null;

	/**
	 * ��������� �������� �� ������������ ���������� ������.
	 * @param mail ������
	 * @param login ������������ 
	 */
	public static void checkAccess(Mail mail, CommonLogin login) throws BusinessException
	{
		if (mail.getSender().equals(login) || mailService.getRecipient(mail,login.getId()) != null)
			return;

		throw new AccessException("� ������� ������������ ��� ���� �� �������� " +
									"������ � id=" + mail.getId());
	}

	/**
	 * ��������� ����� �� ��������� ������ � ������� ������.
	 * @param mail ������
	 * @param currentEmployee ���������
	 */
	public static void checkAccess(Mail mail, Employee currentEmployee) throws BusinessException
	{
		if (mail.getDirection() == MailDirection.CLIENT)
		{
			Employee senderEmployee = employeeService.findByUserId(mail.getSender().getUserId());
			if(mail.getSender().equals(currentEmployee.getLogin()) ||
			   AllowedDepartmentsUtil.isDepartmentAllowed(senderEmployee.getDepartmentId()))
		        return;
		}

		if (mail.getDirection() == MailDirection.ADMIN &&
			AllowedDepartmentsUtil.isDepartmentAllowed(mail.getRecipientId()))
			return;

		throw new AccessException("� ������� ������������ ��� ���� �� �������� " +
									"������ � id=" + mail.getId());
	}

	/**
	 * ����� �� ��������� �������� �� ������
	 * @param mail ������
	 * @param login ���������
	 * @return ��/���
	 */
	public static boolean canReply(Mail mail, CommonLogin login)
	{
		if(mail.getState() == MailState.EMPLOYEE_DRAFT)
			return mail.getEmployee().equals(login);
		if(mail.getDirection() != MailDirection.ADMIN)
			return true;
		if(mail.getEmployee() == null)
			return true;
		return mail.getEmployee().equals(login);
	}


	/**
	 * ��������� �� ������ �����������.
	 * ���� ������ ���� �� ����������� ������������� �������� ������, �� ������ ���������.
	 * @param mail ������
	 * @return ��������� ��� ��� ������.
	 */
	public static boolean isRead(Mail mail) throws BusinessException
	{
		if (MailDirection.ADMIN == mail.getDirection())
		{
			Recipient recipient = mailService.getRecipient(mail, mail.getRecipientId());
			if (recipient == null)
				 return false;
			RecipientMailState stateRecipient = recipient.getState();
			return stateRecipient == RecipientMailState.READ || stateRecipient == RecipientMailState.DRAFT || stateRecipient == RecipientMailState.ANSWER;
		}

		throw new RuntimeException("�������� ����������� ������: ������ ���� 'ADMIN'");
	}

	/**
	 * ��������� �� ������ ��������
	 * @param mail ������
	 * @param login ������
	 * @return ��������� ��� ��� ������.
	 */
	public static boolean isRead(Mail mail, CommonLogin login) throws BusinessException
	{
		if (MailDirection.CLIENT == mail.getDirection() )
		{
			Recipient recipient = mailService.getRecipient(mail, login.getId());
			if (recipient == null)
				 return false;
			RecipientMailState stateRecipient = recipient.getState();
			return stateRecipient == RecipientMailState.READ || stateRecipient == RecipientMailState.DRAFT || stateRecipient == RecipientMailState.ANSWER;
 		}

		throw new RuntimeException("�������� ����������� ������: ������ ���� 'CLIENT'");
	}

	/**
	 * �������� ������ ������� ���������(��� �������)
	 * @return ������ ������� ���������
	 */
	public static List<MailSubject> getAllMailSubjects()
	{
		try
		{
			return mailSubjectService.getAllSubjects(MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch(BusinessException be)
		{
			log.error("������ ��������� ������ ������� ���������", be);
			return Collections.emptyList();
		}
	}

	/**
	 * �������� id �������� ��������� �� ���������.
	 * @return id
	 */
	public static Long getDefaultMailSubjectId()
	{
		return ConfigFactory.getConfig(MailConfig.class).getDefaultSubjectId();
	}

	/**
	 * @return id �������� ��������� �� ��������� � ������������ ������
	 */
	public static Long getDefaultMultiBlockSubjectId()
	{
		return ConfigFactory.getConfig(MailConfig.class).getDefaultMultiBlockSubjectId();
	}

	/**
	 * �������� �������� �� ��������� �������� ���������.
	 * @return �������� ��������� �� ���������
	 */
	public static MailSubject getDefaultMailSubject()
	{
		try
		{
			return mailSubjectService.getMailSubjectById(getDefaultMailSubjectId(), null);
		}
		catch(BusinessException be)
		{
			log.error("������ ��������� �������� ��������� �� ���������", be);
			return null;
		}
	}

	/**
	 * �������� ���������� ����� �����
	 * @return ����������
	 */
	public static Long getCountNewLetters()
	{
		try
		{
			return PersonContext.getPersonDataProvider().getPersonData().getCountNewLetters();
		}
		catch(Exception ex)
		{
			log.error("������ ��������� ���������� ����� �����", ex);
			return 0L;
		}
	}

	/**
	 * ��������� ������� ���������� ����� ����� �� 1
	 */
	public static void decCountNewLetters()
	{
	    PersonContext.getPersonDataProvider().getPersonData().decCountNewLetters();
	}

	/**
	 * �������� ��� ���������� �� ������
	 * @param login �����
	 * @return ���
	 */
	public static String getEmployeeFIO(BankLogin login)
	{
		try
		{
			Employee employee = employeeService.findByLogin(login);
			if(employee != null)
			{
				return employee.getFullName();
			}
			return "";
		}
		catch(Exception ex)
		{
			log.error("������ ��������� ��� ����������", ex);
			return "";
		}
	}

	/**
	 * �������� ��� ������� �� ������
	 * @param login �����
	 * @return ���
	 */
	public static String getClientFIO(Login login)
	{
		try
		{
			Person person = personService.findByLogin(login);
			if(person != null)
			{
				return person.getFullName();
			}
			return "";
		}
		catch(Exception ex)
		{
			log.error("������ ��������� ��� �������", ex);
			return "";
		}
	}

	/**
	 * �������� ��� ����������� ������
	 * @param mail ������
	 * @return ���
	 */
	public static String getSenderFIO(Mail mail)
	{
		if (mail.getDirection()== MailDirection.CLIENT)
			return getEmployeeFIO((BankLogin)mail.getSender());
		else
			return getClientFIO((Login)mail.getSender());
	}

	/**
	 * @return ��������� ����������� �����.
	 */
	private static Configuration getConfiguration()
	{
		Configuration localConfiguration = configuration;
		if (localConfiguration == null)
		{
			synchronized (CONFIGURATION_LOCKER)
			{
				if (configuration == null)
				{
					localConfiguration = new Configuration();
					localConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
					localConfiguration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
					localConfiguration.setDefaultEncoding("windows-1251");
					localConfiguration.setOutputEncoding("windows-1251");
					localConfiguration.setLocale(new Locale("ru", "RU"));
					localConfiguration.setTemplateLoader(new EmailTemplateLoader());
					configuration = localConfiguration;
				}
			}
		}
		return localConfiguration;
	}

	/**
	 * ���������� ���������.
	 * @param contactData ���������� ������ ������������
	 * @param templateName ��������� ����� �������.
	 */
	public static void sendEMail(PersonalSubscriptionData contactData, String templateName, Map keyWords, boolean needLogForLogin) throws IOException, BusinessException
	{
		writeToLog(needLogForLogin, "�������� ������� ��� �������� email ��������� � �����");
		HtmlEmailMessageBuilder htmlBuilder = new HtmlEmailMessageBuilder(getConfiguration(), templateName, MessageBuilderFactory.getMailSession(), new InternetAddressBuilder());

		try
		{
			writeToLog(needLogForLogin, "�������� ������������ email ��������� � �����");
			MimeMessage message = htmlBuilder.create(contactData, keyWords, needLogForLogin);
			if (keyWords.containsKey("subject"))
			{
				message.setSubject((String) keyWords.get("subject"), "utf-8");
			}

			writeToLog(needLogForLogin, "�������� �������� email ��������� � �����");

			Transport.send(message, message.getRecipients(Message.RecipientType.TO));

			writeToLog(needLogForLogin, "����� �������� email ��������� � �����");
		}
		catch (MessagingException ex)
		{
			throw new BusinessException(ex);
		}
	}

	private static void writeToLog(boolean needLogForLogin, String message)
	{
		if (needLogForLogin)
			log.info(message);
	}

	/**
	 * ���������� ���������.
	 * @param subject      ����
	 * @param emailAddress �����
	 * @param templateName ���� � ��������������
	 * @param keyWords     ������ ��� ��������������
	 */
	public static void sendEMail(String subject, String emailAddress, String templateName, Map<String, Object> keyWords) throws IOException, BusinessException
	{
		if (StringHelper.isNotEmpty(subject))
		{
			keyWords.put("subject", subject);
		}

		PersonalSubscriptionData contactData = new PersonalSubscriptionData();
		contactData.setEmailAddress(emailAddress);
		contactData.setMailFormat(MailFormat.HTML);

		sendEMail(contactData, templateName, keyWords, false);
	}

	/**
	 * ���������� ���������.
	 * @param emailTheme ����
	 * @param emailAddress �����
	 * @param textReader ����� ������
	 * @param data ������ ��� ��������������

	 */
	public static void sendEMail(String emailTheme, Reader textReader, String emailAddress, MailFormat mailFormat, Object data) throws IOException, BusinessException
	{
		Template template = new Template(null, textReader, getConfiguration());
		EmailMessageBuilder htmlBuilder = new EmailMessageBuilder(emailTheme, template, MessageBuilderFactory.getMailSession(), new InternetAddressBuilder());
		PersonalSubscriptionData contactData = new PersonalSubscriptionData();
		contactData.setEmailAddress(emailAddress);
		if (mailFormat != null)
			contactData.setMailFormat(mailFormat);

		try
		{
			Message message = htmlBuilder.create(contactData, data);
			Transport.send(message, message.getRecipients(Message.RecipientType.TO));
		}
		catch (MessagingException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * ���������� ��������� � ���������.
	 * @param emailTheme ����
	 * @param emailAddress �����
	 * @param textReader ����� ������
	 * @param data ������ ��� ��������������
	 * @param attachments ��������.
	 */
	public static void sendEMail(String emailTheme, Reader textReader, String emailAddress, MailFormat mailFormat, Object data, Map<String, byte[]> attachments) throws IOException, BusinessException
	{
		Template template = new Template(null, textReader, getConfiguration());
		EmailMessageBuilder htmlBuilder = new EmailMessageBuilder(emailTheme, template, MessageBuilderFactory.getMailSession(), new InternetAddressBuilder());
		PersonalSubscriptionData contactData = new PersonalSubscriptionData();
		contactData.setEmailAddress(emailAddress);
		if (mailFormat != null)
			contactData.setMailFormat(mailFormat);

		try
		{
			Message message = htmlBuilder.create(contactData, data, attachments);
			Transport.send(message, message.getRecipients(Message.RecipientType.TO));
		}
		catch (MessagingException ex)
		{
			throw new BusinessException(ex);
		}
	}

	/**
	 * ��������������� ������ � �������� ��������
	 * @param s ������
	 * @return ������ �� �������
	 */
	public static String getStateDescription(String s)
	{
		if("DELETED".equals(s))
			return "�������";
		return RecipientMailState.valueOf(s).getDescription();
	}

	/**
	 * @return ������ ��������� ���������
	 */
	public static List<Department> getAllowedTB()
	{
		try
		{
			return AllowedDepartmentsUtil.getTerbanksByAllowedDepartments();
		}
		catch(Exception e)
		{
			log.error("�� ������� �������� ������ ��������� ��", e);
			return Collections.emptyList();
		}
	}

	/**
	 * @return ������ ��������� ���������
	 */
	public static List<ContactCenterArea> getAllowedArea()
	{
		try
		{
			List<String> tbs = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();
			if(CollectionUtils.isEmpty(tbs))
			{
				log.error("�� ������� �������� ������ ��������� ��");
				return Collections.emptyList();
			}
			return areaService.getAreaByTbIds(tbs, MultiBlockModeDictionaryHelper.getDBInstanceName());
		}
		catch(Exception e)
		{
			log.error("�� ������� �������� ������ ��������� �������� ��", e);
			return Collections.emptyList();
		}
	}

	/**
	 * @return ���� ������� ���� � ������� ��� �������
	 */
	public static Map<String, String> getCurrentDateFields()
	{
		Map<String, String> keyWords = new HashMap<String, String>();
		Calendar currentTime = Calendar.getInstance();
		keyWords.put("Date", DateHelper.formatDateToStringWithPoint(currentTime));
		keyWords.put("Time", DateHelper.getTimeFormat(DateHelper.toDate(currentTime)));
		return keyWords;
	}
}
