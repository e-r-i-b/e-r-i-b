package com.rssl.phizic.web.common.dictionaries.groupsRisk;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author basharin
 * @ created 01.08.2012
 * @ $Author$
 * @ $Revision$
 * форма просмотра группы риска
 */

public class ListGroupRiskForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		return formBuilder.build();
	}
}
