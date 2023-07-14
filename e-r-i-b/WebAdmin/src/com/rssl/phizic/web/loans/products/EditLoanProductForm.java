package com.rssl.phizic.web.loans.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author gladishev
 * @ created 21.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanProductForm extends EditFormBase
{
	private String html;
	private String[] selectedLoanTypes;

	public void setSelectedLoanTypes(String[] selectedLoanTypes)
	{
		this.selectedLoanTypes = selectedLoanTypes;
	}

	public String[] getSelectedLoanTypes()
	{
		return selectedLoanTypes;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getHtml()
	{
		return html;
	}

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,256}", "Наименование должено быть не более 256 символов")
		);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,256}", "Описание должено быть не более 256 символов"));

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanKind");
		fieldBuilder.setDescription("Вид кредита");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("anonymousClaim");
		fieldBuilder.setDescription("Анонимная заявка");
		fieldBuilder.setType("boolean");
		fieldBuilder.addValidators(new RequiredFieldValidator());

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("packageTemplate");
		fieldBuilder.setDescription("Пакет документов");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());

		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
