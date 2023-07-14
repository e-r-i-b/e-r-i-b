package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FieldBuilderFactory;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.strategy.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.validators.DublicateFieldNameValidator;
import com.rssl.phizic.business.payments.forms.validators.PayPeriodUnbrokenValidator;
import com.rssl.phizic.business.payments.forms.validators.PayPeriodValidator;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.rssl.common.forms.validators.strategy.AbstractModeBasedValidatorStrategy.MODE_DELIMETER;

/**
 * @author lukina
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedFieldBuilderHelper
{
	private static final String REQUIRED_FIELD_VALIDATOR_MODE = PrepareDocumentValidatorStrategy.MODE
			+ MODE_DELIMETER
			+ DocumentValidationStrategy.MODE
			+ MODE_DELIMETER
			+ MobileDocumentValidationStrategy.MODE
			+ MODE_DELIMETER
			+ ByTemplateValidationStrategy.MODE
			+ MODE_DELIMETER
			+ PreTemplateValidationStrategy.MODE;

	private static final String DEFAULT_VALIDATOR_MODE = REQUIRED_FIELD_VALIDATOR_MODE + MODE_DELIMETER + PrepareLongOfferValidationStrategy.MODE;

	private static final String TEMPLATE_VALIDATOR_MODE = DEFAULT_VALIDATOR_MODE + MODE_DELIMETER + TemplateValidationStrategy.MODE;
	private static final String AMOUNT_FIELD_ERROR_MESSAGE = "Пожалуйста, укажите сумму, которую необходимо перевести. Например, 320,66.";
	private static final String MIN_AMOUNT_VALUE = "0.01";
	private static final String MAX_AMOUNT_VALUE = "999999999999999.99";
	private static final DublicateFieldNameValidator dublicateFieldNameValidator = new DublicateFieldNameValidator();

	private static final String OPEN_LINK_REGEX = "\\[url\\]";
	private static final String CLOSE_LINK_REGEX = "\\[/url\\]";
	private static final String FULL_STRING_VALIDATOR_REGEXP = "[^@&«»]*";
	private static final String EMAIL_STRING_VALIDATOR_REGEXP = "[^&«»]*";

	/**
	 * адаптировать список гейтовых описаний полей к бизнесовому(логическому)
	 * @param fields список гейтовых описаний полей для адаптации
	 * @param subType доп. тип поля
	 * @param billingCode код биллинга
	 * @return адаптированые поля(пригодные для работы форм процессора).
	 */
	public static List<Field> adaptFields(List<? extends com.rssl.phizic.gate.payments.systems.recipients.Field> fields, SubType subType, String billingCode) throws BusinessException
	{
		if (fields != null)
		{
			List<com.rssl.common.forms.Field> result = new ArrayList<com.rssl.common.forms.Field>(fields.size());
			for (com.rssl.phizic.gate.payments.systems.recipients.Field field : fields)
			{
				Field newField = adaptField(field, null, subType, billingCode);
				if (dublicateFieldNameValidator.validate(newField.getName()))
				{
					if(ApplicationUtil.isATMApi() && FieldDataType.choice == field.getType())
						continue;
					result.add(newField);
					continue;
				}
				throw new BusinessException("В платеже обнаружено дублирующееся поле:"+ newField.getName() + ". Оплата невозможна.");
			}
			return result;
		}

		return new ArrayList<com.rssl.common.forms.Field>();
	}

	public static Field adaptField(com.rssl.phizic.gate.payments.systems.recipients.Field field)
	{
		return adaptField(field, null, SubType.DINAMIC);
	}

	public static Field adaptField(com.rssl.phizic.gate.payments.systems.recipients.Field field, FieldBuilderFactory fbFactory)
	{
		return adaptField(field, fbFactory, SubType.DINAMIC);
	}

	/**
	 * Версия adaptField() с возможностью указать свой field-builder
	 * @param field поле
	 * @param fbFactory фабрика поля
	 * @param subType доп тип поля
	 * @return поле
	 */
	public static Field adaptField(com.rssl.phizic.gate.payments.systems.recipients.Field field, FieldBuilderFactory fbFactory, SubType subType)
	{
		return adaptField(field, fbFactory, subType, null);
	}

	/**
	 * Версия adaptField() с возможностью указать свой field-builder
	 * @param field поле
	 * @param fbFactory фабрика поля
	 * @param subType доп тип поля
	 * @param billingCode код биллинга
	 * @return поле
	 */
	public static Field adaptField(com.rssl.phizic.gate.payments.systems.recipients.Field field, FieldBuilderFactory fbFactory, SubType subType, String billingCode)
	{
		FieldBuilder fb = getFieldBuilder(field, fbFactory, subType, billingCode);
		if (isInitValue(field, subType))
		{
			String value = (String) field.getValue();
			fb.setValueExpression(new ConstantExpression(!StringHelper.isEmpty(value) ? value : field.getDefaultValue()));
		}
		return fb.build();
	}

	/**
	 * Создание поля с информацией о документ, из которого было взято значение
	 * @param field поле, для которого можно установить значение из документа
	 * @return построенное поле
	 */
	public static Field createDocumentInfoField(com.rssl.phizic.gate.payments.systems.recipients.Field field)
	{
		FieldBuilder fb = new FieldBuilder();
		String name = field.getExternalId() + MaskPaymentFieldUtils.DOCUMENT_POSTFIX;

		fb.setName(name);
		fb.setType(StringType.INSTANCE.getName());
		fb.setSource(String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, name));

		return fb.build();
	}

	/**
	 * Билдер поля
	 * @param field поле
	 * @param fbFactory фабрика
	 * @param subType доп. тип поля
	 * @return билдер
	 */
	protected static FieldBuilder getFieldBuilder(com.rssl.phizic.gate.payments.systems.recipients.Field field, FieldBuilderFactory fbFactory, SubType subType)
	{
		return getFieldBuilder(field, fbFactory, subType, null);
	}
	/**
	 * Билдер поля
	 * @param field поле
	 * @param fbFactory фабрика
	 * @param subType доп. тип поля
	 * @param billingCode код биллинга
	 * @return билдер
	 */
	protected static FieldBuilder getFieldBuilder(com.rssl.phizic.gate.payments.systems.recipients.Field field, FieldBuilderFactory fbFactory, SubType subType, String billingCode)
	{
		if (fbFactory == null)
		{
			if (field.isSaveInTemplate())
			{
				//noinspection AssignmentToMethodParameter
				fbFactory = templateFieldBuilderFactory;
			}
			else
			{
				//noinspection AssignmentToMethodParameter
				fbFactory = defaultFieldBuilderFactory;
			}
		}

		FieldBuilder fb = fbFactory.createFieldBuilder();

		String name = field.getExternalId();
		fb.setName(name);
		// если есть идентификатор расширенного описания, то вырезаем указатели на ссылку, чтоб не мешались при ошибках валидации
		fb.setDescription(StringHelper.isNotEmpty(field.getExtendedDescId()) ?
				field.getName().replaceAll(OPEN_LINK_REGEX, StringUtils.EMPTY).replaceAll(CLOSE_LINK_REGEX, StringUtils.EMPTY) : field.getName());

		fb.setSource(String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, name));
		fb.setType(adaptType(field));
		fb.setKey(field.isKey());
		fb.setBusinessCategory(adaptBusinessCategory(field.getBusinessSubType()));
		if (field.isRequired() && !(MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00) &&
				(field.getBusinessSubType() == BusinessFieldSubType.phone || field.getBusinessSubType() == BusinessFieldSubType.wallet)))
		{
			FieldValidator validator = new RequiredFieldValidator();
			if (!field.isKey())
			{
				validator.setMode(REQUIRED_FIELD_VALIDATOR_MODE);
				((ValidatorsModeFixFieldBuilder)fb).addValidatorsWithDefaultMode(validator); //приходится извращаться((
			}
			else
				fb.addValidators(validator);
		}
		if(StringHelper.isNotEmpty(field.getMask()))
		{
			fb.setMask(field.getMask());
		}
		if (field.getMinLength() != null || field.getMaxLength() != null)
		{
			fb.addValidators(buildLengthValidator(field.getMinLength(), field.getMaxLength(), field.getHint(), field.getDescription(), field.getType()));
		}
		if (!StringHelper.isEmpty(field.getDefaultValue()))
		{
			fb.setInitalValueExpression(new ConstantExpression(field.getDefaultValue()));
		}
		if (field.getType() == FieldDataType.list)
		{
			ListField listField = (ListField) field;
			if (listField.getValues() != null)
			{
				List<ListValue> listValues = listField.getValues();
				List<String> values = new ArrayList<String>(listValues.size());
				for (ListValue listValue : listValues)
				{
					String value = !StringHelper.isEmpty(listValue.getId()) ? listValue.getId() : listValue.getValue();
					values.add(!StringHelper.isEmpty(value)? value.trim() : value);
				}
				fb.addValidators(new ChooseValueValidator(values));
			}
		}
		if (field.getType() == FieldDataType.date)
		{
			fb.addValidators(new DateFieldValidator());
		}
		if (field.getType() == FieldDataType.number)
		{
			fb.addValidators(new RegexpFieldValidator("^((-?\\d+)(\\.\\d+)?|\\d*)$"));
			Integer numberPrecision = ((NumberField)field).getNumberPrecision();
			if(numberPrecision!=null)
			{
				fb.addValidators(new RegexpFieldValidator("^\\d+((\\.|,)\\d{0," + numberPrecision + "})?$","Некорректный формат, введите значение с точностью "+numberPrecision+" знаков после запятой"));
			}
		}
		if (field.getType() == FieldDataType.integer)
		{
			fb.addValidators(new RegexpFieldValidator("\\d*"));
		}
		if (field.getType() == FieldDataType.calendar)
		{
			fb.addValidators(new PayPeriodValidator());
			if (CalendarFieldPeriod.unbroken == ((CalendarField) field).getPeriod())
			{
				fb.addValidators(new PayPeriodUnbrokenValidator());
			}
		}
		if(field.getType() == FieldDataType.string)
		{
			fb.addValidators(new RegexpFieldValidator(getStringFieldRegExp(billingCode),"Вы указали недопустимые символы, например, @, &, «, » . Пожалуйста, введите другое значение в данное поле"),
			                 new IncorrectQuotesValidator());
		}
		if(field.getType() == FieldDataType.email)
		{
			fb.addValidators(new EmailFieldValidator(),
			                 new IncorrectQuotesValidator());
		}
		if (field.getFieldValidationRules() != null)
		{
			for (FieldValidationRule rule : field.getFieldValidationRules())
			{
				fb.addValidators(adaptValidator(rule, field.getType().name()));
			}
		}
		if (field.isMainSum())
		{
			FieldValidator validator = new MoneyFieldValidator();
			validator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, MIN_AMOUNT_VALUE);
			validator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, MAX_AMOUNT_VALUE);
			validator.setMessage(AMOUNT_FIELD_ERROR_MESSAGE);
			fb.addValidators(validator);
		}
		fb.setSignable(field.isRequiredForConformation());
		return fb;
	}

	private static String getStringFieldRegExp(String billingCode)
	{
		if (StringHelper.isEmpty(billingCode))
		{
			return FULL_STRING_VALIDATOR_REGEXP;
		}
		ProvidersConfig providersConfig = ConfigFactory.getConfig(ProvidersConfig.class);
		List<String> billingCodes = providersConfig.getEmailBillingCode();
		return billingCodes.contains(billingCode) ? EMAIL_STRING_VALIDATOR_REGEXP : FULL_STRING_VALIDATOR_REGEXP;
	}

	/**
	 * Создание валидатора (гейтовый тип)
	 * @param rule правило
	 * @param mode режим работы валидатора
	 * @return валидатор
	 */
	public static FieldValidator adaptValidator(FieldValidationRule rule, String mode, String fieldDataType)
	{
		FieldValidator validator = adaptValidator(rule, fieldDataType);
		validator.setMode(mode);

		return validator;
	}

	/**
	 * Создание валидатора (гейтовый тип)
	 * @param rule правило
	 * @return валидатор
	 */
	public static FieldValidator adaptValidator(FieldValidationRule rule, String fieldDataType)
	{
		if (rule.getFieldValidationRuleType() == FieldValidationRuleType.REGEXP)
		{
			FieldValidator validator;
			if (MoneyType.INSTANCE.getName().equalsIgnoreCase(fieldDataType))
				validator = new RegexpMoneyFieldValidator();
			else
				validator = new RegexpFieldValidator();

			validator.setMessage(rule.getErrorMessage());
			for (Map.Entry<String, Object> entry : rule.getParameters().entrySet())
			{
				validator.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
			}
			return validator;
		}
		throw new IllegalArgumentException("Неизвестный тип правила валидации поля " + rule.getFieldValidationRuleType());
	}

	/**
	 * Создание валидатора, проверяющего длину по пришедшим параметрам
	 * @param minLength минимальная длина
	 * @param maxLength максимальная длина
	 * @param hint подсказка
	 * @param description описание поля
	 * @return валидатор
	 */
	public static FieldValidator buildLengthValidator(Long minLength, Long maxLength, String hint, String description, FieldDataType fieldDataType)
	{
		StringBuilder regexp = new StringBuilder();
		regexp.append("^.{");
		if (minLength != null)
		{
			regexp.append(minLength);
		}
		else
		{
			regexp.append(0);
		}

		regexp.append(",");
		if (maxLength != null)
		{
			regexp.append(maxLength);
		}
		regexp.append("}$");

		String message = StringHelper.isEmpty(hint) ? description: hint;
		if (MoneyType.INSTANCE.getName().equalsIgnoreCase(fieldDataType.name()))
		{
			return new RegexpMoneyFieldValidator(regexp.toString(), message);
		}

		return new RegexpFieldValidator(regexp.toString(), message);
	}

	public static BusinessCategory adaptBusinessCategory(BusinessFieldSubType businessSubType)
	{
		if(businessSubType == null)
			return null;

		switch (businessSubType)
		{
			case phone:
				return BusinessCategory.PHONE;
			default:
				return null;
		}
	}

	private static String adaptType(com.rssl.phizic.gate.payments.systems.recipients.Field field)
	{
		if (field.isMainSum())
			return MoneyType.INSTANCE.getName();

		switch (field.getType())
		{
			case integer:
			case number:
			case string:
			case calendar:
			case date:
			case list:
			case link:
			case set:
			case graphicset:
			case email:
				return StringType.INSTANCE.getName();
			case choice:
				return BooleanType.INSTANCE.getName();
			case money:
				return MoneyType.INSTANCE.getName();
			default:
				throw new IllegalArgumentException("Неизвестный тип параметра " + field.getType());
		}
	}

	/**
	 * Билдер устанавливает нужный режим для каждого добавляемого валидатора
	 */
	private static class ValidatorsModeFixFieldBuilder extends FieldBuilder
	{
		private String mode;

		private ValidatorsModeFixFieldBuilder(String mode)
		{
			this.mode = mode;
		}

		public void addValidators(FieldValidator... validators)
		{
			for (FieldValidator validator : validators)
				validator.setMode(mode);
			super.addValidators(validators);
		}

		public void addValidatorsWithDefaultMode(FieldValidator... validators)
		{
			super.addValidators(validators);
		}
	}

	private static final FieldBuilderFactory defaultFieldBuilderFactory = new FieldBuilderFactory()
	{
		public FieldBuilder createFieldBuilder()
		{
			return new ValidatorsModeFixFieldBuilder(DEFAULT_VALIDATOR_MODE);
		}
	};

	private static final FieldBuilderFactory templateFieldBuilderFactory = new FieldBuilderFactory()
	{
		public FieldBuilder createFieldBuilder()
		{
			return new ValidatorsModeFixFieldBuilder(TEMPLATE_VALIDATOR_MODE);
		}
	};

	protected static boolean isInitValue(com.rssl.phizic.gate.payments.systems.recipients.Field field, SubType subType)
	{
		return SubType.STATIC == subType ||  !field.isVisible() || !field.isEditable();
	}
}
