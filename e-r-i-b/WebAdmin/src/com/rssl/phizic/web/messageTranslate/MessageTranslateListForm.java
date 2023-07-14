package com.rssl.phizic.web.messageTranslate;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateListForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();
	//признак работы с справочником из базы логов ЦСА
	private Boolean isCSA = false;

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("code");
		fieldBuilder.setDescription("Код");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("type");
		fieldBuilder.setDescription("Тип");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isNew");
		fieldBuilder.setDescription("Новый");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public Boolean getIsCSA()
	{
		return isCSA;
	}

	public void setIsCSA(Boolean CSA)
	{
		isCSA = CSA;
	}
}
