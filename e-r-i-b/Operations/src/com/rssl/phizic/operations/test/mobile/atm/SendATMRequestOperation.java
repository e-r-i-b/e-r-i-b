package com.rssl.phizic.operations.test.mobile.atm;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;

/**
 * @author Mescheryakova
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class SendATMRequestOperation extends SendAbstractRequestOperation
{
	private static final String JAXB_ATM_PACKAGE = "com.rssl.phizic.business.test.atm.generated";

	protected String getJaxbContextPath() throws BusinessException
	{
		return JAXB_ATM_PACKAGE;
	}

	protected void updateVersion(String key, Object value)	{}
}
