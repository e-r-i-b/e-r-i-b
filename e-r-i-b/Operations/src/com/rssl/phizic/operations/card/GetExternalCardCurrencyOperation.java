package com.rssl.phizic.operations.card;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.counters.CounterType;
import com.rssl.phizic.business.counters.UserCountersService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.IndividualTransferTemplate;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author hudyakov
 * @ created 08.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetExternalCardCurrencyOperation extends OperationBase implements ViewEntityOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final AddressBookService addressBookService = new AddressBookService();
	private static final UserCountersService userCounterService = new UserCountersService();
	private static final String LIMIT_BY_PHONE_NUMBER_MSG = "Превышен кумулятивный лимит запросов по номеру телефона. Используйте перевод по номеру карты.";
	private static final String CARD_NOT_FOUND_MSG = "Не найдена карта по номеру телефона ";
	private static final String ERROR_MSG = "Шаблон id=%s не найден.";

	private Card card;

	/**
	 * Инициализация
	 * @param cardNumber - номер карты
	 */
	public void initialize(String cardNumber) throws BusinessException, BusinessLogicException
	{
		try
		{
			Pair<String, Office> request = new Pair<String, Office>(cardNumber, null);
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			GroupResult<Pair<String, Office>, Card> result = bankrollService.getCardByNumber(person.asClient(), request);
			card = GroupResultHelper.getOneResult(result);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * Инициализация по номеру телефона
	 * @param phoneNumber - номер телефона с 7-кой (всего 11 цифр)
	 */
	public void initializeByPhone(String phoneNumber) throws BusinessException, BusinessLogicException
	{
		String formatNumber = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phoneNumber.trim()));
		//счетчик увеличиваем только если контакт не из адресной книги
		if (!addressBookService.isExistContactClientByPhone(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId(), formatNumber)
				&& !userCounterService.increment(PersonContext.getPersonDataProvider().getPersonData().getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
			throw new BusinessLogicException(LIMIT_BY_PHONE_NUMBER_MSG);

		card = MobileBankManager.getCardByPhone(phoneNumber, PersonContext.getPersonDataProvider().getPersonData().getPerson());
		if (card == null)
			log.error(CARD_NOT_FOUND_MSG + phoneNumber);
	}

	/**
	 *  Инициализация по номеру телефона с проверкой (есть ли номер в адресной книге клиента; при оплате по шаблону проверяется, изменился ли номер) для учета лимитов на запрос личных данных
	 * @param phoneNumber - номер телефона
	 * @param templateId - id шаблона
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initializeByPhone(String phoneNumber, Long templateId) throws BusinessException, BusinessLogicException
	{
		boolean phoneIsFromTemplate = false;
		if(templateId != null)
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(templateId);
			if (template == null || !(template instanceof IndividualTransferTemplate))
				throw new BusinessException(String.format(ERROR_MSG, templateId));

			phoneIsFromTemplate = phoneNumber.equals(template.getReceiverPhone());

		}
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		String formatNumber = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phoneNumber.trim()));

		if(!(addressBookService.isExistContactClientByPhone(login.getId(), formatNumber) || phoneIsFromTemplate))
		{
			if (!userCounterService.increment(PersonContext.getPersonDataProvider().getPersonData().getLogin(), CounterType.RECEIVE_INFO_BY_PHONE, null))
				throw new BusinessLogicException(DocumentHelper.getPersonPaymentLimitMessage());
		}

		card = MobileBankManager.getCardByPhone(phoneNumber, PersonContext.getPersonDataProvider().getPersonData().getPerson());
		if (card == null)
			log.error(CARD_NOT_FOUND_MSG + phoneNumber);
	}

	/**
	 * Инициализация по идентификатору записи справочника доверенных получателей.
	 * @param dictFieldId Идентификатор записи справочника доверенных получателей.
	 */
	public void initializeByDictId(Long dictFieldId) throws BusinessException, BusinessLogicException
	{
		Contact data = addressBookService.findContactById(dictFieldId);
		Long loginId = null;
		if (PersonContext.isAvailable())
			loginId = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		if (data == null || loginId == null || !loginId.equals(data.getOwner().getId()))
			throw new BusinessException("Не найден переданный идентификатор записи справочника доверенных получателей");

		if (StringHelper.isNotEmpty(data.getCardNumber()))
			initialize(data.getCardNumber());
		else if (StringHelper.isNotEmpty(data.getPhone()))
			initializeByPhone(data.getPhone());
	}

	public Card getEntity()
	{
		return card;
	}
}
