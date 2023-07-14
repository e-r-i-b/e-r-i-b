package com.rssl.phizic.operations.erkc;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeData;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 12.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операци работы сотрудника ЕРКЦ
 */

public abstract class ERKCEmployeeOperationBase extends OperationBase
{
	private static final DepartmentService departmentService = new DepartmentService();

	private Map<String, Object> filterParams = new HashMap<String, Object>();
	private Profile templateProfile = new Profile();
	private Functional functional;
	private ProfileWithFullNodeInfo csaPersonInfo;
	private boolean clientDefined = false;
	private Long userLoginId;
	private Long currentPersonId;
	private Map<String, Object> additionalParameters = new HashMap<String, Object>();

	private PersonDocument getPersonDocument(ActivePerson person) throws BusinessException
	{
		PersonDocument document = PersonHelper.getPersonPriorityDocument(person);

		//Если нет паспорта way, то нужно использовать основной документ клиента (DOCUMENTS.IS_MAIN).
		//Если нет падать, если несколько то приоритетней паспорт РФ, если нет паспорта РФ то первый попавшийся, если несколько паспортов РФ, то так же первый попавшийся.
		if(document == null || (document.getDocumentType() != PersonDocumentType.PASSPORT_WAY && !document.getDocumentMain()))
		{
			throw new BusinessException("Не найден документ удостоверяющий личность для персоны с id = " + person.getId());
		}

		return document;
	}

	protected void initializeFunctional(Functional functional)
	{
		this.functional = functional;
	}

	protected void initializeCSAPersonInfo(ProfileWithFullNodeInfo csaPersonInfo)
	{
		this.csaPersonInfo = csaPersonInfo;
	}

	protected void initializeAdditionalParameters(Map<String, Object> additionalParameters)
	{
		this.additionalParameters.putAll(additionalParameters);
	}

	/**
	 * @return определились ли клиентом
	 */
	public boolean isClientDefined()
	{
		return clientDefined;
	}

	protected void initializeFilterParams(ActivePerson person) throws BusinessException
	{
		String personFullName = person.getFullName();
		currentPersonId = person.getId();
		userLoginId =   person.getLogin().getId();
		filterParams.put("personId",  currentPersonId);
		filterParams.put("fio",       personFullName);
		filterParams.put("surName",   person.getSurName());
		filterParams.put("firstName", person.getFirstName());
		filterParams.put("patrName",  person.getPatrName());
		filterParams.put("birthday",  person.getBirthDay().getTime());

		PersonDocument personDoc = getPersonDocument(person);
		filterParams.put("passportNumber",  personDoc.getDocumentNumber());
		filterParams.put("passportSeries",  personDoc.getDocumentSeries());

		Long departmentId = person.getDepartmentId();
		filterParams.put("departmentId",    departmentId);
		Department personDepartment = departmentService.findById(departmentId);
		filterParams.put("departmentName", personDepartment.getName());

		//Доп. инфа для левого меню
		filterParams.put("personStatus",    person.getStatus());
		filterParams.put("creationType",    person.getCreationType());
		filterParams.put("personFullName",  personFullName);
		filterParams.put("loginBlock",      CollectionUtils.isEmpty(person.getLogin().getBlocks()));

		templateProfile.setSurName(person.getSurName());
		templateProfile.setFirstName(person.getFirstName());
		templateProfile.setPatrName(person.getPatrName());
		templateProfile.setPassport(StringHelper.getEmptyIfNull(personDoc.getDocumentNumber()) + StringHelper.getEmptyIfNull(personDoc.getDocumentSeries()));
		templateProfile.setBirthDay(person.getBirthDay());
		templateProfile.setTb(personDepartment.getRegion());

		clientDefined = true;
	}

	protected Profile getTemplateProfile()
	{
		return templateProfile;
	}

	protected void initializeERKCContext() throws BusinessException, BusinessLogicException
	{
		ERKCEmployeeData erkcEmployeeData = getErkcEmployeeData();
		erkcEmployeeData.setCsaPersonInfo(csaPersonInfo);
		erkcEmployeeData.setCurrentFunctional(functional);
		erkcEmployeeData.setUserInfo(filterParams);
		erkcEmployeeData.setCurrentPersonId(currentPersonId);
		erkcEmployeeData.setUserLoginId(userLoginId);
		erkcEmployeeData.setAdditionalParameters(additionalParameters);
	}

	protected ERKCEmployeeData getErkcEmployeeData()
	{
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		if (erkcEmployeeData != null)
			return erkcEmployeeData;

		ERKCEmployeeData newERKCEmployeeData = new ERKCEmployeeData();
		ERKCEmployeeContext.setData(newERKCEmployeeData);
		return newERKCEmployeeData;
	}
}
