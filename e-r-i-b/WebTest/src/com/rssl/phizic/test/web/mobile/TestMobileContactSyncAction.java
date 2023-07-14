package com.rssl.phizic.test.web.mobile;

import com.rssl.phizic.operations.test.mobile.common.SendAbstractRequestOperation;
import com.rssl.phizic.test.web.common.TestAbstractForm;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

/**
 * @author Dorzhinov
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileContactSyncAction extends TestMobileAction
{
	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestMobileContactSyncForm frm = (TestMobileContactSyncForm) form;

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		params.put("contacts", frm.getContacts());
		operation.setParams(params);
	}
}
