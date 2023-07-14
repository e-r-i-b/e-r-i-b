package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.resources.ResourceGuard;
import com.rssl.phizic.business.resources.Resource;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 12:17:26 */
public class ExternalResourceGuard implements ResourceGuard
{
    private static SimpleService simpleService = new SimpleService();

    public void onResourceOwnRemoved(Resource resource) throws BusinessException
    {
    }

    public void onResourceOwnAdded(Resource resource) throws BusinessException
    {
        if(!(resource instanceof ExternalResourceLink))
            throw new BusinessException("resource не является ExternalResourceLink");
        
        simpleService.remove(resource);
    }
}
