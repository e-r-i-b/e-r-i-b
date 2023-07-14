package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.groups.ServicesGroupHelper;
import com.rssl.phizic.business.services.groups.ServicesGroupIterator;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditSchemeOperation extends OperationBase implements EditEntityOperation
{
	private SharedAccessScheme scheme;
	private List<SchemeOperationHelper> serviceHelpers = new ArrayList<SchemeOperationHelper>();

	/**
	 * Инициализировать операцию для редактирования существующей схемы
	 * @param schemeId ID схемы
	 * @throws BusinessException схема не найдена
	 */
	public void initialize(Long schemeId) throws BusinessException, BusinessLogicException
	{
		try
		{
			scheme = (SharedAccessScheme) GateSingleton.getFactory().service(com.rssl.phizic.gate.schemes.AccessSchemeService.class).getById(schemeId);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException("Схема доступа id: " + schemeId + " не найдена", e);
		}
		if (scheme == null)
			throw new BusinessException("Схема доступа id: " + schemeId + " не найдена");

		if (scheme.isCAAdminScheme() && !isAllowEditCASchemes())
			throw new AccessControlException("Нет доступа к схеме прав администратора ЦА. ID = " + schemeId);

		String scope        = AccessHelper.getScopeByCategory(scheme.getCategory());
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		this.serviceHelpers = AccessHelper.createSchemeHelpers(scope, isCaAdmin);
	}

	/**
	 * Инициализировать операцию для редактирования новой схемы
	 * @param scope см SecurityService.SCOPE_*
	 */
	public void initializeNew(String scope) throws BusinessException
	{
		SharedAccessScheme temp = new SharedAccessScheme();
		boolean isCaAdmin = isAllowEditCASchemes();
		boolean isVSPEmployee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isVSPEmployee();

		this.serviceHelpers = AccessHelper.createSchemeHelpers(scope, isCaAdmin);
		temp.setCategory(serviceHelpers.get(0).getCategory());
		temp.setVSPEmployeeScheme(isVSPEmployee);
		this.scheme = temp;
	}

	/**
	 * @return схема Редактируемая схема
	 */
	public SharedAccessScheme getEntity()
	{
		return scheme;
	}

	/**
	 * @param name Установить новое имя схемы
	 */
	public void setNewName(String name)
	{
		scheme.setName(name);
	}

	/**
	 * @param category Установить новую категорию схемы
	 */
	public void setNewCategory(String category)
	{
		scheme.setCategory(category);
	}

	/**
	 * @param serviceIds список сервисов для объединения в схему
	 */
	public void setNewServices(List<Long> serviceIds) throws BusinessException
	{
		List<Service> newServices = new ArrayList<Service>();
		boolean isMailManagement = false;
		for (Long serviceId : serviceIds)
		{
			Service service = findService(serviceId);

			if (service == null)
				throw new BusinessException("Услуга ID" + serviceId + " не найдена среди допустимых услуг");

			newServices.add(service);
			if (!isMailManagement)
				isMailManagement = service.getKey().equals("MailManagment");
		}

		scheme.getServices().clear();
		scheme.getServices().addAll(newServices);
		scheme.setMailManagement(isMailManagement);
	}

	/**
	 * @return хелперы для отображения
	 */
	public List<SchemeOperationHelper> getServiceHelpers()
	{
		return serviceHelpers;
	}

	private Service findService(Long serviceId) throws BusinessException
	{
		for (SchemeOperationHelper helper : serviceHelpers)
		{
			Service service = helper.findById(serviceId);
			if (service != null)
				return service;
		}
		return null;
	}

	/**
	 * @return мап, ключ услуга зачение операции услуги
	 */
	public Map<Service, List<OperationDescriptor>> getServicesTuple() throws BusinessException
	{
		Map<Service, List<OperationDescriptor>> result = new HashMap<Service, List<OperationDescriptor>>();

		for (SchemeOperationHelper helper : serviceHelpers)
		{
			addServicesTuple(helper, result);
		}

		return result;
	}

	private void addServicesTuple(SchemeOperationHelper helper, Map<Service, List<OperationDescriptor>> result) throws BusinessException
	{
		List<Service> services = helper.getServices();
		OperationsConfig operationsConfig = DbOperationsConfig.get();
		for (Service service : services)
		{
			List<OperationDescriptor> operationDescriptors = new ArrayList<OperationDescriptor>();
			List<ServiceOperationDescriptor> serviceOperationDescriptors = operationsConfig.getServiceOperationDescriptors(service);

			for (ServiceOperationDescriptor serviceOperationDescriptor : serviceOperationDescriptors)
			{
				operationDescriptors.add(serviceOperationDescriptor.getOperationDescriptor());
			}

			result.put(service, operationDescriptors);
		}
	}

	/**
	 * сохранить изменения
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			GateSingleton.getFactory().service(com.rssl.phizic.gate.schemes.AccessSchemeService.class).save(getEntity());
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
	 * @return разрешено ли редактирование схем администраторов ЦА
	 */
	public boolean isAllowEditCASchemes()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
	}

	/**
	 * @return текущий сотрудник с пометкой Сотрудник ВСП.
	 */
	public boolean isVSPEmployee()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isVSPEmployee();
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
