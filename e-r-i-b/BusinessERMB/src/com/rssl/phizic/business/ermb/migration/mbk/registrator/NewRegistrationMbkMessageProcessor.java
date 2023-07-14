package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.FakeClient;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ermb.MbkTariff;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.*;

/**
 * Обработчик сообщений на подключение услуги МБ в ЕРИБ
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class NewRegistrationMbkMessageProcessor extends MbkMessageProcessorBase
{
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private static final ErmbClientSearchHelper clientSearchHelper = new ErmbClientSearchHelper();
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	public ProcessResult process(MBKRegistration mbkMessage) throws SystemException, LogicException
	{
		if (CardsUtil.isCorporate(mbkMessage.getPaymentCardNumber()))
		{
			return createErrorResult(mbkMessage, false, "В связке указана корпоративная карта в качестве платежной");
		}

		excludeCorporateFromInfoCards(mbkMessage.getActiveCards());
		Client cardOwner = getClientByCard(mbkMessage.getPaymentCardNumber(), mbkMessage.getOfficeCode());
		if (cardOwner == null)
			return createErrorResult(mbkMessage, false, "Не найден клиент по платежной карте подключения");
		//6.	ЕРИБ должен подменить значение ТБ на код пилотного ТБ. Дальше в алгоритме ТБ = пилотный ТБ .
		mbkMessage.getOfficeCode().getFields().put("region", getPilotTb());
		List<Card> cards = getCardsViaGFL(mbkMessage);
		if (!containsAllCards(cards, mbkMessage.getPaymentCardNumber(), mbkMessage.getActiveCards()))
		{
			return createErrorResult(mbkMessage, false, "В связке присутствуют карты не имеющее отношение к клиенту");
		}

		switch (mbkMessage.getFiltrationReason())
		{
			case ERMB_PHONE:
				return processPhoneConnection(mbkMessage, cards, cardOwner);

			case ERMB_CARD:
				return processCardConnection(mbkMessage, cards);

			case PILOT_ZONE:
				return processPilotConnection(mbkMessage, cardOwner);

			default:
				throw new IllegalArgumentException("Неожиданный FiltrationReason");
		}
	}

	private ProcessResult processPhoneConnection(MBKRegistration mbkMessage, List<Card> cards, Client cardOwner) throws SystemException, LogicException
	{
		ErmbProfileImpl profile = findByPhone(mbkMessage.getPhoneNumber());
		if (profile == null)
			return createErrorResult(mbkMessage, false, "Для связки Телефон в ЕРМБ не найден профиль ЕРМБ по телефону");

		//И проверить,  что платёжная карта из связки открыта на ФИО, ДУЛ, ДР клиента из найденного профиля, сравнение без учета ТБ,
		// и в качестве информационной карты в связке указана хотя бы одна основная карта открытая на имя этого клиента
		boolean canUpdate = isSamePerson(cardOwner, profile.getPerson());
		canUpdate = canUpdate && containsMainCard(cards, mbkMessage.getActiveCards());
		if (!canUpdate)
		{
			return createErrorResult(mbkMessage, false, "Такой телефон уже зарегистрирован в системе и принадлежит другому клиенту");
		}
		updateErmbProfile(mbkMessage, profile);
		return createSuccessResult(mbkMessage);
	}

	private ProcessResult processCardConnection(MBKRegistration mbkMessage, List<Card> cards) throws SystemException, LogicException
	{
		ErmbProfileImpl profile = findByFioDulDrPilotTb(new MigrationClient(mbkMessage));
		//a.	Если профиль найден, ЕРИБ должен проверить, что в качестве информационной карты, в связке,
		// указана хотя бы одна основная карта, открытая на имя владельца платёжной карты.
		if (profile != null)
		{
			//i.	Если проверка прошла успешно, ЕРИБ должен обновить профиль ЕРМБ данными
			// из связки. И установить статус результата обработки связки: «связка успешно подключена к ЕРМБ»
			if (containsMainCard(cards, mbkMessage.getActiveCards()))
			{
				updateErmbProfile(mbkMessage, profile);
			}
			//ii.	Иначе если проверка прошла не успешно, ЕРИБ должен для каждой информационной карты, открытой НЕ на имя владельца платежной карты,
			// получить ФИО, ДУЛ, ДР клиента на имя которого эта карты открыта, через интерфейс CRDWI. КСШ
			else
			{
				List<Card> additionalInfoCards = getAdditionalInfoCards(mbkMessage.getActiveCards(), cards);
				Set<MigrationClient> additionalOwners = getOwnersViaCRDWI(additionalInfoCards, mbkMessage.getOfficeCode());
				if (CollectionUtils.isEmpty(additionalOwners))
					log.info("Не нашлось владельцев инфокарт по CRDWI");

				//iii.	Затем для каждого держателя информационной карты открытой НЕ на имя владельца платежной карты,
				// ЕРИБ должен проверить наличие профиля ЕРМБ по ФИО, ДУЛ,ДР полученным на предыдущем шаге + пилотный ТБ
				for (MigrationClient additionalOwner : additionalOwners)
				{
					if (isErmbConnected(additionalOwner))
					{
						//v.	Иначе если, найден один или больше профилей ЕРМБ, ЕРИБ должен установить статус результата обработки связки: «связка не подключена к ЕРМБ,
						// к МБК подключать нельзя». Текст ошибки: «Невозможно определить владельца телефона среди клиентов подключенных к ЕРМБ». Завершить обработку связки.
						return createErrorResult(mbkMessage, false, "Невозможно определить владельца телефона среди клиентов подключенных к ЕРМБ");
					}
				}
				//iv.	Если не найден ни один профиль ЕРМБ, то ЕРИБ должен установить статус результата обработки связки:
				// «связка не подключена к ЕРМБ, подключить к МБК». Завершить обработку связки.
				return createErrorResult(mbkMessage, true, "В качестве инфокарты в связке, не указана ни одна основная карта, открытая на имя владельца платёжной карты. " +
						"Среди владельцев инфокарт нет клиентов, подключенных к ЕРМБ");
			}
		}
		//b.	Иначе, если профиль НЕ найден, ЕРИБ должен проверить, что в качестве информационной карты,
		// в связке, указана хотя бы одна основная карта, открытая на имя владельца платёжной карты.
		else
		{
			//i.	Если проверка прошла успешно, ЕРИБ должен установить статус результата обработки связки:
			// «связка не подключена к ЕРМБ, подключить к МБК». Завершить обработку связки.
			if (containsMainCard(cards, mbkMessage.getActiveCards()))
			{
				return createErrorResult(mbkMessage, true, "В качестве информационной карты в связке указана основная карта, открытая на имя владельца платёжной карты");
			}
			//ii.	Иначе, если проверка прошла не успешно, ЕРИБ должен для каждой информационной карты, открытой НЕ на имя владельца платежной карты,
			// получить ФИО, ДУЛ, ДР клиента на имя которого эта карты открыта, через интерфейс CRDWI. КСШ.
			else
			{
				List<Card> additionalInfoCards = getAdditionalInfoCards(mbkMessage.getActiveCards(), cards);
				Set<MigrationClient> persons = getOwnersViaCRDWI(additionalInfoCards, mbkMessage.getOfficeCode());
				if (CollectionUtils.isEmpty(persons))
					log.info("Не нашлось владельцев инфокарт по CRDWI");

				//iii.	Затем для каждого держателя информационной карты открытой НЕ на имя владельца платежной карты,
				// ЕРИБ должен проверить наличие профиля ЕРМБ по ФИО, ДУЛ, ДР полученным на предыдущем шаге + пилотный ТБ.
				for (MigrationClient person : persons)
				{
					if (isErmbConnected(person))
					{
						//v.	Иначе если, найден один или больше профилей ЕРМБ, ЕРИБ должен установить статус результата обработки связки:
						// «связка не подключена к ЕРМБ, подключить к МБК». Завершить обработку связки.
						return createErrorResult(mbkMessage, true, "Найдены держатели инфокарт, подключенные к ЕРМБ, но профиль ЕРМБ найти не удалось");
					}
				}
				//iv.	Если не найден ни один профиль ЕРМБ, то ЕРИБ должен установить статус результата обработки связки: «связка не подключена к ЕРМБ, к МБК подключать нельзя».
				// Текст ошибки: «Одна или более карт в связке  имеют признак ЕРМБ, но профиль ЕРМБ найти не удалось». Завершить обработку связки.
				return createErrorResult(mbkMessage, false, "Одна или более карт в связке имеют признак ЕРМБ, но профиль ЕРМБ найти не удалось");
			}
		}
		return createSuccessResult(mbkMessage);
	}

	private ProcessResult processPilotConnection(MBKRegistration mbkMessage, Client cardClient) throws BusinessLogicException, BusinessException, GateException, GateLogicException
	{
		ErmbProfileImpl byPhone = profileService.findByPhone(mbkMessage.getPhoneNumber());
		if (byPhone != null)
			return createErrorResult(mbkMessage, false, "Для связки КАРТА В ПЗ найден профиль ЕРМБ по телефону");

		Client cedboClient = clientSearchHelper.findClient(
				mbkMessage.getOwner().getFirstname(),
				mbkMessage.getOwner().getSurname(),
				mbkMessage.getOwner().getPatrname(),
				mbkMessage.getOwner().getBirthdate(),
				null,
				mbkMessage.getOwner().getPassport(),
				ClientDocumentType.PASSPORT_WAY,
				getPilotTb()
		);
		Client client = mergeClientInfo(cardClient, cedboClient);

		boolean mbvAvailability = ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability();
		if (!mbvAvailability)
			log.error("Настройка доступности МБВ выключена. Проверка/миграция клиента в МБВ выполнена не будет.");

		//todo BUG085485: сейчас эта ветка алгоритма не отрабатывает, т.к. если клиент пришел по crdwi, но не cedbo, запрос в мбв сделать невозможно
		if (!client.isUDBO() && mbvAvailability)
		{
			MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(client);
			if (CollectionUtils.isNotEmpty(mbvInfo.getIdentities()))
				return createErrorResult(mbkMessage, true, "не УДБО-клиент зарегистрирован в МБВ");
		}

		MigratorOnTheFly migrator = new MigratorOnTheFly();
		if (migrator.migrate(mbkMessage, client, mbkMessage.getOfficeCode()))
		{
			return createSuccessResult(mbkMessage);
		}
		else
		{
            return createErrorResult(mbkMessage, true, "Сбой миграции");
		}
	}

	/**
	 * Необходимо объединить информацию из шины для миграции
	 * По CRDWI не приходят типы документа, в CEDBO нет карточного клиента
	 * поэтому
	 *  для udbo - возвращается по cedbo
	 *  нет - интерпретируются как паспорт way
	 * @param crdwiClient клиент, полученный по платежной карте
	 * @param cedboClient клиент из CEDBO (может быть пуст для карточного клиента)
	 * @return клиент
	 */
	private Client mergeClientInfo(Client crdwiClient, Client cedboClient)
	{
		if (cedboClient != null)
			return cedboClient;

		ClientImpl client = new ClientImpl(crdwiClient.getFirstName(), crdwiClient.getPatrName(), crdwiClient.getSurName());
		client.setBirthDay(crdwiClient.getBirthDay());
		client.setId(crdwiClient.getId());
		client.setOffice(crdwiClient.getOffice());

		List<ClientDocument> documents = new ArrayList<ClientDocument>(crdwiClient.getDocuments().size());
		//по CRDWI приходит только серия-номер, интерпретируется как паспорт WAY
		for (ClientDocument clientDocument : crdwiClient.getDocuments())
		{
			ClientDocumentImpl document = new ClientDocumentImpl();
			document.setDocumentType(ClientDocumentType.PASSPORT_WAY);
			document.setDocSeries(DocumentHelper.getPassportWayNumber(clientDocument.getDocSeries(), clientDocument.getDocNumber()));
			document.setDocIdentify(true);
			documents.add(document);
		}
		client.setDocuments(documents);

		return client;
	}

	/**
	 * Получить карты по фио, дул (как Way), др из мбк и пилотному ТБ
	 */
	private List<Card> getCardsViaGFL(MBKRegistration message) throws LogicException, SystemException
	{
		List<Card> cards = new ArrayList<Card>();

		Client fakeClient = new FakeClient(message.getOwner());

		ClientProductsService productsService = GateSingleton.getFactory().service(ClientProductsService.class);
		GroupResult<Class, List<Pair<Object, AdditionalProductData>>> gflResult = productsService.getClientProducts(fakeClient, Card.class);
		for (Pair<Object, AdditionalProductData> card : GroupResultHelper.getResult(gflResult, Card.class))
		{
			cards.add((Card) card.getFirst());
		}
		return cards;
	}

	private List<Card> getAdditionalInfoCards(List<String> infoCards, List<Card> cards)
	{
		List<Card> result = new ArrayList<Card>(cards.size());
		for (Card card : cards)
		{
			if (card.getAdditionalCardType() == AdditionalCardType.CLIENTTOOTHER && infoCards.contains(card.getNumber()))
			{
				result.add(card);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Set<MigrationClient> getOwnersViaCRDWI(List<Card> additionalInfoCards, Code officeCode) throws LogicException, SystemException
	{
		Set<MigrationClient> result = new HashSet<MigrationClient>();
		for (Card card : additionalInfoCards)
		{
			Client client = getClientByCard(card.getNumber(), officeCode);
			result.add(new MigrationClient(client));
		}
		return result;
	}

	private Client getClientByCard(String cardNumber, Code officeCode) throws LogicException, SystemException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		//noinspection unchecked
		return GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(new Pair<String, Office>(cardNumber, new OfficeImpl(officeCode))));
	}

	private void updateErmbProfile(MBKRegistration mbkMessage, ErmbProfileImpl profile) throws LogicException, SystemException
	{
		String mbkPhoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(mbkMessage.getPhoneNumber());
		ErmbProfileEvent profileEvent = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));
		ErmbResourseParamsEvent resourceEvent = new ErmbResourseParamsEvent(profile);
		resourceEvent.setOldData(profile.getCardLinks(), profile.getAccountLinks(), profile.getLoanLinks());

		//обновить информацию по тарифу
		//в случае прихода тарифа "пакет 3" - не менять
		MbkTariff mbkTariff = mbkMessage.getTariff();
		if (mbkTariff != MbkTariff.PACKAGE3)
		{
			ErmbTariff tariff = tariffService.getTariffByCode(mbkTariff.getStringCode());
			ErmbHelper.updateErmbTariff(profile, tariff);
		}

		//добавить телефон в качестве активного (если не было)
		boolean contains = profile.getPhoneNumbers().contains(mbkPhoneNumber);
		if (!contains)
		{
			for (ErmbClientPhone phone : profile.getPhones())
				phone.setMain(false);

			ErmbClientPhone phone = new ErmbClientPhone(mbkPhoneNumber, profile);
			phone.setMain(true);
			profile.getPhones().add(phone);
		}

		//оповещение mss + sms
		profileEvent.setNewProfile(profile);
		resourceEvent.setNewData(profile.getCardLinks(), profile.getAccountLinks(), profile.getLoanLinks());
		ErmbUpdateListener updateListener = ErmbUpdateListener.getListener();
		updateListener.beforeProfileUpdate(profileEvent);
		profileService.addOrUpdate(profile);
		updateListener.afterProfileUpdate(profileEvent);
		updateListener.onResoursesUpdate(resourceEvent);

		//оповещение csa
		if (!contains)
		{
			UserInfo userInfo = PersonHelper.buildUserInfo(profile.getPerson());
			CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(mbkPhoneNumber, userInfo, Collections.<String>singletonList(mbkPhoneNumber), Collections.<String>emptyList());
		}

		ermbPhoneService.saveOrUpdateERMBPhones(Collections.singletonList(mbkPhoneNumber), Collections.<String>emptyList());
	}

	private void excludeCorporateFromInfoCards(List<String> infoCards)
	{
		for (Iterator<String> it = infoCards.iterator(); it.hasNext(); )
		{
			String cardNumber = it.next();
			if (CardsUtil.isCorporate(cardNumber))
				it.remove();
		}
	}

	private boolean containsAllCards(List<Card> cards, String paymentCardNumber, List<String> infoCardNumbers)
	{
		List<String> actualCardNumbers = new ArrayList<String>(cards.size());
		for (Card card : cards)
		{
			actualCardNumbers.add(card.getNumber());
		}

		return actualCardNumbers.contains(paymentCardNumber) && actualCardNumbers.containsAll(infoCardNumbers);
	}

	private boolean isSamePerson(Client gateClient, Person person)
	{
		String gateName = gateClient.getFirstName() + StringHelper.getEmptyIfNull(gateClient.getPatrName()) + gateClient.getSurName();
		String eribName = person.getFirstName() + StringHelper.getEmptyIfNull(person.getPatrName()) + person.getSurName();
		if (!StringHelper.equalsAsPersonName(gateName, eribName))
			return false;
		if (!DateUtils.isSameDay(gateClient.getBirthDay(), person.getBirthDay()))
			return false;

		return true;
	}

	/**
	 * @param clientCards - карты клиента (из GFL)
	 * @param mbkMessageInfoCards - информационные карты в связке
	 * @return true, если в качестве информационной карты в связке
	 * указана хотя бы одна основная карта, открытая на имя владельца платёжной карты.
	 */
	private boolean containsMainCard(List<Card> clientCards, List<String> mbkMessageInfoCards)
	{
		if (CollectionUtils.isEmpty(clientCards))
			return false;

		if (CollectionUtils.isEmpty(mbkMessageInfoCards))
			return false;

		List<String> mainCardNumbers = new ArrayList<String>(clientCards.size());
		for (Card card : clientCards)
		{
			if (card.isMain() && card.getAdditionalCardType() != AdditionalCardType.CLIENTTOOTHER)
				mainCardNumbers.add(card.getNumber());
		}
		return CollectionUtils.containsAny(mbkMessageInfoCards, mainCardNumbers);
	}
}
