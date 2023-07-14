package com.rssl.phizic.operations.erkc;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeContext;
import com.rssl.phizic.operations.erkc.context.ERKCEmployeeData;
import com.rssl.phizic.operations.erkc.context.Functional;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 12.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция для работы ЕРКЦ сотрудника в разных блоках
 */

public class ERKCEmployeeMultiBlockOperation extends ERKCEmployeeOperationBase
{
	private static final String ERKC_EMPLOYEE_CONTEXT_KEY_PREFIX = "ERKCEmployeeContext.";
	private static final String CSA_PERSON_INFO_CONTEXT_KEY = ERKC_EMPLOYEE_CONTEXT_KEY_PREFIX + "csaPersonInfo";
	private static final String CURRENT_FUNCTIONAL_CONTEXT_KEY = ERKC_EMPLOYEE_CONTEXT_KEY_PREFIX + "currentFunctional";
	private static final String ADDITIONAL_PARAMETERS_CONTEXT_KEY = ERKC_EMPLOYEE_CONTEXT_KEY_PREFIX + "additionalParameters";

	private static final CSAAdminAuthService csaAdminAuthService = new CSAAdminAuthService();
	private static final PersonService personService = new PersonService();

	private List<ActivePerson> persons = new ArrayList<ActivePerson>();

	/**
	 * сохранение контекста ЕРКЦ сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void storeNodeContext() throws BusinessException, BusinessLogicException
	{
		try
		{
			Map<String, Object> parameters = new HashMap<String, Object>(2);
			ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
			parameters.put(CSA_PERSON_INFO_CONTEXT_KEY, erkcEmployeeData.getCsaPersonInfo());
			parameters.put(CURRENT_FUNCTIONAL_CONTEXT_KEY, erkcEmployeeData.getCurrentFunctional());
			parameters.put(ADDITIONAL_PARAMETERS_CONTEXT_KEY, erkcEmployeeData.getAdditionalParameters());
			csaAdminAuthService.saveOperationContext(parameters);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void findPerson(Profile profile) throws BusinessException
	{
		persons.addAll(personService.getByFIOAndDoc(profile.getSurName(), profile.getFirstName(), profile.getPatrName(), profile.getPassport(), null, profile.getBirthDay(), profile.getTb()));
		if (CollectionUtils.isNotEmpty(persons))
			return;

		for (Profile historyProfile : profile.getHistory())
		{
			findPerson(historyProfile);
			if (CollectionUtils.isNotEmpty(persons))
				return;
		}
	}

	/**
	 * инициализация операции для восстановления контекста сотрудника ЕРКЦ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeRestore() throws BusinessException, BusinessLogicException
	{
		try
		{
			ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
			Map<String, Object> additionalParameters = new HashMap<String, Object>();
			ProfileWithFullNodeInfo csaPersonInfo;
			Functional functional;
			if (erkcEmployeeData != null)
			{
				csaPersonInfo = erkcEmployeeData.getCsaPersonInfo();
				functional = erkcEmployeeData.getCurrentFunctional();
			}
			else
			{
				Map<String,Object> operationContext = csaAdminAuthService.getOperationContext();
				csaPersonInfo = (ProfileWithFullNodeInfo) operationContext.get(CSA_PERSON_INFO_CONTEXT_KEY);
				functional = (Functional) operationContext.get(CURRENT_FUNCTIONAL_CONTEXT_KEY);
				//noinspection unchecked
				additionalParameters.putAll((Map<String, Object>) operationContext.get(ADDITIONAL_PARAMETERS_CONTEXT_KEY));
			}
			initializeCSAPersonInfo(csaPersonInfo);
			initializeFunctional(functional);
			initializeAdditionalParameters(additionalParameters);
			findPerson(csaPersonInfo);
			if (persons.size() == 1)
				initializeFilterParams(persons.get(0));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private ActivePerson findPerson(Long id) throws BusinessException
	{
		return (ActivePerson) personService.findById(id);
	}

	/**
	 * инициализация операции для восстановления контекста сотрудника ЕРКЦ
	 * @param id идентификатор клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeRestoreByPersonId(Long id) throws BusinessException, BusinessLogicException
	{
		ERKCEmployeeData erkcEmployeeData = ERKCEmployeeContext.getData();
		initializeCSAPersonInfo(erkcEmployeeData.getCsaPersonInfo());
		initializeFunctional(erkcEmployeeData.getCurrentFunctional());
		initializeFilterParams(findPerson(id));
		initializeAdditionalParameters(erkcEmployeeData.getAdditionalParameters());
	}

	/**
	 * @return список найденных клиентов
	 */
	public List<ActivePerson> getPersons()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return persons;
	}

	/**
	 * восстановление контекста ЕРКЦ сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void restoreNodeContext() throws BusinessException, BusinessLogicException
	{
		initializeERKCContext();
	}
}
