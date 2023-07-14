package com.rssl.phizic.web.finances.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import static com.rssl.phizic.config.ips.IPSConstants.*;

/**
 * @author komarov
 * @ created 23.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonalFinanceSettingsForm extends EditPropertiesFormBase
{
	private static final String CARD_OPERATION_JOB_PREFIX = "DeleteOldCardOperation.";
	private static final String DATE_FORMAT = "HH:mm";
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	@SuppressWarnings("OverlyLongMethod")
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("время на выполнение запроса в ИПС (в секундах)");
		fieldBuilder.setName(CONNECTION_TIMEOUT);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период");
		fieldBuilder.setName(CARD_OPERATION_MAX_TIME);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логирование изменения категории");
		fieldBuilder.setName(LOG_CARD_OPERATION_CATEGORY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логирование выбора периода фильтрации");
		fieldBuilder.setName(LOG_FILTER_OUTCOME_PERIOD);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Количество попыток выполнить одну заявку");
		fieldBuilder.setName(CLAIM_EXECUTION_ATTEMPT_MAX_NUM);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(CARD_OPERATION_JOB_PREFIX + "period.type");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators( new RequiredFieldValidator("Вы неправильно указали период запуска. Пожалуйста, выберите другой период."),
									new ChooseValueValidator(ListUtil.fromArray(new String[]{"DAY", "WEEK", "MONTH"})));
		fb.addField(fieldBuilder.build());

		NumericRangeValidator nrvDAY = new NumericRangeValidator();
		nrvDAY.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvDAY.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"31");
		nrvDAY.setEnabledExpression(new RhinoExpression("form['" + CARD_OPERATION_JOB_PREFIX + "period.type']=='DAY'"));
		nrvDAY.setMessage("Значение периодичнсти в днях должно быть в диапазоне от 1 до 31. Измените периодичностью");

		NumericRangeValidator nrvWEEK = new NumericRangeValidator();
		nrvWEEK.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvWEEK.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"4");
		nrvWEEK.setEnabledExpression(new RhinoExpression("form['" + CARD_OPERATION_JOB_PREFIX + "period.type']=='WEEK'"));
		nrvWEEK.setMessage("Значение периодичнсти в неделях должно быть в диапазоне от 1 до 4. Измените периодичность.");

		NumericRangeValidator nrvMONTH = new NumericRangeValidator();
		nrvMONTH.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,"1");
		nrvMONTH.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,"12");
		nrvMONTH.setEnabledExpression(new RhinoExpression("form['" + CARD_OPERATION_JOB_PREFIX + "period.type']=='MONTH'"));
		nrvMONTH.setMessage("Значение периодичнсти в месяцах должно быть в диапазоне от 1 до 12. Измените периодичность.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Периодичность.");
		fieldBuilder.setName(CARD_OPERATION_JOB_PREFIX + "period");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									nrvDAY, nrvWEEK, nrvMONTH);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время.");
		fieldBuilder.setName(CARD_OPERATION_JOB_PREFIX + "time");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators( new RequiredFieldValidator(),
									new DateFieldValidator(DATE_FORMAT, "Время должно быть в формате ЧЧ:мм"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
