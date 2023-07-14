package com.rssl.phizic.web.common.mobile.finances.operations;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.AddCardOperationOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Добавление новой операции
 * @author lepihina
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AddFinanceOperationsMobileAction extends EditActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("save", "save");
		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		AddCardOperationOperation operation = createOperation(AddCardOperationOperation.class, "AddOperationsService");
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return AddFinanceOperationsMobileForm.EDIT_FORM;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		throw new UnsupportedOperationException();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CardOperation operation = (CardOperation)entity;
		operation.setDescription((String) data.get("operationName"));
		operation.setNationalAmount(((BigDecimal) data.get("operationAmount")));
		operation.setDate(DateHelper.toCalendar((Date) data.get("operationDate")));
		operation.setOperationType(OperationType.OTHER);
		operation.setLoadDate(Calendar.getInstance());
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation o) throws Exception
	{
		AddFinanceOperationsMobileForm form = (AddFinanceOperationsMobileForm) frm;
		Map<String, String> source = new HashMap<String, String>();
		source.put("operationName", form.getOperationName());
		source.put("operationAmount", form.getOperationAmount());
		source.put("operationDate", form.getOperationDate());
		source.put("operationCategoryId", form.getOperationCategoryId());

		return new MapValuesSource(source);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		AddCardOperationOperation operation = (AddCardOperationOperation) editOperation;
		operation.setCategory((Long)validationResult.get("operationCategoryId"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
