package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemeTest;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.AccessCategory;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshka
 * @ created 21.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EditSchemeOperationTest extends BusinessTestCaseBase
{
    public void testEditSchemeOperation() throws BusinessException, BusinessLogicException
    {
	    ServiceService serviceService = new ServiceService();
		AccessSchemeService schemeService = new AccessSchemeService();

	    List<Service> clientServices = serviceService.findByCategory(AccessCategory.CATEGORY_CLIENT);

	    assertTrue(clientServices.size() > 3);

	    AccessScheme scheme = AccessSchemeTest.getTestScheme();
	    scheme.getServices().add(clientServices.get(2));
	    schemeService.save(scheme);

        EditSchemeOperation operation = new EditSchemeOperation();

	    List<Long> serviceIds = new ArrayList<Long>();

	    serviceIds.add(clientServices.get(0).getId());
	    serviceIds.add(clientServices.get(1).getId());

	    operation.initialize(scheme.getId());

	    operation.setNewServices(serviceIds);
	    operation.save();

	    operation.setNewServices(new ArrayList<Long>());
	    operation.save();

	    operation.setNewServices(serviceIds);
	    operation.save();

    }
}
