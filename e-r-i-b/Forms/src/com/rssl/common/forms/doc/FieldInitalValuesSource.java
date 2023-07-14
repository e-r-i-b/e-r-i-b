package com.rssl.common.forms.doc;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 02.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class FieldInitalValuesSource implements FieldValuesSource
{
	private Map<String, Field> fieldsMap = new HashMap<String, Field>();
	private Map<String, Object> initialFieldsValues = new HashMap<String, Object>();

	public FieldInitalValuesSource(Form form, Map<String, String> initialValues)
	{
		for (Field field:form.getFields())
		{
			fieldsMap.put(field.getName(), field);
		}
		initialFieldsValues.putAll(initialValues);
	}

	public String getValue(String name)
	{
		Field field = fieldsMap.get(name);
		if (field == null){
			return null;
		}
		return getFieldValue(field);
	}

	String getFieldValue(Field field)
	{
		Expression expression = field.getInitalValueExpression();
		if (expression == null){
			return null;
		}
		return StringHelper.getEmptyIfNull(expression.evaluate(initialFieldsValues));
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>(fieldsMap.size());
		for (Map.Entry<String, Field> entry : fieldsMap.entrySet()) {
			String value = getFieldValue(entry.getValue());
			if (value != null)
				values.put(entry.getKey(), value);
		}
		return values;
	}

	public boolean isChanged(String name)
	{
		Field field = fieldsMap.get(name);
		if (field == null){
			return false;
		}
		return field.isChanged();
	}

	public boolean isEmpty()
	{
		return fieldsMap.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}

	Map<String, Object> getInitialFieldsValues()
	{
		return initialFieldsValues;
	}
}
