package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма для редактирования настроек копилки
 * author miklyaev
 * created 18.08.15
 * @ $Author$
 * @ $Revision$
 */
public class MoneyboxConfigureForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();
	private static final String FIELD_NAME_PROPERTY_PREFIX = "settings.moneybox.field.";
	private static final String PERCENT_MAX_VALUE = "100";

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		//Минимальный размер % перечисления
		NumericRangeValidator minPercentValueValidator = new NumericRangeValidator();
		minPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		minPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, PERCENT_MAX_VALUE);
		String description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "minPercent", "configureBundle");
		minPercentValueValidator.setMessage("Пожалуйста, укажите значение поля «" + description + "» в диапазоне 1.." + PERCENT_MAX_VALUE);

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(description);
		fieldBuilder.setName("com.rssl.iccs.moneybox.percent.min");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(minPercentValueValidator, new RegexpFieldValidator("\\d+", "Введите целочисленное значение в поле «" + description + "»"));
		formBuilder.addField(fieldBuilder.build());

		//Максимальный размер % перечисления
		NumericRangeValidator maxPercentValueValidator = new NumericRangeValidator();
		maxPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		maxPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, PERCENT_MAX_VALUE);
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "maxPercent", "configureBundle");
		maxPercentValueValidator.setMessage("Пожалуйста, укажите значение поля «" + description + "» в диапазоне 1.." + PERCENT_MAX_VALUE);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(description);
		fieldBuilder.setName("com.rssl.iccs.moneybox.percent.max");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(maxPercentValueValidator, new RegexpFieldValidator("\\d+", "Введите целочисленное значение в поле «" + description + "»"));
		formBuilder.addField(fieldBuilder.build());

		//Значение по умолчанию %, перечисляемого в Копилку от зачислений/расходов
		NumericRangeValidator defaultPercentValueValidator = new NumericRangeValidator();
		defaultPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		defaultPercentValueValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, PERCENT_MAX_VALUE);
		description = StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "defaultPercent", "configureBundle");
		defaultPercentValueValidator.setMessage("Пожалуйста, укажите значение поля «" + description + "» в диапазоне 1.." + PERCENT_MAX_VALUE);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(description);
		fieldBuilder.setName("com.rssl.iccs.moneybox.percent.default");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(defaultPercentValueValidator, new RegexpFieldValidator("\\d+", "Введите целочисленное значение в поле «" + description + "»"));
		formBuilder.addField(fieldBuilder.build());

		//Виды вкладов, недоступные для копилки
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "disallowedAccountKinds", "configureBundle"));
		fieldBuilder.setName("com.rssl.iccs.moneybox.disallowedAccountKinds");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//Запрет создания конверсионных копилок
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(StrutsUtils.getMessage(FIELD_NAME_PROPERTY_PREFIX + "disallowedConversion.enable", "configureBundle"));
		fieldBuilder.setName("com.rssl.iccs.moneybox.disallowedConversion.enable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
