package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.web.util.ActionForwardHelper;
import org.apache.struts.action.ActionForward;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author erkin
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
class MobilebankActionForwardHelper
{
	static ActionForward appendRegistrationParams(
			ActionForward forward,
			String phoneCode,
			String cardCode)
	{
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("phoneCode", phoneCode);
		params.put("cardCode", cardCode);
		return ActionForwardHelper.appendParams(forward, params);

	}
}