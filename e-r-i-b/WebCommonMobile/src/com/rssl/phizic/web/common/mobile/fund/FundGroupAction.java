package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.fund.group.*;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @auhor: tisov
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 * Ёкшн управлени€ группой получателей
 */
public class FundGroupAction extends OperationalActionBase
{

	private static final String ERROR_FORWARD_NAME = "Status";
	private static final Long LOGIC_ERROR_STATUS = 1L;
	private static final Long OK_STATUS = 0L;

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("create", "create");
		map.put("edit", "edit");
		map.put("remove", "remove");
		return map;
	}
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundGroupForm frm = (FundGroupForm) form;
		FundGroupOperation operation = createOperation(ViewFundGroupOperation.class);
		return createForward(mapping, frm, operation);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundGroupForm frm = (FundGroupForm) form;
		FormProcessor processor = new FormProcessor(new RequestValuesSource(request), ((FundGroupForm) form).getForm(), new StringErrorCollector(), DefaultValidationStrategy.getInstance());
		if (!processor.process())
		{
			frm.setStatus(LOGIC_ERROR_STATUS);
			StringBuffer errorDescription = new StringBuffer();
			for (String error:((List<String>)processor.getErrors()))
			{
				errorDescription.append(error);
			}
			frm.setErrorDescription(errorDescription.toString());
			return mapping.findForward(ERROR_FORWARD_NAME);
		}
		FundGroupOperation operation = createOperation(EditFundGroupOperation.class);
		return createForward(mapping, frm, operation);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundGroupForm frm = (FundGroupForm) form;
		FundGroupOperation operation = createOperation(RemoveFundGroupOperation.class);
		return createForward(mapping, frm, operation);
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FundGroupForm frm = (FundGroupForm) form;
		FormProcessor processor = new FormProcessor(new RequestValuesSource(request), ((FundGroupForm) form).getForm(), new StringErrorCollector(), DefaultValidationStrategy.getInstance());
		if (!processor.process())
		{
			frm.setStatus(LOGIC_ERROR_STATUS);
			StringBuffer errorDescription = new StringBuffer();
			for (String error:(List<String>)processor.getErrors())
			{
				errorDescription.append(error);
			}
			frm.setErrorDescription(errorDescription.toString());
			return mapping.findForward(ERROR_FORWARD_NAME);
		}
		FundGroupOperation operation = createOperation(CreateFundGroupOperation.class);
		return createForward(mapping, frm, operation);
	}

	public ActionForward createForward(ActionMapping mapping, FundGroupForm frm, FundGroupOperation operation) throws Exception
	{
		try
		{
			operation.initialize(frm.getId(), frm.getName(), frm.getPhones());
			frm.setStatus(OK_STATUS);
		}
		catch (BusinessLogicException e)
		{
			frm.setStatus(LOGIC_ERROR_STATUS);
			frm.setErrorDescription(e.getMessage());
			return mapping.findForward(ERROR_FORWARD_NAME);
		}
		updateForm(frm, operation);
		return mapping.findForward(operation.getForwardName());
	}

	private void updateForm(FundGroupForm form, FundGroupOperation operation)
	{
		if (ViewFundGroupOperation.class.isAssignableFrom(operation.getClass()))
		{
			ViewFundGroupOperation op = (ViewFundGroupOperation)operation;
			form.setName(op.getFundGroup().getName());
			form.setPhonesObjectList(op.getFundGroup().getPhones());
		}
	}


}
