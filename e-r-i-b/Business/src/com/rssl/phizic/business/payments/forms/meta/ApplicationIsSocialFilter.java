package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author sergunin
 * @ created 19.11.2014
 * @ $Authors$
 * @ $Revision$
 */
public class ApplicationIsSocialFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		return ApplicationUtil.isSocialApi();
	}
}
