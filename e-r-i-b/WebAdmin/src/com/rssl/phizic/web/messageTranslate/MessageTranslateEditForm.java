package com.rssl.phizic.web.messageTranslate;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author Mescheryakova
 * @ created 15.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class MessageTranslateEditForm extends EditFormBase
{
	public static final Form EDIT_MESSAGE_TRANSLATE_FORM = createForm();
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
	    fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new RegexpFieldValidator(".{1,256}","Код сообщения не должен превышать 256 символов")
		);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("translate");
        fieldBuilder.setDescription("Перевод");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new RegexpFieldValidator(".{1,256}","Перевод сообщения не должен превышать 256 символов")
		);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("type");
        fieldBuilder.setDescription("Тип");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (
			        new RequiredFieldValidator()
		);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("logType");
	    fieldBuilder.setDescription("Тип логирования");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (new RequiredFieldValidator());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("isNew");
	    fieldBuilder.setDescription("Новый");
	    fieldBuilder.setType("boolean");
	    formBuilder.addField(fieldBuilder.build());
	    return formBuilder.build();
    }

	public Boolean getIsCSA()
	{
		return isCSA;
	}

	public void setIsCSA(Boolean isCSA)
	{
		this.isCSA = isCSA;
	}
}
