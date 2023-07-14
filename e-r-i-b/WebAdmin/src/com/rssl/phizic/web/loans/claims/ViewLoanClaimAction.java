package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

/**
 * @author gladishev
 * @ created 04.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class ViewLoanClaimAction extends ViewActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.execute", "process");
		map.put("button.refuse", "refuse");
		map.put("button.accept", "accept");
		map.put("button.completion", "completion");
		map.put("button.archive", "archive");

		return map;
	}

	public ActionForward refuse(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewLoanClaimForm frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation operation = (ProcessDocumentOperation) createViewEntityOperation(frm);
		operation.refuse(null);

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewLoanClaimForm frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation operation = (ProcessDocumentOperation) createViewEntityOperation(frm);
		operation.accept();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward completion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewLoanClaimForm frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation operation = (ProcessDocumentOperation) createViewEntityOperation(frm);
		operation.completion();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}

	public ActionForward archive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewLoanClaimForm frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation operation = (ProcessDocumentOperation) createViewEntityOperation(frm);
		operation.sendInArchive();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		ViewLoanClaimForm     frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation op  = (ProcessDocumentOperation) operation;

		Metadata metadata  = op.getMetadata();
		Source formData;
		try {
			formData = op.createFormData();
		} catch (TransformerException ex) {
			throw new BusinessException(ex);
		}

		XmlConverter converter = metadata.createConverter("html");

		converter.setParameter("mode", "view");
		converter.setParameter("personAvailable", PersonContext.isAvailable());
		converter.setParameter("webRoot", WebContext.getCurrentRequest().getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setData(formData);

		frm.setDocument(op.getEntity());
		frm.setHtml(converter.convert());
		frm.setComment(op.checkBeforeSetComment());
		frm.setArchive(op.checkBeforeSendInArchive());
		frm.setPrintDocuments(op.checkBeforePrintDocuments());
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		return operation;
	}
}
