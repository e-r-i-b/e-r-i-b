package com.rssl.phizic.business.payments.forms.meta.handlers.subscriptions;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author khudyakov
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 */
public class UpdateReceiverInfoByPhoneHandler extends BusinessDocumentHandlerBase<P2PAutoTransferClaimBase>
{
	private static final UserCountersService userCountersService = new UserCountersService();
	private static final AddressBookService addressBookService = new AddressBookService();

	public void process(P2PAutoTransferClaimBase autoSubscription, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (FormType.CREATE_P2P_AUTO_TRANSFER_CLAIM != autoSubscription.getFormType())
		{
			return;
		}

		//  только для перевода физ лицу на карту по номеру телефона
		if (!Constants.OUR_PHONE_RECEIVER_SUB_TYPE_ATTRIBUTE_NAME.equals(autoSubscription.getReceiverSubType()))
		{
			return;
		}

		try
		{
			// если количество запросов информации по номеру телефона в день не превышено,  или телефон из адресной книги  заполняем информацию о получателе
			// иначе ФИО ничем не заполняем
			BusinessDocumentOwner documentOwner = autoSubscription.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if (addressBookService.isExistContactClientByPhone(documentOwner.getLogin().getId(), PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(autoSubscription.getMobileNumber()))) || userCountersService.increment(autoSubscription.getOwner().getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
			{
				autoSubscription.fillReceiverInfoByCardOwner(getToResourceCard(autoSubscription));

			}
			else
			{
				clearReceiverInfo(autoSubscription);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private Card getToResourceCard(P2PAutoTransferClaimBase autoSubscription) throws DocumentLogicException, DocumentException
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			BusinessDocumentOwner documentOwner = autoSubscription.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(documentOwner.getPerson().asClient(), new Pair<String, Office>(autoSubscription.getReceiverCard(), null)));
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

	private void clearReceiverInfo(P2PAutoTransferClaimBase autoSubscription)
	{
		autoSubscription.setRestrictReceiverInfoByPhone(true);
		autoSubscription.setReceiverFirstName(null);
		autoSubscription.setReceiverSurName(null);
		autoSubscription.setReceiverPatrName(null);
		autoSubscription.setReceiverName(null);
	}
}
