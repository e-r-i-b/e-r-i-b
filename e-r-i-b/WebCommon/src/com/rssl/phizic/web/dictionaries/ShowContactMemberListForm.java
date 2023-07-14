package com.rssl.phizic.web.dictionaries;

import org.apache.struts.action.ActionForm;

import java.util.List;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author Kosyakova
 * @ created 15.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class ShowContactMemberListForm extends ListLimitActionForm
{

	public static final Form FILTER_FORM = createForm();

	private static Form createForm(){
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Страна");
		fieldBuilder.setName("countryName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Город");
		fieldBuilder.setName("city");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
