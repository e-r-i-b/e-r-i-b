package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author Mescheryakova
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class LastYearReportForm extends ReportEditForm
{
	public static final Form EDIT_FORM = createForm();

	protected static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

	    FieldBuilder fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("lastYear");
	    fieldBuilder.setDescription("Дата начала года");
	    fieldBuilder.setType("date");
		fieldBuilder.addValidators(new RequiredFieldValidator("Ошибка получения даты начала года."));

		formBuilder.addFields(ReportEditForm.EDIT_FORM.getFields());
		formBuilder.addField(fieldBuilder.build());

		/* проверка наличия данных на начало года */
	    CompareValidator minDateValidator = new CompareValidator();
		minDateValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		minDateValidator.setBinding(CompareValidator.FIELD_O1, "minDate");
		minDateValidator.setBinding(CompareValidator.FIELD_O2, "lastYear");
		minDateValidator.setMessage("В базе нет данных для построения отчета с начала года.");		
		
		formBuilder.addFormValidators(ReportEditForm.EDIT_FORM.getFormValidators());
		formBuilder.addFormValidators(minDateValidator);
		
		return formBuilder.build();
	}
}
