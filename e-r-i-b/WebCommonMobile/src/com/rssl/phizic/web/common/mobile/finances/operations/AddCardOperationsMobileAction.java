package com.rssl.phizic.web.common.mobile.finances.operations;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.OperationType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.finances.AddCardOperationMobileOperation;
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
 * Добавление карточной операции из мапи
 * @author Jatsky
 * @ created 05.11.14
 * @ $Author$
 * @ $Revision$
 */

public class AddCardOperationsMobileAction extends EditActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("save", "save");
		return map;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		AddCardOperationMobileOperation operation = createOperation(AddCardOperationMobileOperation.class, "AddViewCardOperationsMobileService");
		operation.initialize();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return AddCardOperationsMobileForm.EDIT_FORM;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		throw new UnsupportedOperationException();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CardOperation operation = (CardOperation) entity;
		operation.setDescription((String) data.get("operationName"));
		operation.setNationalAmount(((BigDecimal) data.get("operationAmount")));
		operation.setDate(DateHelper.toCalendar((Date) data.get("operationDate")));
		operation.setOperationType(OperationType.valueOf((String) data.get("operationType")));
		operation.setLoadDate(Calendar.getInstance());
		operation.setCash((Boolean) data.get("cash"));
		operation.setHidden((Boolean) data.get("hidden"));
	}

	protected FieldValuesSource getFormProcessorValueSource(EditFormBase frm, EditEntityOperation o) throws Exception
	{
		AddCardOperationsMobileForm form = (AddCardOperationsMobileForm) frm;
		Map<String, String> source = new HashMap<String, String>();
		source.put("operationName", form.getOperationName());
		source.put("operationAmount", form.getOperationAmount());
		source.put("operationDate", form.getOperationDate());
		source.put("operationCategoryId", form.getOperationCategoryId());
		source.put("loginId", form.getLoginId());
		source.put("operationType", form.getOperationType());
		source.put("pushUID", form.getPushUID());
		source.put("parentPushUID", form.getParentPushUID());
		source.put("cardNumber", form.getCardNumber());
		source.put("operationAuthCode", form.getOperationAuthCode());
		source.put("operationTypeWAY", form.getOperationTypeWAY());
		source.put("cash", form.getCash());
		source.put("hidden", form.getHidden());

		return new MapValuesSource(source);
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		AddCardOperationMobileOperation operation = (AddCardOperationMobileOperation) editOperation;
		operation.setCategory((Long) validationResult.get("operationCategoryId"));
		operation.setPushUID((Long) validationResult.get("pushUID"));
		operation.setCardNumber((String) validationResult.get("cardNumber"));
		operation.setLoginId((Long) validationResult.get("loginId"));
		operation.setParentPushUID((Long) validationResult.get("parentPushUID"));
		operation.setAuthCode((String) validationResult.get("operationAuthCode"));
		operation.setOperationTypeWay((String) validationResult.get("operationTypeWAY"));
		operation.setLoadDateMAPI(Calendar.getInstance());
		operation.setCategoryChange(false);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	@Override public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IPSConfig ipsConfig = ConfigFactory.getConfig(IPSConfig.class);
		if (ipsConfig.getCardOperationLifetime() == 0 || !checkAccess(AddCardOperationMobileOperation.class, "AddViewCardOperationsMobileService"))
			throw new BusinessLogicException("У Вас нет прав для использования данного функционала");
		return super.save(mapping, form, request, response);
	}
}
