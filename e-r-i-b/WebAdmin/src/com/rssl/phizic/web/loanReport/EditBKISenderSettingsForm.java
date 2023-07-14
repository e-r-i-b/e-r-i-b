package com.rssl.phizic.web.loanReport;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.loanreport.CreditBureauConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма редактирования настроек работы джоба отправки запросов в БКИ
 * @author Puzikov
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBKISenderSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_SCHEDULE_START_DAY);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("Число месяца отправки запроса");
		FieldValidator dayInMonthValidator = new NumericRangeValidator();
		dayInMonthValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		dayInMonthValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "31");
		dayInMonthValidator.setMessage("Число месяца находится в диапазоне 1..31");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				dayInMonthValidator
		);
		formBuilder.addField(fieldBuilder.build());

		DateFieldValidator dateFieldValidator = new DateFieldValidator("HH:mm:ss", "Время должно быть в формате ЧЧ:ММ:СС");
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_SCHEDULE_START_TIME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Начало периода отправки запроса");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				dateFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_SCHEDULE_END_TIME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Конец периода отправки запроса");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				dateFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_ENABLED);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Активность джоба");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_SUSPENDED);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Приостановленность джоба");
		formBuilder.addField(fieldBuilder.build());

		DateParser parser = new DateParser(CreditBureauConstants.DATE_TIME_FORMAT);
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_LAST_START_TIME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Отчет: старт джоба");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_LAST_START_DAY);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Отчет: начало последнего периода работы джоба");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CreditBureauConstants.BKI_JOB_LAST_END_DAY);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setDescription("Отчет: окончание последнего периода работы джоба");
		fieldBuilder.setParser(parser);
		fieldBuilder.clearValidators();
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
