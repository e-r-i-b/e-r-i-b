package com.rssl.phizic.web.deposits;

import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.deposits.DepositHtmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.deposits.GetDepositProductsListBankOperation;
import com.rssl.phizic.operations.deposits.RemoveDepositProductOperation;
import com.rssl.phizic.operations.deposits.retail.EditDepositProductOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.utils.xml.StaticResolver;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: krenev $
 * @ $Revision: 12186 $
 */

@SuppressWarnings({"JavaDoc"})
public class ListDepositProductsBankAction extends ListActionBase
{
	private static final String FORWARD_EDIT  = "Edit";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.replic", "replic");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDepositProductsListBankOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveDepositProductOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException
	{
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		GetDepositProductsListBankOperation op = (GetDepositProductsListBankOperation) operation;
		HttpServletRequest request = currentRequest();

		Templates templates = null;

		try
		{
			templates = getTemplate(op);
		}
		catch (TransformerConfigurationException e)
		{
			throw new BusinessException(e);
		}

		XmlConverter converter = new DepositHtmlConverter(templates);
		ListDepositProductsBankForm frm = (ListDepositProductsBankForm) form;
		String departmentId = (String) frm.getFilters().get("departmentId");
		if (departmentId == null || departmentId.equals("all"))
		{
			converter.setData(op.getListSource(null));
		}
		else
		{
			converter.setData(op.getListSource(departmentId));
		}
		converter.setParameter("webRoot", request.getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));
		converter.setParameter("detailsUrl", request.getContextPath() + getCurrentMapping().findForward(FORWARD_EDIT).getPath() + "?id=");
                 		
		if (frm.getFilters().get("departmentName") == null)
		{
			frm.setFilter("departmentName", "Все");
			frm.setFilter("departmentId", "all");
		}
		String html = converter.convert().toString();
		frm.setListHtml(html);
	}

		private Templates getTemplate(GetDepositProductsListBankOperation operation) throws TransformerConfigurationException, BusinessException
	{
		Source templateSource = operation.getTemplateSource();
		TransformerFactory fact = TransformerFactory.newInstance();
		fact.setURIResolver(new StaticResolver());
		return fact.newTemplates(templateSource);
	}

	public ActionForward replic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListDepositProductsBankForm frm = (ListDepositProductsBankForm) form;
		MapValuesSource valuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, ListDepositProductsBankForm.FORM);
		if (processor.process())
		{
			doReplic(processor.getResult(), frm, mapping);
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return sendRedirectToSelf(request);
	}

	private void doReplic(Map<String, Object> result, ListDepositProductsBankForm frm, ActionMapping mapping) throws BusinessException, BusinessLogicException, TransformerException, GateException, GateLogicException
	{
		EditDepositProductOperation operation = createOperation(EditDepositProductOperation.class);
		String[] departmentIds = ((String)result.get("selectedDeps")).split(" ");
		List<String> deposit = new ArrayList<String>();
		for (String departmentId: departmentIds)
		{
			operation.replice(Long.valueOf(departmentId), deposit);
		}
	}
}