package com.rssl.phizic.web.client.ext.sbrf.accounts;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncCloseOldBrowserMessageAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		if (dataProvider != null)
		{
			PersonData data = dataProvider.getPersonData();
			if (data != null)
				data.setShowOldBrowserMessage(false);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
