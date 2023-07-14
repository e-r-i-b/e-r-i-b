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
public class TestMobileContactEditAction extends TestMobileAction
{
	protected void updateOperation(SendAbstractRequestOperation operation, TestAbstractForm form)
	{
		TestMobileContactEditForm frm = (TestMobileContactEditForm) form;

		operation.initialize(form.getUrl(), form.getAddress());
		operation.setCookie(form.getCookie());
		operation.setProxyUrl(form.getProxyUrl());
		operation.setProxyPort(form.getProxyPort());

		MultiMap params = new MultiValueMap();
		params.put("id", frm.getId());
		params.put("name", frm.getName());
		params.put("alias", frm.getAlias());
		params.put("smallalias", frm.getSmallalias());
		params.put("cardnumber", frm.getCardnubmer());
		params.put("category", frm.getCategory());
		params.put("trusted", frm.isTrusted());
		operation.setParams(params);
	}
}
