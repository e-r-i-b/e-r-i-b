package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 28.09.2005 Time: 17:30:50 */
public class ResourceService
{
    private static SimpleService simpleService = new SimpleService();

    public ResourceType addResourceTypes(ResourceType resourceType) throws BusinessException
    {
       return simpleService.add(resourceType);
    }

    public void removeResourceType(ResourceType resourceType) throws BusinessException
    {
       simpleService.remove(resourceType);
    }

    public void removeResourceTypes(List<ResourceType> resourceTypes) throws BusinessException
    {
        simpleService.removeList(resourceTypes);
    }

    public List<ResourceType> getAllResourceTypes() throws BusinessException
    {
        return simpleService.getAll(ResourceType.class);
    }

	public void replicateAll(String toInstanceName)  throws BusinessException
	{
		List<ResourceType> list = getAllResourceTypes();
		for (ResourceType resourceType : list)
		{
			simpleService.replicate(resourceType,toInstanceName);
		}
	}

    public List<ResourceType> findResourceTypes(ResourceType resourceType) throws BusinessException
    {
        return simpleService.find(resourceType);
    }

    public List<ResourceType> updateResourceTypes(final List<ResourceType> types) throws Exception
    {
        return HibernateExecutor.getInstance().execute(new HibernateAction<List<ResourceType>>()
        {
            public List<ResourceType> run(Session session) throws Exception
            {
                List<ResourceType> result = new ArrayList<ResourceType>();

                for (int i = 0; i < types.size(); i++)
                {
                    ResourceType type = types.get(i);

                    ResourceType resourceType = findResourceByClass(type.getClassName());

                    if(resourceType != null)
                    {
                        resourceType.setName(type.getName());
                        updateResourceType(resourceType);
                        result.add(resourceType);
                    }
                    else
                    {
                        addResourceTypes(type);
                        result.add(type);
                    }
                }

                return result;
            }
        });
    }

    /**
     * Сохранить ResourceType в БД
     * @param type
     * @throws BusinessException
     */
    public void updateResourceType(final ResourceType type) throws BusinessException
    {
        simpleService.update(type);
    }

    public ResourceType findResourceByClass(final String className) throws BusinessException
    {
        ResourceType resourceType = new ResourceType();
        resourceType.setClassName(className);
        List<ResourceType> list = simpleService.find(resourceType);
        return (list.size() > 0 ? list.get(0) : null);
    }
}

