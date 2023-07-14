package com.rssl.phizic.web.dictionaries.pfp.products.types;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.dictionaries.pfp.products.types.EditProductTypeParametersOperation;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования параметров отображения типов продуктов в ПФП
 */

public class EditProductTypeParametersForm extends EditProductFormBase
{
	public static final String TYPE_FIELD_NAME = "type";
	public static final String NAME_FIELD_NAME = "name";
	public static final String USE_FIELD_NAME = "use";
	public static final String DESCRIPTION_FIELD_NAME = "description";
	public static final String USE_ON_DIAGRAM_FIELD_NAME = "useOnDiagram";
	public static final String USE_ON_TABLE_FIELD_NAME = "useOnTable";

	public static final String DIAGRAM_AXIS_TYPE_X = "x";
	public static final String DIAGRAM_AXIS_TYPE_Y = "y";

	public static final String DIAGRAM_AXIS_USE_ZERO_FIELD_NAME = "useZero";
	public static final String DIAGRAM_AXIS_NAME_FIELD_NAME = "AxisName";
	public static final String DIAGRAM_AXIS_STEP_USE_FIELD_NAME = "AxisStepUse";
	public static final String DIAGRAM_AXIS_STEP_COUNT_FIELD_NAME = "AxisStepCount";
	public static final String DIAGRAM_AXIS_STEP_FROM_FIELD_NAME_PREFIX = "AxisStepFrom";
	public static final String DIAGRAM_AXIS_STEP_TO_FIELD_NAME_PREFIX = "AxisStepTo";
	public static final String DIAGRAM_AXIS_STEP_NAME_FIELD_NAME_PREFIX = "AxisStepName";

	public static final String TABLE_COLUMN_NAME_COUNT_FIELD_NAME = "tableColumnNameCount";
	public static final String TABLE_COLUMN_NAME_FIELD_NAME_PREFIX = "tableColumnName";
	public static final String TABLE_COLUMN_ORDER_FIELD_NAME_PREFIX = "order";
	public static final String TABLE_COLUMN_ID_FIELD_NAME_PREFIX = "columnId";
	public static final String LINK_NAME_FIELD_NAME = "linkName";
	public static final String LINK_HINT_FIELD_NAME = "linkHint";
	public static final int DIAGRAM_AXIS_STEP_MAX_VALUE = 6;
	public static final int DIAGRAM_AXIS_STEP_MAX_COUNT = 6;
	public static final int TABLE_PARAMETERS_MAX_COLUMN_COUNT = 5;

	private static final List<String> imageIds;
	private static final List<SegmentCodeType> segmentCodeTypeList = Arrays.asList(SegmentCodeType.values());
	private static final int FIFTY = 50;
	private static final int ONE_HUNDRED = 100;
	private static final int FIVE_HUNDRED = 500;

	static
	{
		List<String> imageIdsTemp = new ArrayList<String>();
		imageIdsTemp.add(EditProductTypeParametersOperation.PRODUCT_TYPE_IMAGE);
		imageIds = Collections.unmodifiableList(imageIdsTemp);
	}

	/**
	 * @return список сегментов клиентов
	 */
	public List<SegmentCodeType> getSegmentCodeTypeList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return segmentCodeTypeList;
	}

	public List<String> getImageIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return imageIds;
	}

	/**
	 * @return максимальное значение шага на диаграме
	 */
	public int getDiagramAxisStepMaxValue()
	{
		return DIAGRAM_AXIS_STEP_MAX_VALUE;
	}

	/**
	 * @return максимальное число шагов на диаграме
	 */
	public int getDiagramAxisStepMaxCount()
	{
		return DIAGRAM_AXIS_STEP_MAX_COUNT;
	}

	/**
	 * @return максимальное число столбцов в таблице
	 */
	public int getTableParametersMaxColumnCount()
	{
		return TABLE_PARAMETERS_MAX_COLUMN_COUNT;
	}

	private Field getStringField(String name, String description, long maxLength, boolean isRequired)
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

	private Field getBooleanField(String name, String description, boolean isRequired)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		if (isRequired)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		return fieldBuilder.build();
	}

	private FieldBuilder getLongFieldBuilder(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		return fieldBuilder;
	}

	private Field getLongField(String name, String description)
	{
		return getLongFieldBuilder(name, description).build();
	}

	private Field getLongField(String name, String description, long maxValue)
	{
		FieldBuilder fieldBuilder = getLongFieldBuilder(name, description);
		String message = "Значение ".concat(description).concat(" не должно превышать ").concat(String.valueOf(maxValue));
		fieldBuilder.addValidators(new NumericRangeValidator(BigDecimal.ZERO, BigDecimal.valueOf(maxValue), message));
		return fieldBuilder.build();
	}

	private String getNotEmptyExpression(String fieldName)
	{
		return "form." + fieldName + " != null && form." + fieldName + " != ''";
	}

	private MultiFieldsValidator getCompareValidator(String fieldName1, String fieldName2, String message)
	{
		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setMessage(message);
		compareValidator.setBinding(CompareValidator.FIELD_O1, fieldName1);
		compareValidator.setBinding(CompareValidator.FIELD_O2, fieldName2);
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		String enabledExpression = "(".concat(getNotEmptyExpression(fieldName1)).concat(")||(").concat(getNotEmptyExpression(fieldName2)).concat(")");
		compareValidator.setEnabledExpression(new RhinoExpression(enabledExpression));
		return compareValidator;
	}

	private MultiFieldsValidator getRequiredMultiFieldsValidator(String fieldName1, String fieldName2, String fieldName3)
	{
		MultiFieldsValidator requiredMultiFieldValidator = new RequiredAllMultiFieldValidator();
		requiredMultiFieldValidator.setBinding("fieldName1", fieldName1);
		requiredMultiFieldValidator.setBinding("fieldName2", fieldName2);
		requiredMultiFieldValidator.setBinding("fieldName3", fieldName3);
		String enabledExpression = "(".concat(getNotEmptyExpression(fieldName1)).concat(")||(").concat(getNotEmptyExpression(fieldName2)).concat(")||(").concat(getNotEmptyExpression(fieldName3)).concat(")");
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression(enabledExpression));
		requiredMultiFieldValidator.setMessage("Невозможно сохранить шаг. Задайте корректно параметры шага.");
		return requiredMultiFieldValidator;
	}

	private Pair<List<Field>, List<MultiFieldsValidator>> getAxisFields(String axisType)
	{
		List<Field> fields = new ArrayList<Field>();
		List<MultiFieldsValidator> validators = new ArrayList<MultiFieldsValidator>();

		fields.add(getStringField(axisType.concat(DIAGRAM_AXIS_NAME_FIELD_NAME), "Название оси", FIFTY, false));
		fields.add(getBooleanField(axisType.concat(DIAGRAM_AXIS_STEP_USE_FIELD_NAME), "Отображать разделители шагов", false));

		if (Boolean.valueOf((String) getField(axisType.concat(DIAGRAM_AXIS_STEP_USE_FIELD_NAME))))
		{
			String axisStepCountFieldName = axisType.concat(DIAGRAM_AXIS_STEP_COUNT_FIELD_NAME);
			fields.add(getLongField(axisStepCountFieldName, "Количество шагов"));

			long axisStepCount = Long.parseLong((String) getField(axisStepCountFieldName));
			if(axisStepCount > 0)
			{
				CompleteIntervalValidator completeIntervalValidator = new CompleteIntervalValidator();
				completeIntervalValidator.setParameter(CompleteIntervalValidator.INTERVAL_START, "0");
				completeIntervalValidator.setParameter(CompleteIntervalValidator.INTERVAL_END, String.valueOf(getDiagramAxisStepMaxValue()));
				completeIntervalValidator.setBinding(CompleteIntervalValidator.INTERVAL_COUNT, axisStepCountFieldName);
				completeIntervalValidator.setMessage("Последняя координата предыдущего шага должна быть первой координатой следующего шага. Пожалуйста, проверьте диапазоны шагов.");

				for (int i = 0; i < axisStepCount; i++)
				{
					String index = String.valueOf(i);
					String fromFieldName = axisType.concat(DIAGRAM_AXIS_STEP_FROM_FIELD_NAME_PREFIX).concat(index);
					String toFieldName = axisType.concat(DIAGRAM_AXIS_STEP_TO_FIELD_NAME_PREFIX).concat(index);
					String nameFieldName = axisType.concat(DIAGRAM_AXIS_STEP_NAME_FIELD_NAME_PREFIX).concat(index);

					fields.add(getLongField(fromFieldName, "от", getDiagramAxisStepMaxValue()));
					fields.add(getLongField(toFieldName, "до", getDiagramAxisStepMaxValue()));
					fields.add(getStringField(nameFieldName, "до", FIFTY, false));

					validators.add(getRequiredMultiFieldsValidator(fromFieldName, toFieldName, nameFieldName));

					validators.add(getCompareValidator(fromFieldName, toFieldName, "Значение в поле \"до\" должно быть больше значения в поле \"от\"."));

					completeIntervalValidator.setBinding(CompleteIntervalValidator.INTERVAL_START.concat(index), fromFieldName);
					completeIntervalValidator.setBinding(CompleteIntervalValidator.INTERVAL_END.concat(index), toFieldName);
				}

				validators.add(completeIntervalValidator);
			}
		}
		return new Pair<List<Field>, List<MultiFieldsValidator>>(fields, validators);
	}

	private List<Field> getTableParametersFields()
	{
		List<Field> fields = new ArrayList<Field>();

		fields.add(getLongField(TABLE_COLUMN_NAME_COUNT_FIELD_NAME, "Количество столбцов"));

		long axisStepCount = Long.parseLong((String) getField(TABLE_COLUMN_NAME_COUNT_FIELD_NAME));
		for (int i = 0; i < axisStepCount; i++)
		{
			fields.add(getStringField(TABLE_COLUMN_NAME_FIELD_NAME_PREFIX.concat(String.valueOf(i)), "Название столбца", FIFTY, false));
			fields.add(getLongField(TABLE_COLUMN_ID_FIELD_NAME_PREFIX.concat(String.valueOf(i)), "Идентификатор столбца"));
		}
		for (int i = 0; i < getTableParametersMaxColumnCount(); i++)
			fields.add(getLongField(TABLE_COLUMN_ORDER_FIELD_NAME_PREFIX.concat(String.valueOf(i)), "Порядок", getTableParametersMaxColumnCount()));


		return fields;
	}

	/**
	 * @return логическая форма
	 */
	public Form getForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField(TYPE_FIELD_NAME, "Название", ONE_HUNDRED, true));
		formBuilder.addField(getStringField(NAME_FIELD_NAME, "Название, отображаемое клиентам", ONE_HUNDRED, true));
		formBuilder.addField(getBooleanField(USE_FIELD_NAME, "Статус", true));
		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getImageField(EditProductTypeParametersOperation.PRODUCT_TYPE_IMAGE));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DESCRIPTION_FIELD_NAME);
		fieldBuilder.setDescription("Описание раздела");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), new MultiLineTextValidator("Значение поля Описание раздела не должно превышать ", FIVE_HUNDRED));
		formBuilder.addField(fieldBuilder.build());
		formBuilder.addField(getBooleanField(USE_ON_DIAGRAM_FIELD_NAME, "на графике", false));
		formBuilder.addField(getBooleanField(USE_ON_TABLE_FIELD_NAME, "в табличном виде", false));

		if (Boolean.valueOf((String) getField(USE_ON_DIAGRAM_FIELD_NAME)))
		{
			formBuilder.addFields(getBooleanField(DIAGRAM_AXIS_USE_ZERO_FIELD_NAME, "Отображать 0 на графике", false));
			Pair<List<Field>, List<MultiFieldsValidator>> axisFields = getAxisFields(DIAGRAM_AXIS_TYPE_X);
			formBuilder.addFields(axisFields.getFirst());
			formBuilder.addFormValidators(axisFields.getSecond());
			formBuilder.addFields(getStringField(DIAGRAM_AXIS_TYPE_Y.concat(DIAGRAM_AXIS_NAME_FIELD_NAME), "Название оси", FIFTY, false));
		}

		if (Boolean.valueOf((String) getField(USE_ON_TABLE_FIELD_NAME)))
		{
			formBuilder.addFields(getTableParametersFields());
		}
		formBuilder.addField(getStringField(LINK_NAME_FIELD_NAME, "Название", ONE_HUNDRED, true));
		formBuilder.addField(getStringField(LINK_HINT_FIELD_NAME, "Подсказка", FIVE_HUNDRED, true));

		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + USE_FIELD_NAME + " == true")));

		return formBuilder.build();
	}
}
