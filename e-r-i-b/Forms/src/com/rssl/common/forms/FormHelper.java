package com.rssl.common.forms;

import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.types.SubType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.utils.PropertyHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 11.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class FormHelper
{
	/**
	 *  онструирует форму по списку полей
	 * @param fields - список полей
	 * @return форма
	 */
	public static Form buildForm(List<Field> fields)
	{
		if (fields == null)
			throw new NullPointerException("јргумент 'fields' не может быть null");

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(fields);
		return formBuilder.build();
	}

	/**
	 * ¬озвращает значени€ "по-умолчанию" дл€ тех полей, где такие значени€ есть
	 * @param form - форма с пол€ми
	 * @return мап "поле -> значение_по_умолчанию"
	 */
	public static Map<String, String> getFieldInitialValues(Form form)
	{
		if (form == null)
			throw new NullPointerException("јргумент 'form' не может быть null");

		Map<String, String> map = new HashMap<String, String>();
		for (Field field : form.getFields()) {
			Expression exp = field.getInitalValueExpression();
			if (exp != null) {
				Object value = exp.evaluate(null);
				if (value != null)
					map.put(field.getName(), value.toString());
			}
		}
		return map;
	}


	/**
	 * Ѕерет названи€ полей вида <key> + index из списка полей формы и строит массив
	 * из json объектов вида {field1: <field1_value>, field2: <field2_value>, field3: <field3_value>, ...}
	 * @param formFields - пол€ формы
	 * @param fieldsString список полей, разделенных '|'
	 * @return строка - массив из значений полей таблицы
	 */
	public static String formatTableSettingToArray(Map<String,Object> formFields, String fieldsString)
	{
		String[] fields = fieldsString.split("\\|");
		String key = fields[0];
		fields = (String[]) ArrayUtils.remove(fields, 0);
		StringBuilder result = new StringBuilder("[");
		List<Integer> indexList = PropertyHelper.getTableSettingNumbers(formFields, key);
		for (Integer index : indexList)
		{

			StringBuilder jsonRecord = new StringBuilder("{");
			jsonRecord.append(key);
			jsonRecord.append(":");
			jsonRecord.append("'");
			jsonRecord.append(formFields.get(key + index));
			jsonRecord.append("'");

			if (fields != null)
			{
				for (String field : fields)
				{
					jsonRecord.append(", ");

					jsonRecord.append(field);
					jsonRecord.append(":");
					jsonRecord.append("'");
					jsonRecord.append(formFields.get(field + index));
					jsonRecord.append("'");
				}
			}

			jsonRecord.append("}");

			if (result.length() > 1)
				result.append(", ");

			result.append(jsonRecord);
		}

		result.append("]");

		return result.toString();
	}

	/**
	 * ѕолучение пол€ по id
	 * @param fields пол€
	 * @param id id пол€
	 * @return поле
	 */
	public static Field getFieldById(List<Field> fields, String id)
	{
		if (CollectionUtils.isEmpty(fields))
		{
			return null;
		}

		for (Field field : fields)
		{
			if (field.getName().equals(id))
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * јдаптировать поле
	 * @param field поле
	 * @param value значение
	 * @return поле
	 */
	public static Field adaptField(Field field, String value)
	{
		if (SubType.STATIC == field.getSubType())
		{
			return buildStaticField(field, value);
		}
		return field;
	}

	/**
	 * ѕосторить статическое поле на основе существующего пол€
	 * @param field поле
	 * @param value значение пол€
	 * @return поле
	 */
	public static Field buildStaticField(Field field, String value)
	{
		FieldBuilder fb = new FieldBuilder();

		fb.setName(field.getName());
		fb.setDescription(field.getDescription());
		fb.setType(field.getType().getName());
		fb.setSubType(field.getSubType());
		fb.setSource(field.getSource());
		fb.setValueExpression(new ConstantExpression(value));
		fb.setInitalValueExpression(new ConstantExpression(value));
		fb.setMask(field.getMask());
		fb.setEnabledExpression(field.getEnabledExpression());
		fb.setBusinessCategory(field.getBusinessCategory());
		fb.setFromApi(field.getFromApi());
		fb.setToApi(field.getToApi());

		return fb.build();
	}

	/**
	 * TODO пока как эксперимент, используетс€ тольк в за€вке на автоплатеж карта-карта в ј— "јвтоплатежи"
	 *
	 * Ќеобходимость показать значени€ статических полей
	 * @param formType тип формы
	 * @return true - да
	 */
	public static boolean isShowStatic(FormType formType)
	{
		return FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM == formType;
	}
}
