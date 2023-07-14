package com.rssl.phizic.web.log;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.phizic.logging.operations.LogParemetersReaderBase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 31.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class FormLogParametersReader extends LogParemetersReaderBase
{
	private Form form;
	private Map<String, Object> values;

	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public FormLogParametersReader(String description, Form form, Map<String, Object> values)
	{
		super(description);
		this.form = form;
		this.values = values;
	}

	public LinkedHashMap read()
	{
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		for (Field field : form.getFields())
		{
			//игнорим вычислимые поля
			if (field.getValueExpression() == null)
			{
				map.put(field.getDescription(), values.get(field.getName()));
			}
		}
		return map;
	}
}
