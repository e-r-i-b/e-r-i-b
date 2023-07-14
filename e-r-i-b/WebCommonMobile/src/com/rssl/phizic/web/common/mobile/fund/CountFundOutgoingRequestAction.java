package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение количества неотвеченных входящих запросов на сбор средств
 * @author sergunin
 * @ created 12.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class CountFundOutgoingRequestAction extends OperationalActionBase
{

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // todo посылать разумный запрос
        FundCountRequestForm frm = (FundCountRequestForm) form;
        frm.setCount(0);
        return mapping.findForward(FORWARD_START);
    }
}
