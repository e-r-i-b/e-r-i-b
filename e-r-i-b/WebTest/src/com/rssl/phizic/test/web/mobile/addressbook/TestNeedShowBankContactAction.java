package com.rssl.phizic.test.web.mobile.addressbook;

import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.test.web.mobile.TestMobileAction;
import com.rssl.phizic.test.web.mobile.TestMobileContactSyncForm;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 * @author bogdanov
 * @ created 17.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestNeedShowBankContactAction extends TestMobileAction
{
	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		operation.setParams(params);
	}
}
