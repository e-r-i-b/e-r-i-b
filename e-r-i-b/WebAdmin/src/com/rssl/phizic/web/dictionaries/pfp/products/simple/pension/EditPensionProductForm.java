package com.rssl.phizic.web.dictionaries.pfp.products.simple.pension;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;

import java.math.BigInteger;

/**
 * @author akrenev
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * форма редактирования пенсионного продукта
 */

public class EditPensionProductForm extends EditProductFormBase
{
	public static final String NAME_FIELD_NAME = "name";
	public static final String PENSION_FUND_ID_FIELD_NAME = "pensionFundId";
	public static final String ENTRY_FEE_FIELD_NAME = "entryFee";
	public static final String QUARTERLY_FEE_FIELD_NAME = "quarterlyFee";
	public static final String MIN_PERIOD_FIELD_NAME = "minPeriod";
	public static final String MAX_PERIOD_FIELD_NAME = "maxPeriod";
	public static final String DEFAULT_PERIOD_FIELD_NAME = "defaultPeriod";
	public static final String DESCRIPTION_FIELD_NAME = "description";

	private static final BigInteger NAME_FIELD_MAX_LENGTH = BigInteger.valueOf(50);
	private static final int DESCRIPTION_FIELD_MAX_LENGTH = 500;

	public static final Form EDIT_FORM = createForm();

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getBigDecimalField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		return fieldBuilder.build();
	}

	private static Field getLongField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		return fieldBuilder.build();
	}

	private static RhinoExpression anyExistRhinoExpression(String fieldName1, String fieldName2)
	{
		return new RhinoExpression("(form." + fieldName1 + " != null && form." + fieldName1 + " != '') || (form." + fieldName2 + " != null && form." + fieldName2 + " != '')");
	}

	private static MultiFieldsValidator getCompareValidator(String fieldName1, String fieldName2, String message)
	{
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setMessage(message);
		compareValidator.setBinding(CompareValidator.FIELD_O1, fieldName1);
		compareValidator.setBinding(CompareValidator.FIELD_O2, fieldName2);
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setEnabledExpression(anyExistRhinoExpression(fieldName1, fieldName2));
		return compareValidator;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField(NAME_FIELD_NAME, "Название", new RequiredFieldValidator(), new LengthFieldValidator(NAME_FIELD_MAX_LENGTH)));

		formBuilder.addField(getLongField(PENSION_FUND_ID_FIELD_NAME, "Пенсионный фонд"));

		formBuilder.addField(getBigDecimalField(QUARTERLY_FEE_FIELD_NAME, "Ежеквартальный взнос"));
		formBuilder.addField(getBigDecimalField(ENTRY_FEE_FIELD_NAME, "Стартовый взнос"));

		formBuilder.addField(getLongField(MIN_PERIOD_FIELD_NAME, "Срок"));
		formBuilder.addField(getLongField(MAX_PERIOD_FIELD_NAME, "Срок"));
		formBuilder.addField(getLongField(DEFAULT_PERIOD_FIELD_NAME, "Срок по умолчанию"));

		formBuilder.addFields(PFPProductEditFormHelper.getIncomeFields());

		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getBaseFormFields());
		formBuilder.addFields(getImageField());

		formBuilder.addField(getStringField(DESCRIPTION_FIELD_NAME, "Описание", new RequiredFieldValidator(), new MultiLineTextValidator("Пожалуйста, введите описание вклада не более", DESCRIPTION_FIELD_MAX_LENGTH)));

		formBuilder.addFormValidators(PFPProductEditFormHelper.getIncomeFormValidators());

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(MIN_PERIOD_FIELD_NAME, MIN_PERIOD_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(MAX_PERIOD_FIELD_NAME, MAX_PERIOD_FIELD_NAME);
		requiredMultiFieldValidator.setEnabledExpression(anyExistRhinoExpression(MIN_PERIOD_FIELD_NAME, MAX_PERIOD_FIELD_NAME));
		requiredMultiFieldValidator.setMessage("Пожалуйста, укажите минимальный и максимальный срок для данного продукта.");
		formBuilder.addFormValidators(requiredMultiFieldValidator);

		formBuilder.addFormValidators(getCompareValidator(MIN_PERIOD_FIELD_NAME, MAX_PERIOD_FIELD_NAME,     "Максимальный срок продукта должен быть больше минимального значения."));
		formBuilder.addFormValidators(getCompareValidator(MIN_PERIOD_FIELD_NAME, DEFAULT_PERIOD_FIELD_NAME, "Пожалуйста, укажите срок по умолчанию в пределах заданного диапазона допустимого срока."));
		formBuilder.addFormValidators(getCompareValidator(DEFAULT_PERIOD_FIELD_NAME, MAX_PERIOD_FIELD_NAME, "Пожалуйста, укажите срок по умолчанию в пределах заданного диапазона допустимого срока."));

		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + ENABLED_PARAMETER_NAME + " == true")));

		return formBuilder.build();
	}
}
