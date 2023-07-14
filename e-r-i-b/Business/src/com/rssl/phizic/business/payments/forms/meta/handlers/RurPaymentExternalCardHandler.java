package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentFieldsIndicateException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ResourceNotFoundDocumentException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.payments.ReceiverCardType;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Collections;

/**
 * @author akrenev
 * @ created 04.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����������� ����������� ��������� �� ����� ������� �����
 */

public class RurPaymentExternalCardHandler extends BusinessDocumentHandlerBase
{
	private static final String VISA_MONEY_SEND_SERVICE_NAME = "VisaMoneySendService";
	private static final String VISA_MONEY_SEND_SERVICE_NAME_MAPI = "VisaMoneyTransferService";
	private static final String MASTERCARD_MONEY_SEND_SERVICE_NAME = "MastercardMoneySendService";
	private static final String MASTERCARD_MONEY_SEND_SERVICE_NAME_MAPI = "MasterCardMoneyTransferService";
	private static final String TO_CARD_ERROR_MESSAGE = "�������� ���� ���������, �������� �� ����� %s � ������ ������ ����������. %s";
	private static final String ERROR_ADDITIONAL_MESSAGE = "�� ������ ��������� ������� �� ���������� ���� ��� ��������� �������� �� ������.������";
	private static final String CONTACT_NOT_FOUND_ERROR_MSG = "��������� ������� ����������� � �������� �����. ���������� ��������� ��������.";
	private static final String SBRF_CLIENT_ERROR_MSG = "��� ����������� - �������� ��������� �������� ������� ������ �� ������ ���������� �������� ��� �� ������ ����� ���������";
	private static final String CARD_NOT_FOUND_ERROR_MSG = "����� ���������� �� ����������.";

	private AddressBookService addressBookService = new AddressBookService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof RurPayment))
			throw new DocumentException("�������� ��� ������� - ��������� RurPayment");

		RurPayment payment = (RurPayment) document;

		boolean isMobileApi = ApplicationUtil.isMobileApi();
		//�������� ������� �� �������� ����� ������ ��� mAPI
		if (isMobileApi)
			addressBookValidate(payment);

		if (payment.isOurBankCard())
			return;

		boolean isVisaPayment = payment.getReceiverCardType() == ReceiverCardType.VISA;
		boolean isMasterCardPayment = payment.getReceiverCardType() == ReceiverCardType.MASTERCARD;

		if (!isVisaPayment && !isMasterCardPayment)
			return;

		String serviceName = isVisaPayment ? VISA_MONEY_SEND_SERVICE_NAME : MASTERCARD_MONEY_SEND_SERVICE_NAME;

		if (isMobileApi)
			serviceName = isVisaPayment ? VISA_MONEY_SEND_SERVICE_NAME_MAPI : MASTERCARD_MONEY_SEND_SERVICE_NAME_MAPI;

		try
		{
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			if (!DocumentHelper.isAvailableService(documentOwner.getLogin(), serviceName))
			{
				String cardType = isVisaPayment ? ReceiverCardType.VISA.name() : ReceiverCardType.MASTERCARD.name();
				String additionalMessage = isMobileApi ? ERROR_ADDITIONAL_MESSAGE : "";
				String errorMessage = String.format(TO_CARD_ERROR_MESSAGE, cardType, additionalMessage);
				throw new DocumentFieldsIndicateException(Collections.<String, String>emptyMap(), false, errorMessage);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * �������� ������ ������� �� �������� �����
 	 * @param payment
	 * @throws DocumentLogicException
	 * @throws DocumentException
	 */
	private void addressBookValidate(RurPayment payment) throws DocumentLogicException, DocumentException
	{
		if (MobileApiUtil.isMobileApiLT(MobileAPIVersions.V9_00) || StringHelper.isEmpty(payment.getReceiverSubType()))
			return;

		boolean isOurContactToOtherCard = payment.getReceiverSubType().equals(RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE);
		boolean isOurContact =  payment.getReceiverSubType().equals(RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE);

		try
		{
			if ( isOurContactToOtherCard ||	isOurContact )
			{
				//����� �������� � �������� ����� �������
				Contact contact = getContactFromAddressBook(payment);
				if (contact == null)
					throw new ResourceNotFoundDocumentException(CONTACT_NOT_FOUND_ERROR_MSG, Contact.class);

				if (StringHelper.isNotEmpty(payment.getMobileNumber()) &&
						StringHelper.isNotEmpty(payment.getReceiverCardNumber()) &&
						contact.isSberbankClient())
					throw new DocumentLogicException(SBRF_CLIENT_ERROR_MSG);

				if (isOurContact)
				{
					String cardNumber = contact.getCardNumber();
					BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
					Client client = payment.getOwner().getPerson().asClient();
					Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(cardNumber, client.getOffice())));

					//����� �� ������� � ����
					if (card == null)
						throw new ResourceNotFoundDocumentException(CARD_NOT_FOUND_ERROR_MSG, Card.class);
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
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

	private Contact getContactFromAddressBook(RurPayment payment) throws DocumentException
	{
		try
		{
			String externalPhoneNumber = payment.getMobileNumber();
			Long externalContactId = payment.getContactId();
			if (StringHelper.isNotEmpty(externalPhoneNumber) || (externalContactId != null))
	        {
	            Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
			    Contact contact = null;
                if (StringHelper.isNotEmpty(externalPhoneNumber))
                    contact = addressBookService.findContactClientByPhone(loginId, externalPhoneNumber);
                else if (externalContactId != null)
                    contact = addressBookService.findContactById(externalContactId);

		        return contact;
	        }
		}
        catch (BusinessException e)
        {
	        throw new DocumentException(e);
        }
		return null;
	}
}
