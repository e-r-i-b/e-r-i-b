package com.rssl.phizic.operations.person.search.pfp;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonDataForEmployee;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.messaging.info.SubscriptionService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author akrenev
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� �������������� ������ �������������� �������
 */

public class EditPotentialPersonInfoOperation extends OperationBase<UserRestriction> implements EditEntityOperation<ActivePerson, UserRestriction>
{
	private static final PersonService personService = new PersonService();
	private static final DepartmentService departmentService = new DepartmentService();
	private static final SubscriptionService subscriptionService = new SubscriptionService();

	private ActivePerson editingPerson;
	private String departmentName;
	private PersonalSubscriptionData subscriptionData;

	/**
	 * ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		editingPerson = new ActivePerson(PersonContext.getPersonDataProvider().getPersonData().getPerson());
		Department department = departmentService.findById(editingPerson.getDepartmentId());
		departmentName = department.getName();

		if(editingPerson.getLogin() != null)
		{
			subscriptionData = subscriptionService.findPersonalData(editingPerson.getLogin());
		}
		else
		{
			subscriptionData = new PersonalSubscriptionData();
		}
	}

	/**
	 * ���������� ���������� �������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void continueSave() throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("����������� ���������� �������������� ������ � ������������ ���������");
	}

	/**
	 * @return �������� ��������
	 */
	public String getDepartmentName()
	{
		return departmentName;
	}

	public ActivePerson getEntity() throws BusinessException, BusinessLogicException
	{
		return editingPerson;
	}

	protected void setEditingPerson(ActivePerson editingPerson)
	{
		this.editingPerson = editingPerson;
	}

	/**
	 * @return ������ �� ����������� �������
	 */
	public PersonalSubscriptionData getSubscriptionData()
	{
		return subscriptionData;
	}

	protected void setSubscriptionData(PersonalSubscriptionData subscriptionData)
	{
		this.subscriptionData = subscriptionData;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		updatePerson();
		PersonContext.getPersonDataProvider().setPersonData(new StaticPersonDataForEmployee(editingPerson));
	}

	@Transactional
	private void updatePerson() throws BusinessException, BusinessLogicException
	{
		if (editingPerson.getId() == null)
		{
			personService.createLogin(editingPerson);
			personService.add(editingPerson);
			subscriptionData.setLogin(editingPerson.getLogin());
			updateProfileInCSA();
		}
		subscriptionService.changePersonalData(subscriptionData);
	}

	private void updateProfileInCSA() throws BusinessException, BusinessLogicException
	{
		try
		{
			CSABackRequestHelper.sendUpdateProfileAdditionalDataRq(PersonHelper.buildUserInfo(editingPerson), editingPerson.getCreationType(), editingPerson.getAgreementNumber(), editingPerson.getMobilePhone());
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException("������ ���������� ������� � ���.", e);
		}
	}
}
