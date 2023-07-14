package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loans.loanOffer.ClaimsHoursValidator;

/**
 * @author Mescheryakova
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanCardOfferLoadForm extends EditFormBase
{
	public static String TIMESTAMP = "HH:mm";

	private String radio = "MonthDay";

	public String getRadio()
	{
		return radio;
	}

	public void setRadio(String radio)
	{
		this.radio = radio;
	}

	public static final Form EDIT_FORM = createForm();

	protected static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		DateParser timeParser = new DateParser(TIMESTAMP);
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("интервал загрузки");
		fieldBuilder.setName("radio");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Каталог ручной загрузки");
		fieldBuilder.setName("manualLoadPath");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "Длина названия каталога ручной загрузки  должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название файла ручной загрузки");
		fieldBuilder.setName("manualLoadFile");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "Длина названия файла ручной загрузки должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Каталог автоматической загрузки");
		fieldBuilder.setName("automaticLoadPath");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "Длина названия каталога автоматической загрузки  должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название файла автоматической загрузки");
		fieldBuilder.setName("automaticLoadFile");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,255}", "Длина названия файла автоматической загрузки должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		DateFieldValidator timeFieldValidator = new DateFieldValidator(TIMESTAMP, "Дата должна быть в формате ЧЧ:ММ");

		RegexpFieldValidator dayFieldValidator   = new RegexpFieldValidator("^{1}[1-7]", "Можно указать промежуток не  более 7 дней");
		RegexpFieldValidator hourFieldValidator = new RegexpFieldValidator("^{1}[1-9]*", "В значении часа нельзя указывать лидирующий ноль ");
		FieldValidator hoursValidator = new ClaimsHoursValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("Hour");
		fieldBuilder.setDescription("Периодичность: часы");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, hoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Hours'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("Day");
		fieldBuilder.setDescription("Периодичность: дней");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Days'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("DayTime");
		fieldBuilder.setDescription("Периодичность: дней в(время)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Days'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("DayInMonth");
		fieldBuilder.setDescription("Периодичность: число месяца");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='MonthDay'"));
		fieldBuilder.addValidators( new RegexpFieldValidator("[1-9]|1[0-9]|2[0-9]|3[0-1]", "Можно указать промежуток не  более 31 дня"));
		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}