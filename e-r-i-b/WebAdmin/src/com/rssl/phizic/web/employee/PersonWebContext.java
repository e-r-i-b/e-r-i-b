package com.rssl.phizic.web.employee;

import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.web.util.HttpSessionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author niculichev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 */
abstract public class PersonWebContext
{
	private static String SESSION_DATA_KEY = PersonData.class.getName();

    public static PersonData get(HttpServletRequest request)
    {
        return HttpSessionUtils.getSessionAttribute(request, PersonWebContext.SESSION_DATA_KEY);
    }

    public static void set(HttpServletRequest request, PersonData data)
    {
        request.getSession().setAttribute(PersonWebContext.SESSION_DATA_KEY, data);
    }
}
