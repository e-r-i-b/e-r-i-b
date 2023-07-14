package com.rssl.phizic.business.operations.config;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.test.annotation.ClassTest;
import com.rssl.phizic.config.ConfigFactory;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 23.11.2005
 * @ $Author$
 * @ $Revision$
 */
@ClassTest
public class XmlResourceConfigTest extends BusinessTestCaseBase
{
    public void testXmlResourceConfig() throws BusinessException, ClassNotFoundException
    {
        XmlOperationsConfig config = XmlOperationsConfig.get();

	    List<OperationDescriptor> operationDescriptors = config.getOperationDescriptors();

	    for (OperationDescriptor descriptor : operationDescriptors)
	    {
		    // проверка возможности получить класс
		    try
		    {
		        ClassHelper.loadClass(descriptor.getOperationClassName());
		    }
		    catch(ClassNotFoundException ignored)
		    {   
				fail("Fail on load operation with key " + descriptor.getKey() + ". Class " + descriptor.getOperationClassName() + " not found.");
		    }
	    }
    }
}
