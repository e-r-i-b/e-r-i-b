package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.AgreementNumberCreatorHelper;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.business.persons.clients.PersonImportService;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;

import java.util.Set;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 10.10.2005 Time: 17:03:03 */
public class ImportClientOperation extends OperationBase
{
	//*********************************************************************//
	//***************************  CLASS MEMBERS  *************************//
	//*********************************************************************//

	private static ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
	private static PersonImportService importService = new PersonImportService();
	private static final SimpleService    simpleService    = new SimpleService();

	//*********************************************************************//
	//**************************  INSTANCE MEMBERS  ***********************//
	//*********************************************************************//

	private String clientId;
	private Set<String> clientIds;
	private String agreementNumber = "";

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Set<String> getClientIds()
	{
		return clientIds;
	}

	/**
	 * список клиентов отличных от основного
	 * @param clientIds
	 */
	public void setClientIds(Set<String> clientIds)
	{
		this.clientIds = clientIds;
	}

	public Client getClient() throws BusinessException
	{
		if (clientId == null)
			return null;
		try
		{
			return clientService.getClientById(clientId);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	@Transactional
	public ActivePerson createPerson() throws BusinessException, BusinessLogicException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		Long departmentId = employeeData.getEmployee().getDepartmentId();
		Department department = simpleService.findById(Department.class, departmentId);

		agreementNumber = AgreementNumberCreatorHelper.getNextAgreementNumber(department);
		Client client = getClient();
		return importService.createPerson(client, client.getId(), department, agreementNumber, getClientIds(), CreationType.SBOL, DefaultSchemeType.SBOL);
	}
}
