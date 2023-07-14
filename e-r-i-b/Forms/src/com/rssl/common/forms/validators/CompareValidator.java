package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Сравнение двух объектов.
 * @author Roshka
 * @ created 20.12.2005
 * @ $Author: mihaylov $
 * @ $Revision: 37298 $
 */
public class CompareValidator extends MultiFieldsValidatorBase
{
    public static final String FIELD_O1 = "obj1";
    public static final String FIELD_O2 = "obj2";

    public static final String GREATE       = "gt";
    public static final String GREATE_EQUAL = "ge";
    public static final String EQUAL        = "eq";
    public static final String NOT_EQUAL    = "ne";
    public static final String LESS         = "lt";
    public static final String LESS_EQUAL   = "le";

    public static final String OPERATOR = "operator";

    static final protected Map<String,int[]> resultTable;

    static
    {
        resultTable = new HashMap<String, int[]>();
        resultTable.put(GREATE,       new int[]{1} );
        resultTable.put(GREATE_EQUAL, new int[]{0, 1});
        resultTable.put(EQUAL,        new int[]{0});
        resultTable.put(NOT_EQUAL,    new int[]{-1, 1});
        resultTable.put(LESS,         new int[]{-1});
        resultTable.put(LESS_EQUAL,   new int[]{-1, 0});
    }

    public boolean validate(Map values) throws TemporalDocumentException
    {
        Comparable<Comparable> obj1 = (Comparable<Comparable>) retrieveFieldValue(FIELD_O1, values);
        Comparable obj2 = (Comparable) retrieveFieldValue(FIELD_O2, values);

        String     op   = getParameter(OPERATOR);
        if (op == null)
            throw new RuntimeException("Параметр '" + OPERATOR + "' не определен!");

        if (obj1 == null || obj2 == null)
            return true;

        int result = normalize(obj1.compareTo(obj2));

        int[] criterias = resultTable.get(op);

        for (int criteria : criterias)
        {
            if (criteria == result)
                return true;
        }

        return false;
    }

    private static int normalize(int compareResult)
    {
        if(compareResult == 0)
            return 0;
        return compareResult > 0 ? 1 : -1;
    }
}
