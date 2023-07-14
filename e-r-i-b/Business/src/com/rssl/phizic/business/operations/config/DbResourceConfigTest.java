package com.rssl.phizic.business.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.config.ConfigFactory;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 23.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class DbResourceConfigTest extends BusinessTestCaseBase
{
    public void testDbResourceConfig() throws BusinessException, ClassNotFoundException
    {
	    DbOperationsConfig config = DbOperationsConfig.get();
	    assertNotNull(config);

	    List<OperationDescriptor> list = config.getOperationDescriptors();
	    for (OperationDescriptor operationDescriptor : list)
			 ClassHelper.loadClass(operationDescriptor.getOperationClassName());
    }
}
