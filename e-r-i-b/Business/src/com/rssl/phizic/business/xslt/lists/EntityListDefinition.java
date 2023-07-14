package com.rssl.phizic.business.xslt.lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: gladishev $
 * @ $Revision$
 */

public class EntityListDefinition
{
    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//

    public static final int SCOPE_REQUEST     = 0;
    public static final int SCOPE_SESSION     = 1;
    public static final int SCOPE_APPLICATION = 2;

    //*********************************************************************//
    //**************************  INSTANCE MEMBERS  ***********************//
    //*********************************************************************//

    private String name;
    private String className;
    private int    scope;
    private int    maxElementsInMemory;
    private int    timeToIdleSeconds;
    private int    timeToLiveSeconds;

	private Map<String,String>    parameters = new HashMap<String, String>();
	//параметры по которым строится список - используются в ключе для кеша
	private List<String> cacheParameters = null;
	//список классов по которым будем делать записи в колбаккеше
	private List<Class> calbackcacheDependences = null;
	//список классов при изменении объектов данных классов чистим кеш справочника
	private List<Class> cacheDependences = null;

    /**
     * Имя списка
     * @return  имя
     */
    public String getName()
    {
        return name;
    }

    /**
     * Имя списка
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Класс реализующий получение списка
     * @return класс
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Класс реализующий получение списка
     * @param className
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * Время жизни списка (SCOPE_APPLICATION | SCOPE_REQUEST | SCOPE_SESSION)
     * @return время жизни
     */
    public int getScope()
    {
        return scope;
    }

    /**
     * Время жизни списка (SCOPE_APPLICATION | SCOPE_REQUEST | SCOPE_SESSION)
     * @param scope
     */
    public void setScope(int scope)
    {
        this.scope = scope;
    }

	/**
	 * @return максимальное количество элементов в памяти кеша справочника
	 */
	public int getMaxElementsInMemory()
	{
		return maxElementsInMemory;
	}

	/**
	 * Установить максимальное количество элементовв памяти кеша
	 * @param maxElementsInMemory - количество
	 */
	public void setMaxElementsInMemory(int maxElementsInMemory)
	{
		this.maxElementsInMemory = maxElementsInMemory;
	}

	/**
	 * @return время жизни записей кеша справочника без обращений
	 */
	public int getTimeToIdleSeconds()
	{
		return timeToIdleSeconds;
	}

	/**
	 * Установить время жизни записей кеша справочника без обращений
	 * @param timeToIdleSeconds время жизни записей кеша справочника без обращений
	 */
	public void setTimeToIdleSeconds(int timeToIdleSeconds)
	{
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	/**
	 * @return время жизни записей кеша справочника
	 */
	public int getTimeToLiveSeconds()
	{
		return timeToLiveSeconds;
	}

	/**
	 * Установить время жизни записей кеша справочника
	 * @param timeToLiveSeconds время жизни записей кеша справочника
	 */
	public void setTimeToLiveSeconds(int timeToLiveSeconds)
	{
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	/**
	 * @return Параметры списка в конфигурационном файле
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}

	/**
	 * Параметры списка в конфигурационном файле
	 * @param parameters
	 */
	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return список параметров, используемых для кеширования
	 */
	public List<String> getCacheParameters()
	{
		return cacheParameters;
	}

	/**
	 * Установить параметры для кеширования списка
	 * @param cacheParameters - параметры
	 */
	public void setCacheParameters(List<String> cacheParameters)
	{
		this.cacheParameters = cacheParameters;
	}
	/**
	 * @return классы для объектов которых будем хранить ссылки в колбаккеше
	 */
	public List<Class> getCalbackDependences()
	{
		return calbackcacheDependences;
	}

	/**
	 * Установить классы для объектов которых будем хранить ссылки в колбаккеше
	 * @param calbackcacheDependences - список классов
	 */
	public void setCalbackDependences(List<Class> calbackcacheDependences)
	{
		this.calbackcacheDependences = calbackcacheDependences;
	}

	/**
	 * @return список классов - зависимостей
	 */
	public List<Class> getCacheDependences()
	{
		return cacheDependences;
	}

	/**
	 * установить классы зависимости
	 * @param cacheDependences-классы
	 */
	public void setCacheDependences(List<Class> cacheDependences)
	{
		this.cacheDependences = cacheDependences;
	}
}
