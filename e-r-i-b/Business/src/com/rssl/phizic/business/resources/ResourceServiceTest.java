package com.rssl.phizic.business.resources;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 28.09.2005 Time: 17:43:18 */
public class ResourceServiceTest extends RSSLTestCaseBase
{
    private static final ResourceService resourceService = new ResourceService();

    public void testUpdateResourceTypesAndOperations() throws Exception
    {
        HibernateExecutor.getInstance().execute(new HibernateAction()
        {
            public Object run(Session session) throws Exception
            {
                List<ResourceType> resourceTypes = new ArrayList<ResourceType>();
                ResourceType resourceType = new ResourceType();
                resourceType.setClassName(ResourceServiceTest.class.getName());
                resourceType.setName(ResourceServiceTest.class.getName() + 1);
                resourceTypes.add(resourceType);

                resourceTypes = resourceService.updateResourceTypes(resourceTypes);

                resourceService.removeResourceTypes(resourceTypes);

                return null;
            }
        });
    }
}
