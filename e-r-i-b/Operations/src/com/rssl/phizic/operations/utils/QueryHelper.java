package com.rssl.phizic.operations.utils;

import java.util.*;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author$
 * @ $Revision$
 */
final public class QueryHelper
{
    private QueryHelper()
    {
    }

    private static String formatForLike(String val)
    {
        return ((val == null || val.length() == 0) ? null : val + "%");
    }

    /**
     * Фороматирование параметров для запроса с like
     * @param parameters
     * @return отформатированный
     */
    public static Map<String,String> formatForLike(Map parameters)
    {
        Map<String,String> formatedParams = new HashMap<String, String>();

        if (parameters != null && parameters.size() > 0)
        {
	        for (Object o : parameters.entrySet())
	        {
		        Map.Entry<String,String> entry = (Map.Entry<String,String>) o;
		        String name = entry.getKey();
		        String value = entry.getValue();
		        formatedParams.put(name, formatForLike(value));
	        }
        }
        return formatedParams;
    }


}
