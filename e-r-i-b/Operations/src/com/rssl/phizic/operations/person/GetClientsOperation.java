package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * User: Kidyaev
 * Date: 10.10.2005
 * Time: 14:08:47
 */
public class GetClientsOperation extends OperationBase implements ListEntitiesOperation
{
	private final static ClientService service = GateSingleton.getFactory().service(ClientService.class);
	private static final DepartmentService departmentService = new DepartmentService();

	public void initialize() throws BusinessException, BusinessLogicException
	{
	}

	@Transactional
	public List<Client> getClientsByTemplate(Client template, int maxResults) throws BusinessException, BusinessLogicException
	{
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		Long departmentId = employeeData.getEmployee().getDepartmentId();
		Office office = departmentService.findById(departmentId);
		try
		{
			return service.getClientsByTemplate(template, office, 0, maxResults);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
