package com.rssl.phizic.web.client.loyalty;

import com.rssl.phizic.operations.loyalty.LoyaltyGenRefOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.BusinessException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 18.07.2011
 * Time: 17:29:36
 * в данном экшене генерируем ссылку для переход на сайт программы 'Лояльность'
 */
public class LoyaltyAction extends OperationalActionBase
{
	public static final String URL_START = "Url";
	private static final String ERROR = "error";
	private static final String OFFERS_PAGE_PARAM = "&page=personal";
	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoyaltyGenRefOperation operation = createOperation();
		if (operation != null)
		{
			operation.initialize();
			LoyaltyForm	frm = (LoyaltyForm) form;

			String	url = operation.getRef();
			if(url != null && url.length() != 0)
			{
				if (frm.isOffersPage())
					url = url+ OFFERS_PAGE_PARAM;
				frm.setUrl(url);
			}
			else
				frm.setUrl(ERROR);
		}
	 	return mapping.findForward(URL_START);
	}

	private LoyaltyGenRefOperation createOperation() throws BusinessException
	{
		if (checkAccess("LoyaltyGenRefOperation"))
		{
			return createOperation("LoyaltyGenRefOperation");
		}
		else if (checkAccess("LoyaltyProgramOffersOperation"))
		{
			return createOperation("LoyaltyProgramOffersOperation");
		}
		return  null;
	}
	
	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
