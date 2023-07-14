package com.rssl.phizic.web.dictionaries.pfp.products.card.recommendation;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.dictionaries.pfp.products.card.recommendation.EditUseCreditCardRecommendationOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования настроек рекоммендаций
 */

public class EditUseCreditCardRecommendationForm extends EditFormBase
{
	public static final String STEP_NAME_FIELD_NAME_PREFIX = "stepName";
	public static final String STEP_DESCRIPTION_FIELD_NAME_PREFIX = "stepDescription";

	public static final String ACCOUNT_TYPE = "account";
	public static final String THANKS_TYPE = "thanks";

	public static final String FROM_INCOME_FIELD_NAME_SUFFIX = "IncomeFrom";
	public static final String TO_INCOME_FIELD_NAME_SUFFIX = "IncomeTo";
	public static final String DEFAULT_INCOME_FIELD_NAME_SUFFIX = "IncomeDefault";
	public static final String DESCRIPTION_FIELD_NAME_SUFFIX = "Description";

	public static final String RECOMMENDATION_FIELD_NAME = "recommendation";
	public static final String CARD_NAME_FIELD_NAME_PREFIX = "cardProductNameFor";

	private static final String BUNDLE_KEY_PREFIX = "edit.card.recomendation.form.";
	private static final String BUNDLE_RESOURCE = "editCardOperationSettingsBundle";

	private static final BigInteger ONE_HUNDRED = BigInteger.valueOf(100);
	private static final int ONE_HUNDRED_FIFTY = 150;
	private static final int FIVE_HUNDRED = 500;
	private Long[] cardProductIds = new Long[]{};   //идентификаторы ПИФов

	/**
	 * @return максимальное количество шагов
	 */
	public static int getMaxRecommendationStepCount()
	{
		return EditUseCreditCardRecommendationOperation.MAX_STEP_COUNT;
	}

	/**
	 * @return максимальное количество шагов
	 */
	public int getMaxStepCount()
	{
		return getMaxRecommendationStepCount();
	}

	/**
	 * @return идентификаторы карт
	 */
	public Long[] getCardProductIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cardProductIds;
	}

	/**
	 * задать идентификаторы карт
	 * @param cardProductIds идентификаторы карт
	 */
	public void setCardProductIds(Long[] cardProductIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cardProductIds = cardProductIds;
	}

	/**
	 * @return логическая форма
	 */
	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		for (int i = 0; i < getMaxRecommendationStepCount(); i++)
		{
			String index = String.valueOf(i);
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(STEP_NAME_FIELD_NAME_PREFIX.concat(index));
			fieldBuilder.setDescription("Название шага");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(ONE_HUNDRED);
			lengthFieldValidator.setMessage("Значение поля Название не должно превышать 100 символов.");
			fieldBuilder.addValidators(new RequiredFieldValidator(), lengthFieldValidator);
			formBuilder.addField(fieldBuilder.build());

			formBuilder.addField(getTextField(STEP_DESCRIPTION_FIELD_NAME_PREFIX.concat(index), "Описание шага", FIVE_HUNDRED, true));
		}

		Pair<List<Field>, List<MultiFieldsValidator>> accountEfficacy = getEfficacy(ACCOUNT_TYPE, "Доходность по вкладу");
		formBuilder.addFields(accountEfficacy.getFirst());
		formBuilder.addFormValidators(accountEfficacy.getSecond());

		Pair<List<Field>, List<MultiFieldsValidator>> thanksEfficacy = getEfficacy(THANKS_TYPE, "Спасибо от сбербанка");
		formBuilder.addFields(thanksEfficacy.getFirst());
		formBuilder.addFormValidators(thanksEfficacy.getSecond());

		formBuilder.addField(getTextField(RECOMMENDATION_FIELD_NAME, "Текст блока с рекомендациями по накоплению баллов", ONE_HUNDRED_FIFTY, false));

		for (Long cardId: getCardProductIds())
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(CARD_NAME_FIELD_NAME_PREFIX.concat(String.valueOf(cardId)));
			fieldBuilder.setDescription("Наименование карты");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}

	private static Pair<List<Field>, List<MultiFieldsValidator>> getEfficacy(String type, String description)
	{
		String fromIncomeFieldName = type.concat(FROM_INCOME_FIELD_NAME_SUFFIX);
		String toIncomeFieldName = type.concat(TO_INCOME_FIELD_NAME_SUFFIX);
		String defaultIncomeFieldName = type.concat(DEFAULT_INCOME_FIELD_NAME_SUFFIX);

		ArrayList<Field> efficacyFields = new ArrayList<Field>();
		efficacyFields.add(getPercentField(fromIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "range." + type, BUNDLE_RESOURCE), false));
		efficacyFields.add(getPercentField(toIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "range." + type, BUNDLE_RESOURCE), false));
		efficacyFields.add(getPercentField(defaultIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "default." + type, BUNDLE_RESOURCE), true));
		efficacyFields.add(getTextField(type.concat(DESCRIPTION_FIELD_NAME_SUFFIX), "Подсказка к полю", ONE_HUNDRED_FIFTY, true));

		ArrayList<MultiFieldsValidator> validators = new ArrayList<MultiFieldsValidator>();

		MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(fromIncomeFieldName, fromIncomeFieldName);
		requiredMultiFieldValidator.setBinding(toIncomeFieldName, toIncomeFieldName);
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("(" + getNotEmptyExpression(fromIncomeFieldName) + ")||(" + getNotEmptyExpression(toIncomeFieldName) + ")"));
		requiredMultiFieldValidator.setMessage(StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "maxmin." + type, BUNDLE_RESOURCE));
		validators.add(requiredMultiFieldValidator);

		validators.add(getCompareValidator(fromIncomeFieldName, toIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "maxlessmin." + type, BUNDLE_RESOURCE)));
		validators.add(getCompareValidator(fromIncomeFieldName, defaultIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "outrange." + type, BUNDLE_RESOURCE)));
		validators.add(getCompareValidator(defaultIncomeFieldName, toIncomeFieldName, StrutsUtils.getMessage(BUNDLE_KEY_PREFIX + "outrange." + type, BUNDLE_RESOURCE)));
		return new Pair<List<Field>, List<MultiFieldsValidator>>(efficacyFields, validators);
	}

	private static MultiFieldsValidator getCompareValidator(String fieldName1, String fieldName2, String message)
	{
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setMessage(message);
		compareValidator.setBinding(CompareValidator.FIELD_O1, fieldName1);
		compareValidator.setBinding(CompareValidator.FIELD_O2, fieldName2);
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setEnabledExpression(new RhinoExpression("(" + getNotEmptyExpression(fieldName1) + ")&&(" + getNotEmptyExpression(fieldName2) + ")"));
		return compareValidator;
	}

	private static Field getTextField(String name, String description, int maxLength, boolean isRequired)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		if (isRequired)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new MultiLineTextValidator("Поле " + description + " не должно превышать", maxLength));
		return fieldBuilder.build();
	}

	private static String getNotEmptyExpression(String fieldName)
	{
		return "!isNaN(parseFloat(form.".concat(fieldName).concat("))");
	}

	private static Field getPercentField(String name, String description, boolean isRequired)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser(1));
		if (isRequired)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(100((\\.|,)0)?|(([1-9])?[0-9]((\\.|,)\\d)?))$", "Некорректо заполнено поле " + description + ". Формат для процентных величин ###.#."));

		return fieldBuilder.build();
	}
}
