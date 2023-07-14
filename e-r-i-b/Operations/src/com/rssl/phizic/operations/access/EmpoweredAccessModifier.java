package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.MultiInstanceAccessPolicyService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.own.MultiInstanceSchemeOwnService;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.MultiInstanceAccessSchemeService;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import org.hibernate.Session;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 09.08.2006
 * @ $Author: krenev $
 * @ $Revision: 58098 $
 */

/*todo ��� ����������� ��������� ��, ��� ������� ��� �������
  todo �� ������� �� ���������� ��� � jsp, ���������, action, form
  todo ���� ������, �.�. ��� ������������� ��� �������� � ����� action
  todo ��� ���������, ������������ ����������.
* */

public class EmpoweredAccessModifier
{
	private final static MultiInstanceSchemeOwnService schemeOwnService = new MultiInstanceSchemeOwnService();
	private final static MultiInstanceAccessSchemeService accessSchemeService = new MultiInstanceAccessSchemeService();
	private final static MultiInstanceAccessPolicyService accessModesService  = new MultiInstanceAccessPolicyService();

	private final static Comparator<Service> serviceComparator = new Comparator<Service>()
	{
		public int compare(Service o1, Service o2)
		{
			return o1.getId().compareTo(o2.getId());
		}
	};

	private List<Service> currentServices;  // ������� ������(���������(�����������) �������)
	private List<Service> trustingServices; // ������ ����������(��� ������ ��������� ������ ����������)
	private ActivePerson person;
	private AccessType accessType = AccessType.simple;
	private boolean currentAccessAllowed;
	private Properties properties = null;
	private String instanceName = null;

	/**
	 *  �����������
	 * @param person �������������
	 * @param trustingPerson ����������
	 * @param accessType ��� �������
	 * @param instanceName ��� ���������� �� , � ������� ���������� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public EmpoweredAccessModifier(ActivePerson person, ActivePerson trustingPerson, AccessType accessType, String instanceName) throws BusinessException, BusinessLogicException
	{
		this.instanceName = instanceName;
		this.accessType = accessType;
		AccessScheme scheme = schemeOwnService.findScheme(person.getLogin(), accessType, instanceName);

		if (scheme != null)
		{
			currentServices = scheme.getServices();
		}
		else
		{
			currentServices = new ArrayList<Service>();
		}
		
		AccessScheme trustingScheme = schemeOwnService.findScheme(trustingPerson.getLogin(), accessType, instanceName);

		if (trustingScheme == null)
		{
			trustingServices = new ArrayList<Service>();
		}
		else
		{
			trustingServices = trustingScheme.getServices();
		}

		this.person = person;

		try
		{
			Properties temp = null;
			this.currentAccessAllowed = false;
			if(person.getLogin() != null)
			{
				temp = accessModesService.getProperties(person.getLogin(), accessType, instanceName);
				currentAccessAllowed = (temp != null);
			}
			this.properties = temp == null ? new Properties() : temp;
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������������, � ������� ������� ������. 
	 * @return
	 */
	public ActivePerson getPerson()
	{
		return person;
	}

	/**
	 * �������� ��� �������, �� ������� �������� ������
	 * @return
	 */
	public boolean isCurrentAccessAllowed()
	{
		return currentAccessAllowed;
	}

	/**
	 * ��������, ��� �������� ���� �������
	 * @return
	 */
	public Properties getProperties()
	{
		return properties;
	}

	/**
	 * ������� ���������(�����������) �������
	 * @return
	 */
	public List<Service> getCurrentServices()
	{
		return Collections.unmodifiableList(currentServices);
	}

	/**
	 * ��������� ������� ����������
	 * @return
	 */
	public List<Service> getTrustingServices()
	{
		return Collections.unmodifiableList(trustingServices);
	}

	/**
	 * �������� ��� �������� ������
	 * @return
	 */
	public AccessPolicy getPolicy()
	{
		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class, accessType.getApplication());
		return config.getPolicy(accessType);
	}

	/**
	 * ������ �������� �������� � ������.
	 * @return
	 */
	public Map<Service, List<OperationDescriptor>> getServiceOperationDescriptors()
	{
		Map<Service, List<OperationDescriptor>> result = new HashMap<Service, List<OperationDescriptor>>();
		List<Service> services = trustingServices;
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
		return result;
	}

	/**
	 * �������� ������� ����� ������� �������
	 * @param newServices ��������� �������
	 * @param enabled �������� �� ������� ��� �������
	 * @param properties ������� ��� �������� ���� �������
	 * @throws BusinessException
	 */
	public void change(final List<Service> newServices,Boolean enabled, Properties properties) throws BusinessException
	{
		try
		{
			if(enabled)
			{
				accessModesService.enableAccess(person.getLogin(), accessType,properties, instanceName);
			}
			else
			{
				accessModesService.disableAccess(person.getLogin(), accessType, instanceName);
			}


			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					List<Service> filteredServices = filterServices(newServices);
					AccessScheme newScheme =
							accessSchemeService.createPersonalScheme(AccessCategory.CATEGORY_CLIENT, filteredServices);

					accessSchemeService.save(newScheme, instanceName);
					schemeOwnService.setScheme(person.getLogin(), accessType, newScheme, instanceName);
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������ ������ ����� �������� ����������
	 * @param services
	 * @return ��������������� ������
	 */
	public List<Service> filterServices(List<Service> services)
	{
		List<Service> sorted = new ArrayList<Service>(trustingServices);
		Collections.sort(sorted, serviceComparator);

		List<Service> result = new ArrayList<Service>();
		for (Service service : services)
		{

			if(Collections.binarySearch(sorted, service, serviceComparator) >= 0)
			{
				result.add(service);
			}
		}

		return result;
	}

	public String getInstanceName()
	{
		return instanceName;
	}

	public void setInstanceName(String instanceName)
	{
		this.instanceName = instanceName;
	}
}