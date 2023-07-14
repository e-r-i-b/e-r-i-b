package com.rssl.phizic.web.actions.templates.offer;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.Status;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.templates.offer.CreditOfferTemplateListOperation;
import com.rssl.phizic.operations.templates.offer.CreditOfferTemplateRemoveOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @since  03.06.2015.
 */
public class CreditOfferTemplateListAction extends ListActionBase
{
	@Override
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> methodMap = super.getAditionalKeyMethodMap();
		methodMap.put("button.template.offer.remove",  "remove");
		return methodMap;
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(CreditOfferTemplateListOperation.class);
	}

	@Override
	protected CreditOfferTemplateRemoveOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		CreditOfferTemplateRemoveOperation operation = createOperation(CreditOfferTemplateRemoveOperation.class);
		operation.initialize(frm.getSelectedId());
		return operation;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		CreditOfferTemplateListOperation listOperation = (CreditOfferTemplateListOperation) operation;
		listOperation.setResultSize(webPageConfig().getListLimit() + 1);
		listOperation.applyFilterParameters(filterParams);
		super.doFilter(filterParams, listOperation, frm);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CreditOfferTemplate template = createRemoveOperation((ListFormBase) form).getEntity();

		if (template.getStatus() != Status.INTRODUCED)
		{
			saveError(request, new ActionMessage("credit.loan.offer.remove.unsupported"));
			return filter(mapping, form, request, response);
		}

		return super.remove(mapping, form, request, response);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return CreditOfferTemplateListForm.FILTER;
	}
}
