package com.rssl.phizic.web.common.client;

import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.web.util.HttpSessionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.10.2005
 * Time: 19:15:20
 */
public abstract class ClientWebContext
{
    private static String SESSION_DATA_KEY = StaticPersonData.class.getName();

    public static PersonData get(HttpServletRequest request)
    {
        return HttpSessionUtils.getSessionAttribute(request, SESSION_DATA_KEY);
    }

    public static void set(HttpServletRequest request, PersonData data)
    {
        request.getSession().setAttribute(SESSION_DATA_KEY, data);
    }

}
