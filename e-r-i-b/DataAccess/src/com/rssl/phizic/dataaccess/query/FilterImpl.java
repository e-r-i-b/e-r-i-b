package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.query.Filter;

import java.util.*;

/**
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author$
 * @ $Revision$
 */
class FilterImpl implements Filter
{
    private Map<String,Object> parameters = new HashMap<String, Object>();
    private String name;

    FilterImpl(String name)
    {
        this.name = name;
    }

    public Filter setParameter(String name, Object value)
    {
        parameters.put(name, value);
        return this;
    }

    public String getName()
    {
        return name;
    }

    Map<String,Object> getParameters()
    {
        return parameters;
    }
}
