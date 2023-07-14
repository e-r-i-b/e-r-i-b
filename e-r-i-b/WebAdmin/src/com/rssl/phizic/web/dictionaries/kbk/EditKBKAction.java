package com.rssl.phizic.web.dictionaries.kbk;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.kbk.DublicateKBKException;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.PaymentType;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.kbk.EditKBKOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.security.AccessControlException;
import java.util.Map;

/**
 * @author akrenev
 * @ created 10.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditKBKAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditKBKOperation operation = createOperation(EditKBKOperation.class);

		Long id = frm.getId();
		if ((id != null) && (id != 0))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected EditEntityOperation createViewOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditKBKOperation operation = createOperation("ViewKBKOperation");

		Long id = frm.getId();
		if ((id != null) && (id != 0))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditKBKForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		KBK kbk = (KBK) entity;
		kbk.setCode((String) data.get("code"));
		kbk.setDescription((String) data.get("description"));
		kbk.setAppointment((String) data.get("appointment"));
		kbk.setPaymentType(PaymentType.valueOf((String) data.get("paymentType")));
		Currency currency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode("RUB");
		kbk.setMinCommission(new Money((BigDecimal) data.get("minCommission"), currency));
		kbk.setMaxCommission(new Money((BigDecimal) data.get("maxCommission"), currency));
		kbk.setRate((BigDecimal) data.get("rate"));
		kbk.setShortName((String) data.get("shortName"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		KBK kbk = (KBK) entity;
		frm.setField("code", kbk.getCode());
		frm.setField("description", kbk.getDescription());
		frm.setField("appointment", kbk.getAppointment());
		frm.setField("paymentType", kbk.getPaymentType());
		frm.setField("shortName", kbk.getShortName());
		Money minCommission = kbk.getMinCommission();
		if (minCommission != null)
		{
			frm.setField("minCommission", minCommission.getDecimal());
		}
		Money maxCommission = kbk.getMaxCommission();
		if (maxCommission != null)
		{
			frm.setField("maxCommission", maxCommission.getDecimal());
		}
		frm.setField("rate", kbk.getRate());
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		EditKBKOperation op = (EditKBKOperation) operation;
		ActionForward actionForward = new ActionForward();
		try
		{
			//Фиксируем данные, введенные пользователем
			addLogParameters(frm);
			op.save();
			actionForward = mapping.findForward(FORWARD_SUCCESS);
		}
		catch(DublicateKBKException ex)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
			saveErrors(currentRequest(), msgs);
		    actionForward = mapping.findForward(FORWARD_START);
		}
		return actionForward;
	}
}
