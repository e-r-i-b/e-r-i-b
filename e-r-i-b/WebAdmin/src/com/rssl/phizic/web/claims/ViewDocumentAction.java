package com.rssl.phizic.web.claims;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.operations.payment.SpecifyStatusDocumentOperation;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentActionBase;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kidyaev
 * @ created 14.12.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ViewDocumentAction extends ViewDocumentActionBase
{
	private static final String FORWARD_REFUSE = "Refuse";
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.execute", "process");
		map.put("button.accept",  "accept");
		map.put("button.refuse",  "refuse");
		map.put("button.send",    "send");
		map.put("button.completion", "completion");
		map.put("button.comment", "comment");
		map.put("button.archive", "archive");
		map.put("button.specify", "specify");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewDocumentOperation operation = null;
		if (checkAccess(ViewDocumentOperation.class, "ViewPaymentList"))
			operation = createOperation(ViewDocumentOperation.class, "ViewPaymentList");
		else
			operation = createOperation(ViewDocumentOperation.class, "ViewPaymentListUseClientForm");

		operation.initialize(new ExistingSource(frm.getId(), new NullDocumentValidator()));
		return operation;
	}

	public ActionForward process(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);

		addLogParameters(new BeanLogParemetersReader("Первоначальный статус", operation.getEntity().getState()));
		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		try
		{
			operation.execute();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateFormData(createViewEntityOperation(frm), frm);

			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		try
		{
			operation.accept();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateFormData(createViewEntityOperation(frm), frm);

			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);

		ActionForward forward = new ActionForward(mapping.findForward(FORWARD_REFUSE));
		forward.setPath(forward.getPath() + "?id=" + operation.getEntity().getId());
		return forward;
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		try
		{
			operation.send();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateFormData(createViewEntityOperation(frm), frm);

			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_CLOSE);
	}

	protected ProcessDocumentOperation getOperation(ViewDocumentForm frm) throws BusinessException, BusinessLogicException
	{
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(new ExistingSource(frm.getId(), new NullDocumentValidator()));

		return operation;
	}

	public ActionForward completion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		try
		{
			operation.completion();
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e);
			updateFormData(createViewEntityOperation(frm), frm);

			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward archive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ProcessDocumentOperation operation = getOperation(frm);
		operation.sendInArchive();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward specify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewDocumentForm frm = (ViewDocumentForm) form;
		ExistingSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());

		SpecifyStatusDocumentOperation operation = createOperation("SpecifyStatusDocumentOperation");
		operation.initialize(source.getDocument());

		try
		{
			operation.specify();
		}
		catch (LogicException e)
		{
			saveMessage(request, e.getMessage());
			updateFormData(createViewEntityOperation(frm), frm);
			return mapping.findForward(FORWARD_START);
		}

		return mapping.findForward(FORWARD_CLOSE);
	}

	private void updateUserDataForERKC(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ViewDocumentOperation.class, "ViewPaymentList"))
			return;

		Map<String, Object> userInfo = ERKCEmployeeUtil.getUserInfo();

		if (userInfo.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить просмотр записей, перейдите к журналу из анкеты клиента.");
		}

		ERKCEmployeeUtil.addUserDataForERKC(form, userInfo);
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation, form);

		updateUserDataForERKC(form);
	}

}
