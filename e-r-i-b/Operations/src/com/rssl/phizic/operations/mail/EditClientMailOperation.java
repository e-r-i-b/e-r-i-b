package com.rssl.phizic.operations.mail;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counters;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gainanov
 * @ created 05.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class EditClientMailOperation extends OperationBase implements EditEntityOperation
{
	private static final CounterService counterService = new CounterService();

	private static final MailService mailService = new MailService();
	private static final PersonService personService = new PersonService();
	private Mail mail;
	private CommonLogin login;

	public void initialize()
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
		mail = new Mail();
		mail.setDate(Calendar.getInstance());
	}
	
	public void initialize(Long id) throws BusinessException
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
		mail = mailService.findMailById(id);

		if (mail == null) throw new BusinessException("Письмо с id=" + id + " не найдено");
	}

	public void initializeNew() throws BusinessException
	{
		try
		{
			initialize();
			mail.setNum(counterService.getNext(Counters.LETTER_NUMBER));
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initializeNew(Long id) throws BusinessException
	{
		try
		{
			initialize(id);
			mail.setNum(counterService.getNext(Counters.LETTER_NUMBER));
		}
		catch (CounterException e)
		{
			throw new BusinessException(e);
		}

	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public Mail getEntity()
	{
		return mail;
	}

	public Mail findMailById(Long id) throws BusinessException
	{
		return mailService.findMailById(id);
	}

	@Transactional
	public void save() throws BusinessException
	{
		mailService.addOrUpdate(mail);
	}


	public void markMailReceived() throws BusinessException
	{
		Recipient recipient = mailService.getRecipient(mail,getLogin().getId());
		if (recipient == null)
			throw new AccessException("Пользователь с логином ID="+getLogin().getId()+" не является получателем письма ID="+mail.getId());
		recipient.setState(RecipientMailState.READ);
		mailService.update(recipient);
	}

	public List<Recipient> getRecipients() throws BusinessException
	{
		Person person = personService.findByLoginId(getLogin().getId());
		List<Employee> employees = getEmployeesFromDepartment(person.getDepartmentId());
		List<Recipient> recipients= new ArrayList<Recipient>();
		for (Employee employee : employees)
		{
			Recipient recipient = new Recipient();
			recipient.setRecipientType(RecipientType.ADMIN);
			recipient.setRecipientId(employee.getLogin().getId());
			recipient.setRecipientName(employee.getFullName());
			recipients.add(recipient);
		}

		return recipients;
	}

	private List<Employee> getEmployeesFromDepartment(Long departmentId) throws BusinessException
    {
	    SimpleService service = new SimpleService();
	    DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
        criteria.add(Expression.eq("departmentId",departmentId));
        return service.find(criteria);
    }
}
