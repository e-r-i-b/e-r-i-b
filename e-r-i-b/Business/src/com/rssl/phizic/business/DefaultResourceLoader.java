package com.rssl.phizic.business;

import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.ResourceType;
import com.rssl.phizic.business.resources.Resource;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 04.10.2005
 * Time: 18:56:41
 */
public class DefaultResourceLoader implements ResourceLoader
{
    private static final SimpleService service = new SimpleService();

    private ResourceType resourceType;

    public DefaultResourceLoader(ResourceType resourceType)
    {
        if(resourceType == null)
            throw new NullPointerException("resourceClass не может быть null");
        this.resourceType = resourceType;
    }

    public Resource load(Long resourceId) throws BusinessException
    {
        List<Resource> list = service.find(DetachedCriteria.forClass(resourceType.getResourceClass()).add(Expression.eq("id", resourceId)));

        if(list.size() != 1)
            throw new BusinessException("Ресурс тип=" + resourceType.getResourceClass().getName() + " id=" + resourceId + "не найден");

        return list.get(0);
    }

    public List<Resource> getPersonResources(Person person) throws BusinessException
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(resourceType.getResourceClass());
        criteria.add(Expression.eq("login",person.getLogin())).
                add(Expression.eq("resourceType", resourceType));
        return service.find(criteria);
    }
}
