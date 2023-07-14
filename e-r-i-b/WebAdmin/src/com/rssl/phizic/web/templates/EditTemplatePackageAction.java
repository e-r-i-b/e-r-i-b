package com.rssl.phizic.web.templates;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.DublicatePackageNameException;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.templates.EditTemplatePackageOperation;
import com.rssl.phizic.operations.templates.TemplateDocumentsListOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 16:43:49
 * To change this template use File | Settings | File Templates.
 */
public class EditTemplatePackageAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditTemplatePackageForm frm = (EditTemplatePackageForm) form;

		EditTemplatePackageOperation operation = createOperation(EditTemplatePackageOperation.class);
		operation.initialize(frm.getId());
		
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		PackageTemplate packageTemplate = (PackageTemplate) entity;
		if (packageTemplate != null)
		{
			frm.setField("name", packageTemplate.getName());
			frm.setField("description", packageTemplate.getDescription());
		}		
	}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws BusinessException, BusinessLogicException
	{
		EditTemplatePackageForm frm = (EditTemplatePackageForm) form;
		EditTemplatePackageOperation op = (EditTemplatePackageOperation) operation;

		List docTemplate = op.getEntity().getTemplates();
		List<String> l = new ArrayList<String>();

		for (Object tmp : docTemplate)
		   l.add(((DocTemplate)tmp).getId().toString());

		frm.setSelectedIds(l.toArray(new String[l.size()]));

		TemplateDocumentsListOperation listOperation = createOperation(TemplateDocumentsListOperation.class);
		try
		{
			frm.setTemplates(listOperation.createQuery("list").executeList());
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditTemplatePackageForm.PACKAGE_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		PackageTemplate packageTemplate = (PackageTemplate) entity;

		packageTemplate.setName((String) data.get("name"));
		packageTemplate.setDescription((String) data.get("description"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult)
	{
		EditTemplatePackageForm frm = (EditTemplatePackageForm) form;
		EditTemplatePackageOperation op = (EditTemplatePackageOperation) editOperation;

		PackageTemplate packageTemplate = op.getEntity();
		packageTemplate.setTemplates(frm.getTemplates());

		String[] selectedIds = frm.getSelectedIds();
		DocTemplate docTemplate = null;
		List<DocTemplate> tmp = new ArrayList<DocTemplate>();
		for (String id : selectedIds)
		{
			docTemplate = new DocTemplate();
			docTemplate.setId(Long.valueOf(id));
		    tmp.add(docTemplate);
		}

		packageTemplate.setTemplates(tmp);
		op.setPackageTemplate(packageTemplate);
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doSave(operation, mapping, frm);
		}
		catch(DublicatePackageNameException ex)
	    {

			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Пакет с таким именем уже существует",false));
		    saveErrors(currentRequest(), msgs);

		    return mapping.findForward(FORWARD_START);
	   }
	}
}
