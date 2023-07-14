package com.rssl.phizic.web.dictionaries.jbt;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;

/**
 * @author akrenev
 * @ created 22.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class UnloadJBTForm extends ListFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm";

	public static final Form UNLOAD_JBT_FORM = createForm();

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип поставщика услуг");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Биллинговая система");
		fieldBuilder.setName("billingName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type == 'B'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Биллинговая система");
		fieldBuilder.setName("billingId");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator()
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type == 'B'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период выгрузки");
		fieldBuilder.setName("unloadPeriodDateFrom");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ")
		);
		fieldBuilder.setParser(dateParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период выгрузки");
		fieldBuilder.setName("unloadPeriodTimeFrom");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ")
		);
		fieldBuilder.setParser(timeParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период выгрузки");
		fieldBuilder.setName("unloadPeriodDateTo");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ")
		);
		fieldBuilder.setParser(dateParser);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период выгрузки");
		fieldBuilder.setName("unloadPeriodTimeTo");
		fieldBuilder.setType("date");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ")
		);
		fieldBuilder.setParser(timeParser);
		fb.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE,   "unloadPeriodDateFrom");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME,   "unloadPeriodTimeFrom");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE,     "unloadPeriodDateTo");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME,     "unloadPeriodTimeTo");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		fb.setFormValidators(dateTimeCompareValidator);

		return fb.build();
	}
}
