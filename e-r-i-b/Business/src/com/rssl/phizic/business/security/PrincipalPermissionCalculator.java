package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.operations.config.XmlOperationsConfig;
import com.rssl.phizic.business.resources.own.SchemeOwnService;
import com.rssl.phizic.business.schemes.*;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.client.DefaultSchemeType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Roshka
 * @ created 13.04.2006
 * @ $Author$
 * @ $Revision$
 */

class PrincipalPermissionCalculator implements PermissionCalculator, Serializable
{
	private static final SchemeOwnService schemeOwnService = new SchemeOwnService();
	protected static final DefaultAccessSchemeService defaultAccessSchemeService = new DefaultAccessSchemeService();

	private Set<String> rigidOperations = new HashSet<String>();        // ����������� ��� ���������� ��������, �������� ������ ���� "operationKey[#serviceKey]" #serviceKey ����������� ������ ��� �������� � spread=false
	private Set<String> weakOperations = new HashSet<String>();         // ��� �������� �������� � ������ ����������� ����������, �������� �����
	private Set<String> rigidServices = new HashSet<String>();          // ����� ����� ����� � ������� ��������� ������� ���������������
	private Set<String> weakServices = new HashSet<String>();           // ����� ����� ����� � ������� ��������� ������� ��������������� ��� ����� ��������������� (spread) ��������
	private UserPrincipal principal;

	public PrincipalPermissionCalculator(UserPrincipal principal) throws BusinessException
	{
		this.principal = principal;
		fill();
	}

	UserPrincipal getPrincipal()
	{
		return principal;
	}

	public boolean impliesOperation(String serviceKey, String operationKey, boolean rigid)
	{
		// ��������� ������ ��� � spreaded
		boolean implies = rigidOperations.contains(operationKey);
		if (implies)
		{
			return true;
		}

		// ��������� ������ ��� � unspreaded
		if (serviceKey != null)
		{
			implies = rigidOperations.contains(operationKey + "#" + serviceKey);
		}

		if (rigid)
		{
			return implies;
		}

		return weakOperations.contains(operationKey);
	}

	public boolean impliesService(String serviceKey, boolean rigid)
	{
		if (rigid)
		{
			return rigidServices.contains(serviceKey);
		}
		return weakServices.contains(serviceKey);
	}

	public boolean impliesAccessScheme(String schemeKey)
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		SharedAccessScheme scheme = schemesConfig.getByCode(schemeKey);
		if (scheme == null)
			return false;

		List<Service> services = scheme.getServices();
		if (CollectionUtils.isEmpty(services))
			return false;

		for (Service service : services)
		{
			if (impliesService(service.getKey(), false))
				return true;
		}

		return false;
	}

	private void fill() throws BusinessException
	{
		AuthenticationContext authenticationContext = AuthenticationContext.getContext();
		AccessScheme scheme = getAccessScheme(principal, authenticationContext);
		if (scheme == null)
			return;

		Set<String> blockedServices = getBlockedServices();           //����� �����, ������ � ������� ������������
		Set<String> blockedOperations = getBlockedOperations();     //��������������� ��������

		List<Service> services = scheme.getServices();
		OperationsConfig operationsConfig = XmlOperationsConfig.get();
		// ������ ������ - ���������� ��������� ��������
		for (Service service : services)
		{
			String serviceKey = service.getKey();
            //���� ������ ������������, �� � rigidServices �� ���������
            if (blockedServices.contains(serviceKey))
                continue;

			List<ServiceOperationDescriptor> serviceOperations = operationsConfig.getServiceOperationDescriptors(service);

			int count = 0;
			if (serviceOperations != null) {
			for (ServiceOperationDescriptor sod : serviceOperations)
			{
				String operationKey = sod.getOperationDescriptor().getKey();
				if (blockedOperations.contains(operationKey))
				{
					count++;
					continue;
				}
				else if (sod.isSpread())
				{
					rigidOperations.add(operationKey);
				}
				else
				{
					rigidOperations.add(operationKey + "#" + serviceKey);
				}
				weakOperations.add(operationKey);
			}
			}
			if (CollectionUtils.isEmpty(serviceOperations) || count != serviceOperations.size())
			{
				rigidServices.add(serviceKey);
			}
		}

		// ������ ������ - ���������� ��������� ������
		// ��� ���� ��������� (spread) �������� �������� �� ������ ������
		weakServices.addAll(rigidServices);
		List<Service> allServices = operationsConfig.getServices();
		for (Service service : allServices)
		{
			//�������� ������ �� ������ ��� �� ��������� ��� � ����� ����
			if (!service.getCategory().equals(scheme.getCategory()))
				continue;

			String serviceKey = service.getKey();
            //���� ������ ������������, �� � weakServices ��� �� �� ���������
            if (blockedServices.contains(serviceKey))
                continue;

			List<ServiceOperationDescriptor> serviceOperations = operationsConfig.getServiceOperationDescriptors(service);
			for (ServiceOperationDescriptor sod : serviceOperations)
			{
				String operationKey = sod.getOperationDescriptor().getKey();
				if (rigidOperations.contains(operationKey))
				{
					weakServices.add(serviceKey);
					break;
				}
			}
		}
	}

	protected AccessScheme getAccessScheme(UserPrincipal principal, AuthenticationContext context) throws BusinessException
	{
		if (Application.atm == LogThreadContext.getApplication() && context.getLogin() != null && !context.isCheckedCEDBO())
			return defaultAccessSchemeService.findByCreationTypeAndDepartment(DefaultSchemeType.CARD, null);

		if(principal.isColdPeriod())
			return new ColdPeriodAccessScheme(schemeOwnService.findScheme(principal.getLogin(), principal.getAccessType()));

		return schemeOwnService.findScheme(principal.getLogin(), principal.getAccessType());
	}

	/**
	 * @return �������������� �������
	 */
	Set<String> getBlockedServices()
	{
		return Collections.emptySet();
	}

	/**
	 * @return ��������������� ��������
	 */
	Set<String> getBlockedOperations()
	{
		return Collections.emptySet();
	}
}