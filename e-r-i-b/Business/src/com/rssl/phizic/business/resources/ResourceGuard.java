package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.BusinessException;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 12.10.2005 Time: 11:56:11 */
public interface ResourceGuard
{
    void onResourceOwnRemoved(Resource resource) throws BusinessException;
    void onResourceOwnAdded(Resource resource) throws BusinessException;
}
