package com.rssl.phizic.web.dictionaries.pfp.products.card;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.FieldRestrictedExpression;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardProgrammType;
import com.rssl.phizic.operations.dictionaries.pfp.products.card.EditCardOperation;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;

import java.math.BigInteger;
import java.util.*;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class EditCardForm extends EditProductFormBase
{
	public static final String NAME_FIELD_NAME                  = "name";
	public static final String PROGRAMM_TYPE_FIELD_NAME         = "programmType";
	public static final String INPUTS_FIELD_NAME                = "inputs";
	public static final String BONUS_FIELD_NAME                 = "bonus";
	public static final String CLAUSE_FIELD_NAME                = "clause";
	public static final String DESCRIPTION_FIELD_NAME           = "description";
	public static final String RECOMMENDATION_FIELD_NAME        = "recommendation";
	public static final String DIAGRAM_USE_IMAGE_FIELD_NAME     = "diagramUseImage";
	public static final String DIAGRAM_COLOR_FIELD_NAME         = "diagramColor";
	public static final String DIAGRAM_USE_NET_FIELD_NAME       = "diagramUseNet";
	public static final String SHOW_AS_DEFAULT_FIELD_NAME       = "showAsDefault";


	public static final Form EDIT_FORM = createForm();

	private static final int EIGHTY = 80;
	private static final int ONE_HUNDRED_SEVENTY = 170;
	private static final int TWO_HUNDRED_FIFTY = 250;
	private static final int FIVE_HUNDRED = 500;
	private static final int SIXTEEN = 16;

	private static final List<String> imageIds;

	static
	{
		List<String> imageIdsTemp = new ArrayList<String>();
		imageIdsTemp.add(EditCardOperation.CARD_IMAGE);
		imageIdsTemp.add(EditCardOperation.PROGRAMM_IMAGE);
		imageIdsTemp.add(EditCardOperation.DIAGRAM_IMAGE);
		imageIds = Collections.unmodifiableList(imageIdsTemp);
	}

	public List<String> getImageIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return imageIds;
	}

	private static Field getStringField(String name, String description, int maxLength, boolean isRequired)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		if (isRequired)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(new LengthFieldValidator(BigInteger.valueOf(maxLength)));
		return fieldBuilder.build();
	}

	private static Field getBigDecimalField(String name, String description, Expression requiredExpression)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setEnabledExpression(requiredExpression);
		fieldBuilder.addValidators(requiredFieldValidator);
		return fieldBuilder.build();
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

	private static Field getBooleanField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		return fieldBuilder.build();
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField(NAME_FIELD_NAME, "Название", EIGHTY, true));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PROGRAMM_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Тип");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new EnumParser<CardProgrammType>(CardProgrammType.class));
		fieldBuilder.addValidators(new RequiredFieldValidator(), new EnumFieldValidator<CardProgrammType>(CardProgrammType.class, "Неизвестный тип карты."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addField(getBigDecimalField(INPUTS_FIELD_NAME,
												"Перечисление с покупки",
												new FieldRestrictedExpression(PROGRAMM_TYPE_FIELD_NAME, CardProgrammType.aeroflot, CardProgrammType.mts)));
		formBuilder.addField(getBigDecimalField(BONUS_FIELD_NAME,
												"Перечисление с покупки",
												new FieldRestrictedExpression(PROGRAMM_TYPE_FIELD_NAME, CardProgrammType.beneficent, CardProgrammType.aeroflot, CardProgrammType.mts)));

		formBuilder.addField(getStringField(CLAUSE_FIELD_NAME, "Условие программы", TWO_HUNDRED_FIFTY, false));

		formBuilder.addFields(getImageField(EditCardOperation.CARD_IMAGE));
		formBuilder.addFields(getImageField(EditCardOperation.PROGRAMM_IMAGE));

		formBuilder.addField(getTextField(DESCRIPTION_FIELD_NAME, "Описание продукта", ONE_HUNDRED_SEVENTY, true));
		formBuilder.addField(getTextField(RECOMMENDATION_FIELD_NAME, "Текст блока с рекомендациями по накоплению баллов", FIVE_HUNDRED, false));

		formBuilder.addField(getBooleanField(DIAGRAM_USE_IMAGE_FIELD_NAME, "График по карте"));
		formBuilder.addFields(getImageField(EditCardOperation.DIAGRAM_IMAGE));
		formBuilder.addField(getStringField(DIAGRAM_COLOR_FIELD_NAME, "Цвет", SIXTEEN, false));
		formBuilder.addField(getBooleanField(DIAGRAM_USE_NET_FIELD_NAME, "Отображение сетки на графике"));
		formBuilder.addField(getBooleanField(SHOW_AS_DEFAULT_FIELD_NAME, "Предлагать по умолчанию"));


		return formBuilder.build();
	}
}
