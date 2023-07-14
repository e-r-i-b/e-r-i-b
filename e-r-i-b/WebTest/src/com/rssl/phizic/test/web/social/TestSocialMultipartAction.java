package com.rssl.phizic.test.web.social;

import com.rssl.phizic.operations.test.mobile.SendMobileMultipartRequestOperation;
import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.test.web.mobile.TestMobileAction;
import com.rssl.phizic.test.web.mobile.TestMobileMultipartForm;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Тест-кейс upload-а файла на примере сохранения письма.
 * @author Dorzhinov
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestSocialMultipartAction extends TestSocialAction
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
			TestMobileMultipartForm frm = (TestMobileMultipartForm) form;
			Map<String, Object> paramsMap = new HashMap<String, Object>();

			paramsMap.put("id", StringHelper.getNullIfEmpty(frm.getId()));
			paramsMap.put("parentId", StringHelper.getNullIfEmpty(frm.getParentId()));
			paramsMap.put("type", frm.getType());
			paramsMap.put("themeId", frm.getThemeId());
			paramsMap.put("responseMethod", frm.getResponseMethod());
			paramsMap.put("phone", StringHelper.getNullIfEmpty(frm.getPhone()));
			paramsMap.put("eMail", StringHelper.getNullIfEmpty(frm.getEMail()));
			paramsMap.put("subject", frm.getSubject());
			paramsMap.put("body", frm.getBody());
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
