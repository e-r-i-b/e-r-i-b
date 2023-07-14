package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.profile.addressbook.ContactHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.operations.payment.FindMBClientByContactOperation;
import com.rssl.phizic.operations.payment.FindMBClientOperation;
import com.rssl.phizic.operations.userprofile.addressbook.GetContactOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * получение контакта из АК текущего клиента по номеру телефона
 * @author gladishev
 * @ created 09.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class AsyncGetContactInfoAction extends OperationalActionBase
{
	private static final UserCountersService userCountersService = new UserCountersService();

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetContactInfoForm frm = (GetContactInfoForm) form;
		try
		{
			if (StringHelper.isNotEmpty(frm.getPhone()))
			{
				boolean isContact = false;
				GetContactOperation getContactOperation=null;
				try
				{
					getContactOperation = createOperation(GetContactOperation.class);
					getContactOperation.initialize(frm.getPhone());
					frm.setContact(getContactOperation.getEntity());
					isContact = getContactOperation.getEntity() != null;
				}
				catch (AccessControlException ignore){}

				if (!RurPayment.YANDEX_WALLET_TRANSFER_TYPE_VALUE.equals(frm.getPaymentType()))
				{
					//если контакт есть в адресной книге, не увеличиваем счетчик
					if (isContact || userCountersService.increment(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
					{
						FindMBClientOperation operation = createOperation(FindMBClientOperation.class);
						operation.initialize(frm.getPhone());
						Pair<Client,Card> entity = operation.getEntity();
						frm.setUserInfo(entity);
						if (getContactOperation != null)
							ContactHelper.updateSberbankClient(entity, getContactOperation.getEntity());
						if (entity != null && !isContact)
							frm.setReceiverAvatarPath(operation.searchAvatar());

					}
					else
						frm.setLimitExceeded(true);
				}
			}
			else if (StringHelper.isNotEmpty(frm.getContactId()))
			{
				FindMBClientByContactOperation operation = createOperation(FindMBClientByContactOperation.class);
				operation.initialize(frm.getContactId());
				frm.setUserInfo(operation.getEntity());
				ContactHelper.updateSberbankClient(operation.getEntity(), operation.getContact());
				frm.setSberbankClient(operation.getContact().isSberbankClient());
			}
			else
			{
				log.error("Не указан идентификатор для поиска контакта в ЕРМБ/МБК.");
				return mapping.findForward(FORWARD_START);
			}
		}
		catch (BusinessException ex)
		{
			log.error(ex);
		}
		catch (InactiveExternalSystemException e)
		{
			log.error(e.getMessage(), e);
			frm.setExternalSystemError(true);
		}
		return mapping.findForward(FORWARD_START);
	}

	protected boolean isAjax()
	{
		return true;
	}
}
