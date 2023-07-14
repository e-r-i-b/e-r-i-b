package com.rssl.phizic.operations.mail;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.groups.DublicateGroupNameException;
import com.rssl.phizic.business.groups.GroupService;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.mail.Recipient;
import com.rssl.phizic.business.mail.RecipientMailState;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.SecurityDbException;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gainanov
 * @ created 05.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditMailOperation extends OperationBase implements EditEntityOperation
{
	private static final CounterService counterService = new CounterService();
	private static final MailService mailService = new MailService();
	private static final GroupService groupService = new GroupService();
	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();
	private Mail mail;
	private CommonLogin login;

	public void initialize()
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
		mail = new Mail();
		mail.setDate(Calendar.getInstance());
	}

	public void initializeNew() throws BusinessException
	{
		initialize();
		try
		{
			mail.setNum(counterService.getNext(Counters.LETTER_NUMBER));
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initialize(Long mailId) throws BusinessException
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
		mail = mailService.findMailById(mailId);

		if (mail == null) throw new BusinessException("Письмо с id=" + mailId + " не найдено");
	}

	public void initializeNew(Long mailId) throws BusinessException
	{
		try
		{
			initialize(mailId);
			mail.setNum(counterService.getNext(Counters.LETTER_NUMBER));
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	public Mail getEntity()
	{
		return mail;
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	@Transactional
	public void save() throws BusinessException, DublicateGroupNameException
	{
		mailService.addOrUpdate(mail);
	}


	// TODO: убрать в запросе BUG032134
	public List<CommonLogin> getGroupContents(Long groupId) throws BusinessException 
	{
		return groupService.getGroupsElements(groupId);
	}

	public Long getUserLoginId(Long id) throws BusinessException
	{
		return personService.findById(id).getLogin().getId();
	}

	public Mail findMailById(Long id) throws BusinessException
	{
		return mailService.findMailById(id);
	}

	public Person findPersonById(Long id) throws BusinessException, SecurityDbException
	{
		Login login = (Login)securityService.findById(id);
		return personService.findByLogin(login);
	}

	public void markMailReceived() throws BusinessException
	{
		Recipient recipient = mailService.getRecipient(mail, login.getId());
		if (recipient == null)
			throw new AccessException("Администратор с логином ID=" + login.getId() + " не является получателем письма ID=" + mail.getId());
		recipient.setState(RecipientMailState.READ);
		mailService.update(recipient);
	}
}
