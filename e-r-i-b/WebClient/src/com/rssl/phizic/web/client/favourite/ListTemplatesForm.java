package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author mihaylov
 * @ created 29.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListTemplatesForm extends ListFormBase
{
	public static final Form LIST_FORM = createForm();

	private String formType;
	private List<TemplateDocument> templates;

	public String getFormType()
	{
		return formType;
	}

	public void setFormType(String formType)
	{
		this.formType = formType;
	}

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder   = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setDescription("Тип формы");
		fieldBuilder.setName("formType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("INTERNAL_TRANSFER|IMA_PAYMENT|LOAN_PAYMENT|INDIVIDUAL_TRANSFER|EXTERNAL_PAYMENT_SYSTEM_TRANSFER|INTERNAL_PAYMENT_SYSTEM_TRANSFER|JURIDICAL_TRANSFER|CONVERT_CURRENCY_TRANSFER"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
