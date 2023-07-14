package com.rssl.phizic.test.web.social;

import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.operations.test.social.SendSocialRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractAction;
import com.rssl.phizic.test.web.common.TestAbstractForm;

/**
 * @author sergunin
 * @ created 03.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestSocialAction extends TestAbstractAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return new SendSocialRequestOperation();
	}

	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestSocialForm frm = (TestSocialForm) form;
		SendSocialRequestOperation op = (SendSocialRequestOperation) operation;
		
		super.updateOperation(op, frm);
	}

	protected void updateForm(TestAbstractForm form, SendAbstractRequestOperation operation)
	{
		TestSocialForm frm = (TestSocialForm) form;
		SendSocialRequestOperation op = (SendSocialRequestOperation) operation;

		super.updateForm(frm, op);
	}
}
