package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author Erkin
 * @ created 29.04.2011
 * @ $Author$
 * @ $Revision$
 */
abstract class FilterFormBuilder
{
	protected abstract FormBuilder prepareFilterFormBuilder();

	Form build()
	{
		FormBuilder formBuilder = prepareFilterFormBuilder();
		return formBuilder.build();
	}
}
