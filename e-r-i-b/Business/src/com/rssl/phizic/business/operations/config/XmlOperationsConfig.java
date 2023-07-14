package com.rssl.phizic.business.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.MethodMatcher;
import com.rssl.phizic.business.operations.NameMatcher;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.resources.ResourceType;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;
import javax.xml.xpath.*;

/**
 * @author Evgrafov
 * @ created 23.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class XmlOperationsConfig extends OperationsConfig
{
	private static final Map<String, String> xml2DbCategory = new HashMap<String, String>();

	static
	{
		xml2DbCategory.put("client",   AccessCategory.CATEGORY_CLIENT);
		xml2DbCategory.put("admin",    AccessCategory.CATEGORY_ADMIN);
		xml2DbCategory.put("employee", AccessCategory.CATEGORY_EMPLOYEE);
		xml2DbCategory.put("system",   AccessCategory.CATEGORY_SYSTEM);
	}

	public static final String CONFIG_FILE_NAME = "operations.xml";

	/**
	 * Переводит текст категории в ее однобуквенный эквивалент
	 * @param xmlCategory строковое представление категории
	 * @return однобуквенное представление
	 */
	public static String xml2DbCalegory(String xmlCategory)
	{
		String value = xml2DbCategory.get(xmlCategory);
		if (value == null)
			throw new RuntimeException("Invalid or unknown XML category " + xmlCategory);
		return value;
	}

	private List<ResourceType> resources;
	private Map<String, ResourceType> resourcesByClassName;

	private List<OperationDescriptor> operations;
	private Map<String, OperationDescriptor> operationsByKey;

	private List<Service> services;
	private Map<String, Service> servicesByKey;
	private Map<String, List<ServiceOperationDescriptor>> operationsByService;

	private Set<OperationDescriptor> spreadedOperations;
	private Set<OperationDescriptor> unspreadedOperations;
	private Map<OperationDescriptor, List<MethodMatcher>> methodMatchersMap;

	private XPathExpression selectServices;
	private XPathExpression selectOperations;
	private XPathExpression selectOperationRef;
	private XPathExpression selectRestriction;
	private XPathExpression selectMethodsRemove;
	private XPathExpression selectResources;

	private XmlOperationsConfig(){setupExpressions();doRefresh();}

	private static volatile XmlOperationsConfig config;
	private static final Object LOCK = new Object();
	public static XmlOperationsConfig get()
	{
		if (config != null)
			return config;

		synchronized (LOCK)
		{
			if (config != null)
				return config;

			config = new XmlOperationsConfig();
			return config;
		}
	}

	public Service findService(String key)
	{
		return servicesByKey.get(key);
	}

	public List<ResourceType> getResourceTypes()
	{
		return Collections.unmodifiableList(resources);
	}

	public List<OperationDescriptor> getOperationDescriptors()
	{
		return Collections.unmodifiableList(operations);
	}

	public OperationDescriptor findOperationByKey(String key) throws BusinessException
	{
		OperationDescriptor od = operationsByKey.get(key);

		if (od == null)
			throw new BusinessException("Операция с кодом " + key + " не найдена");
		return od;
	}

	public List<Service> getServices()
	{
		return Collections.unmodifiableList(services);
	}

	public ResourceType findResourceType(String className) throws BusinessException
	{
		return resourcesByClassName.get(className);
	}

	public ResourceType findResourceType(Class clazz) throws BusinessException
	{
		return findResourceType(clazz.getName());
	}

	public void doRefresh()
	{
		resources = new ArrayList<ResourceType>();
		resourcesByClassName = new HashMap<String, ResourceType>();

		operations = new ArrayList<OperationDescriptor>();
		operationsByKey = new HashMap<String, OperationDescriptor>();

		services = new ArrayList<Service>();
		servicesByKey = new HashMap<String, Service>();
		operationsByService = new HashMap<String, List<ServiceOperationDescriptor>>();

		spreadedOperations = new HashSet<OperationDescriptor>();
		unspreadedOperations = new HashSet<OperationDescriptor>();
		methodMatchersMap = new HashMap<OperationDescriptor, List<MethodMatcher>>();

		try
		{
			Document config = XmlHelper.loadDocumentFromResource(CONFIG_FILE_NAME);

			Element rootElem = config.getDocumentElement();

			readResources(rootElem);
			readOperations(rootElem);
			readServices(rootElem);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void setupExpressions()
	{
		try
		{
			XPath xpath = XPathFactory.newInstance().newXPath();

			selectServices      = xpath.compile("services/service");
			selectOperations    = xpath.compile("operations/operation");
			selectOperationRef  = xpath.compile("operation-ref");
			selectRestriction   = xpath.compile("restriction");
			selectMethodsRemove = xpath.compile("methods/remove");
			selectResources     = xpath.compile("resources/resource");
		}
		catch (XPathExpressionException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void readServices(Element rootElem) throws Exception
	{
		NodeList servicesNodeList = (NodeList) selectServices.evaluate(rootElem, XPathConstants.NODESET);

		for (int i = 0; i < servicesNodeList.getLength(); i++)
		{
			Element element = (Element) servicesNodeList.item(i);
			String key = element.getAttribute("key");
			Service service = readService(element);
			services.add(service);
			servicesByKey.put(key, service);
		}
	}

	private void readOperations(Element rootElem) throws Exception
	{
		NodeList operationsNodeList = (NodeList) selectOperations.evaluate(rootElem, XPathConstants.NODESET);

		for (int i = 0; i < operationsNodeList.getLength(); i++)
		{
			Element operationElem = (Element) operationsNodeList.item(i);
			OperationDescriptor od = readOperationDescriptor(operationElem);
			operations.add(od);
			operationsByKey.put(od.getKey(), od);
		}
	}

	private Service readService(Element serviceElement) throws Exception
	{
		String key = serviceElement.getAttribute("key");
		String name = serviceElement.getAttribute("name");
		String xmlCategory = serviceElement.getAttribute("category");
		String isCaAdmin = serviceElement.getAttribute("caadmin");
		String dbCategory = xml2DbCategory.get(xmlCategory);

		List<ServiceOperationDescriptor> serviceOperations = new ArrayList<ServiceOperationDescriptor>();

		if (dbCategory == null)
			throw new BusinessException("invalid service category: " + xmlCategory);

		Service service = new Service();
		service.setKey(key);
		service.setName(name);
		service.setCategory(dbCategory);
		if (StringHelper.isNotEmpty(isCaAdmin))
			service.setCaAdminService(Boolean.valueOf(isCaAdmin));

		NodeList operationsNodeList = (NodeList) selectOperationRef.evaluate(serviceElement, XPathConstants.NODESET);

		for (int i = 0; i < operationsNodeList.getLength(); i++)
		{
			Element operationRefElement = (Element) operationsNodeList.item(i);
			ServiceOperationDescriptor sod = readServiceOperationDescriptor(operationRefElement, service);

			OperationDescriptor od = sod.getOperationDescriptor();
			if (sod.isSpread())
			{
				if (unspreadedOperations.contains(od))
				{
					throw new BusinessException(
							"Ошибка при добавлении операции " + od + " в услугу: " + service +
							"Операция не может быть помечена и как распространяемая и как не распространяемая");
				}

				spreadedOperations.add(od);
			}
			else
			{
				if (spreadedOperations.contains(od))
				{
					throw new BusinessException("Ошибка при добавлении операции " + od + " в услугу: " + service
							+ ". Операция не может быть помечена и как распространяемая и как не распространяемая");
				}

				unspreadedOperations.add(od);
			}

			serviceOperations.add(sod);
		}

		operationsByService.put(key, serviceOperations);

		return service;
	}

	private ServiceOperationDescriptor readServiceOperationDescriptor(Element operationRefElement, Service service)
			throws BusinessException
	{
		String operationKey = operationRefElement.getAttribute("key");
		OperationDescriptor od = operationsByKey.get(operationKey);

		if (od == null)
		{
			throw new BusinessException("Can't find operation with key: " +
					operationKey + " in service " + service);
		}

		ServiceOperationDescriptor sod = new ServiceOperationDescriptor();
		sod.setService(service);
		sod.setOperationDescriptor(od);

		String spreadStr = operationRefElement.getAttribute("spread");

		if (!spreadStr.equals(""))
		{
			sod.setSpread(Boolean.parseBoolean(spreadStr));
		}
		else
		{
			sod.setSpread(true); //default
		}
		return sod;
	}

	public List<ServiceOperationDescriptor> getServiceOperationDescriptors(Service service)
	{
		return operationsByService.get(service.getKey());
	}

	public List<MethodMatcher> getRemoveMethodMatchers(OperationDescriptor descriptor)
	{
		List<MethodMatcher> list = methodMatchersMap.get(descriptor);
		return Collections.unmodifiableList(list);
	}

	private OperationDescriptor readOperationDescriptor(Element operationElem) throws Exception
	{
		String key = operationElem.getAttribute("key");
		String name = operationElem.getAttribute("name");
		String className = operationElem.getAttribute("class");
		String strategy = operationElem.getAttribute("strategy");
		Element restrictionElement = (Element) selectRestriction.evaluate(operationElem, XPathConstants.NODE);
		String interfaceName = restrictionElement == null ? null : restrictionElement.getAttribute("interface");
		String defaultRestrictionClassName = restrictionElement == null ? null : restrictionElement.getAttribute("default");

		if (interfaceName != null && defaultRestrictionClassName == null)
			throw new BusinessException("Default restriction class name for operation: "
					+ className + " must be defined.");

		OperationDescriptor od = new OperationDescriptor();
		od.setKey(key);
		od.setName(name);
		od.setOperationClassName(className);
		od.setRestrictionInterfaceName(interfaceName);
		od.setDefaultRestrictionClassName(defaultRestrictionClassName);
		od.setStrategy(strategy);

		List<MethodMatcher> matchers = readMethodMatchers(selectMethodsRemove, operationElem);
		methodMatchersMap.put(od, matchers);

		return od;
	}

	/**
	 * Читает описание MethodMatcher
	 * @param expression выражение для поиска матчеров
	 * @param operationElem корень для поиска (search root)
	 * @return список наденных матчеров
	 * @throws BusinessException
	 * @throws XPathExpressionException
	 */
	public static List<MethodMatcher> readMethodMatchers(XPathExpression expression, Element operationElem) throws BusinessException, XPathExpressionException
	{
		NodeList list = (NodeList) expression.evaluate(operationElem, XPathConstants.NODESET);

		List<MethodMatcher> result = new ArrayList<MethodMatcher>(list.getLength());

		for (int i = 0; i < list.getLength(); i++)
		{
			Element elem = (Element) list.item(i);
			MethodMatcher matcher = null;

			String name = elem.getAttribute("name");
			if (!name.equals(""))
			{
				matcher = new NameMatcher(name);
			}
			else
			{
				throw new BusinessException("Не заданы атрибуты!");
			}

			result.add(matcher);
		}

		return result;
	}

	private void readResources(Element rootElem) throws Exception
	{
		NodeList resourcesNodeList = (NodeList) selectResources.evaluate(rootElem, XPathConstants.NODESET);

		for (int i = 0; i < resourcesNodeList.getLength(); i++)
		{
			Element resourceElem = (Element) resourcesNodeList.item(i);
			ResourceType rt = readResourceType(resourceElem);
			resources.add(rt);
			resourcesByClassName.put(rt.getClassName(), rt);
		}
	}

	private ResourceType readResourceType(Element resourceElem) throws Exception
	{
		String name = resourceElem.getAttribute("name");
		String className = resourceElem.getAttribute("class");

		ResourceType rt = new ResourceType();

		rt.setName(name);
		rt.setClassName(className);

		return rt;
	}
}
