package com.rssl.common.forms.doc;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * »сточник данных, вычисл€ющий значение пол€ (*.pfd.xml field:value)
 *
 * @author khudyakov
 * @ created 01.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class FieldValueValuesSource extends FieldInitalValuesSource
{
	public FieldValueValuesSource(Form form, Map<String, String> initialValues)
	{
		super(form, initialValues);
	}

	String getFieldValue(Field field)
	{
		if (SubType.DINAMIC == field.getSubType())
		{
			return StringUtils.EMPTY;
		}

		Expression expression = field.getValueExpression();
		if (expression == null)
		{
			return StringUtils.EMPTY;
		}

		String fieldValue = StringHelper.getEmptyIfNull(expression.evaluate(getInitialFieldsValues()));
		if (StringHelper.isNotEmpty(fieldValue))
		{
			getInitialFieldsValues().put(field.getName(), fieldValue);
			return fieldValue;
		}
		return StringUtils.EMPTY;
	}

}
