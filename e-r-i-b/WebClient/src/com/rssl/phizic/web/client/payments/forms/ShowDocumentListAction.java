package com.rssl.phizic.web.client.payments.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;
import com.rssl.phizic.operations.payment.GetMetaPaymentListOperation;
import com.rssl.phizic.operations.payment.RemoveDocumentOperation;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;

/**
 * @author Kosyakov
 * @ created 12.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
@SuppressWarnings({"JavaDoc"})
public class ShowDocumentListAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.withdraw", "withdraw");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		String formName = frm.getName();
		GetMetaPaymentListOperation operation = createOperation(GetMetaPaymentListOperation.class, formName);
		operation.initialize(formName);
		return operation;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		return new RequestValuesSource(currentRequest());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		GetMetaPaymentListOperation op = (GetMetaPaymentListOperation) operation;

		StringBuffer stringBuffer = createListHtml(op, filterParams);
		frm.setListHtml(stringBuffer);
		updateFormAdditionalData(form, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		GetMetaPaymentListOperation op = (GetMetaPaymentListOperation) operation;
		Metadata metadata = op.getMetadata();
		Form filterForm = metadata.getFilterForm();
		frm.setTitle(filterForm.getDescription());
		StringBuffer filterHtml = createFilterHtml(frm, metadata);
		frm.setFilterHtml(filterHtml);
		frm.setMetadata(metadata);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		GetMetaPaymentListOperation op = (GetMetaPaymentListOperation) operation;
		Metadata metadata = op.getMetadata();
		return metadata.getFilterForm();
	}

	private StringBuffer createListHtml(GetMetaPaymentListOperation op, Map<String, Object> result) throws BusinessException
	{
		Metadata metadata = op.getMetadata();
		storeAbsentFields(metadata.getFilterForm(), result);
		result.put("loginId", op.getLoginId());

		HttpServletRequest request = currentRequest();
		if (result.get("state") != null)
		{
			String[] resultString = result.get("state").toString().split(",");
			result.put("state", resultString);
		}

		FilteredEntityListSource listSource = metadata.getListSource();
		Source listData = listSource.getList(result, new String[]{});
		XmlConverter converter = metadata.createConverter("list-html");
		converter.setData(listData);
		converter.setParameter("webRoot", request.getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));

		return converter.convert();
	}

	private void storeAbsentFields(Form filterForm, Map<String, Object> result)
	{
		for (Field field : filterForm.getFields())
		{
			String name = field.getName();
			if (!result.containsKey(name)){
				result.put(name, null);
			}
		}
	}

	private StringBuffer createFilterHtml(ShowMetaPaymentListForm frm, Metadata metadata) throws BusinessException
	{
		Form metaForm = metadata.getFilterForm();
		FilteredEntityListSource listSource = metadata.getListSource();
		FieldValuesSource valuesSource = getFilterValuesSource(frm);

		Map<String, Object> rawValues = createFieldsMap(metaForm, valuesSource);

		Source filterData = listSource.getFilter(rawValues);
		XmlConverter converter = metadata.createConverter("list-filter-html");
		converter.setData(filterData);
		converter.setParameter("webRoot", currentRequest().getContextPath());
		converter.setParameter("resourceRoot", currentServletContext().getInitParameter("resourcesRealPath"));

		return converter.convert();
	}

	private Map<String, Object> createFieldsMap(Form metaForm, FieldValuesSource valuesSource)
	{
		List<Field> fields = metaForm.getFields();
		Map<String, Object> rawValues = new HashMap<String, Object>();
		for (Field field : fields)
		{
			rawValues.put(field.getName(), valuesSource.getValue(field.getName()));
		}
		return rawValues;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		String formName = frm.getName();
		return createOperation(RemoveDocumentOperation.class, formName);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		RemoveDocumentOperation op = (RemoveDocumentOperation)operation;
		ActionMessages msgs = new ActionMessages();
		try
		{
			DocumentSource source = new ExistingSource(id, new IsOwnDocumentValidator());
			op.initialize(source);
			op.remove();
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		String formName = frm.getName();
		ViewDocumentOperation operation = createOperation(ViewDocumentOperation.class, formName);

		Long selectedId = frm.getSelectedId();
		ExistingSource source = new ExistingSource(selectedId, new IsOwnDocumentValidator());
		operation.initialize(source);
		if (!operation.canWithdraw())
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выбранный документ нельзя отозвать", false));
			saveErrors(request, actionErrors);
			return start(mapping, form, request, response);
		}
		return new ActionForward("/private/payments/add.do" + "?form=RecallPayment&parentId=" + selectedId);
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form)
	{
		ShowMetaPaymentListForm frm = (ShowMetaPaymentListForm) form;
		return mapping.getPath() + "/" + frm.getName();
	}
}
