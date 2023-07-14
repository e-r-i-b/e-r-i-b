package com.rssl.phizic.web.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.common.forms.Form;
import com.rssl.phizic.business.xml.CommonXmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.deposits.DublicateDepositProductNameException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.operations.deposits.retail.EditDepositProductOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerConfigurationException;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: osminin $
 * @ $Revision: 41239 $
 */

@SuppressWarnings({"JavaDoc"}) public class EditDepositProductBankAction extends EditActionBase
{
	private static final String FORWARD_LIST  = "List";

	protected Form getEditForm(EditFormBase frm)
	{
		return EditDepositProductBankForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase form, Object entity) {}

	protected void updateFormAdditionalData(EditFormBase form, EditEntityOperation operation) throws TransformerConfigurationException, BusinessException
	{
		EditDepositProductBankForm  frm = (EditDepositProductBankForm)  form;
		EditDepositProductOperation op  = (EditDepositProductOperation) operation;

		XmlConverter converter = new CommonXmlConverter(getTemplate(op));
		converter.setData(op.getProductSource());
		converter.setParameter("webRoot", currentRequest().getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setParameter("departmentId", op.getDepartmentId());

		String html = converter.convert().toString();

		frm.setHtml(html);
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		DepositProduct product = (DepositProduct) entity;

		product.setName((String) data.get("name"));
		product.setProductDescription((String) data.get("productDescription"));
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditDepositProductOperation operation = createOperation(EditDepositProductOperation.class);

		Long id = frm.getId();
		if(id != null && id != 0)
			operation.initialize(id);
		else
		{
			EditDepositProductBankForm form = (EditDepositProductBankForm) frm;
			operation.ininitializeNew(form.getDepartmentId());
		}
		return operation;
	}

	private Templates getTemplate(EditDepositProductOperation operation) throws TransformerConfigurationException, BusinessException
	{
		Source templateSource = operation.getTemplateSource();
		TransformerFactory fact = TransformerFactory.newInstance();
		fact.setURIResolver(new StaticResolver());
		return fact.newTemplates(templateSource);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult)
	{
		EditDepositProductOperation op = (EditDepositProductOperation) editOperation;

		String[] ids  = currentRequest().getParameterValues("field(selectedIds)"); // берем из реквеста так как в формах нет поддержки массивов
		op.setRetailDeposits(Arrays.asList(ids));
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditDepositProductOperation op = (EditDepositProductOperation) operation;
		ActionForward actionForward = new ActionForward();

		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			op.save();
			actionForward = mapping.findForward(FORWARD_LIST);
		}
		catch(DublicateDepositProductNameException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("Депозитный продукт с таким именем уже существует", false));
			saveErrors(currentRequest(), msgs);
		    actionForward = mapping.findForward(FORWARD_START);
		}

		return actionForward;
	}
}