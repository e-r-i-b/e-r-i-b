package com.rssl.common.forms.validators;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ¬алидатор провер€ющий соотношение суммы нескольких полей на форме с заданным в параметрах значением 
 */
public class MultiFieldSumValidator extends MultiFieldsValidatorBase
{
	public static final String SUM_PARAMETER_NAME = "sum";
	public static final String OPERATOR = "operator";

	public static final String EQUAL        = "eq";
	public static final String LESS         = "lt";
	public static final String GREATER      = "gt";
	public static final String LESS_EQUAL   = "le";
	public static final String GREATE_EQUAL = "ge";

	static final private Map<String,int[]> resultTable;

	static
    {
        resultTable = new HashMap<String, int[]>();
	    resultTable.put(EQUAL,        new int[]{0});
	    resultTable.put(LESS,         new int[]{-1});
	    resultTable.put(GREATER,      new int[]{1});
	    resultTable.put(LESS_EQUAL,   new int[]{-1, 0});
	    resultTable.put(GREATE_EQUAL, new int[]{0, 1});
    }

	public boolean validate(Map values)
	{
		double sum = 0;
		String[] names = getBindingsNames();
		for (String name : names)
		{
			Object fieldValue = retrieveFieldValue(name, values);
			if (!(fieldValue instanceof Number))
				return false;

			sum += ((Number) fieldValue).doubleValue();
		}
		double sumParameter = NumberUtils.toDouble(getParameter(SUM_PARAMETER_NAME));
		String     op   = getParameter(OPERATOR);
		if(StringHelper.isEmpty(op))
			throw new RuntimeException("ѕараметр '" + OPERATOR + "' не определен!");

		int[] criterias = resultTable.get(op);

		int result = NumberUtils.compare(sum,sumParameter);

        for (int criteria : criterias)
        {
            if (criteria == result)
                return true;
        }

        return false;
	}
}
