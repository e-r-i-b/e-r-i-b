package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Хендлер, проверяющий, совпадает ли ФИО клиента из справочника доверенных получателей с ФИО получателя платежа.
 *
 * @author EgorovaA
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 */
public class CheckReceiverClientName extends BusinessDocumentHandlerBase
{
	private static final String WARNING_MESSAGE = "Обратите внимание! Не совпадает ФИО получателя.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00))
			return;

		if(!(document instanceof RurPayment))
			throw new DocumentException("Ожидался RurPayment");

		RurPayment rurPayment = (RurPayment) document;

		if(rurPayment.isByTemplate())
			return;

		//  только для перевода физ лицу на карту по номеру телефона
		if(!RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(rurPayment.getReceiverSubType()))
			return;

		String mobileNumber = rurPayment.getMobileNumber();
		if (StringHelper.isEmpty(mobileNumber))
			return;

		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(new Pair<String, Office>(rurPayment.getReceiverCard(), rurPayment.getOffice())));

			if (!compareReceiverFio(client, rurPayment))
			{
				if (MobileApiUtil.isLightScheme())
					throw new DocumentLogicException(WARNING_MESSAGE);
				else
					stateMachineEvent.addMessage(WARNING_MESSAGE);
			}
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * совпадает ли ФИО клиента из справочника доверенных получателей с ФИО получателя платежа.
	 * @param client - данные клиента, полученные по номеру карты
	 * @param rurPayment - платеж с данными из справочника (могут быть не заполнены)
	 * @return false, если ФИО не совпадают
	 */
	private boolean compareReceiverFio(Client client, RurPayment rurPayment)
	{
		if (client == null || rurPayment == null)
			return false;
		if (!StringHelper.equalsNullIgnore(client.getFirstName(), rurPayment.getReceiverFirstName()))
			return false;
		if (!StringHelper.equalsNullIgnore(client.getSurName(), rurPayment.getReceiverSurName()))
			return false;
		if (!StringHelper.equalsNullIgnore(client.getPatrName(), rurPayment.getReceiverPatrName()))
			return false;

		return true;
	}
}
