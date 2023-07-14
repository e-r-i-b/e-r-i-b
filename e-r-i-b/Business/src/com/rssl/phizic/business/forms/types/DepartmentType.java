package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.business.departments.Department;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentType implements Type
{
	public static final Type INSTANCE = new DepartmentType();

	private static final FieldValueParser     defaultParser     = new DepartmentParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new RegexpFieldValidator("(-?\\d+)|\\d*") });
	private static final FieldValueFormatter  formatter         = new Formatter();

	public String getName()
	{
		return "department";
	}

	public FieldValueParser getDefaultParser()
	{
		return DepartmentType.defaultParser;
	}

	public List<FieldValidator> getDefaultValidators()
	{
		return defaultValidators;
	}

	public FieldValueFormatter getFormatter()
	{
		return formatter;
	}

	private static class Formatter implements FieldValueFormatter
	{
		public String toSignableForm(String value)
		{
			if(value == null)
				return "";

//			Department department = (Department)value;
//			return Long.toString( department.getId() );
			return value;
		}
	}

}
