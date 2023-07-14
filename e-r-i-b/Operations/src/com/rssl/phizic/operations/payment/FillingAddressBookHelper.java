package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

/**
 * Хелпер для добавления информации о получателе платежа в адресную книгу
 * User: miklyaev
 * Date: 15.10.14
 * Time: 17:16
 */
public class FillingAddressBookHelper
{
	private static final AddressBookService contactService = new AddressBookService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final int INCREASE_CONTACT_FREQUENCY_VALUE = 1;
	private static final int INITIAL_CONTACT_FREQUENCY_VALUE = 0;
	private static final String PAYMENT_PHONE_FIELD_NAME = "RecIdentifier";

	/**
	 * Определение типа платежа
	 * @param document - платёжный документ
	 */
	public static void fill(BusinessDocument document)
	{
		boolean enabledFillFromP2P = ConfigFactory.getConfig(AddressBookConfig.class).isFillAddressBookFromP2P();
		boolean enabledFillFromServicePayments = ConfigFactory.getConfig(AddressBookConfig.class).isFillAddressBookFromServicePayments();

		if (!enabledFillFromP2P && !enabledFillFromServicePayments)
			return;

		if (!(document instanceof RurPayment))
			return;

		RurPayment rurPayment = (RurPayment) document;
		//если перевод клиенту Сбербанка по номеру телефона (P2P)
		if (enabledFillFromP2P && rurPayment.getType().equals(ExternalCardsTransferToOurBank.class) && StringHelper.isNotEmpty(rurPayment.getMobileNumber()))
		{
			fillFromP2P(rurPayment);
			return;
		}

		boolean isJurPayment = document instanceof JurPayment;
		if ((FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER == document.getFormType()) && isJurPayment ||
				(FormType.INDIVIDUAL_TRANSFER_NEW == document.getFormType()) && rurPayment.isServiceProviderPayment())
		{
			try
			{
				ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(rurPayment.getReceiverInternalId());
				if (provider != null)
				{
					//если перевод на Яндекс.деньги
					if (enabledFillFromP2P && ConfigFactory.getConfig(ProvidersConfig.class).getYandexPaymentId() == rurPayment.getReceiverInternalId())
					{
						fillFromYandexPayment(document, isJurPayment);
						return;
					}
					//если перевод поставщикам услуги "Мобильная связь"
					if (enabledFillFromServicePayments && provider.getSubType() == ServiceProviderSubType.mobile && isJurPayment)
						fillFromMobileServicePayment((JurPayment) document);
				}
			}
			catch (Exception e)
			{
				log.error("Ошибка при получении поставщика услуг платежа.", e);
			}
		}
	}

	/**
	 * Заполнение из P2P платежа
	 * @param payment - платёжный документ
	 */
	private static void fillFromP2P(RurPayment payment)
	{
		try
		{
			String phone = RurPayment.OUR_CONTACT_TRANSFER_TYPE_VALUE.equals(payment.getReceiverSubType()) ? payment.getContactPhone() : payment.getMobileNumber();
			if (PersonHelper.isSelfPhone(phone))
				return;

			String formatPhone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone));
			//ищем контакт в адресной книге клиента
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Contact contact = contactService.findContactClientByPhone(documentOwner.getLogin().getId(), formatPhone);
			//не нашли - добавляем новый контакт
			if (contact == null)
			{
				contact = new Contact();
				contact.setOwner(documentOwner.getLogin());
				contact.setPhone(PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(payment.getMobileNumber())));
				contact.setFullName(payment.getReceiverName());
				contact.setSberbankClient(true);
				contact.setIncognito(false);
				contact.setFio((payment.getReceiverSurName() + " " + payment.getReceiverFirstName() + " " + StringHelper.getEmptyIfNull(payment.getReceiverPatrName())).trim());
				if (StringHelper.isNotEmpty(payment.getReceiverCard()))
					contact.setCardNumber(payment.getReceiverCard());
				contact.setTrusted(true);
				contact.setFrequencypP2P(INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setFrequencyPay(INITIAL_CONTACT_FREQUENCY_VALUE);
				contact.setStatus(ContactStatus.ACTIVE);
			}
			//нашли - обновляем текущий контакт
			else
			{
				contact.setFrequencypP2P(contact.getFrequencypP2P() + INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setTrusted(true);
				//Если у контакта статус «Скрыт» и частотность контакта стала равна или более значения настройки «Количество P2P переводов для контакта,
				//при котором происходит добавление контакта в адресную книгу», то контакт переходит в статус «Активен» и устанавливается наименование контакта согласно типу контакта
				if (contact.getStatus().equals(ContactStatus.HIDE) && contact.getFrequencypP2P() >= ConfigFactory.getConfig(AddressBookConfig.class).getAmountP2PToAdd())
				{
					contact.setStatus(ContactStatus.ACTIVE);
					contact.setFullName(payment.getReceiverName());
				}
				//если название контакта как у Яндекс-контакта или как при олате мобильных телефонов, то переименовываем
				if (contact.getFullName().startsWith(contactService.YANDEX_CONTACT_NAME_PREFIX) || contact.getFullName().startsWith(contactService.MOBILE_SERVICE_CONTACT_NAME))
					contact.setFullName(payment.getReceiverName());
			}
			//сохраняем изменённый контакт
			contactService.addOrUpdateContact(contact);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (DocumentException de)
		{
			log.error("Ошибка при работе с контактом клиента.", de);
		}
	}

	/**
	 * Заполнение из платежа на Яндекс.деньги
	 * @param document - платёжный документ
	 * @param isJurPayment - признак того, что платёжка класса JurPayment
	 */
	private static void fillFromYandexPayment(BusinessDocument document, boolean isJurPayment)
	{
		try
		{
			//Получаем телефон из платежа
			String phone = null;
			if (isJurPayment)
				phone = ((JurPayment)document).getExtendedAttributes().get(PAYMENT_PHONE_FIELD_NAME);
			else
				phone = ((RurPayment)document).getMobileNumber();

			if (StringHelper.isEmpty(phone) || PersonHelper.isSelfPhone(phone))
				return;

			String formatPhone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone));
			//ищем контакт в адресной книге клиента
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Contact contact = contactService.findContactClientByPhone(documentOwner.getLogin().getId(), formatPhone);
			//не нашли - добавляем новый контакт
			if (contact == null)
			{
				contact = new Contact();
				contact.setOwner(documentOwner.getLogin());
				contact.setPhone(PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone)));
				contact.setFullName(contactService.getNewYandexContactFullName());
				contact.setSberbankClient(false);
				contact.setIncognito(false);
				contact.setTrusted(true);
				contact.setFrequencypP2P(INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setFrequencyPay(INITIAL_CONTACT_FREQUENCY_VALUE);
				contact.setStatus(ContactStatus.ACTIVE);

			}
			//нашли - обновляем текущий контакт
			else
			{
				contact.setFrequencypP2P(contact.getFrequencypP2P() + INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setTrusted(true);
				//Если у контакта статус «Скрыт» и частотность контакта стала равна или более значения настройки «Количество P2P переводов для контакта,
				//при котором происходит добавление контакта в адресную книгу», то контакт переходит в статус «Активен» и устанавливается наименование контакта согласно типу контакта
				if (contact.getStatus().equals(ContactStatus.HIDE) && contact.getFrequencypP2P() >= ConfigFactory.getConfig(AddressBookConfig.class).getAmountP2PToAdd())
				{
					contact.setStatus(ContactStatus.ACTIVE);
					contact.setFullName(contactService.getNewYandexContactFullName());
				}
			}
			//сохраняем изменённый контакт
			contactService.addOrUpdateContact(contact);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (DocumentException e)
		{
			log.error("Ошибка при получении поля платежа.", e);
		}
	}

	/**
	 * Заполнение из платежа услуги "Мобильная связь"
	 * @param payment - платёжный документ
	 */
	private static void fillFromMobileServicePayment(JurPayment payment)
	{
		try
		{
			//Получаем телефон из платежа
			String phone = payment.getExtendedAttributes().get(PAYMENT_PHONE_FIELD_NAME);
			if (StringHelper.isEmpty(phone) || PersonHelper.isSelfPhone(phone))
				return;

			String formatPhone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone));
			//ищем контакт в адресной книге клиента
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Contact contact = contactService.findContactClientByPhone(documentOwner.getLogin().getId(), formatPhone);
			//не нашли - добавляем новый контакт
			if (contact == null)
			{
				contact = new Contact();
				contact.setOwner(documentOwner.getLogin());
				contact.setPhone(PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phone)));
				contact.setFullName(contactService.MOBILE_SERVICE_CONTACT_NAME);
				contact.setSberbankClient(false);
				contact.setIncognito(false);
				contact.setTrusted(true);
				contact.setFrequencypP2P(INITIAL_CONTACT_FREQUENCY_VALUE);
				contact.setFrequencyPay(INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setStatus(ContactStatus.HIDE);
			}
			//нашли - обновляем текущий контакт
			else
			{
				contact.setFrequencyPay(contact.getFrequencyPay() + INCREASE_CONTACT_FREQUENCY_VALUE);
				contact.setTrusted(true);
			}
			//сохраняем изменённый контакт
			contactService.addOrUpdateContact(contact);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при работе с контактом клиента.", e);
		}
		catch (DocumentException e)
		{
			log.error("Ошибка при получении поля платежа.", e);
		}
	}
}

