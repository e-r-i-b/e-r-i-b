package com.rssl.phizic.operations.person.list;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author akrenev
 * @ created 19.11.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���������� � ������������� �������
 */

public class ChangeLockClientOperation extends OperationBase<UserRestriction>
{
	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();
	private static final ProfileService profileService = new ProfileService();

	private UserInfo userInfo;

	/**
	 * ������������� �������� ������� �������
	 * @param userInfo - ������ �������
	 */
	public void initialize(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}

	/**
	 * @return ���������� ������������ �������
	 */
	public UserInfo getUserInfo()
	{
		return userInfo;
	}

	/**
	 * ����������
	 * @param reason �������
	 * @param blockedFrom ������
	 * @param blockedUntil ���������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@Transactional
	public void lock(String reason, Date blockedFrom, Date blockedUntil) throws BusinessLogicException, BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		ActivePerson person = findActivePerson(userInfo);
		try
		{
			securityService.lock(person.getLogin(),blockedFrom,blockedUntil, BlockingReasonType.employee,reason,employee.getLogin().getId(),null);
			CSABackRequestHelper.sendLockProfileCHG071536Rq(userInfo, DateHelper.toCalendar(blockedFrom), DateHelper.toCalendar(blockedUntil), reason, employee.getFullName());
		}
		catch (FailureIdentificationException e)
		{
			throw new BusinessLogicException("������ " + userInfo.getSurname() + " " + userInfo.getFirstname() + " " + userInfo.getPatrname() + " �� ������ � ���.", e);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void unlock() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = findActivePerson(userInfo);
		try
		{
			securityService.unlock(person.getLogin(),false,null, Calendar.getInstance().getTime());
			CSABackRequestHelper.sendUnlockProfileRq(userInfo);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private ActivePerson findActivePerson(UserInfo userInfo) throws BusinessException, BusinessLogicException
	{
		List<ActivePerson> clients = personService.getByFIOAndDoc(userInfo.getSurname(), userInfo.getFirstname(), userInfo.getPatrname(), null, userInfo.getPassport(), userInfo.getBirthdate(), userInfo.getTb());
		//���� � ����� �� �������-���� �� ������� ��������� ������� � ���
		if(CollectionUtils.isEmpty(clients))
		{
			Profile profileFromCSA = profileService.findProfileWithHistory(userInfo.getSurname(), userInfo.getFirstname(), userInfo.getPatrname(), userInfo.getPassport(), userInfo.getBirthdate(), userInfo.getTb());
			if(profileFromCSA != null)
			{
				for(Profile history : profileFromCSA.getHistory())
				{
					clients = personService.getByFIOAndDoc(history.getSurName(), history.getFirstName(), history.getPatrName(), null, history.getPassport(), history.getBirthDay(), history.getTb());
					if(CollectionUtils.isNotEmpty(clients))
						break;
				}
			}
		}
		//���� � �� ������� ������ �� �����-���� �� ������ ���� ������� �� �����������. (�� ��������� ����� �����)
		if(CollectionUtils.isEmpty(clients))
		{
			List<String> cardNumberList = profileService.findProfileCardNumberList(userInfo);
			for(String cardNumber: cardNumberList)
			{
				clients.addAll(personService.findByLasLogonCardNumber(cardNumber));
			}
		}

		switch (clients.size())
		{
			case 0: throw new BusinessLogicException("������ �� ������ � ����");
			case 1: return clients.get(0);
			default: throw new TooManyActivePersonsException("�� �������� ���������� ������� ������ ������ �������");
		}
	}
}
