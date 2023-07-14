package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.ermb.RequestCardByPhoneLogEntry;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author lukina
 * @ created 10.11.2014
 * @ $Author$
 * @ $Revision$
 * Форма постороения отчета  «По запросам информации по номеру телефона»
 */
public class RequestByPhoneReportForm extends ListFormBase
{

	public static final String LOGIN_FIELD_NAME     = "loginId";
	public static final String FROM_DATE_FIELD_NAME = "fromDate";
	public static final String TO_DATE_FIELD_NAME   = "toDate";

	public static final Form FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Необходимо задать начало периода."));
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Необходимо задать окончание периода."));
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_FIELD_NAME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setDescription("Логин клиента");
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");
		formBuilder.addFormValidators(compareDateValidator);

		return formBuilder.build();
	}
}
