package com.rssl.phizic.business.ermb.migration.list.task.migration;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbClientSearchHelper;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.config.ErmbListMigrationConfig;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Conflict;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationInfo;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.task.PhoneActivity;
import com.rssl.phizic.business.ermb.migration.list.task.TaskLog;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ClientService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.common.counters.CounterException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.*;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils.*;
import static com.rssl.phizic.business.ermb.migration.list.task.TaskType.START_MIGRATION;

/**
 * Выполнение списковой миграции
 * @author Puzikov
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MigratorList extends MigratorBase
{
	private static final ClientService clientService = new ClientService();

	private final Long currentBlock;
	private final TaskLog log;
	private final MigrationStats stats;

	/**
	 * ctor
	 * @param currentBlock текущий блок, в котором запущена миграция
	 */
	public MigratorList(Long currentBlock) throws IOException, CounterException
	{
		this.log = new TaskLog(START_MIGRATION);
		log.add("Миграция в блоке " + currentBlock + " началась");
		this.currentBlock = currentBlock;
		this.stats = new MigrationStats();
	}

	/**
	 * Запустить миграцию
	 * @param client клиент, которого нужно смигрировать
	 */
	public void migrate(Client client) throws BusinessException
	{
		try
		{
			//сделать запрос CEDBO по данным из csv
			ErmbClientSearchHelper clientSearchHelper = new ErmbClientSearchHelper();
			com.rssl.phizic.gate.clients.Client cedboClient = clientSearchHelper.findClient(
					client.getLastName(),
					client.getFirstName(),
					StringHelper.getNullIfEmpty(client.getMiddleName()),
					client.getBirthday(),
					null,
					//передавать паспорт WAY в поле номер по интерфейсу
					client.getDocument(),
					ClientDocumentType.PASSPORT_WAY,
					StringHelper.getNullIfEmpty(client.getTb())
			);
			checkUdboState(client, cedboClient);

			//поиск в БД текущего блока приложения
			ActivePerson eribClient = getEribPerson(client);
			if (eribClient == null)
			{
				boolean isInflatable = currentBlock.equals(ConfigFactory.getConfig(ErmbListMigrationConfig.class).getInflatableBlockNumber());
				if (isInflatable)
				{
					eribClient = createPerson(client, cedboClient);
				}
				else
				{
					sendToNextBlock(client);
					stats.pass(client);
					return;
				}
			}

			MigrationHelper.initContext(eribClient);

			MigrationInfo info = migrate(client, eribClient);
			if (info != null && (info.getMbkMigrationId() != null || info.getMbvMigrationId() != null))
				clientService.saveMigrationInfo(info);
			clientService.setMigrationStatus(client, MigrationStatus.MIGRATED);
			stats.ok(client);
		}
		catch (UnresolvedConflictException e)
		{
			if (log != null)
			{
				log.add(e.getMessage());
			}
			stats.error(client);
			clientService.markClientBlockById(client.getId(), null);
		}
		catch (MigrationLogicException e)
		{
			if (log != null)
			{
				log.add("Ошибка во время миграции клиента id=" + client.getId() + ". " + e.getMessage());
			}
			stats.error(client);
			clientService.setMigrationError(client);
		}
		catch (Exception e)
		{
			if (log != null)
			{
				log.add("Ошибка во время миграции клиента id=" + client.getId())
				.add(e);
			}
			stats.error(client);
			clientService.setMigrationError(client);
		}
		finally
		{
			if (log != null)
				log.flush();
		}
	}

	/**
	 * Записать в лог информацию о работе мигратора
	 */
	public void flushInfo()
	{
		log.add("Миграция в блоке " + currentBlock + " завершилась")
		.add(stats.getInfo())
		.flush();
	}

	private void sendToNextBlock(Client client) throws Exception
	{
		Long nextBlock = getNextBlock();
		clientService.markClientBlockById(client.getId(), nextBlock);
	}

	private MigrationInfo migrate(Client segmentedClient, ActivePerson eribClient) throws Exception
	{
		//Подгрузка актуальных данных
		List<Conflict> conflicts = clientService.findConflictsByClient(segmentedClient.getId(), false);
		ErmbProfileImpl profile = ErmbHelper.getErmbProfileByPerson(eribClient);

		clientResourcesService.updateResources(eribClient, false);
		List<CardLink> cardLinks = externalResourceService.getLinks(eribClient.getLogin(), CardLink.class, null);
		Set<MbkCard> cards = MigrationHelper.getCardsToMigrate(cardLinks);

		MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(eribClient.asClient());
		//Проверка возможности миграции
		checkData(segmentedClient, conflicts, profile, cards, mbvInfo);

		//Обработка сценариев с доп. картами
		if (segmentedClient.getSegment_5())
			processFifthSegment(segmentedClient);

		//Поиск телефонов, которые войдут в ЕРМБ профиль, и активного телефона профиля
		Set<Phone> possiblePhones = getAllErmbPhones(segmentedClient, conflicts);
		ErmbPhones ermbPhones = getPhonesForProfile(segmentedClient, possiblePhones);

		//телефоны для миграционных запросов в мобильный банк
		Set<String> mbvPhones = getMbvPhoneNumbers(ermbPhones.phones);
		Set<String> mbvPhoneNumberDelete = getMbvPhoneNumberDelete(segmentedClient.getPhones(), conflicts);
		//для телефонов, помеченных к удалению, запускать миграцию точно так же (но без проноса телефона в профиль)
		mbvPhones.addAll(mbvPhoneNumberDelete);
		//Передать на миграцию в мбк все телефоны которые войдут в профиль
		Set<String> mbkPhoneNumbersMigrate = getPhoneNumbers(ermbPhones.phones);
		Set<String> mbkPhoneNumberDelete = getMbkPhoneNumberDelete(segmentedClient.getPhones(), conflicts);

		MigrationInfo migrationInfo = new MigrationInfo();
		migrationInfo.setClient(segmentedClient);
		migrationInfo.setMigrationDate(Calendar.getInstance());
		try
		{
			//-------------------
			//BEGIN MIGRATION MBV
			//-------------------
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& CollectionUtils.isNotEmpty(mbvPhones))
			{
				if (CollectionUtils.isNotEmpty(mbvInfo.getIdentities()))
				{
					UUID mbvMigrationId = getMbvService().beginMigration(mbvInfo.getIdentities().get(0));
					migrationInfo.setMbvMigrationId(mbvMigrationId);
				}
			}
			//-------------------
			//BEGIN MIGRATION MBK
			//-------------------
			Collection<MbkConnectionInfo> mbkConnections = new ArrayList<MbkConnectionInfo>();
			if (!mbkPhoneNumbersMigrate.isEmpty() || !mbkPhoneNumberDelete.isEmpty())
			{
				BeginMigrationResult mbkBeginResult = getMbkService().beginMigrationErmb(cards, mbkPhoneNumbersMigrate, mbkPhoneNumberDelete, MigrationType.LIST);
				Long migrationId = mbkBeginResult.getMigrationId();
				if (migrationId == null)
					throw new GateException("Не удалось проинициализировать миграционную транзакцию МБК");
				migrationInfo.setMbkMigrationId(migrationId);
				mbkConnections.addAll(mbkBeginResult.getMbkConnectionInfo());
			}

			//если найден активный телефон для ермб, создать профиль
			if (ermbPhones.active != null)
			{
				profile = createOrUpdateErmbProfile(profile, eribClient, getPhoneNumbers(ermbPhones.phones), ermbPhones.active.getPhoneNumber());
				updateProfileWithMbkData(profile, mbkConnections, segmentedClient.getSegments());
				//При миграции клиента сегмента 3_2_3 нужно для всех вкладов установить признак видимости в канале ЕРМБ в положение "выключено"
				//для 4-го сегмента: видимость вкладов так же д.б. выключена
				if (segmentedClient.getSegment_3_2_3() || segmentedClient.getSegment_4())
					hideAccountsInSmsChannel(eribClient);
			}
		}
		//При любых ошибках прекратить миграционные транзакции в мобильном банке
		catch (Exception e)
		{
			//----------------------
			//ROLLBACK MIGRATION MBK
			//----------------------
			try
			{
				if (migrationInfo.getMbkMigrationId() != null)
				{
					getMbkService().rollbackMigration(migrationInfo.getMbkMigrationId());
				}
			}
			catch (Exception e1)
			{
				log.add("Не удалось сделать ROLLBACK миграции МБК id=" + migrationInfo.getMbkMigrationId())
				.add(e1);
				if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
						&& migrationInfo.getMbvMigrationId() != null)
				{
					getMbvService().rollbackMigration(migrationInfo.getMbvMigrationId());
				}
				throw e1;
			}

			//----------------------
			//ROLLBACK MIGRATION MBV
			//----------------------
			if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
					&& migrationInfo.getMbvMigrationId() != null)
			{
				getMbvService().rollbackMigration(migrationInfo.getMbvMigrationId());
			}

			throw e;
		}
		//----------------------
		//COMMIT MIGRATION MBK
		//----------------------
		List<CommitMigrationResult> commitResult = null;
		if (migrationInfo.getMbkMigrationId() != null)
		{
			commitResult = getMbkService().commitMigrationErmb(migrationInfo.getMbkMigrationId());
		}
		//----------------------
		//COMMIT MIGRATION MBV
		//----------------------
		if (ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability()
				&& migrationInfo.getMbvMigrationId() != null)
		{
			getMbvService().commitMigration(migrationInfo.getMbvMigrationId());
		}

		if (migrationInfo.getMbkMigrationId() != null && profile != null)
			setTariffInfo(profile, commitResult);
		if (ermbPhones.active != null)
		{
			//Сохранение профиля
			boolean sendWelcomeSms = ConfigFactory.getConfig(ErmbListMigrationConfig.class).isDefaultWelcomeSms();
			ErmbHelper.saveCreatedProfile(profile, sendWelcomeSms);
			ermbPhoneService.saveOrUpdateERMBPhones(profile.getPhoneNumbers(), Collections.<String>emptyList());

			sendCsaUpdatePhones(profile, ermbPhones.active.getPhoneNumber(), getPhoneNumbers(ermbPhones.phones));
			sendErmbConnected(profile);

			//Смс по результатам миграции
			try
			{
				Set<String> nonActivePhoneNumbers = getNonActivePhoneNumbers(ermbPhones.phones, ermbPhones.active);
				MigrationSmsSender sender = new MigrationSmsSender(segmentedClient, ermbPhones.active, nonActivePhoneNumbers);
				sender.send();
			}
			catch (Exception e)
			{
				log.add("Не удалось отправить СМС по результатам миграции. id=" + segmentedClient.getId())
						.add(e);
			}
		}

		return migrationInfo;
	}

	/**
	 * Найти телефоны, которые войдут в профиль клиента
	 * Здесь делается разрешение конфликта 3+4, и выбор активного телефона
	 * @param segmentedClient клиент из мигратора
	 * @param possiblePhones телефоны, которые могут принадлежать клиенту
	 * @return активный телефон, все телефоны
	 * @throws MigrationLogicException
	 */
	private ErmbPhones getPhonesForProfile(Client segmentedClient, Set<Phone> possiblePhones) throws MigrationLogicException
	{
		ErmbPhones ermbPhones = new ErmbPhones();

		//автоматическое разрешение конфликта 3/4
		if (segmentedClient.getSegment_3() && segmentedClient.getSegment_4())
		{
			PhoneActivity phoneActivity = new PhoneActivity(segmentedClient, possiblePhones);
			PhoneActivity.Result result = phoneActivity.calculate();
			ermbPhones.active = result.getMain();
			ermbPhones.phones.addAll(result.getPhones());
		}
		//для сегмента 4 (без 3) нужно выбрать активный телефон по тому же алгоритму
		else if (segmentedClient.getSegment_4())
		{
			if (CollectionUtils.isEmpty(possiblePhones))
				throw new MigrationLogicException("Техническая ошибка Мигратора: Для клиента из сегмента 4 не найдено телефонов");
			PhoneActivity phoneActivity = new PhoneActivity(segmentedClient, possiblePhones);
			PhoneActivity.Result result = phoneActivity.calculate();
			ermbPhones.active = result.getMain();
			//если алгоритм не нашел подходящего телефона, выбрать любой
			if (ermbPhones.active == null)
			{
				ermbPhones.active = possiblePhones.iterator().next();
			}

			ermbPhones.phones.addAll(possiblePhones);
		}
		//иначе все телефоны идут в профиль
		else if (CollectionUtils.isNotEmpty(possiblePhones))
		{
			ermbPhones.active = possiblePhones.iterator().next();
			ermbPhones.phones.addAll(possiblePhones);
		}

		//если не найдено телефонов для ЕРМБ профиля нужно отдельно проверить на ошибки,
		//т.к. телефоны могут пропасть при миграции и информация по услуге будет потеряна
		if (ermbPhones.active == null)
		{
			//такая ситуация по бизнесу может возникнуть:
			//  - при удалении неудачников разрешения конфликтов (3 сегмент)
			//  - при подчистке владельцев доп. карт (5 сегмент)
			if (!(segmentedClient.getSegment_3() || segmentedClient.getSegment_5()))
				throw new MigrationLogicException("Техническая ошибка Мигратора: не найдено телефонов для профиля ЕРМБ");

			log.add("WARNING Профиль ЕРМБ не создается. Данные в МБК будут миграционно удалены. clientId=" + segmentedClient.getId());
		}

		return ermbPhones;
	}

	//Проверка возможности миграции
	private void checkData(Client segmentedClient, List<Conflict> conflicts, ErmbProfileImpl profile, Set<MbkCard> cards, MbvRegistrationInfo mbvInfo)
			throws IKFLException
	{
		if (segmentedClient.getSegments().isEmpty())
			throw new MigrationLogicException("По результатам сегментации клиент не попал ни в один сегмент.");

		if (segmentedClient.getSegment_5_1())
			throw new MigrationLogicException("Автоматическая миграция клиентов Сегмента 5_1 не осуществляется.");

		if (hasUnresolvedConflict(conflicts))
			throw new UnresolvedConflictException("У клиента имеются неразрешенные конфликты. id=" + segmentedClient.getId());

		if (profile != null && profile.isServiceStatus())
			throw new MigrationLogicException("Ермб-профиль клиента уже существует. profileId=" + profile.getId());
		//TODO профиль в другом блоке?

		if (segmentedClient.getSegment_4())
			checkAndThrow4SegmentActivity(segmentedClient);

		String[] cardNumbers = new String[cards.size()];
		int i = 0;
		for (MbkCard card : cards)
		{
			cardNumbers[i++] = card.getNumber();
		}

		//загрузить регистрации мбк (по платежным+информационным картам, mb_WWW_GetRegistrations2)
		List<MobileBankRegistration> mbkRegistrations = new ArrayList<MobileBankRegistration>();
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			List<MobileBankRegistration> registrations = getMbkService().getRegistrationsPack(null, cardNumbers);
			mbkRegistrations.addAll(registrations);
		}
		else
		{
			GroupResult<String, List<MobileBankRegistration>> groupResult = getMbkService().getRegistrations(null, cardNumbers);
			for (String cardNumber : cardNumbers)
			{
				List<MobileBankRegistration> result = GroupResultHelper.getResult(groupResult, cardNumber);
				if (result != null)
				{
					mbkRegistrations.addAll(groupResult.getResult(cardNumber));
				}
			}
		}
		checkAndThrowNewPhones(segmentedClient.getPhones(), mbkRegistrations, mbvInfo);
	}

	/**
	 * Проверка возможности миграции 4 сегмента (более одного телефона)
	 * 4 сегмент нельзя мигрировать в следующей ситуации
	 *  - все телефоны клиента неактивны (не было отправки смс)
	 *  - При этом если последний телефон зарегистрирован во временном интервале 5 календарный дней от регистрации другого номера(ов) телефона(ов), или в ту же дату, что и другой телефон(ы)
	 * @param segmentedClient клиент из 4 сегмента
	 * @throws MigrationLogicException нельзя мигрировать
	 */
	private void checkAndThrow4SegmentActivity(Client segmentedClient) throws MigrationLogicException
	{
		Set<Phone> phones = segmentedClient.getPhones();
		if (phones.size() < 2)
			throw new MigrationLogicException("Техническая ошибка мигратора: у клиента из 4 сегмента менее 2 телефонов.");

		//есть активный телефон - ок
		for (Phone phone : phones)
		{
			if (phone.isLastSmsActivity())
				return;
		}

		Phone lastPhone = Collections.max(phones, new Comparator<Phone>()
		{
			public int compare(Phone o1, Phone o2)
			{
				return DateHelper.nullSafeCompare(o1.getRegistrationDate(), o2.getRegistrationDate());
			}
		});

		//ни по одному телефону не известна дата регистрации - ок
		if (lastPhone.getRegistrationDate() == null)
			return;

		//дата регистрации телефонов не попадает в промежуток 5 дней от последнего
		Calendar lastRegistrationDate = lastPhone.getRegistrationDate();
		for (Phone phone : phones)
		{
			boolean error = !phone.equals(lastPhone)
					&& phone.getRegistrationDate() != null
					&& DateHelper.daysDiff(phone.getRegistrationDate(), lastRegistrationDate) <= 5;
			if (error)
				throw new MigrationLogicException("Невозможна миграция клиента 4 сегмента со следующими условиями: "
						+ "1. Отсутствуют активные телефоны."
						+ "2. Последний телефон зарегистрирован во временном интервале 5 календарный дней от регистрации другого номера(ов) телефона(ов)");
		}
	}

	/**
	 * Проверяет совпадает ли список номеров телефонов из бд мигратора с актуальным
	 * Выкидывает исключение, если не совпадает
	 * @param phones телефоны из бд мигратора
	 * @param mbkRegistrations связки мбк по картам
	 * @param mbvInfo информация по текущему подключению МБВ
	 */
	private void checkAndThrowNewPhones(Set<Phone> phones, Collection<MobileBankRegistration> mbkRegistrations, MbvRegistrationInfo mbvInfo)
			throws MigrationLogicException
	{
		Set<String> mbkPhones = ErmbHelper.getPhonesFromMbkRegistrations(mbkRegistrations);
		Set<String> mbvPhones = mbvInfo.getPhones();

		if (!isSamePhones(phones, mbkPhones, mbvPhones))
		{
			StringBuilder messageBuilder = new StringBuilder("С момента загрузки csv у клиента изменился набор телефонов в МБК или МБВ.");
			messageBuilder.append(" БД Мигратора(МБК):");
			messageBuilder.append(getMaskedPhones(getMbkPhoneNumbers(phones)));
			messageBuilder.append(" МБК:");
			messageBuilder.append(getMaskedPhones(mbkPhones));
			messageBuilder.append(" БД Мигратора(МБВ):");
			messageBuilder.append(getMaskedPhones(getMbvPhoneNumbers(phones)));
			messageBuilder.append(" МБВ:");
			messageBuilder.append(getMaskedPhones(mbvPhones));

			throw new MigrationLogicException(messageBuilder.toString());
		}
	}

	//Исключить из списка телефонов, которые нельзя мигрировать из-за доп. карт
	private void processFifthSegment(Client client) throws MigrationLogicException
	{
		Iterator<Phone> it = client.getPhones().iterator();
		while (it.hasNext())
		{
			Phone phone = it.next();
			//проверять только телефоны, к которым привязаны доп. карты
			if (phone.isHasAdditional())
			{
				//должен был попасть в 5_1
				if (phone.getVipOrMbc())
				{
					throw new MigrationLogicException("Клиент (либо один из владельцев дополнительных карт в мбк клиента) является ВИП/МВС. " +
							"Автоматическая миграция не осуществляется.");
				}

				//может быть подключен в профиль основного клиента, только в случае выявления В МБК основных информационных карт клиента, подключенных на этот номер
				//или наличие МБВ
				if (phone.isAdditionalCardOwner())
				{
					if (!phone.isHasMain() || isMbvPhone(phone))
						it.remove();
				}
				//владельцев доп. карт не подключаем
				else
				{
					it.remove();
				}
			}
		}
	}

	private void checkUdboState(Client client, com.rssl.phizic.gate.clients.Client cedboClient) throws MigrationLogicException
	{
		boolean oldUdbo = client.getUDBO();
		boolean newUdbo = cedboClient != null && cedboClient.isUDBO();

		if (oldUdbo && !newUdbo)
		{
			throw new MigrationLogicException("Клиент мигрирует как УДБО клиент, но подключение не найдено по CEDBO. Необходима пересегментация.");
		}
		if (!oldUdbo && newUdbo)
		{
			throw new MigrationLogicException("Клиент мигрирует как карточный клиент, но найдено подключение по CEDBO. Необходима пересегментация.");
		}
	}

	private Long getNextBlock()
	{
		List<Long> blockSequence = ConfigFactory.getConfig(ErmbListMigrationConfig.class).getMigrationBlockSequence();
		if (!blockSequence.contains(currentBlock))
			throw new ConfigurationException("Текущий блок №" + currentBlock + " не найден в конфиге списковой миграции");

		int indexNext = blockSequence.lastIndexOf(currentBlock) + 1;
		if (indexNext >= blockSequence.size())
			throw new ConfigurationException("Не найден следующий блок для текущего блока");
		else
			return blockSequence.get(indexNext);
	}

	private void hideAccountsInSmsChannel(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		List<AccountLink> accountLinks = externalResourceService.getLinks(person.getLogin(), AccountLink.class);
		for (AccountLink accountLink : accountLinks)
		{
			if (accountLink.getShowInSms())
			{
				accountLink.setShowInSms(false);
				externalResourceService.updateLink(accountLink);
			}
		}
	}
}
