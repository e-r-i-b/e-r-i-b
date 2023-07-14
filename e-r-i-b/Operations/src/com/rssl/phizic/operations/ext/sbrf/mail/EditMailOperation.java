package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ext.sbrf.NotificationSender;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Iterator;

/**
 * @author kligina
 * @ created 18.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditMailOperation extends EditMailOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();
	private static final NotificationSender notificationSender = new NotificationSender();
	private static final DepartmentService departmentSender = new DepartmentService();

	private Long nodeId;
	private UserInfo userInfo;

	/**
	 * инициализаци€ операции письмом
	 * @param source письмо (источник данных)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Mail source) throws BusinessException, BusinessLogicException
	{
		initialize(source.getId());
		updateMailData(source);
	}

	/**
	 * инициализаци€ операции новым письмом на основе другого
	 * @param source письмо (источник данных)
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeNew(Mail source) throws BusinessException, BusinessLogicException
	{
		createNewMail();
		updateMailData(source);
	}

	/**
	 * обновить письмо на основе другого
	 * @param source письмо (источник данных)
	 */
	public void updateMailData(Mail source)
	{
		mail.setBody(source.getBody());
		mail.setDirection(MailDirection.CLIENT);
		mail.setSubject(source.getSubject());
		mail.setState(source.getState());
		mail.setType(source.getType());
		mail.setData(source.getData());
		mail.setFileName(source.getFileName());
	}

	/**
	 * задать информацию о получателе
	 * @param userInfo информаци€ о получателе
	 */
	public void setUserInfo(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}

	/**
	 * @return информаци€ о получателе
	 */
	public UserInfo getUserInfo()
	{
		return userInfo;
	}

	/**
	 * задать блок получател€
	 * @param nodeId блок получател€
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return блок получател€
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	protected void updateParentMailAfterUpdateRecipientState(Mail parentMail) throws BusinessException
	{
		//«апоминаем логин сотрудника, который последний сохранил черновик.
		parentMail.setEmployee(getLogin());
		mailService.addOrUpdate(parentMail);
	}

	protected void updateRecipients() throws BusinessException
	{
		addRecipient(RecipientType.PERSON, mail.getRecipientId(), mail.getRecipientName());
	}

	protected MailState getMailDraftState()
	{
		return MailState.EMPLOYEE_DRAFT;
	}

	protected Long getOwnerId(Mail mail)
	{
		return mail.getRecipientId();
	}

	protected void checkMailAccess(Mail checkingMail) throws BusinessException
	{
		if (checkingMail.getSender().equals(getLogin()))
		{
			MailHelper.checkAccess(checkingMail, getLogin());
			return;
		}

		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		MailHelper.checkAccess(checkingMail, employee);
	}

	protected void initializeNewMail() throws BusinessException
	{
		super.initializeNewMail();
		mail.setRecipientType(RecipientType.PERSON);
		mail.setEmployee(getLogin());
	}

	@Override
	protected void checkReply(Long id) throws BusinessException, BusinessLogicException
	{
		Mail temp = findMailById(id);
		checkReply(temp);
	}

	protected void checkReply(Mail mail) throws BusinessLogicException
	{
		if(!MailHelper.canReply(mail, getLogin()))
			throw new BusinessLogicException("¬ы не можете ответить на это письмо, оно закреплено за другим сотрудником.");
	}

	protected void initializeReply(Long parentId) throws BusinessException
	{
		super.initializeReply(parentId);

		//ответ может быть только клиенту (не группе)
		Mail parentMail = findMailById(parentId);
		//«акрепл€ем письмо за сотрудником открывшим письмо.
		setResponsibleEmployee(parentMail, getLogin());
		ActivePerson recipient = getPersonByLogin(parentMail.getSender());
		mail.setRecipientId(parentMail.getSender().getId());
		mail.setRecipientName(recipient.getFullName());
		mail.setRecipientType(RecipientType.PERSON);
		mail.setType(parentMail.getType());
		mail.setTheme(parentMail.getTheme());
		mail.setDirection(MailDirection.CLIENT);
		mail.setEmployee(parentMail.getEmployee());
	}

	protected void setResponsibleEmployee(Mail m, CommonLogin employee) throws BusinessException
	{
		//≈сли письмо ещЄ никто не открывал, закрепл€ем его.
		if(m.getEmployee() == null)
		{
			m.setEmployee(employee);
			mailService.addOrUpdate(m);
		}
	}

	/**
	 * @return номер телефона, получаем из родительского
	 */
	public String getParentMailPhone() throws BusinessException
	{
		if (mail.getParentId() == null)
			return null;

		Mail parentMail = findMailById(mail.getParentId());
		if (parentMail == null)
			return null;

		return parentMail.getPhone();
	}

	private Person getPersonByLoginId(Long loginId) throws BusinessException, SecurityDbException
	{
		CommonLogin login = securityService.findById(loginId);
		return getPersonByLogin(login);
	}

	/**
	 * @param login логин клиента
	 * @return клиент
	 * @throws BusinessException
	 */
	public ActivePerson getPersonByLogin(CommonLogin login) throws BusinessException
	{
		if (login == null)
			return null;

		if (!(login instanceof Login))
			return null;

		return personService.findByLogin((Login) login);
	}

	/**
	 * @return идентификатор получател€ (»ƒ группы или »ƒ клиента)
	 */
	public Long getRecipientId() throws BusinessException
	{
		Long recipientId = mail.getRecipientId();
		if (recipientId == null)
			return null;
		
		try
		{
			Person person = getPersonByLoginId(recipientId);
			return person!= null ? person.getId() : null;
		}
		catch(SecurityDbException ex)
		{
			log.error("ќшибка получени€ идентификатора получател€.", ex);
			return null;
		}
	}

	/**
	 * @return информаци€ о получателе
	 */
	public UserInfo getRecipientUserInfo() throws BusinessException
	{
		Long recipientId = mail.getRecipientId();
		if (recipientId == null)
			return null;

		try
		{
			Person person = getPersonByLoginId(recipientId);
			if (person == null)
				return null;

			return PersonHelper.buildUserInfo(person);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception ex)
		{
			log.error("ќшибка получени€ информации получател€.", ex);
			return null;
		}
	}

	/**
	 * создание оповещени€ о получении письма дл€ клиентов
	 */
	public void createNotification() throws BusinessException
	{
		if (mail.getId() == null)
			throw new BusinessException("ќшибка при создании оповещени€ о получении письма: не задан id письма");

		Iterator<Recipient> recipients = mailService.getRecipients(mail.getId());
		while(recipients.hasNext())
		{
			Recipient recipient = recipients.next();
			if(recipient.getRecipientType() == RecipientType.PERSON)
				notificationSender.sendNotifocation(recipient.getRecipientId());
		}
	}

	/**
	 * удаление текущего письма из Ѕƒ
	 * @throws BusinessException
	 */
	public void remove() throws BusinessException
	{
		mailService.remove(mail);
	}
}

