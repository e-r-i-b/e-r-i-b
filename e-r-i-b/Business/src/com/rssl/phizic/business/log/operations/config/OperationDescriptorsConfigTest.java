package com.rssl.phizic.business.log.operations.config;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.logging.operations.OperationType;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Kidyaev
 * @ created 13.03.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
public class OperationDescriptorsConfigTest extends BusinessTestCaseBase
{
	private String OPERATION_CLASS = "com.rssl.phizic.web.common.dictionaries.ShowBankListAction";
	private String METHOD_START = "start";

	public void testOperationDescriptorsConfig() throws Exception
	{
		OperationDescriptorsConfig operationDescriptorsConfig = ConfigFactory.getConfig(OperationDescriptorsConfig.class);

		Class operationClass = Class.forName(OPERATION_CLASS);

		LogEntryDescriptor descriptor = operationDescriptorsConfig.getDescriptor(operationClass, METHOD_START);
		assertNotNull(descriptor);
		assertNotNull(descriptor.getDescription());
		assertSame(OperationType.PASSIVE, descriptor.getType());
	}
}
