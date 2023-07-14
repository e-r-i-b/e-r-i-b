package com.rssl.phizic.web.servlet;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Request с добавленными параметрами 
 * @author Rydvanskiy
 * @ created 24.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class HttpServletEditableRequest extends HttpServletRequestWrapper
{
    private Map<String, String[]> modifiableParameters;
	private final Set<String> removedParameters = new HashSet<String>();

	/**
     * —оздаем новый Request с добавление дополнительных параметров
     *
     * @param request
     * @param additionalParams
     */
    public HttpServletEditableRequest(HttpServletRequest request,
                                                    Map<String, String[]> additionalParams)
    {
        super(request);
        modifiableParameters = new TreeMap<String, String[]>();
        modifiableParameters.putAll(additionalParams);
    }

	/**
     * —оздаем новый Request
     *
     * @param request
     */
    public HttpServletEditableRequest(HttpServletRequest request)
    {
        super(request);
        modifiableParameters = new TreeMap<String, String[]>();
    }

	public void putParameter(String name, String value)
	{
		modifiableParameters.put(name, new String[] { value });
		removedParameters.remove(name);
	}

	public void removeParameter(String name)
	{
		modifiableParameters.remove(name);
		removedParameters.add(name);
	}

    @Override
    public String getParameter(final String name)
    {
	    if (removedParameters.contains(name))
	        return null;

        String[] strings = getParameterMap().get(name);
        if (strings != null)
        {
            return strings[0];
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
	    Map<String, String[]> allParameters = new TreeMap<String, String[]>();
        allParameters.putAll(super.getParameterMap());
        allParameters.putAll(modifiableParameters);
	    for (String name : removedParameters)
		    allParameters.remove(name);

        return Collections.unmodifiableMap(allParameters);
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(String name)
    {
        return getParameterMap().get(name);
    }
}
