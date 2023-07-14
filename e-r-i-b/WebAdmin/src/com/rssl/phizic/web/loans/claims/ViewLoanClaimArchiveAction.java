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
 * @ created 06.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class ViewLoanClaimArchiveAction extends ViewActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.fromarchive", "returnFromArchive");
		return map;
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

		StringBuffer buffer = converter.convert();

		frm.setDocument(op.getEntity());
		frm.setHtml(buffer);
		frm.setArchive(op.checkBeforeSendInArchive());
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ExistingSource source = new ExistingSource(frm.getId(), new NullDocumentValidator());
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		operation.initialize(source);

		return operation;
	}

	public ActionForward returnFromArchive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewLoanClaimForm frm = (ViewLoanClaimForm) form;
		ProcessDocumentOperation operation = (ProcessDocumentOperation) createViewEntityOperation(frm);
		operation.returnFromArchive();

		addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getEntity()));

		return mapping.findForward(FORWARD_CLOSE);
	}
}
