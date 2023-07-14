package com.rssl.phizic.test.web.atm;

import com.rssl.phizic.test.web.common.TestAbstractAction;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.operations.test.mobile.atm.SendATMRequestOperation;

/**
 * @author Erkin
 * @ created 06.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMAction extends TestAbstractAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return new SendATMRequestOperation();
	}
}