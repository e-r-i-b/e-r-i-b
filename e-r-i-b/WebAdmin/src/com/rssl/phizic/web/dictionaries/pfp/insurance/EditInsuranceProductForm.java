package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceProductForm extends EditProductFormBase
{
	private Long[] periodTypeIds = new Long[]{};  //идентификаторы типов периодов
	private Long[] lineNumbers = new Long[]{};    //номера блоков (отображаемых на форме)

	public Long[] getPeriodTypeIds()
	{
		return periodTypeIds;
	}

	public void setPeriodTypeIds(Long[] periodTypeIds)
	{
		this.periodTypeIds = periodTypeIds;
	}

	public Long[] getLineNumbers()
	{
		return lineNumbers;
	}

	public void setLineNumbers(Long[] lineNumbers)
	{
		this.lineNumbers = lineNumbers;
	}

	public Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(PFPProductEditFormHelper.getIncomeFields());
		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getBaseFormFields());

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,250}", "Поле Название должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceCompanyId");
		fieldBuilder.setDescription("Страховая компания");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceCompanyName");
		fieldBuilder.setDescription("Страховая компания");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("typeId");
		fieldBuilder.setDescription("Тип страхования");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("typeParentId");
		fieldBuilder.setDescription("Тип страхования");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forComplex");
		fieldBuilder.setDescription("Доступность клиенту");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minAge");
		fieldBuilder.setDescription("минимальный возраст");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Поле Для клиентов может содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAge");
		fieldBuilder.setDescription("максимальный возраст");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Поле Для клиентов может содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultPeriod");
		fieldBuilder.setDescription("Период по умолчанию");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		MoneyFieldValidator sumValidator = new MoneyFieldValidator();
		sumValidator.setMessage("Поле Сумма может содержать только цифры.");
		CompareValidator compareValidator;
		IntegerIntervalsHelper periodValidator = new IntegerIntervalsHelper();
		periodValidator.setMinValue(1);
		periodValidator.setMaxValue(150);
		periodValidator.setMessage("Вы неправильно указали срок страхового продукта. Пожалуйста, введите цифры или диапазон цифр через запятую, например: 1,4-5,9,12.");
		String fieldName;
		for (Long rowNum: lineNumbers)
		{
			fieldName = "idPeriodType" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("Период");
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());

			fieldName = "namePeriodType" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("Период");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());

			fieldName = "period" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("Срок");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
									   periodValidator);
			formBuilder.addField(fieldBuilder.build());

			fieldName = "minSum" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("минимальная сумма взноса");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setValidators(sumValidator);
			formBuilder.addField(fieldBuilder.build());

			fieldName = "maxSum" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("максимальная сумма взноса");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setValidators(sumValidator);
			formBuilder.addField(fieldBuilder.build());

			compareValidator = new CompareValidator();
		    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		    compareValidator.setBinding(CompareValidator.FIELD_O1, "minSum" + rowNum);
		    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxSum" + rowNum);
		    compareValidator.setMessage("Вы неправильно указали минимальную сумму. Минимальная сумма не должна превышать максимальную сумму.");

			RequiredMultiFieldValidator requiredSumMultiFieldValidator = new RequiredMultiFieldValidator();
			requiredSumMultiFieldValidator.setBinding("minSum" + rowNum, "minSum" + rowNum);
			requiredSumMultiFieldValidator.setBinding("maxSum" + rowNum, "maxSum" + rowNum);
			requiredSumMultiFieldValidator.setMessage("Пожалуйста, укажите минимальную и максимальную сумму средств для вложений в данный страховой продукт.");
			requiredSumMultiFieldValidator.setEnabledExpression(new RhinoExpression("form.forComplex != true"));
			formBuilder.addFormValidators(compareValidator, requiredSumMultiFieldValidator);
		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new MultiLineTextValidator("Поле Описание должно содержать не более", 500));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(lineNumbers)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("idPeriodType");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(validator);
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding("minAge", "minAge");
		requiredMultiFieldValidator.setBinding("maxAge","maxAge");
		requiredMultiFieldValidator.setMessage("Пожалуйста, укажите минимальный и максимальный возраст клиентов, которым должен быть доступен данный страховой продукт.");

		compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minAge");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxAge");
	    compareValidator.setMessage("Вы неправильно указали минимальный возраст. Минимальный возраст не должен превышать максимальный возраст.");

		formBuilder.addFormValidators(PFPProductEditFormHelper.getIncomeFormValidators());
		formBuilder.addFormValidators(compareValidator, requiredMultiFieldValidator);
		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + ENABLED_PARAMETER_NAME + " == true && form.forComplex == false")));

		return formBuilder.build();
	}
}
