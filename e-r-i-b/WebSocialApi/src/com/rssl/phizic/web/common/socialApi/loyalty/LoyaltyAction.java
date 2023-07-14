package com.rssl.phizic.web.common.socialApi.loyalty;

import com.rssl.phizic.operations.loyalty.LoyaltyGenRefOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 01.02.2012
 * Time: 16:19:01
 */
public class LoyaltyAction  extends OperationalActionBase
{
	private static final String START = "Start";
	private static final String ERROR = "Не удалось получить ссылку на программу лояльность ";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (checkAccess(LoyaltyGenRefOperation.class))
		{
			LoyaltyGenRefOperation operation = createOperation(LoyaltyGenRefOperation.class);
		 	operation.initialize();
		    LoyaltyForm	frm = (LoyaltyForm) form;

			String	url = operation.getRef();
			if(!StringHelper.isEmpty(url))
			{
				frm.setUrl(XmlHelper.escape(url));
			}
			else
			{
				ActionMessages msgs = new ActionMessages();
				msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ERROR, false));
				saveErrors(currentRequest(), msgs);
			}
		}
		return mapping.findForward(START);
	}
}
