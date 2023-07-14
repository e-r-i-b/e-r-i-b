package com.rssl.phizic.business;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 04.10.2005
 * Time: 17:10:50
 */
public class DefaultResourceFormatter implements ResourceFormatter
{
    public String format(Object object)
    {
        return object != null ? object.toString() : "";
    }
}
