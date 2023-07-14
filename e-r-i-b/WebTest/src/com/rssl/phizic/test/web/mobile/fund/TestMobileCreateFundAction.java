package com.rssl.phizic.test.web.mobile.fund;

import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.test.web.mobile.TestMobileAction;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 * @author vagin
 * @ created 12.12.14
 * @ $Author$
 * @ $Revision$
 * Тестовый экшен создания запроса на сбор средств.
 */
public class TestMobileCreateFundAction extends TestMobileAction
{
	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestMobileCreateFundForm frm = (TestMobileCreateFundForm) form;
		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		params.put("operation", "create");
		params.put("message", frm.getMessage());
		params.put("closeDate", frm.getCloseDate());
		params.put("reccomendSum", frm.getReccomendSum());
		params.put("requiredSum", frm.getRequiredSum());
		params.put("resource", frm.getResource());
		params.put("phones", frm.getPhones());
		operation.setParams(params);
	}
}
