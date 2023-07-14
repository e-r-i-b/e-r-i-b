package com.rssl.phizic.test.web.social;

import com.rssl.phizic.operations.test.mobile.SaveMobileTargetMultipartRequestOperation;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.test.web.mobile.TestMobileAction;
import com.rssl.phizic.test.web.mobile.TestMobileCreateTargetForm;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author koptyaev
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class TestSocialCreateTargetAction extends TestMobileAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return new SaveMobileTargetMultipartRequestOperation();
	}

	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		super.updateOperation(operation, form);

		try
		{
			SaveMobileTargetMultipartRequestOperation op = (SaveMobileTargetMultipartRequestOperation) operation;
			TestMobileCreateTargetForm frm = (TestMobileCreateTargetForm) form;
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			paramsMap.put("type", frm.getType() == null ? "OTHER":frm.getType());
			paramsMap.put("name", StringHelper.getNullIfEmpty(frm.getName()));
			paramsMap.put("comment", StringHelper.getNullIfEmpty(frm.getComment()));
			paramsMap.put("amount", StringHelper.getNullIfEmpty(frm.getAmount()));
			paramsMap.put("date", StringHelper.getNullIfEmpty(frm.getDate()));
			paramsMap.put("fileName", frm.getFileName());
			paramsMap.put("file", frm.getFile().getFileData());

			op.setParamsMap(paramsMap);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
