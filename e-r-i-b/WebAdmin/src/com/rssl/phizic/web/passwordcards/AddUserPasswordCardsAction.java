package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 22.11.2006 Time: 18:01:09 To change this template use
 * File | Settings | File Templates.
 */
public class AddUserPasswordCardsAction  extends OperationalActionBase
{
    public static final String FORWARD_SHOW   = "Show";

    protected Map<String, String> getKeyMethodMap()
    {
	    return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AddUserPasswordCardsForm frm = (AddUserPasswordCardsForm) form;

	    if(frm.getId()==null)
	        throw new BusinessException("Не установлен пользователь");
	    
        return mapping.findForward(FORWARD_SHOW);
    }
}
