package com.rssl.phizic.web.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.auth.csa.front.operations.news.AuthNewsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * action для отпарвки списка новостей на страницу входа 
 * @author basharin
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class AuthNewsAction extends LookupDispatchAction
{
	protected static final String START_FORWARD = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			AuthNewsOperation authNewsOperation = new AuthNewsOperation();
			List<News> list = authNewsOperation.initialize();
			AuthNewsForm frm = (AuthNewsForm) form;
			frm.setJsonString(createJSON(list));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return mapping.findForward(START_FORWARD);
	}

	private String createJSON(List<News> list)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("[");

        Iterator<News> i = list.iterator();
        boolean hasNext = i.hasNext();
        while (hasNext) {
	        News news = i.next();
            buf.append(toJSON(news));
            hasNext = i.hasNext();
            if (hasNext)
                buf.append(", ");
        }

		buf.append("]");
		return buf.toString();
	}

	/**
	 * @return JSON объект
	 */
	private String toJSON(News news)
	{
		return new String("{\"id\":" + news.getId() + ",\"title\":\"" + news.getTitle().replace("\"","&quot;").replace("\\","&#92;")
				+ "\",\"important\":\"" + news.getImportant() + "\"," + "\"date\":\""
				+ DateHelper.formatDateDDMM(news.getNewsDate())+"\"}");
	}
}
