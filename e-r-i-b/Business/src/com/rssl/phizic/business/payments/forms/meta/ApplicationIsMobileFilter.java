package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author gulov
 * @ created 28.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class ApplicationIsMobileFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		return ApplicationUtil.isMobileApi();
	}
}
