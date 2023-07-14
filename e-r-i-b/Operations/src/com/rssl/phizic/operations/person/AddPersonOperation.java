package com.rssl.phizic.operations.person;

import com.rssl.phizic.armour.*;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.AgreementNumberCreatorHelper;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import com.rssl.phizic.person.Person;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:55:26
 */
@PublicDefaultCreatable
public class AddPersonOperation extends PersonOperationBase implements EditEntityOperation<ActivePerson, UserRestriction>
{
	private static final PersonService    personService    = new PersonService();
	private static final SecurityService  securityService  = new SecurityService();
	private static final SimpleService    simpleService    = new SimpleService();
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	private static final Log log                            = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Department department;

	public ActivePerson getEntity()
	{
		return getPerson();
	}

	public Long getPersonId()
	{
		return getPerson().getId();
	}

	public ActivePerson newInstance(String clientId, String agreementNumber ) throws BusinessException, BusinessLogicException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();

		ActivePerson person = new ActivePerson();

		PersonCreateConfig flowConfig = ConfigFactory.getConfig(PersonCreateConfig.class);
		if(flowConfig.getAgreementSignMandatory())
		{
			person.setStatus(Person.SIGN_AGREEMENT);
		}

		Long departmentId = employeeData.getEmployee().getDepartmentId();
		department = simpleService.findById(Department.class, departmentId);

		if(clientId == null)
		{
			clientId = PersonHelper.generateClientId(department);
		}

		person.setClientId(clientId);
		person.setDepartmentId(departmentId);

		if (agreementNumber == null)
		{
			agreementNumber= AgreementNumberCreatorHelper.getNextAgreementNumber(department);
		}

		person.setAgreementNumber(agreementNumber);
		person.setCreationType(CreationType.SBOL);
		setPerson(person);
		return person;
	}


	/**
	 * сохранить
	 */
	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = getPerson();
		PersonHelper personHelper = new PersonHelper();
		personHelper.saveNewPerson(person,department);
	}


	/**
	 * +
	 * @return
	 */
	public Department getDepartment()
	{
		return department;
	}
}
