package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.operations.test.mobile.SaveMobileTargetMultipartRequestOperation;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Тест аплоада картинки для цели в АЛФ
 * @author koptyaev
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileEditTargetAction  extends TestMobileAction
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
			TestMobileEditTargetForm frm = (TestMobileEditTargetForm) form;
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			paramsMap.put("id", StringHelper.getNullIfEmpty(frm.getId()));
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
