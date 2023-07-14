package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * Хендле обновляющий информацию о получателе в случае перевода частному лицу по номеру телефона
 * Обновление ведется с учетом счетчика(CHG041044)
 * @author niculichev
 * @ created 05.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class UpdateReceiverInfoByPhoneHandler  extends BusinessDocumentHandlerBase
{
	private static final UserCountersService userCountersService = new UserCountersService();
	private static final SimpleService simpleService = new SimpleService();
	private static final AddressBookService addressBookService = new AddressBookService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if(!(document instanceof RurPayment))
			throw new DocumentException("Ожидался RurPayment");

		RurPayment rurPayment = (RurPayment) document;
		//  только для перевода физ лицу на карту по номеру телефона
		String receiverSubType = rurPayment.getReceiverSubType();
		if (FormConstants.NEW_RUR_PAYMENT.equals(rurPayment.getFormName()))
		{
			if (!RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(receiverSubType) && !RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType))
				return;
		}
		else if(!RurPayment.OUR_PHONE_TRANSFER_TYPE_VALUE.equals(receiverSubType))
			return;

		try
		{
			if (rurPayment.isByTemplate())
			{
				//если платеж создается по шаблону или копия платежа
				TemplateDocument template = TemplateDocumentService.getInstance().findById(rurPayment.getTemplateId());
				if (template == null)
				{
					throw new DocumentException("Не найден шаблон");
				}
				if (isSameReceiver(rurPayment, template))
				{
					rurPayment.fillReceiverInfoByCardOwner(getCard(rurPayment));
					return;
				}
			}
			if (rurPayment.isCopy())
			{
				BusinessDocument copy = simpleService.findById(BusinessDocumentBase.class, rurPayment.getTemplateId());
				if (!(copy instanceof RurPayment))
				{
					throw new DocumentException("Ожидался RurPayment");
				}
				if (isSameReceiver(rurPayment, (RurPayment) copy))
				{
					return;
				}
			}

			// если количество запросов информации по номеру телефона в день не превышено,  или телефон из адресной книги  заполняем информацию о получателе
			// иначе ФИО ничем не заполняем
			BusinessDocumentOwner documentOwner = rurPayment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if(RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(receiverSubType) ||
					addressBookService.isExistContactClientByPhone(documentOwner.getLogin().getId(),  PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(rurPayment.getMobileNumber())))
					|| rurPayment.isByTemplate() || userCountersService.increment(documentOwner.getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
			{
				rurPayment.fillReceiverInfoByCardOwner(getCard(rurPayment));
			}
			else
			{
				clearReceiverInfo(rurPayment);
			}

		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void clearReceiverInfo(RurPayment payment)
	{
		payment.setRestrictReceiverInfoByPhone(Boolean.TRUE);
		payment.setReceiverFirstName(null);
		payment.setReceiverSurName(null);
		payment.setReceiverPatrName(null);
		payment.setReceiverName(null);
		payment.setReceiverDocSeries(null);
		payment.setReceiverDocNumber(null);
		payment.setReceiverBirthday(null);
	}

	private Card getCard(RurPayment payment) throws DocumentException, DocumentLogicException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		try
		{
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ActivePerson activePerson = documentOwner.getPerson();
			String receiverCardNumber = payment.getReceiverCard();

			if (activePerson == null)
				throw new DocumentLogicException("Невозможно получить информацию о владельце документа");

			Pair<String, Office> pair = new Pair<String, Office>(receiverCardNumber, null);
			Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(activePerson.asClient(), pair));

			if (card == null)
				throw new DocumentLogicException("Не найдена информация по карте " + MaskUtil.getCutCardNumber(receiverCardNumber));

			return card;
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

	private boolean isSameReceiver(AbstractPhizTransfer payment, AbstractPhizTransfer template)
	{
		return payment.getReceiverSurName().equals(template.getReceiverSurName())
			&& payment.getReceiverFirstName().equals(template.getReceiverFirstName())
			&& payment.getReceiverPatrName().equals(template.getReceiverPatrName());
	}
}
