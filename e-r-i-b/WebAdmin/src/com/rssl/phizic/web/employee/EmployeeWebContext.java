package com.rssl.phizic.web.employee;

import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.web.util.HttpSessionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.10.2005
 * Time: 19:15:20
 */
public abstract class EmployeeWebContext
{
    private static String SESSION_DATA_KEY = EmployeeData.class.getName();

    public static EmployeeData get(HttpServletRequest request)
    {
        return HttpSessionUtils.getSessionAttribute(request, EmployeeWebContext.SESSION_DATA_KEY);
    }

    public static void set(HttpServletRequest request, EmployeeData data)
    {
        request.getSession().setAttribute(EmployeeWebContext.SESSION_DATA_KEY, data);
    }

}
