package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.operations.loans.LoanCalculatorOperation;
import com.rssl.common.forms.xslt.XmlConverter;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.dom.DOMSource;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanCalculatorAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap ()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
								 HttpServletResponse response ) throws Exception
	{
		LoanCalculatorForm frm       = (LoanCalculatorForm)form;
		LoanCalculatorOperation operation = createOperation(LoanCalculatorOperation.class);
		XmlConverter converter = new CommonXmlConverter(operation.getTemplateSource());
		Long id = frm.getId();

		if ( id != null )
			converter.setParameter("selectedProduct", id);

		converter.setParameter("webRoot",      request.getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setData(new DOMSource(operation.getListDocument()));
		frm.setHtml( converter.convert() );
        
		return mapping.findForward(FORWARD_SHOW);
	}
}
