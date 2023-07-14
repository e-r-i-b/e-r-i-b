package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sergunin
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileCreateP2PAutoTransferAction extends TestMobileDocumentAction
{
    private static final String FORWARD_MAKE_LONG_OFFER = "MakeLongOffer";
    protected static final String FORWARD_INIT = "Init";
   	protected static final String FORWARD_FIRST_STEP = "FirstStep";
   	protected static final String FORWARD_SECOND_STEP = "SecondStep";

    public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
        String operation = form.getOperation();

        if (StringHelper.isEmpty(operation))
            return mapping.findForward(FORWARD_INIT);

        if ("init".equals(operation))
            return submitInit(mapping, form);

        if ("next".equals(operation) || "save".equals(operation))
            return submitSave(mapping, form);

        if ("makeAutoTransfer".equals(operation))
            return submitMakeAutoTransfer(mapping, form);

        return null;
    }

    private ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
    {
        if (send(form) == 0)
            return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
        return mapping.findForward(FORWARD_INIT);
    }

    private ActionForward submitMakeAutoTransfer(ActionMapping mapping, TestMobileDocumentForm form)
    {
        if (send(form) == 0)
            return mapping.findForward(FORWARD_FIRST_STEP);
        return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
    }

    private ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
    {
        final int result = send(form);
        if (result == 0 && form.getDocument() != null)
            return mapping.findForward(FORWARD_SECOND_STEP);
        return mapping.findForward(FORWARD_FIRST_STEP);
    }

}
