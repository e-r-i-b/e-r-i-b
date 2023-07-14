package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.operations.test.mobile.SendMobileRequestOperation;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractAction;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 06.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileAction extends TestAbstractAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return new SendMobileRequestOperation();
	}

	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestMobileForm frm = (TestMobileForm) form;
		SendMobileRequestOperation op = (SendMobileRequestOperation) operation;
		
		super.updateOperation(op, frm);
	}

	protected void updateForm(TestAbstractForm form, SendAbstractRequestOperation operation)
	{
		TestMobileForm frm = (TestMobileForm) form;
		SendMobileRequestOperation op = (SendMobileRequestOperation) operation;

		if(StringHelper.isNotEmpty(op.getVersion()))
			frm.setVersion(op.getVersion());

		super.updateForm(frm, op);
	}
}
