package com.rssl.phizic.web.common.dictionaries;

import org.apache.struts.action.ActionForm;

import java.util.List;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author Kosyakova
 * @ created 13.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowKBKListForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm(){
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ส๎ไ สมส");
		fieldBuilder.setName("code");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
