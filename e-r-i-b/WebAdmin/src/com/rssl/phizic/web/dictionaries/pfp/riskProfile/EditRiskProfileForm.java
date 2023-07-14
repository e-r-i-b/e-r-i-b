package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileUtil;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.web.common.EditFormBase;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования риск профиля
 */

public class EditRiskProfileForm extends EditFormBase
{
	private static final List<ProductType> productTypeList = RiskProfileUtil.getProductTypes(); //типы продуктов
	private static final FieldValueParser SEGMENT_PARSER = new FieldValueParser()
	{
		public Serializable parse(String value)
		{
			return SegmentCodeType.valueOf(value);
		}
	};
	private static final EnumFieldValidator<SegmentCodeType> SEGMENT_VALIDATOR = new EnumFieldValidator<SegmentCodeType>(SegmentCodeType.class, "Неизвестный сегмент.");
	private static final int DESCRIPTION_MAX_SIZE = 500;

	private Map<ProductType, List<SegmentCodeType>> productTypeSegmentDependence;

	/**
	 * @return список возможных типов продуктов
	 */
	public List<ProductType> getProductTypeList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return productTypeList;
	}

	/**
	 * @return настройки видимости типов продуктов
	 */
	public Map<ProductType, List<SegmentCodeType>> getProductTypeSegmentDependence()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return productTypeSegmentDependence;
	}

	/**
	 * задать настройки видимости типов продуктов
	 * @param productTypeSegmentDependence настройки видимости типов продуктов
	 */
	public void setProductTypeSegmentDependence(Map<ProductType, List<SegmentCodeType>> productTypeSegmentDependence)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.productTypeSegmentDependence = productTypeSegmentDependence;
	}

	/**
	 * @return логическая форма
	 */
	@SuppressWarnings({"ReuseOfLocalVariable", "OverlyLongMethod"})
	public Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
							       new RegexpFieldValidator(".{1,250}", "Поле \"Название\" должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("segment");
		fieldBuilder.setDescription("Сегмент");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(SEGMENT_PARSER);
		fieldBuilder.addValidators(new RequiredFieldValidator(), SEGMENT_VALIDATOR);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new MultiLineTextValidator("Поле \"Описание\" должно содержать не более", DESCRIPTION_MAX_SIZE));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minWeight");
		fieldBuilder.setDescription("Минимальный балл");
		fieldBuilder.clearValidators();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите минимальное количество баллов, которое необходимо для определения данного риск-профиля."),
								   new RegexpFieldValidator("(\\d{0,3})", "Поле \"Количество баллов\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxWeight");
		fieldBuilder.setDescription("Максимальный балл");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("(\\d{0,3})", "Поле \"Количество баллов\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minWeight");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxWeight");
	    compareValidator.setMessage("Вы неправильно указали минимальное количество баллов. Минимальный балл должен быть меньше максимального балла риск-профиля.");
		formBuilder.addFormValidators(compareValidator);

		for (ProductType type: productTypeList)
		{
			List<SegmentCodeType> segments = productTypeSegmentDependence.get(type);
			fieldBuilder = new FieldBuilder();
			String fieldName = "productType" + type;
			fieldBuilder.setName(fieldName);
			String fieldDescription = "Процентное соотношение для " + type.getDescription();
			fieldBuilder.setDescription(fieldDescription);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(getEnabledExpression(segments, (String) getField("segment")));
			fieldBuilder.clearValidators();
			fieldBuilder.addValidators(new RequiredFieldValidator(),
									   new RegexpFieldValidator("(\\d{0,2})|100", "Вы неправильно указали соотношение продуктов в портфеле. Пожалуйста, введите цифры от 0 до 100."));
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}

	private Expression getEnabledExpression(List<SegmentCodeType> segments, String currentSegments)
	{
		for (SegmentCodeType segment : segments)
			if (segment.name().equals(currentSegments))
				return new ConstantExpression(true);
		return new ConstantExpression(false);
	}
}
