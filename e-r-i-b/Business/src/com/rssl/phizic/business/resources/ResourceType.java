package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.*;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 28.09.2005
 * Time: 14:18:20
 */
public class ResourceType
{
    private static final ResourceFormatter DEFAULT_RESOURCE_FORMATTER = new DefaultResourceFormatter();

    private Long   id;
    private String className;
    private Class  clazz;
    private String name;
    private ResourceFormatter formatter = DEFAULT_RESOURCE_FORMATTER;
    private ResourceLoader loader;
    private ResourceGuard  guard = NullResourceGuard.getInstance();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className) throws BusinessException
    {
        try
        {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            throw new BusinessException("При поиске типа ресурса не найден класс " + className, e);
        }

        loader = new DefaultResourceLoader(this);

        this.className = className;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return "Ресурс :"+getName()+", класс: "+getClassName();
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || !(o instanceof ResourceType)) return false;

        final ResourceType that = (ResourceType) o;

        if (id != null ? !id.equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    public int hashCode()
    {
        return (id != null ? id.hashCode() : 0);
    }

    public ResourceFormatter getFormatter()
    {
        return formatter;
    }

    public ResourceLoader getLoader()
    {
        return loader;
    }

    public Class getResourceClass()
    {
        return clazz;
    }

    public ResourceGuard getGuard()
    {
        return guard;
    }
}
