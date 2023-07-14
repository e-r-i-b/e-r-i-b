package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.xslt.lists.cache.composers.CacheKeyComposer;
import com.rssl.phizic.business.xslt.lists.cache.composers.SessionCacheKeyComposer;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 03.11.2005
 * Time: 17:25:10
 */
public class XmlEntitiesListsConfig extends EntityListsConfig
{
	private static final String ENTITIES_LISTS_DEFINITION_FILE_NAME = "lists.xml";
    private static final String APPLICATION = "application";
    private static final String SESSION = "session";

	//мап <название справочника, определение справочника>
    private Map<String, EntityListDefinition> entitiesDefinitionsMap;
	//мап <название класса сущности, список справочников, по которым будем чистить кеш>
	private Map<Class, List<EntityListDefinition>> listsByClasses = new HashMap<Class, List<EntityListDefinition>>();
	//мап - <название класса, инстанс копмозера>
	private Map<Class, ? extends CacheKeyComposer> composers = new HashMap<Class, CacheKeyComposer>();

	public XmlEntitiesListsConfig(PropertyReader reader)
    {
	    super(reader);
        try
        {
	        Document document = XmlHelper.loadDocumentFromResource(ENTITIES_LISTS_DEFINITION_FILE_NAME);
			Element root = document.getDocumentElement();
            loadEntityListDefinitions(root);
            loadCacheKeyComposers(root, "cache-key-composers/composer", composers);
            this.<SessionCacheKeyComposer>loadCacheKeyComposers(root, "cache-key-composers/session-composer", composers);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

	private <T extends CacheKeyComposer> void loadCacheKeyComposers(Element root, String path, Map destiation) throws TransformerException
	{
		NodeList childNodes = XmlHelper.selectNodeList(root, path);
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			Element element     = (Element) childNodes.item(i);
			String className    = element.getAttribute("class");
			String composerName = element.getAttribute("composer");
			try
			{
				Class composerClass = ClassHelper.loadClass(composerName);
				T composerInstance = (T) composerClass.getConstructor().newInstance();
				destiation.put(ClassHelper.loadClass(className), composerInstance);
			}
			catch (Exception e)
			{
				throw new ConfigurationException("Ошибка при регистрации композера " + composerName + " для класса " + className, e);
			}
		}
	}

	/**
	 * @param name название справочника
	 * @return определение справочника
	 */
	public EntityListDefinition getListDefinition(String name)
    {
        return entitiesDefinitionsMap.get(name);
    }

	/**
	 * @return итератор всех определений справочников
	 */
	public Iterator<EntityListDefinition> getListDefinitionsIterator()
	{
		return entitiesDefinitionsMap.values().iterator();
	}

	/**
	 * @param clazz класс для которого получаем композер
	 * @return композер для класса
	 */
	public <T extends CacheKeyComposer> T getComposer(Class clazz)
	{
		return (T) composers.get(clazz);
	}

	/**
	 * @param clazz класc
	 * @return список определений справочников, в которых используется класс clazz
	 */
	public List<EntityListDefinition> getListDefinitionsByClassName(Class clazz)
	{
		return listsByClasses.get(clazz);
	}

	private void loadEntityListDefinitions(Element root) throws TransformerException
    {
        entitiesDefinitionsMap = new HashMap<String, EntityListDefinition>();

	    Map<String, Integer> defaultCacheParameters = parseDefaultCacheParameters(root);

	    NodeList childNodes = XmlHelper.selectNodeList(root, "entity-lists-def/entity-list-def");

        for (int i = 0; i < childNodes.getLength(); i++)
        {
            Element element   = (Element) childNodes.item(i);
            String name       = element.getAttribute("name");
            String className  = element.getAttribute("className");
            String scope      = element.getAttribute("scope");
	        Map<String,String> parameters = parseParameters(element);

	        EntityListDefinition entityListDefinition = new EntityListDefinition();
            entityListDefinition.setName(name);
            entityListDefinition.setClassName(className);
	        int parseScope = parseScope(scope);
	        entityListDefinition.setScope(parseScope);
	        entityListDefinition.setParameters(parameters);

	        if (parseScope != EntityListDefinition.SCOPE_REQUEST)
	        {
		        entityListDefinition.setCacheParameters(parseCacheParameters(element));
		        List<Class> classes = parseDependenceClasses(entityListDefinition.getScope(), element, name);
		        addDependenceClasses(entityListDefinition, classes);
		        entityListDefinition.setCacheDependences(classes);
		        entityListDefinition.setCalbackDependences(parseCallBackDependences(element));
		        try
		        {
			        entityListDefinition.setMaxElementsInMemory(
					        getCacheParameterValue(defaultCacheParameters, element, "maxElementsInMemory"));

					entityListDefinition.setTimeToIdleSeconds(
							getCacheParameterValue(defaultCacheParameters, element, "timeToIdleSeconds"));

					entityListDefinition.setTimeToLiveSeconds(
							getCacheParameterValue(defaultCacheParameters, element, "timeToIdleSeconds"));
		        }
		        catch (NumberFormatException e)
		        {
			        throw new ConfigurationException("Неверно заданы настройки кеширования xml-справочника" + name);
		        }
	        }
            entitiesDefinitionsMap.put(name, entityListDefinition);
        }
    }

	private int getCacheParameterValue(Map<String, Integer> defaultCacheParameters, Element element, String parameterName)
	{
		String parameter = element.getAttribute(parameterName);
		return !StringHelper.isEmpty(parameter) ? Integer.parseInt(parameter)
			                                    : defaultCacheParameters.get(parameterName);
	}

	/**
	 * возвращает параметры кеша
	 * @return мап<название параметра, значение>
	 */
	private Map<String, Integer> parseDefaultCacheParameters(Element element) throws TransformerException
	{
		Map<String, Integer> result = new HashMap<String, Integer>();
		NodeList parameterNodes = XmlHelper.selectNodeList(element, "default-cache-parameters/parameter");
		for (int i = 0; i < parameterNodes.getLength(); i++)
		{
			Element parameter = (Element) parameterNodes.item(i);
			String name = parameter.getAttribute("name");
			String  value = XmlHelper.getElementText(parameter);
			try
			{
				result.put(name, Integer.parseInt(value));
			}
			catch (Exception e)
			{
				throw new ConfigurationException("Ошибка при чтении дефолтных параметров кеширования справочников");
			}
		}

		return result;
	}

	private List<Class> parseDependenceClasses(int scope, Element element, String name)  throws TransformerException
	{
		NodeList dependences = XmlHelper.selectNodeList(element, "cache-dependences/dependence");
		try
		{
			List<Class> result = new ArrayList<Class>();
			for ( int i = 0; i < dependences.getLength(); i++ )
			{
				Element parameter = (Element) dependences.item(i);
				Class<Object> clazz = ClassHelper.loadClass(parameter.getAttribute("class"));
				result.add(clazz);
			}
			return result;
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка при загрузке класса из определения справочника " + name, e);
		}
	}

	private List<Class> parseCallBackDependences(Element element) throws TransformerException
	{
		NodeList dependences = XmlHelper.selectNodeList(element, "calbackcache-dependences/dependence");
		try
		{
			List<Class> result = new ArrayList<Class>();

			for ( int i = 0; i < dependences.getLength(); i++ )
			{
				Element parameter = (Element) dependences.item(i);
				String className = parameter.getAttribute("class");
				result.add(ClassHelper.loadClass(className));
			}

			return result;
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка при инициализации EntityListDefinition", e);
		}
	}

	private void addDependenceClasses(EntityListDefinition definition, List<Class> classes)
	{
		for (Class aClass : classes)
		{
			List<EntityListDefinition> entitiesLists = listsByClasses.get(aClass);
			if (entitiesLists == null)
			{
				entitiesLists = new ArrayList<EntityListDefinition>();
				listsByClasses.put(aClass, entitiesLists);
			}
			entitiesLists.add(definition);
		}
	}

	private Map<String,String> parseParameters(Element element) throws TransformerException
	{
		Map<String,String>      parameters     = new HashMap<String, String>();
		NodeList parameterNodes = XmlHelper.selectNodeList(element, "parameter");

		for ( int i = 0; i < parameterNodes.getLength(); i++ )
		{
			Element parameter = (Element) parameterNodes.item(i);
			String  name      = parameter.getAttribute("name");
			String  value     = XmlHelper.getElementText(parameter);

			parameters.put(name, value);
		}

		return parameters;
	}

	private List<String> parseCacheParameters(Element element) throws TransformerException
	{
		List<String> cacheParameters = new ArrayList<String>();
		NodeList parameterNodes = XmlHelper.selectNodeList(element, "cache-parameters/parameter");

		for ( int i = 0; i < parameterNodes.getLength(); i++ )
		{
			Element parameter = (Element) parameterNodes.item(i);
			cacheParameters.add(parameter.getAttribute("name"));
		}

		return cacheParameters;
	}

	private int parseScope(String scope)
	{
	    if (scope.equals(APPLICATION))
	        return EntityListDefinition.SCOPE_APPLICATION;
	    if (scope.equals(SESSION))
	        return EntityListDefinition.SCOPE_SESSION;
	    return EntityListDefinition.SCOPE_REQUEST;
	}

	public void doRefresh() throws ConfigurationException
	{
		// do nothing
	}
}

