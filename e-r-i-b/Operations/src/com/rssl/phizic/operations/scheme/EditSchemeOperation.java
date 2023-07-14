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
	 * ���������������� �������� ��� �������������� ������������ �����
	 * @param schemeId ID �����
	 * @throws BusinessException ����� �� �������
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
			throw new BusinessException("����� ������� id: " + schemeId + " �� �������", e);
		}
		if (scheme == null)
			throw new BusinessException("����� ������� id: " + schemeId + " �� �������");

		if (scheme.isCAAdminScheme() && !isAllowEditCASchemes())
			throw new AccessControlException("��� ������� � ����� ���� �������������� ��. ID = " + schemeId);

		String scope        = AccessHelper.getScopeByCategory(scheme.getCategory());
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		this.serviceHelpers = AccessHelper.createSchemeHelpers(scope, isCaAdmin);
	}

	/**
	 * ���������������� �������� ��� �������������� ����� �����
	 * @param scope �� SecurityService.SCOPE_*
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
	 * @return ����� ������������� �����
	 */
	public SharedAccessScheme getEntity()
	{
		return scheme;
	}

	/**
	 * @param name ���������� ����� ��� �����
	 */
	public void setNewName(String name)
	{
		scheme.setName(name);
	}

	/**
	 * @param category ���������� ����� ��������� �����
	 */
	public void setNewCategory(String category)
	{
		scheme.setCategory(category);
	}

	/**
	 * @param serviceIds ������ �������� ��� ����������� � �����
	 */
	public void setNewServices(List<Long> serviceIds) throws BusinessException
	{
		List<Service> newServices = new ArrayList<Service>();
		boolean isMailManagement = false;
		for (Long serviceId : serviceIds)
		{
			Service service = findService(serviceId);

			if (service == null)
				throw new BusinessException("������ ID" + serviceId + " �� ������� ����� ���������� �����");

			newServices.add(service);
			if (!isMailManagement)
				isMailManagement = service.getKey().equals("MailManagment");
		}

		scheme.getServices().clear();
		scheme.getServices().addAll(newServices);
		scheme.setMailManagement(isMailManagement);
	}

	/**
	 * @return ������� ��� �����������
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
	 * @return ���, ���� ������ ������� �������� ������
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
	 * ��������� ���������
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
	 * @return ��������� �� �������������� ���� ��������������� ��
	 */
	public boolean isAllowEditCASchemes()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
	}

	/**
	 * @return ������� ��������� � �������� ��������� ���.
	 */
	public boolean isVSPEmployee()
	{
		return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isVSPEmployee();
	}

	/**
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public ServicesGroupIterator getServicesGroups() throws BusinessException
	{
		return ServicesGroupHelper.getServicesGroups();
	}
}
