package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.addressBook.reports.ContactsCountReportEntity;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма постороения отчета "По количеству контактов"
 */

public class AddressBookContactsCountReportForm extends ListFormBase<ContactsCountReportEntity>
{
	public static final String COUNT_FIELD_NAME = "count";
	public static final String LOGIN_ID_FIELD_NAME = "loginId";

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
		formBuilder.addField(FieldBuilder.buildLongField(COUNT_FIELD_NAME, "Количество клиентов"));
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Необходимо задать количество клиентов для отчета либо идентификатор клиента.");
		requiredFieldValidator.setEnabledExpression(new RhinoExpression("form." + COUNT_FIELD_NAME + " == null || form." + COUNT_FIELD_NAME + " == ''"));
		formBuilder.addField(FieldBuilder.buildLongField(LOGIN_ID_FIELD_NAME, "Идентификатор клиента", requiredFieldValidator));
		return formBuilder.build();
	}
}
