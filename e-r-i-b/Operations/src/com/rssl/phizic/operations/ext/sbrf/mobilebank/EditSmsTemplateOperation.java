package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.ext.sbrf.mobilebank.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankConstants.MAX_PAYERS_PER_RECIPIENT;

/**
 * @author Erkin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */

/**
 * Операция редактирования шаблонов платежей МБ
 */
@Deprecated
//todo CHG059738 удалить
public class EditSmsTemplateOperation extends MobileBankOperationBase
{
	private static final ServiceProviderService providerService
			= new ServiceProviderService();

	private PaymentTemplateUpdate update;

	private CardLink cardLink = null;

	private List<SmsCommand> newSmsCommands = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции добавления нового шаблона
	 * @param phoneCode - код номера телефона
	 * @param cardCode - код номера карты
	 * @param recipientId - ID поставщика услуг в ИКФЛ
	 * @param keyFieldValue - значение ключевого поля (без кода подуслуги)
	 */
	public void initializeNewAdditiveUpdate(String phoneCode, String cardCode, long recipientId, String keyFieldValue)
			throws BusinessException, BusinessLogicException
	{
		// 1. Определяем поставщика
		ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(recipientId);
		if (provider == null)
			throw new BusinessException("Не найден поставщик ID: " + recipientId);

		// 2. Проверяем возможность добавления шаблона
		testAddSmsTemplateAbility(provider.getMobilebankCode(), phoneCode, cardCode);

		// 3. Вычисляем код получателя и код плательщика
		String recipientCode = provider.getMobilebankCode();
		@SuppressWarnings({"UnnecessaryLocalVariable"})
		String payerCode = keyFieldValue;

		initializeNewUpdate(UpdateType.ADD, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * Инициализация операции добавления нового шаблона
	 * @param phoneCode - код номера телефона
	 * @param cardCode - код номера карты
	 * @param recipientCode - код поставшика услуг в Мобильном банке
	 * @param keyFieldValue - значение ключевого поля (без кода подуслуги)
	 */
	public void initializeNewAdditiveUpdate(String phoneCode, String cardCode, String recipientCode, String keyFieldValue)
			throws BusinessException, BusinessLogicException
	{
		// 1. Проверяем возможность добавления шаблона
		testAddSmsTemplateAbility(recipientCode, phoneCode, cardCode);

		// 2. Вычисляем код плательщика
		@SuppressWarnings({"UnnecessaryLocalVariable"})
		String payerCode = keyFieldValue;

		initializeNewUpdate(UpdateType.ADD, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * Инициализация операции удаления шаблона
	 * @param phoneCode - код номера телефона
	 * @param cardCode - код номера карты
	 * @param recipientCode - код поставшика услуг в Мобильном банке
	 * @param payerCode - идентификатор получателя в терминах МБ
	 */
	public void initializeNewRemovingUpdate(String phoneCode, String cardCode, String recipientCode, String payerCode)
			throws BusinessException, BusinessLogicException
	{
		initializeNewUpdate(UpdateType.REMOVE, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * Инициализация операции для нового апдейта
	 * @param updateType - тип апдейта списка шаблонов (добавить, удалить и т.п.)
	 * @param phoneCode - код номера телефона
	 * @param cardCode - код номера карты
	 * @param recipientCode - код поставшика услуг в Мобильном банке
	 * @param payerCode - идентификатор получателя в терминах МБ
	 */
	private void initializeNewUpdate(
			UpdateType updateType,
			String phoneCode,
			String cardCode,
			String recipientCode,
			String payerCode) throws BusinessException, BusinessLogicException
	{
		if (updateType == null)
			throw new NullPointerException("Аргумент 'updateType' не может быть null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("Аргумент 'phoneCode' не может быть пустым");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("Аргумент 'cardCode' не может быть пустым");
		if (StringHelper.isEmpty(recipientCode))
			throw new IllegalArgumentException("Аргумент 'recipientCode' не может быть пустым");
		if (StringHelper.isEmpty(payerCode))
			throw new IllegalArgumentException("Аргумент 'payerCode' не может быть пустым");

		super.initialize();

		RegistrationShortcut registrationShortcut = getRegistrationShortcut(phoneCode, cardCode);
		if (registrationShortcut == null)
			throw new BusinessException("Отсутствует подключение к Мобильному Банку " +
					"с кодом номера телефона " + phoneCode + " " +
					"и кодом номера карты " + cardCode + ". " +
					"LOGIN_ID=" + getLogin().getId());

		Collection<Pair<String, String>> samples =
				Collections.singleton(new Pair<String, String>(recipientCode, payerCode));
		String destlist = MobileBankUtils.encodeDestlist(samples);

		update = new PaymentTemplateUpdate();
		update.setLogin(getLogin());
		update.setPhoneNumber(registrationShortcut.getPhoneNumber());
		update.setCardNumber(registrationShortcut.getCardNumber());
		update.setDestlist(destlist);
		update.setType(updateType);
	}

	/**
	 * Инициализация операции для сохранённого апдейта SMS-шаблонов
	 * @param id - ID апдейта (см. PaymentTemplateUpdate)
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			throw new NullPointerException("Argument 'id' cannot be null");

		super.initialize();

		update = mobileBankService.getUpdate(getLogin(), id);
		if (update == null)
			throw new BusinessException("Не найден апдейт шаблонов SMS-платежей " +
					"(PaymentTemplateUpdate) ID = " + id);
	}

	public Long getUpdateId()
	{
		return update.getId();
	}

	public PaymentTemplateUpdate getUpdate()
	{
		return update;
	}

	public CardLink getCardLink() throws BusinessException, BusinessLogicException
	{
		if (cardLink == null) {
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			cardLink = MobileBankUtils.selectCardLinkByCardNumber(personData.getCards(), update.getCardNumber(), true);
		}
		return cardLink;
	}

	public List<SmsCommand> getNewSmsCommands() throws BusinessException, BusinessLogicException
	{
		if (newSmsCommands == null)
			newSmsCommands = mobileBankService.getUpdateSmsCommands(update);
		return Collections.unmodifiableList(newSmsCommands);
	}

	/**
	 * Сохранение апдейта (в базе ИКФЛ)
	 */
	@Transactional
	public void saveUpdate() throws SecurityLogicException, BusinessException
	{
		if (update == null)
			throw new IllegalStateException("Operation is not initialized yet");

		mobileBankService.saveUpdate(update);
	}

	/**
	 * Отправка изменений в МБ
	 * @throws BusinessException
	 */
	@Transactional
	public void applyUpdate() throws BusinessException, BusinessLogicException
	{
		if (update == null)
			throw new IllegalStateException("Operation is not initialized yet");

		mobileBankService.applyUpdate(update);
	}

	/**
	 * Удаление апдейта (из базы ИКФЛ)
	 * @throws BusinessException
	 */
	@Transactional
	public void deleteUpdate() throws BusinessException
	{
		if (update != null)
			mobileBankService.deleteUpdate(update);
	}

	private void testAddSmsTemplateAbility(String recipientMobileBankCode, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		RegistrationProfile registration = mobileBankService.getRegistrationProfile(person.getLogin(), phoneCode, cardCode);
		List<SmsCommand> smsPaymentTemplates = registration.getMainCard().getPaymentSmsTemplates();
		int count = 0;
		for (SmsCommand smsPaymentTemplate : smsPaymentTemplates) {
			if (recipientMobileBankCode.equals(smsPaymentTemplate.getRecipientCode()))
				count ++;
		}
		if (count >= MAX_PAYERS_PER_RECIPIENT)
			throw new BusinessLogicException("Вы можете создать " + MAX_PAYERS_PER_RECIPIENT + " шаблонов для одного получателя. " +
					"Для того, чтобы добавить новый шаблон, удалите один из старых");
	}
}
