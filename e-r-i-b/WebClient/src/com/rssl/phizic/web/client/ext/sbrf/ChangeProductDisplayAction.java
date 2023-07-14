package com.rssl.phizic.web.client.ext.sbrf;

import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.operations.account.EditExternalLinkOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Изменение состояния продукта
 * @author gladishev
 * @ created 24.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class ChangeProductDisplayAction extends OperationalActionBase
{
	private static final Map<String, Class> productTypes = new HashMap<String, Class>();

	static
	{
		productTypes.put("account",    AccountLink.class);
		productTypes.put("card" ,      CardLink.class);
		productTypes.put("loan" ,      LoanLink.class);
		productTypes.put("imaccount",  IMAccountLink.class);
		productTypes.put("depaccount", DepoAccountLink.class);
		productTypes.put("pfr",        PFRLink.class);
		productTypes.put("loyaltyProgram", LoyaltyProgramLink.class);
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.showHide", "showHide");
		map.put("button.showInMain", "showInMain");
		return map;
	}

	private ActionForward getEmptyPage(HttpServletResponse response) throws Exception
	{
	    response.getOutputStream().println(" ");
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getEmptyPage(response);
	}

	private EditExternalLinkOperation prepareOperation(ActionForm form) throws Exception
    {
        ChangeProductDisplayForm frm = (ChangeProductDisplayForm) form;
        String id = frm.getId();
        if (StringHelper.isEmpty(id))
		    return null;
	    EditExternalLinkOperation operation = createOperation(EditExternalLinkOperation.class);
	    String productType = frm.getProductType();
	    Class clazz = productTypes.get(productType);
	    if (clazz == null)
		    return null;
	    operation.initialize(Long.parseLong(id), clazz);
	    return operation;
    }

	public ActionForward showHide(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    EditExternalLinkOperation operation = prepareOperation(form);
		response.addHeader("Content-Length","0");
		if (operation != null)
			operation.changeShowOperations();
		return getEmptyPage(response);
	}

	public ActionForward showInMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    EditExternalLinkOperation operation = prepareOperation(form);
		if (operation != null)
			operation.changeShowInMain();
		return getEmptyPage(response);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
