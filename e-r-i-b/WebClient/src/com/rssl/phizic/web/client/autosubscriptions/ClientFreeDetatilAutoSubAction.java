package com.rssl.phizic.web.client.autosubscriptions;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateFreeDetailAutoSubOperation;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.payments.forms.CreateFreeDetailAutoSubAction;
import com.rssl.phizic.web.actions.payments.forms.CreateFreeDetailAutoSubForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ClientFreeDetatilAutoSubAction extends CreateFreeDetailAutoSubAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.makeRecipientAutoSub", "makeRecipientAutoSubscription");
		map.remove("button.changeConditions");
		map.remove("afterAccountOpening");
		return map;
	}

	protected CreateFreeDetailAutoSubOperation createEditOperation(CreateFreeDetailAutoSubForm form) throws BusinessException, BusinessLogicException
	{
		CreateFreeDetailAutoSubOperation operation = createOperation(CreateFreeDetailAutoSubOperation.class, "ClientFreeDetailAutoSubManagement");
		Long paymentId = form.getId();
		if (paymentId != null && paymentId > 0)
		{
			//если пришел идентфикатор платежа
			operation.initialize(form.getFromResource(), paymentId, true, true);
		}
		else
		{
			//Ничего не пришло - инициализируем операцию только источником списания.
			operation.initialize(form.getFromResource());
		}
		return operation;
	}

	protected CreateESBAutoPayOperation createESBAutoPayOperation(DocumentSource source) throws BusinessException, BusinessLogicException
	{
		CreateESBAutoPayOperation esbAutoPayOperation = createOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
		esbAutoPayOperation.initialize(source, null, ObjectEvent.CLIENT_EVENT_TYPE);
		return esbAutoPayOperation;
	}

	/**
	 * Переход на форму оформления автоплатежа в адрес найденого в БД ПУ, предоставляющего автоплатеж.
	 * @param mapping - текущий маппинг
	 * @param form - форм
	 * @return форвард на переход создания автоплатежа в пользу найденого ПУ.
	 */
	public ActionForward makeRecipientAutoSubscription(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException
	{
		CreateFreeDetailAutoSubForm frm = (CreateFreeDetailAutoSubForm) form;
		String receiverId = (String)frm.getField("receiverId");
		if (!StringHelper.isEmpty(receiverId))
		{
			try
			{
				return makeRecipientAutoSubscription(Long.valueOf(receiverId), frm.getFromResource());
			}
			catch(NumberFormatException e)
			{
				throw new BusinessException("Некоррекный формат идентификатора поставщика услуг.",e);
			}
		}
		throw new BusinessException("Неверный идентификатор поставщика услуг.");
	}

	protected ActionForward makeRecipientAutoSubscription(Long providerId, String fromResource) throws BusinessException
	{
		ActionForward forward = getCurrentMapping().findForward(FORWARD_CREATE_RECIPIENT_AUTOSUB);
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameter("createLongOffer", "true");
		urlBuilder.addParameter("fromResource", fromResource);
		urlBuilder.addParameter("recipient", providerId.toString());
		return new ActionForward(urlBuilder.getUrl(), true);
	}

	protected ActionForward findRecipients(CreateFreeDetailAutoSubForm frm, CreateFreeDetailAutoSubOperation operation, HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();	
	}
}
