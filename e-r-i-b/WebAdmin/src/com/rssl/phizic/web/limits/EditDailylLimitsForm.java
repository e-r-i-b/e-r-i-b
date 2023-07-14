package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;

/**
 * @author: vagin
 * @ created: 26.01.2012
 * @ $Author$
 * @ $Revision$
 * Форма для редактирования суточного комулятивного лимита
 */
public class EditDailylLimitsForm extends EditLimitForm
{
	public static final Form EDIT_FORM = createEditForm();

	protected static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser timeParser = new DateParser("HH:mm:ss");

		FieldBuilder fb = new FieldBuilder();
		fb.setName("status");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Статус лимита");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("limitType");
		fb.setEnabledExpression(new RhinoExpression("form.status == null"));
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Тип лимита");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("startDate");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("Дата начала действия лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("startDateTime");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("Время начала действия лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("HH:mm:ss", "Время должно быть в формате ЧЧ:ММ:СС"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		rangeValidator.setMessage("Укажите сумму лимита");

		MoneyFieldValidator minAmountValidator = new MoneyFieldValidator();
		minAmountValidator.setParameter("maxValue", "9999999.99");
		minAmountValidator.setMessage("Укажите сумму лимита в правильном формате: #######.##");

		fb = new FieldBuilder();
		fb.setName("amount");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(MoneyType.INSTANCE.getName());
		fb.setDescription("Величина лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(), minAmountValidator, rangeValidator);
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setParameter(DateTimeCompareValidator.CUR_DATE, "to_cleartime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "startDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("Дата начала действия лимита не может быть меньше текущей даты");

		formBuilder.addFormValidators(dateTimeValidator);

		return formBuilder.build();
	}
}
