package com.rssl.phizic.business.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.MethodMatcher;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.OperationDescriptorService;
import com.rssl.phizic.business.resources.ResourceService;
import com.rssl.phizic.business.resources.ResourceType;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * @author Evgrafov
 * @ created 23.11.2005
 * @ $Author$
 * @ $Revision$
 * @noinspection ReturnOfCollectionOrArrayField
 */

public class DbOperationsConfig extends OperationsConfig
{
	private static ResourceService resourceService = new ResourceService();
	private static OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
	private static ServiceService serviceService = new ServiceService();

	private List<ResourceType> resourceTypes;
	private Map<String, ResourceType> resourceTypesByClassName;

	private List<OperationDescriptor> operationDescriprors;
	private Map<String, OperationDescriptor> operationsByKey;

	private Map<String, Service> servicesByKey;
	private Map<String, List<ServiceOperationDescriptor>> serviceOperationsByKey;
	private Set<OperationDescriptor> spreadedOperations;
	private Set<OperationDescriptor> unspreadedOperations;
	private Map<OperationDescriptor, List<MethodMatcher>> removeMatchersByOperation;
	private List<Service> allServices;

	private DbOperationsConfig(){doRefresh();}

	private static volatile DbOperationsConfig config;
	private static final Object LOCK = new Object();
	public static DbOperationsConfig get()
	{
		if (config != null)
			return config;

		synchronized (LOCK)
		{
			if (config != null)
				return config;

			config = new DbOperationsConfig();
			return config;
		}
	}

	public void doRefresh()
	{
		try
		{
			loadAll();
			loadMethods();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public List<ResourceType> getResourceTypes()
	{
		return resourceTypes;
	}

	public List<OperationDescriptor> getOperationDescriptors()
	{
		return operationDescriprors;
	}

	public OperationDescriptor findOperationByKey(String key) throws BusinessException
	{
		OperationDescriptor od = operationsByKey.get(key);
		if (od == null)
			throw new BusinessException("OperationDescriptor ключом " + key + " не найден");

		return od;
	}

	public List<Service> getServices()
	{
		return Collections.unmodifiableList(allServices);
	}

	public ResourceType findResourceType(String className) throws BusinessException
	{
		ResourceType rt = resourceTypesByClassName.get(className);

		if (rt == null)
		{
			throw new BusinessException("ResourceType для класса: " + className + " не найден");
		}
		return rt;
	}

	public ResourceType findResourceType(Class clazz) throws BusinessException
	{
		return findResourceType(clazz.getName());
	}

	public Service findService(String key)
	{
		return servicesByKey.get(key);
	}

	private synchronized void loadAll() throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{

				loadResourceTypes();
				loadOperationDescriptors();
				loadServices();
				return null;
			}
		});
	}

	public List<ServiceOperationDescriptor> getServiceOperationDescriptors(Service service)
	{
		return serviceOperationsByKey.get(service.getKey());
	}

	public List<MethodMatcher> getRemoveMethodMatchers(OperationDescriptor descriptor)
	{
		return removeMatchersByOperation.get(descriptor);
	}

	private void loadServices() throws BusinessException
	{
		allServices = serviceService.getAll();
		servicesByKey = new HashMap<String, Service>();
		serviceOperationsByKey = new HashMap<String, List<ServiceOperationDescriptor>>();
		spreadedOperations = new HashSet<OperationDescriptor>();
		unspreadedOperations = new HashSet<OperationDescriptor>();

		for (Service service : allServices)
		{
			servicesByKey.put(service.getKey(), service);
			List<ServiceOperationDescriptor> serviceOperations = serviceService.getServiceOperations(service);
			serviceOperationsByKey.put(service.getKey(), serviceOperations);

			for (ServiceOperationDescriptor sod : serviceOperations)
			{

				OperationDescriptor od = sod.getOperationDescriptor();
				if (sod.isSpread())
				{
					if (unspreadedOperations.contains(od))
					{
						throw new BusinessException("Ошибка при добавлении операции " + od
								+ " в услугу: " + service
								+ ". Операция не может быть помечена и как распространяемая, и как не распространяемая");
					}

					spreadedOperations.add(od);
				}
				else
				{
					if (spreadedOperations.contains(od))
					{
						throw new BusinessException("Ошибка при добавлении операции " + od
								+ " в услугу: " + service
								+ ". Операция не может быть помечена и как распространяемая, и как не распространяемая");
					}

					unspreadedOperations.add(od);
				}
			}
		}
	}

	private void loadOperationDescriptors() throws BusinessException
	{
		operationDescriprors = operationDescriptorService.getAll();

		operationsByKey = new HashMap<String, OperationDescriptor>();
		for (OperationDescriptor od : operationDescriprors)
		{
			operationsByKey.put(od.getKey(), od);
		}
	}

	private void loadResourceTypes() throws BusinessException
	{
		resourceTypes = resourceService.getAllResourceTypes();
		resourceTypesByClassName = new HashMap<String, ResourceType>();

		for (ResourceType rt : resourceTypes)
		{
			resourceTypesByClassName.put(rt.getClassName(), rt);
		}
	}

	public Set<OperationDescriptor> getSpreadedOperations()
	{
		return Collections.unmodifiableSet(spreadedOperations);
	}

	public Set<OperationDescriptor> getUnspreadedOperations()
	{
		return Collections.unmodifiableSet(unspreadedOperations);
	}

	private void loadMethods() throws Exception
	{
		removeMatchersByOperation = new HashMap<OperationDescriptor, List<MethodMatcher>>();
		Document config = XmlHelper.loadDocumentFromResource(XmlOperationsConfig.CONFIG_FILE_NAME);
		Element root = config.getDocumentElement();

		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression methodsRemove = xpath.compile("methods/remove");

		for (OperationDescriptor od : operationDescriprors)
		{
			Element operationElem = (Element) xpath.evaluate("//operation[@key=\"" + od.getKey() + "\"]", root, XPathConstants.NODE);
			List<MethodMatcher> removeMatchers = XmlOperationsConfig.readMethodMatchers(methodsRemove, operationElem);
			removeMatchersByOperation.put(od, removeMatchers);
		}
	}
}
