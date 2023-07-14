package com.rssl.phizic.web.promocodes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author gladishev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditPromoCodeSettingsForm extends EditFormBase
{
	public static final Form FORM  = createForm();
	public static final String TERMLESS_FIELD = "termless";
	public static final String TB_ID_FIELD = "TB_ID";
	public static final String TB_SYNCH_KEY = "synchKey";
	public static final String TB_NAME_FIELD = "TBName";
	public static final String START_DATE_FIELD = "startDate";
	public static final String START_TIME_FIELD = "startTime";
	public static final String END_DATE_FIELD = "endDate";
	public static final String END_TIME_FIELD = "endTime";

	private static final String TIMESTAMP_FORMAT = "HH:mm:ss";
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";
	private static final String DATE_ERROR_MESSAGE = "Введите дату %s промо-акции в формате ДД.ММ.ГГГГ.";
	private static final String TIME_ERROR_MESSAGE = "Введите время %s промо-акции в формате ЧЧ:ММ:СС.";

	private static Form createForm()
	{
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName(TB_SYNCH_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(TERMLESS_FIELD);
		fieldBuilder.setDescription("бессрочно");
		fieldBuilder.setParser(new BooleanParser());
		formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(START_DATE_FIELD);
		fieldBuilder.setDescription("Начальная дата");
		String startDateErrorMessage = String.format(DATE_ERROR_MESSAGE, "начала");
		fieldBuilder.addValidators(new RequiredFieldValidator(startDateErrorMessage),
								   new DateFieldValidator(DATESTAMP_FORMAT, startDateErrorMessage)
		);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(START_TIME_FIELD);
		fieldBuilder.setDescription("Начальное время");
		String startTimeErrorMessage = String.format(TIME_ERROR_MESSAGE, "начала");
		fieldBuilder.addValidators(new RequiredFieldValidator(startTimeErrorMessage),
								   new DateFieldValidator(TIMESTAMP_FORMAT, startTimeErrorMessage)
		);
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(END_DATE_FIELD);
		fieldBuilder.setDescription("Конечная дата");
		String endDateErrorMessage = String.format(DATE_ERROR_MESSAGE, "окончания");
		fieldBuilder.addValidators(new RequiredFieldValidator(endDateErrorMessage),
								   new DateFieldValidator(DATESTAMP_FORMAT, endDateErrorMessage)
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.termless != true"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(END_TIME_FIELD);
		fieldBuilder.setDescription("Конечное время");
		String endTimeErrorMessage = String.format(TIME_ERROR_MESSAGE, "окончания");
		fieldBuilder.addValidators(new RequiredFieldValidator(endTimeErrorMessage),
								   new DateFieldValidator(TIMESTAMP_FORMAT, endTimeErrorMessage)
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.termless != true"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE,   START_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME,   START_TIME_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE,     END_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME,     END_TIME_FIELD);
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше начальной!");
		formBuilder.setFormValidators(dateTimeCompareValidator);

		return formBuilder.build();
	}
}
