package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.operations.access.restrictions.CardListRestrictionOperation;
import com.rssl.phizic.operations.access.restrictions.AccountListRestrictionOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class CardListRestrictionAction extends ResourceListResstrictionActionBase<CardListRestrictionOperation>//TODO operationQuery??
{
       public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        CardListRestrictionForm frm = (CardListRestrictionForm) form;

        CardListRestrictionOperation operation = createOperation(frm);

        List<CardLink> cardLinks   = operation.getRestrictionData().getCardLinks();
        String[]       selectedIds = new String[ cardLinks.size() ];

        for(int i = 0; i < cardLinks.size(); i++)
        {
            selectedIds[i] = cardLinks.get(i).getId().toString();
        }

        frm.setSelectedIds(selectedIds);
        frm.setCards(operation.getAllCardLinks());

        return super.start(mapping, form, request, response);
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        CardListRestrictionForm      frm         = (CardListRestrictionForm) form;
        CardListRestrictionOperation operation   = createOperation(frm);
        List<Long>                   selectedIds = getSelectedIds(form);

        operation.setCardLinks(selectedIds);
        operation.save();
        
        return super.save(mapping, form, request, response);
    }

    protected CardListRestrictionOperation createOperation(ActionForm form) throws BusinessException, BusinessLogicException
    {
        RestrictionFormBase          frm       = (CardListRestrictionForm) form;
        CardListRestrictionOperation operation = createOperation(CardListRestrictionOperation.class);
        operation.initialize(frm.getLoginId(), frm.getServiceId(), frm.getOperationId());

        return operation;
    }
}