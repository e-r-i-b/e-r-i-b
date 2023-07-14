package com.rssl.phizic.web.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.forms.UpdateFormOperation;
import com.rssl.phizic.operations.templates.TemplateDocumentsListOperation;
import com.rssl.phizic.operations.templates.TemplatePackageListOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 24.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPrintFormAction extends EditActionBase//TODO operationQuery??
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditPrintFormForm frm = (EditPrintFormForm) form;
		UpdateFormOperation operation = createOperation(UpdateFormOperation.class,"PaymentFormManagement");
		operation.initialize(frm.getFormName());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity) {}

	protected void updateEntity(Object entity, Map<String, Object> data) {}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, BusinessLogicException, DataAccessException
	{
		EditPrintFormForm   frm = (EditPrintFormForm) form;
		UpdateFormOperation op  = (UpdateFormOperation) operation;

		MetadataBean paymentForm = op.getEntity();

	    TemplatePackageListOperation packageListOperation = createOperation(TemplatePackageListOperation.class);
		List allPackages     =         packageListOperation.createQuery("list").executeList();
		frm.setPackages(allPackages);

		List<PackageTemplate> packages = paymentForm.getPackages();
		List<String> l =new ArrayList<String>();
		for (Object tmp : packages)
		   l.add(((PackageTemplate)tmp).getId().toString());

		frm.setSelectedPackages(l.toArray(new String[l.size()]));

		TemplateDocumentsListOperation templateListOperation = createOperation(TemplateDocumentsListOperation.class,"ShowTemplatesDocuments");
		List allTemplates     = templateListOperation.createQuery("list").executeList();
		frm.setTemplates(allTemplates);

		List<DocTemplate> templates = paymentForm.getTemplates();
		l=new ArrayList<String>();

		for (Object tmp : templates)
		   l.add(((DocTemplate)tmp).getId().toString());

		frm.setSelectedTemplates(l.toArray(new String[l.size()]));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult)
	{
		EditPrintFormForm   frm = (EditPrintFormForm) form;
		UpdateFormOperation op  = (UpdateFormOperation) editOperation;

		MetadataBean paymentForm = op.getEntity();

		DocTemplate docTemplate = null;
		List<DocTemplate> docTmp = new ArrayList<DocTemplate>();
		String[] selectedTemplates = frm.getSelectedTemplates();
		for (String id : selectedTemplates)
		{
			docTemplate = new DocTemplate();
			docTemplate.setId(Long.valueOf(id));
		    docTmp.add(docTemplate);
		}
		paymentForm.setTemplates(docTmp);

		PackageTemplate pacTemplate = null;
		List<PackageTemplate> pacTmp = new ArrayList<PackageTemplate>();
		String[] selectedPackages = frm.getSelectedPackages();
		for (String id : selectedPackages)
		{
			pacTemplate = new PackageTemplate();
			pacTemplate.setId(Long.valueOf(id));
		    pacTmp.add(pacTemplate);
		}
		paymentForm.setPackages(pacTmp);
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
