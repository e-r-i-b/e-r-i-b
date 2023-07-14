package com.rssl.phizic.web.client.deposits;

import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.operations.deposits.DepositCalculatorOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kosyakov
 * @ created 12.05.2006
 * @ $Author: kosyakov $
 * @ $Revision: 1317 $
 */

public class DepositCalculatorAction extends OperationalActionBase
{
    private static final String FORWARD_SHOW   = "Show";

    protected Map<String, String> getKeyMethodMap ()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start ( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response ) throws Exception
    {
        DepositCalculatorForm      frm       = (DepositCalculatorForm)form;
        DepositCalculatorOperation operation = createOperation(DepositCalculatorOperation.class);
        XmlConverter               converter = new CommonXmlConverter(operation.getTemplateSource());
        Long                       id        = frm.getId();

        if ( id != null )
            converter.setParameter("selectedProduct", id);

        converter.setParameter("webRoot",      request.getContextPath());
	    converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
	    converter.setData(operation.getListSource());
        frm.setHtml( converter.convert() );

        return mapping.findForward(DepositCalculatorAction.FORWARD_SHOW);
    }
}
