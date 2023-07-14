package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.cache.CachedEntityListSource;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Фабрика для создания инстансов сорсов xml справочников
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: erkin $
 * @ $Revision$
 */
public class EntitiesListSourcesFactory
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final Class[] CTOR_WITH_PARAMETERS_TYPES = new Class[]{Map.class};
	private static final Class[] CTOR_WITH_DEFINITION_TYPES = new Class[]{EntityListDefinition.class};
	private static final Class[] CTOR_WITH_DEFIN_AND_PARAMS_TYPES = new Class[]{EntityListDefinition.class, Map.class};

	private static EntitiesListSourcesFactory factoryInstance = new EntitiesListSourcesFactory();

	//мап - <название справочника, инстанс EntityListSource>
	private Map<String, EntityListSource> sourcesByListName = new HashMap<String, EntityListSource>();

	private EntitiesListSourcesFactory()
	{
		EntityListsConfig entityListsConfig = ConfigFactory.getConfig(EntityListsConfig.class);
		for (Iterator<EntityListDefinition> itr = entityListsConfig.getListDefinitionsIterator(); itr.hasNext();)
		{
			registerSource(itr.next());
		}
	}

	/**
	 * @return инстанс фабрики xml-справочников
	 */
	public static EntitiesListSourcesFactory getInstance()
	{
		return factoryInstance;
	}

	/**
	 * Регистрирует EntityListSource
	 * @param listDefinition - определение справочника
	 */
	private void registerSource(EntityListDefinition listDefinition)
	{
	    try
	    {
		    Class clazz = ClassHelper.loadClass(listDefinition.getClassName());
		    if (isCachedEntityListSource(clazz))
		    {
				sourcesByListName.put(listDefinition.getName(), createCachedSourceInstance(clazz, listDefinition));
			    XmlEntityListCacheSingleton.getInstance().addCache(listDefinition.getName(), listDefinition.getMaxElementsInMemory(), listDefinition.getTimeToIdleSeconds(), listDefinition.getTimeToLiveSeconds());
		    }
		    else
		    {
				sourcesByListName.put(listDefinition.getName(), createInstance(clazz, listDefinition.getParameters()));
		    }
	    }
	    catch (Exception e)
	    {
		    log.error(e.getMessage(), e);
	        throw new ConfigurationException("Ошибка при создании сорса xml-справочника " + listDefinition.getName(), e);
	    }
	}

	private EntityListSource createCachedSourceInstance(Class clazz, EntityListDefinition listDefinition) throws BusinessException
	{
		try
		{
			if (listDefinition.getParameters().size()==0)
			{
				Constructor constructor = clazz.getConstructor(CTOR_WITH_DEFINITION_TYPES);
				return (EntityListSource) constructor.newInstance(listDefinition);
			}
			else
			{
				Constructor constructor = clazz.getConstructor(CTOR_WITH_DEFIN_AND_PARAMS_TYPES);
				return (EntityListSource) constructor.newInstance(listDefinition, listDefinition.getParameters());
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListSource createInstance(Class clazz, Map<String, String> parameters) throws BusinessException
	{
		try
		{
			if (parameters.size()==0)
				return (EntityListSource) clazz.newInstance();
			else
			{
				Constructor constructor = clazz.getConstructor(CTOR_WITH_PARAMETERS_TYPES);
				return (EntityListSource) constructor.newInstance(parameters);
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param listName - название справочника
	 * @return инстанс EntityListSource по названию справочника
	 */
	public EntityListSource getEntityListSource(String listName)
	{
		return sourcesByListName.get(listName);
	}

	private boolean isCachedEntityListSource(Class clazz)
	{
		return CachedEntityListSource.class.isAssignableFrom(clazz);
	}
}