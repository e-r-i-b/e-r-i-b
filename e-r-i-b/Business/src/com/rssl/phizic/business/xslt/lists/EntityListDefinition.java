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
	//��������� �� ������� �������� ������ - ������������ � ����� ��� ����
	private List<String> cacheParameters = null;
	//������ ������� �� ������� ����� ������ ������ � ����������
	private List<Class> calbackcacheDependences = null;
	//������ ������� ��� ��������� �������� ������ ������� ������ ��� �����������
	private List<Class> cacheDependences = null;

    /**
     * ��� ������
     * @return  ���
     */
    public String getName()
    {
        return name;
    }

    /**
     * ��� ������
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * ����� ����������� ��������� ������
     * @return �����
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * ����� ����������� ��������� ������
     * @param className
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * ����� ����� ������ (SCOPE_APPLICATION | SCOPE_REQUEST | SCOPE_SESSION)
     * @return ����� �����
     */
    public int getScope()
    {
        return scope;
    }

    /**
     * ����� ����� ������ (SCOPE_APPLICATION | SCOPE_REQUEST | SCOPE_SESSION)
     * @param scope
     */
    public void setScope(int scope)
    {
        this.scope = scope;
    }

	/**
	 * @return ������������ ���������� ��������� � ������ ���� �����������
	 */
	public int getMaxElementsInMemory()
	{
		return maxElementsInMemory;
	}

	/**
	 * ���������� ������������ ���������� ���������� ������ ����
	 * @param maxElementsInMemory - ����������
	 */
	public void setMaxElementsInMemory(int maxElementsInMemory)
	{
		this.maxElementsInMemory = maxElementsInMemory;
	}

	/**
	 * @return ����� ����� ������� ���� ����������� ��� ���������
	 */
	public int getTimeToIdleSeconds()
	{
		return timeToIdleSeconds;
	}

	/**
	 * ���������� ����� ����� ������� ���� ����������� ��� ���������
	 * @param timeToIdleSeconds ����� ����� ������� ���� ����������� ��� ���������
	 */
	public void setTimeToIdleSeconds(int timeToIdleSeconds)
	{
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	/**
	 * @return ����� ����� ������� ���� �����������
	 */
	public int getTimeToLiveSeconds()
	{
		return timeToLiveSeconds;
	}

	/**
	 * ���������� ����� ����� ������� ���� �����������
	 * @param timeToLiveSeconds ����� ����� ������� ���� �����������
	 */
	public void setTimeToLiveSeconds(int timeToLiveSeconds)
	{
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	/**
	 * @return ��������� ������ � ���������������� �����
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}

	/**
	 * ��������� ������ � ���������������� �����
	 * @param parameters
	 */
	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @return ������ ����������, ������������ ��� �����������
	 */
	public List<String> getCacheParameters()
	{
		return cacheParameters;
	}

	/**
	 * ���������� ��������� ��� ����������� ������
	 * @param cacheParameters - ���������
	 */
	public void setCacheParameters(List<String> cacheParameters)
	{
		this.cacheParameters = cacheParameters;
	}
	/**
	 * @return ������ ��� �������� ������� ����� ������� ������ � ����������
	 */
	public List<Class> getCalbackDependences()
	{
		return calbackcacheDependences;
	}

	/**
	 * ���������� ������ ��� �������� ������� ����� ������� ������ � ����������
	 * @param calbackcacheDependences - ������ �������
	 */
	public void setCalbackDependences(List<Class> calbackcacheDependences)
	{
		this.calbackcacheDependences = calbackcacheDependences;
	}

	/**
	 * @return ������ ������� - ������������
	 */
	public List<Class> getCacheDependences()
	{
		return cacheDependences;
	}

	/**
	 * ���������� ������ �����������
	 * @param cacheDependences-������
	 */
	public void setCacheDependences(List<Class> cacheDependences)
	{
		this.cacheDependences = cacheDependences;
	}
}
