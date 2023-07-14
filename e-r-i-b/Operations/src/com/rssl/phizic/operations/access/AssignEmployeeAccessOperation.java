package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.employee.EmployeeService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.schemes.AccessSchemeService;
import com.rssl.phizic.operations.employees.EditEmployeeOperation;
import com.rssl.phizic.operations.scheme.AccessHelper;
import com.rssl.phizic.operations.scheme.SchemeOperationHelper;
import com.rssl.phizic.business.services.groups.ServicesGroupHelper;
import com.rssl.phizic.business.services.groups.ServicesGroupIterator;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: komarov $
 * @ $Revision: 79196 $
 */

public class AssignEmployeeAccessOperation extends EditEmployeeOperation
{
	private String newCategory;
	private List<Long> personalScheme;
	private Long newAccessSchemeId;
	private com.rssl.phizic.gate.schemes.AccessScheme newAccessScheme;
	private List<AssignAccessHelper> helpers;
	private AccessScheme oldScheme;

	@Override
	public void initialize(Long employeeId) throws BusinessException, BusinessLogicException
	{
		super.initialize(employeeId);
		boolean isCaAdmin = AccessHelper.isCAAdmin();
		helpers = AccessHelper.createAssignAccessHelpers(SecurityService.SCOPE_EMPLOYEE, isCaAdmin);
		try
		{
			com.rssl.phizic.gate.schemes.AccessScheme scheme = getEntity().getScheme();
			if (scheme != null)
				oldScheme = (AccessScheme) GateSingleton.getFactory().service(AccessSchemeService.class).getById(scheme.getId());
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

	/**
	 * задать параметры индивидуальной схемы прав
	 * @param newCategory категори€
	 * @param personalScheme сервисы
	 */
	public void setPersonalScheme(String newCategory, List<Long> personalScheme)
	{
		this.newCategory = newCategory;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.personalScheme = personalScheme;
	}

	/**
	 * задать схему прав
	 * @param newAccessSchemeId идентификатор схемы
	 */
	public void setNewAccessSchemeId(Long newAccessSchemeId)
	{
		this.newAccessSchemeId = newAccessSchemeId;
	}

	/**
	 * @return хелперы
	 * @throws BusinessException
	 */
	public List<AssignAccessHelper> getHelpers() throws BusinessException
	{
		//noinspection ReturnOfCollectionOrArrayField
		return helpers;
	}

	/**
	 * @return политика безопасности дл€ которой выполн€ютс€ настройки
	 */
	public AccessPolicy getPolicy()
	{
		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIA);
		return config.getPolicy(AccessType.employee);
	}

	/**
	 * @return структура сервисов
	 * @throws BusinessException
	 */
	public Map<Service, List<OperationDescriptor>> getServicesData() throws BusinessException
	{
		Map<Service, List<OperationDescriptor>> result = new HashMap<Service, List<OperationDescriptor>>();

		for (SchemeOperationHelper helper : helpers)
		{
			addServicesData(helper, result);
		}

		return result;
	}

	private void addServicesData(SchemeOperationHelper helper, Map<Service, List<OperationDescriptor>> result) throws BusinessException
	{
		List<Service> services = helper.getServices();
		OperationsConfig operationsConfig = DbOperationsConfig.get();
		for (Service service : services)
		{
			List<OperationDescriptor> operationDescriptors = new ArrayList<OperationDescriptor>();
			List<ServiceOperationDescriptor> serviceOperationDescriptors = operationsConfig.getServiceOperationDescriptors(service);
			if (serviceOperationDescriptors != null)
			{
				for (ServiceOperationDescriptor serviceOperationDescriptor : serviceOperationDescriptors)
				{
					operationDescriptors.add(serviceOperationDescriptor.getOperationDescriptor());
				}
			}

			result.put(service, operationDescriptors);
		}
	}

	/**
	 * назначить схему прав
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void assign() throws BusinessException, BusinessLogicException
	{
		try
		{
			EmployeeService service = GateSingleton.getFactory().service(EmployeeService.class);
			if (CollectionUtils.isEmpty(personalScheme))
				newAccessScheme = service.assign(newAccessSchemeId, getEntity());
			else
				newAccessScheme = service.assign(personalScheme, newCategory, getEntity());
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

	/**
	 * @return стара€ схема прав
	 */
	public AccessScheme getOldAccessScheme()
	{
		return oldScheme;
	}

	/**
	 * @return нова€ схема прав
	 */
	public com.rssl.phizic.gate.schemes.AccessScheme getNewAccessScheme()
	{
		return newAccessScheme;
	}

	/**
	 * @return группы сервисов
	 * @throws BusinessException
	 */
	public ServicesGroupIterator getServicesGroups() throws BusinessException
	{
		return ServicesGroupHelper.getServicesGroups();
	}
}
