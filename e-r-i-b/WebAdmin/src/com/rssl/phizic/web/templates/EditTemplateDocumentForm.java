package com.rssl.phizic.web.templates;

import org.apache.struts.upload.FormFile;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 16:44:28
 * To change this template use File | Settings | File Templates.
 */
public class EditTemplateDocumentForm extends EditFormBase
{
    private FormFile template;

	public FormFile getTemplate()
	{
		return template;
	}

	public void setTemplate(FormFile template)
	{
		this.template = template;
	}

	public static Form EDIT_FORM = createForm();

	private static Form createForm ()
	{
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("name");
        fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType("string");
	    fieldBuilder.setValidators
	    (
	        new RequiredFieldValidator(),
	        new RegexpFieldValidator(".{0,256}", "Наименование должено быть не более 256 символов")
	    );
	    formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators
		(
			new RegexpFieldValidator(".{0,256}", "Описание должено быть не более 256 символов")
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fileName");
		fieldBuilder.setDescription("Имя файла");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fileType");
		fieldBuilder.setDescription("Тип файла");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators
		(
			new RequiredFieldValidator(),
			new RegexpFieldValidator(".{0,10}", "Тип файла должен быть не более 10 символов")
		);
	    formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
