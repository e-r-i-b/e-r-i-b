package com.rssl.phizic.web.loans.kinds;

import org.apache.struts.upload.FormFile;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author gladishev
 * @ created 19.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanKindForm extends EditFormBase
{
	private FormFile claimDescription;
	private FormFile detailsTemplate;

	public FormFile getClaimDescription()
	{
		return claimDescription;
	}

	public void setClaimDescription(FormFile claimDescription)
	{
		this.claimDescription = claimDescription;
	}

	public FormFile getDetailsTemplate()
	{
		return detailsTemplate;
	}

	public void setDetailsTemplate(FormFile detailsTemplate)
	{
		this.detailsTemplate = detailsTemplate;
	}

	public static final Form FORM = createForm();

	private static Form createForm ()
	{
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fieldBuilder = new FieldBuilder();

        fieldBuilder.setName("name");
        fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators
	    (
	        new RequiredFieldValidator(),
	        new RegexpFieldValidator(".{0,256}", "Наименование должно быть не более 256 символов")
	    );
        formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Кредитный калькулятор");
		fieldBuilder.setName("calculator");
		fieldBuilder.setType("boolean");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Целевой кредит");
		fieldBuilder.setName("target");
		fieldBuilder.setType("boolean");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
		(
			new RegexpFieldValidator(".{0,256}", "Описание должно быть не более 256 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
