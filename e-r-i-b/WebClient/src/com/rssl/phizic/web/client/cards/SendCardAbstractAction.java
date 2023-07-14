package com.rssl.phizic.web.client.cards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bankroll.SendedAbstract;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.card.AddSendedAbstractsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.exception.FormProcessorException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author egorova
 * @ created 07.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class SendCardAbstractAction extends EditActionBase
{
	private static final String PERIOD_TYPE = "period";
	private static final String PERIOD_TYPE_MONTH = "month";
	private static final String AS_VYPISKA = "AS_VYPISKA";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("button.send", "save");
		keyMap.put("button.history", "history");
		return keyMap;
	}

	protected void updateForm(EditFormBase form, Object entity) throws BusinessException, BusinessLogicException
	{

		SendCardAbstractForm frm = (SendCardAbstractForm) form;
		SendedAbstract sendedAbstract = (SendedAbstract) entity;
		frm.setCardLink(sendedAbstract.getCardLink());
		//значения по умолчанию
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setFilter(SendCardAbstractForm.E_MAIL_FIELD_NAME, person.getEmail());
		Calendar toDate = DateHelper.getCurrentDate();
		Calendar fromDate = DateHelper.toCalendar(DateHelper.add(DateHelper.getCurrentDate().getTime(), 0, -1, 0));
		frm.setFilter(SendCardAbstractForm.FROM_DATE_FIELD_NAME, fromDate.getTime());
		frm.setFilter(SendCardAbstractForm.TO_DATE_FIELD_NAME, toDate.getTime());
		frm.setFilter(SendCardAbstractForm.TYPE_FIELD_NAME, PERIOD_TYPE_MONTH);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		AddSendedAbstractsOperation sendedAbstractsOperation = (AddSendedAbstractsOperation) operation;
		SendCardAbstractForm form = (SendCardAbstractForm) frm;

		Query query = sendedAbstractsOperation.createQuery("list");
		query.setParameter("cardLink_id", sendedAbstractsOperation.getEntity().getCardLink().getId());

		List<SendedAbstract> sendedAbstract = query.executeList();
		form.setSendedAbstract(sendedAbstract);

		form.setAsVypiskaActive(sendedAbstractsOperation.isAsVypiskaActive());
		form.setFilter(SendCardAbstractForm.FORMAT_FIELD_NAME, sendedAbstractsOperation.getFormat());
		form.setFilter(SendCardAbstractForm.LANG_FIELD_NAME, sendedAbstractsOperation.getLang());

		form.setActivePDF_RUS(sendedAbstractsOperation.isActivePDF_RUS());
		form.setActivePDF_ENG(sendedAbstractsOperation.isActivePDF_ENG());
		form.setActiveHTML_RUS(sendedAbstractsOperation.isActiveHTML_RUS());
		form.setActiveHTML_ENG(sendedAbstractsOperation.isActiveHTML_ENG());

		form.setFilter(SendCardAbstractForm.FILL_REPORT_FIELD_NAME, "ER");
	}

	protected FormProcessor<ActionMessages, ?> getFormProcessor(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		FieldValuesSource valuesSource = new MapValuesSource(frm.getFilters());
		AddSendedAbstractsOperation op = (AddSendedAbstractsOperation) operation;
		((SendCardAbstractForm)frm).setAsVypiskaActive(op.isAsVypiskaActive());
		return createFormProcessor(valuesSource, getEditForm(frm));
	}

	protected Map<String, Object> checkFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		Map<String, Object> result = super.checkFormData(frm, operation);

		AddSendedAbstractsOperation op = (AddSendedAbstractsOperation) operation;
		result.put(AS_VYPISKA, op.isAsVypiskaActive());

		return result;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		SendedAbstract sendedAbstract = (SendedAbstract) entity;
		Calendar fromDate;
		Calendar toDate;
		String period = (String)data.get(SendCardAbstractForm.TYPE_FIELD_NAME);

		if ((Boolean)data.get(AS_VYPISKA))
		{
			fromDate = DateHelper.toCalendar((Date) data.get(SendCardAbstractForm.FROM_DATE_FIELD_NAME));
			toDate = DateHelper.toCalendar((Date) data.get(SendCardAbstractForm.TO_DATE_FIELD_NAME));
		}
		else if (PERIOD_TYPE.equals(period))
		{
			fromDate = DateHelper.toCalendar((Date) data.get(SendCardAbstractForm.FROM_DATE_FIELD_NAME));
			toDate = DateHelper.toCalendar((Date) data.get(SendCardAbstractForm.TO_DATE_FIELD_NAME));
		}
		else
		{
			toDate = DateHelper.getCurrentDate();
			fromDate = DateHelper.getCurrentDate();
			if(PERIOD_TYPE_MONTH.equals(period))
				fromDate.add(Calendar.MONTH, -1);
			else
				fromDate.add(Calendar.WEEK_OF_MONTH, -1);
		}

		sendedAbstract.setFromDate(fromDate);
		sendedAbstract.setToDate(toDate);
		sendedAbstract.setSendedDate(DateHelper.getCurrentDate());
	}

	//Посылаем email
	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		//Фиксируем данные, введенные пользователем
		addLogParameters(frm);
		operation.save();

		try
		{
			((AddSendedAbstractsOperation) operation).sendReport((String) frm.getFilter(SendCardAbstractForm.E_MAIL_FIELD_NAME),
								(String)frm.getFilter(SendCardAbstractForm.FORMAT_FIELD_NAME),
								(String)frm.getFilter(SendCardAbstractForm.LANG_FIELD_NAME),
								(String)frm.getFilter(SendCardAbstractForm.FILL_REPORT_FIELD_NAME));
		}
		catch (BusinessTimeOutException e)
		{
			if (!((AddSendedAbstractsOperation) operation).isAsVypiskaActive())
				throw e;

			((SendCardAbstractForm)frm).setTimeoutError(true);
		}
		catch (InactiveExternalSystemException e)
		{
			if (!((AddSendedAbstractsOperation) operation).isAsVypiskaActive())
				throw e;
			((SendCardAbstractForm)frm).setInactiveESError(e.getMessage());
		}
		catch (Exception e)
		{
			if (!((AddSendedAbstractsOperation) operation).isAsVypiskaActive())
							throw e;

			((SendCardAbstractForm)frm).setOtherError(true);
		}

		return createSaveActionForward(operation, frm);
	}

	//Запрос истории
	public ActionForward history(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AddSendedAbstractsOperation op = createOperation(AddSendedAbstractsOperation.class);
		SendCardAbstractForm frm = (SendCardAbstractForm) form;
		frm.setHistory(op.getHistory(frm.getId()));

		return mapping.findForward("ShowHistory");
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((SendCardAbstractForm)frm).isAsVypiskaActive() ? SendCardAbstractForm.EDIT_FORM_EXT : SendCardAbstractForm.EDIT_FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		AddSendedAbstractsOperation operation = createOperation("AddSendedAbstractsOperation", "Abstract");
		operation.initialize(frm.getId());
		return operation;
	}

	protected ActionForward createSaveActionForward(EditEntityOperation operation, EditFormBase frm) throws BusinessException
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
