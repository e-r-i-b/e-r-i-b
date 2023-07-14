package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.payments.forms.validators.DublicateFieldNameValidator;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.List;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditServiceProviderFieldForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	private Long fieldId;//Идентифкатор редактируемого поля.
	private boolean editable;
	private List<RequisiteType> requisiteTypeList;
	//в id находится идентифкатор поставщика.

	public Long getFieldId()
	{
		return fieldId;
	}

	public void setFieldId(Long fieldId)
	{
		this.fieldId = fieldId;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public List<RequisiteType> getRequisiteTypeList()
	{
		return requisiteTypeList;
	}

	public void setRequisiteTypeList(List<RequisiteType> requisiteTypeList)
	{
		this.requisiteTypeList = requisiteTypeList;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код поля во внешней системе");
		fieldBuilder.setName("exteranlId");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,40}", "Значение Код во внешней системе не должно превышать 40 символов"),
				new RegexpFieldValidator("^[a-zA-Z_:]{1}[a-zA-Z0-9_:]*$", "Поле Код во внешней системе может содержать цифры и буквы латинского алфавита, символы «_» и «:», но не должно начинаться с цифры"),
				new DublicateFieldNameValidator("В форме платежа обнаружены дублирующиеся поля. Пожалуйста укажите другой код поля во внешней системе.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование поля для клиента");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,60}", "Значение Наименование поля для клиента не должно превышать 60 символов")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Комментарий к полю для клиента");
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		MultiLineTextValidator descriptionValidator = new MultiLineTextValidator("Значение Комментарий к полю для клиента не должно превышать", 200);
		fieldBuilder.addValidators(descriptionValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Подсказка по заполнению");
		fieldBuilder.setName("hint");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		MultiLineTextValidator hintValidator = new MultiLineTextValidator("Значение Подсказка по заполнению не должно превышать", 200);
		fieldBuilder.addValidators(hintValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип поля");
		fieldBuilder.setName("type");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(string|number|date|list|integer|set|money|choice)")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор расширенного описания");
		fieldBuilder.setName("extendedDescId");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type == 'choice'"));
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new LengthFieldValidator(BigInteger.valueOf(50)));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальное значение");
		fieldBuilder.setName("maxlength");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("Значение поля Максимальное знaчение должно быть от 0 до 100");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальное значение");
		fieldBuilder.setName("minlength");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("Значение поля Минимальное значение должно быть от 0 до 100");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Точность числового значения");
		fieldBuilder.setName("numberPrecision");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		rangeValidator.setMessage("Значение поля Точность числового значения должно быть от 0 до 20");
		fieldBuilder.addValidators(rangeValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Обязательно для заполнения");
		fieldBuilder.setName("mandatory");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Редактируемое");
		fieldBuilder.setName("editable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Видимое");
		fieldBuilder.setName("visible");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setName("sum");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ключевое");
		fieldBuilder.setName("key");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выводить в чеке");
		fieldBuilder.setName("isForBill");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Включать в текст SMS-сообщения с одноразовым паролем для подтверждения платежа");
		fieldBuilder.setName("isIncludeInSMS");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заполнять при сохранении в шаблоне платежа");
		fieldBuilder.setName("isSaveInTemplate");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Скрывать на форме подтверждения");
		fieldBuilder.setName("isHideInConfirmation");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Бизнес атрибут");
		fieldBuilder.setName("businessSubType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("(phone|wallet)"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Значение по умолчанию");
		fieldBuilder.setName("defaultValue");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,50}", "Значение Значение по умолчанию не должно превышать 50 символов"),
				new RegexpFieldValidator("[^@&«»]*","Вы указали недопустимые символы, например, @, &, «, » . Пожалуйста, введите другое значение в поле Значение по умолчанию.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Возможные заначения");
		fieldBuilder.setName("values");
		fieldBuilder.setType(StringType.INSTANCE.getName());

		Expression fieldTypeExpression = new RhinoExpression("form.type == 'list' ");
		RequiredFieldValidator dependFromTypeValidator = new RequiredFieldValidator();
		dependFromTypeValidator.setEnabledExpression(fieldTypeExpression);

		RegexpFieldValidator lineFeedNotCaontainsValidator =
				new RegexpFieldValidator(".*", "Поле Возможные значения не должно содержать символа перевода строки");
		RegexpFieldValidator lenghtValuesValidator = new RegexpFieldValidator(".{0,500}", "Значение Возможные значения не должно превышать 500 символов");
		RegexpFieldValidator specialSimbolValidator = new RegexpFieldValidator("[^@&«»]*","Вы указали недопустимые символы, например, @, &, «, » . Пожалуйста, введите другое значение в поле Возможные заначения.");
		fieldBuilder.addValidators(dependFromTypeValidator, lineFeedNotCaontainsValidator, lenghtValuesValidator, specialSimbolValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Реквизиты поля");
		fieldBuilder.setName("requisiteTypes");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new FieldRequisiteTypeValidator("Вы указали недопустимые реквизиты поля. Пожалуйста, выберите другие значения."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Маска поля");
		fieldBuilder.setName("mask");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minlength");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxlength");
	    compareValidator.setMessage("Вы неправильно указали минимальную длину поля. Минимальная длина не должна превышать максимальной длины поля.");
	    fb.setFormValidators(compareValidator);

		return fb.build();
	}
}
