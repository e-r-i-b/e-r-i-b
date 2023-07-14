package com.rssl.phizic.test.web.mobile.addressbook;

import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import com.rssl.phizic.test.web.mobile.TestMobileAction;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 * @author bogdanov
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileContactGetAction extends TestMobileAction
{
	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestMobileContactGetForm frm = (TestMobileContactGetForm) form;

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		params.put("phones", frm.getPhones());
		params.put("showBookmark", frm.isShowBookmark());
		operation.setParams(params);
	}
}
