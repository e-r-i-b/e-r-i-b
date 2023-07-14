package com.rssl.phizic.web.persons;

import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForward;

import javax.servlet.http.HttpServletRequest;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 06.10.2005 Time: 14:31:06 */
public final class PersonUtils
{
    private PersonUtils(){}

    public static Long getPersonId(HttpServletRequest request)
    {
        String personId = request.getParameter("person");
        if ( personId != null) return Long.decode(personId);
        return null;
    }

    public static ActionForward redirectWithPersonId(ActionForward action, Long selectedId)
    {
        ActionForward result     = new ActionForward(action);
        UrlBuilder    urlBuilder = new UrlBuilder();

        urlBuilder.setUrl(result.getPath());
        urlBuilder.addParameter("person", selectedId.toString());

        result.setPath(urlBuilder.getUrl());

        return result;
    }

    public static ActionForward redirectWithPersonId(ActionForward action, HttpServletRequest request)
    {
        return redirectWithPersonId(action, getPersonId(request));
    }

}
