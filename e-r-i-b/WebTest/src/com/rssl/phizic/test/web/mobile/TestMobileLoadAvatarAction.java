package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.operations.test.mobile.SendMobileMultipartRequestOperation;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileLoadAvatarAction extends TestMobileAction
{
	public SendAbstractRequestOperation getSendRequestOperation()
	{
		return new SendMobileMultipartRequestOperation();
	}

	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		super.updateOperation(operation, form);

		try
		{
			SendMobileMultipartRequestOperation op = (SendMobileMultipartRequestOperation) operation;
			TestMobileLoadAvatarForm frm = (TestMobileLoadAvatarForm) form;
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			paramsMap.put("file", frm.getFile().getFileData());
			paramsMap.put("fileName", frm.getFileName());

			op.setParamsMap(paramsMap);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
}
