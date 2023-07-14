package com.rssl.phizic.web.skins;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author egorova
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListSkinsForm extends ListLimitActionForm
{
	public static Form FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		return formBuilder.build();
	}
}
