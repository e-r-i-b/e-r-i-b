package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.operations.access.restrictions.DepositProductListRestrictionOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author: krenev $
 * @ $Revision: 10114 $
 */
public class DepositProductListRestrictionAction extends ResourceListResstrictionActionBase<DepositProductListRestrictionOperation>//TODO operationQuery??
{
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        DepositProductListRestrictionForm frm = (DepositProductListRestrictionForm) form;

        DepositProductListRestrictionOperation operation = createOperation(frm);

        List<DepositProduct> products   = operation.getRestrictionData().getProducts();
        String[]             selectedIds = new String[ products.size() ];

        for(int i = 0; i < products.size(); i++)
        {
            selectedIds[i] = products.get(i).getId().toString();
        }

        frm.setSelectedIds(selectedIds);
        frm.setProducts(operation.getAllProducts());

        return super.start(mapping, form, request, response);
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        DepositProductListRestrictionForm      frm         = (DepositProductListRestrictionForm) form;
        DepositProductListRestrictionOperation operation   = createOperation(frm);
        List<Long>                             selectedIds = getSelectedIds(form);

        operation.setProductIds(selectedIds);
        operation.save();

        return super.save(mapping, form, request, response);
    }

    protected DepositProductListRestrictionOperation createOperation(ActionForm form) throws BusinessException, BusinessLogicException
    {
        RestrictionFormBase          frm       = (RestrictionFormBase) form;
	    DepositProductListRestrictionOperation operation = createOperation(DepositProductListRestrictionOperation.class);
        operation.initialize(frm.getLoginId(), frm.getServiceId(), frm.getOperationId());
        return operation;
    }
}