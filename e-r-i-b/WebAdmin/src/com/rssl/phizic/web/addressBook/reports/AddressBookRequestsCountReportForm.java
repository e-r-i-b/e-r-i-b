package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.addressBook.reports.RequestsCountReportEntity;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма постороения отчета "По запросам к сервису"
 */

public class AddressBookRequestsCountReportForm extends ListFormBase<RequestsCountReportEntity>
{
	public static final String COUNT_FIELD_NAME     = "count";
	public static final String FROM_DATE_FIELD_NAME = "fromDate";
	public static final String TO_DATE_FIELD_NAME   = "toDate";

	private static final Form FILTER_FORM = createForm();

	/**
	 * @return логическая форма фильтрации
	 */
	public static Form getFilterForm()
	{
		return FILTER_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(FieldBuilder.buildLongField(COUNT_FIELD_NAME,     "Количество клиентов", new RequiredFieldValidator("Необходимо задать количество клиентов.")));
		formBuilder.addField(FieldBuilder.buildDateField(FROM_DATE_FIELD_NAME, "Начало периода",      new RequiredFieldValidator("Необходимо задать начало периода.")));
		formBuilder.addField(FieldBuilder.buildDateField(TO_DATE_FIELD_NAME,   "Окончание периода",   new RequiredFieldValidator("Необходимо задать окончание периода.")));

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");
		formBuilder.addFormValidators(compareDateValidator);
		return formBuilder.build();
	}
}
