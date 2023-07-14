package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * Форма просмотра списка информационных сообщений.
 * @author komarov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListInformMessagesForm extends ListInformMessagesFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = createFilter();
		return fb.build();
	}	
}
