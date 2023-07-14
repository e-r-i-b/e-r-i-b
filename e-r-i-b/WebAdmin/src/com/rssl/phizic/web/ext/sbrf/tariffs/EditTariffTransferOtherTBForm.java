package com.rssl.phizic.web.ext.sbrf.tariffs;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompositeRequiredFieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.EqualToOneFromParametersValidator;
import com.rssl.phizic.business.payments.forms.validators.FailureValidator;
import com.rssl.phizic.web.common.EditFormBase;
import static com.rssl.phizic.web.ext.sbrf.tariffs.Constants.*;

/**
 * Форма для создания/редактирования тарифов на переводов в другой ТБ
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTariffTransferOtherTBForm extends EditFormBase
{
	public static Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		MoneyFieldValidator minAmountValidator = new MoneyFieldValidator();
		minAmountValidator.setParameter("minValue", "0.01");
		minAmountValidator.setParameter("maxValue", "999999999999999.99");
		minAmountValidator.setMessage("Укажите правильно минимальную сумму комиссии. Например, 320,66");

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("minValue", "0.01");
		maxAmountValidator.setParameter("maxValue", "999999999999999.99");
		maxAmountValidator.setMessage("Укажите правильно минимальную сумму комиссии. Например, 320,66");
		
		MoneyFieldValidator prcentValidator = new MoneyFieldValidator();
		prcentValidator.setParameter("minValue", "0.01");
		prcentValidator.setParameter("maxValue", "999999999999999.99");
		prcentValidator.setMessage("Укажите правильно процент от суммы. Например, 320,66");

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
	    fb.setName(IS_EDIT_FIELD_NAME);
	    fb.setDescription("флажок редактирования");
	    fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setEnabledExpression(new RhinoExpression("form.isEdit != true"));
	    fb.setName(OPERATION_FIELD_NAME);
	    fb.setDescription("Операция");
	    fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setEnabledExpression(new RhinoExpression("form.isEdit != true"));
	    fb.setName(CARRENCY_FIELD_NAME);
	    fb.setDescription("Валюта операции");
	    fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(PERCENT_FIELD_NAME);
	    fb.setDescription("Процент от суммы");
	    fb.addValidators(prcentValidator);
	    fb.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(MIN_AMOUNT_FIELD_NAME);
	    fb.setDescription("Минимальная сумма");
		fb.addValidators(minAmountValidator);
	    fb.setType(MoneyType.INSTANCE.getName());
	    fb.addValidators( );
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
	    fb.setName(MAX_AMOUNT_FIELD_NAME);
	    fb.setDescription("Максимальная сумма");
		fb.addValidators(maxAmountValidator);
	    fb.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		CompositeRequiredFieldValidator requiredValidator = new CompositeRequiredFieldValidator("Укажите размер комиссии");
		requiredValidator.setParameter("param1", PERCENT_FIELD_NAME);
		requiredValidator.setParameter("param2", MIN_AMOUNT_FIELD_NAME);
		requiredValidator.setParameter("param3", MAX_AMOUNT_FIELD_NAME);

		EqualToOneFromParametersValidator equalValidator = new EqualToOneFromParametersValidator();
		equalValidator.setEnabledExpression(new RhinoExpression("form.isEdit != true"));
		equalValidator.setBinding("parameterName", CARRENCY_FIELD_NAME);
		equalValidator.setMessage("Укажите правильно валюту.");
		equalValidator.setParameter("param1", "RUB");
		equalValidator.setParameter("param2", "USD");
		equalValidator.setParameter("param3", "EUR");

		FailureValidator failureValidator = new FailureValidator();
		failureValidator.setEnabledExpression(new RhinoExpression("form.percent == null && form.minAmount != null && form.maxAmount != null"));
		failureValidator.setMessage("Пожалуйста, укажите процент комиссии, взимаемой за совершение данной операции.");

		formBuilder.setFormValidators(requiredValidator, equalValidator, failureValidator);

		return formBuilder.build();
	}
}

