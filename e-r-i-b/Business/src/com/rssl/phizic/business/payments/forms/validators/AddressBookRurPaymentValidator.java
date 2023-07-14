package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
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
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Валидатор проверяет получателя универсального перевода по адресной книге ЕРИБ
 * @ author: Gololobov
 * @ created: 04.11.14
 * @ $Author$
 * @ $Revision$
 */
public class AddressBookRurPaymentValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_RECEIVER_SUB_TYPE = "receiverSubType";
	private static final String FIELD_PHONE_NUMBER = "externalPhoneNumber";
	private static final String FIELD_CONTACT_ID = "externalContactId";
	private static final String SBRF_CLIENT_ERROR_MSG = "Для получателей - клиентов Сбербанка возможен перевод только по номеру мобильного телефона или по номеру карты Сбербанка";
	private static final String CARD_NOT_FOUND_ERROR_MSG = "Карта получателя не обнаружена.";
	private static ThreadLocal<String> message = new ThreadLocal<String>();

	private AddressBookService addressBookService = new AddressBookService();

	public String getMessage()
	{
		String currentMessage = message.get();
		if (StringHelper.isEmpty(currentMessage))
			return super.getMessage();
		else
			return currentMessage;
	}

	public void setMessage(String value)
	{
		message.set(value);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (!ApplicationUtil.isMobileApi())
			return true;

		String receiverSubType = (String)retrieveFieldValue(FIELD_RECEIVER_SUB_TYPE, values);

		boolean isOurContactToOtherCard = receiverSubType.equals(RurPayment.OUR_CONTACT_TO_OTHER_CARD_TRANSFER_TYPE_VALUE);
		boolean isOurContact =  receiverSubType.equals(RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE);
		try
		{
			if ( isOurContactToOtherCard ||	isOurContact )
			{
				//Поиск контакта в адресной книге клиента
				Contact contact = getContactFromAddressBook(values);
				if (contact == null)
					return false;

				//Перевод на карту сбербанка по номеру мобильного
				if (isOurContact)
				{
					//Поиск карты в ЕРИБ
					String cardNumber = contact.getCardNumber();
					BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
					Client client = PersonContext.getPersonDataProvider().getPersonData().getPerson().asClient();
					Card card = GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(cardNumber, client.getOffice())));

					if (card == null)
					{
						//Карта не найдена в ЕРИБ
						setMessage(CARD_NOT_FOUND_ERROR_MSG);
						return false;
					}
				}
			}
			return true;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (LogicException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (SystemException e)
		{
			throw new TemporalDocumentException(e);
		}
	}

	/**
	 * Проверка контакта получателя по адресной книге (АК) ЕРИБ
	 * @return
	 * @throws BusinessException
	 */
	private Contact getContactFromAddressBook(Map values) throws BusinessException
	{
		String externalPhoneNumber = (String)retrieveFieldValue(FIELD_PHONE_NUMBER, values);
		Long externalContactId = (Long)retrieveFieldValue(FIELD_CONTACT_ID, values);

		if (StringHelper.isEmpty(externalPhoneNumber) && (externalContactId == null))
			return null;

		Long loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		Contact contact = null;

		if (StringHelper.isNotEmpty(externalPhoneNumber))
            contact = addressBookService.findContactClientByPhone(loginId, externalPhoneNumber);
        else if (externalContactId != null)
			contact = addressBookService.findContactById(externalContactId);

        if (contact != null && contact.isSberbankClient())
        {
            setMessage(SBRF_CLIENT_ERROR_MSG);
        }

        return contact;
	}
}
