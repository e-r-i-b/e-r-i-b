package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.gate.csa.MQInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.lang.BooleanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;

/**
 * @author Moshenko
 * @ created 07.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузчик новых связок МБК в задаче «Обработка подключений к МБК в ЕРМБ» (миграция на лету)
 * Реализует загрузку связок из МБК.
 * Раздаёт связки по блокам
 */
@Statefull
public class MBKRegistrationLoader
{
	/**
	 * Статусы загрузки
	 */
	@SuppressWarnings("PublicInnerClass")
	public static enum Status
	{
		ENOUGH,
		MORE;
	}

	/**
	 * Максимальное число попыток запуска одного Загрузчика
	 */
	private static final int MAX_RUN_COUNT = 10;

	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);

	private final MBKRegistrationParser parser = new MBKRegistrationParser();

	private final EribNodeResolver nodeResolver = new EribNodeResolver();

	private final ClientPassportResolver passportResolver = new ClientPassportResolver();

	private final MBKRegistrationResultDatabaseService resultDatabaseService = new MBKRegistrationResultDatabaseService();

	/**
	 * Размер пачки связок, которые Загрузчик обрабатывает вместе
	 */
	private final int packSize;

	/**
	 * Количество запусков Загрузчика
	 */
	private int runCount = 0;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 */
	public MBKRegistrationLoader()
	{
		MobileBankConfig mobileBankСonfig = ConfigFactory.getConfig(MobileBankConfig.class);
		mobileBankСonfig.doRefresh();

		packSize = mobileBankСonfig.getConfirmRegMaxValue();
		if (packSize < 1)
			throw new ConfigurationException("Размер пачки слишком мал: " + packSize);
	}

	/**
	 * Основной цикл работы загрузчика
	 * @return статус работы загрузчика
	 * @throws Exception - общий сбой загрузки
	 */
	public Status load() throws Exception
	{
		if (runCount++ >= MAX_RUN_COUNT)
			return Status.ENOUGH;

		// Ф3.1. Загрузчик запрашивает из МБК очередную пачку связок через вызов ХП mb_BATCH_ERMB_GetRegistrations.
		// За один вызов Загрузчика ХП вызывается единожды.
		// Если ХП возвращает ошибку, загрузчик аварийно завершает работу.
		List<MBKRegistration> registrations = mbkService.getMbkRegistrationsForErmb();
		// Ф3.2. МБК через resultset возвращает пачку связок.
		// Если пачка пуста, Загрузчик завершает работу с результатом ENOUGH.
		// Ф3.3. Загрузчик разбирает resultset со связками и валидирует их по формату.
		// Если не удалось прочитать ни одной связки, Загрузчик завершает работу с результатом ENOUGH.
		// Если не удалось прочитать ID связки, связка выбывает из дальнейшей обработки.
		// Связка, не прошедшая валидацию по иным причинам, помечается как неформатная с описанием ошибки.
		// Неформатные связки уходят в п. Ф3.6.
		if (registrations.isEmpty())
			return Status.ENOUGH;

		// Работаем по пачкам
		for (List<MBKRegistration> registrationPack : ListUtil.split(registrations, packSize))
		{
			// Ф3.4. Загрузчик проверяет статус связки по Таблице статусов связок.
			// Если связка не найдена, обработка связки продолжается.
			// Если по связке определён статус IN_PROCESS, обработка связки продолжается.
			// Если по связке определён статус PROCESSED, связка помечается как обработанная.
			// Обработанные связки уходят в п. Ф3.6.
			loadResults(registrationPack);

			// Загрузчик получает данные клиента по связке
			loadOwners(registrationPack);

			// Ф3.5. Для каждой связки Загрузчик определяет блок ЕРИБ.
			// Если блок определить не удалось, связка помечается как проблемная.
			// Связки, для которых удалось определить блок,  уходят в Ф3.12.
			// Связки, для которых блок ЕРИБ определить не удалось, уходят в п. Ф3.6.
			loadEribNodes(registrationPack);

			// Ф3.6. Проблемные и обработанные связки передаются в МБК через вызов ХП mb_BATCH_ERMB_RegistrationResult.
			// Ф3.7. МБК принимает результаты по связке.
			// В случае отказа МБК, Загрузчик логирует проблему по связке и продолжает работу.
			returnProcessedRegistrations(registrationPack);

			// Ф3.8. Загрузчик удаляет обработанные связки из Таблицы статусов связок.
			deleteAcceptedResults(registrationPack);

			// Ф3.9. Загрузчик подтверждает приём новых связок через вызов ХП mb_BATCH_ERMB_ConfirmRegistrationLoading.
			// Ф3.10. МБК принимает подтверждение приёма связок.
			// В случае отказа МБК, Загрузчик логирует проблему по связке и продолжает работу.
			confirmNewRegistrations(registrationPack);

			// Ф3.11. Загрузчик добавляет связку в Таблицу статусов связок со статусом IN_PROCESS
			// Ничего не делаем, т.к. в Таблице статусов связок хранятся только результаты обработки

			// Ф3.12. Загрузчик передаёт связки в Блок
			passRegistrationsToNodes(registrationPack);
		}

		for (MBKRegistration registration : registrations)
		{
			// Просим ещё связок, если хотя бы одна связка была успешно передана в блок
			if (BooleanUtils.isTrue(registration.getPassedToNode()))
				return Status.MORE;
		}

		return Status.ENOUGH;
	}

	/**
	 * Грузит результаты обработки связок
	 * Результаты записываются в связку в поле resultCode и errorDescr
	 * Сбой загрузки результатов расценивается как общий сбой Загрузчика
	 * @param registrations - все связки
	 */
	private void loadResults(List<MBKRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return;

		// 1. Формируем список ID необработанных связок
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// Обходим только валидные необработанные связки
			if (registration.isWellParsed() && registration.getResultCode() == null)
				ids.add(registration.getId());
		}

		// 2. Грузим список результатов
		List<MBKRegistrationResult> resultList = resultDatabaseService.loadRegistrationsResults(ids);
		if (resultList.isEmpty())
			return;

		// 3. Формируем мапу <id -> результат> из списка результатов
		Map<Long, MBKRegistrationResult> resultMap = new HashMap<Long, MBKRegistrationResult>(resultList.size());
		for (MBKRegistrationResult result : resultList)
			resultMap.put(result.id, result);

		// 4. Записываем результаты в связки
		for (MBKRegistration registration : registrations)
		{
			MBKRegistrationResult result = resultMap.get(registration.getId());
			if (result != null)
			{
				registration.setResultCode(result.resultCode);
				registration.setErrorDescr(result.errorDescr);
			}
		}
	}

	/**
	 * Получает ФИО ДУЛ ДР владельца платёжной карты связки
	 * Результаты записываются в связку в поле owner
	 * Сбой определения ФИО ДУЛ ДР расценивается как ошибка обработки связки и записывается в связку
	 * @param registrations - все связки
	 */
	private void loadOwners(List<MBKRegistration> registrations)
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		for (MBKRegistration registration : registrations)
		{
			// Обходим только валидные необработанные связки
			if (registration.isWellParsed() && registration.getResultCode() == null)
			{
				try
				{
					String card = registration.getPaymentCardNumber();
					Office office = new OfficeImpl(registration.getOfficeCode());
					Pair<String, Office> cardAndOffice = new Pair<String, Office>(card, office);

					// noinspection unchecked
					Client client = GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(cardAndOffice));
					if (client == null)
						// noinspection ThrowCaughtLocally
						throw new RuntimeException("Не удалось найти владельца карты " + MaskUtil.getCutCardNumber(card));

					String passport = passportResolver.getClientPassport(client);
					String tb = registration.getOfficeCode().getFields().get("region");
					registration.setOwner(new UserInfo(client, passport, tb, tb));
				}
				catch (Exception e)
				{
					log.error(e.getMessage(), e);
					registration.setResultCode(MBKRegistrationResultCode.ERROR_NOT_REG);
					registration.setErrorDescr("Клиент не найден по карте");
				}
			}
		}
	}

	/**
	 * Ищет блоки ЕРИБ, в которых должны обрабатываться связки
	 * Результаты поиска записываются в связку в поле node
	 * Сбой определения блока расценивается как ошибка обработки связки и записывается в связку
	 * @param registrations - все связки
	 */
	private void loadEribNodes(List<MBKRegistration> registrations)
	{
		for (final MBKRegistration registration : registrations)
		{
			// Обходим необработанные связки, по которым удалось определить владельца
			if (registration.getOwner() != null && registration.getResultCode() == null)
			{
				PhoneNumber phone = registration.getPhoneNumber();
				try
				{
					switch (registration.getFiltrationReason())
					{
						// Ф4.3. Если поле FiltrationReasonName равно «Телефон в ЕРМБ»,
						// номер Блока определяется запросом в ЦСА по номеру телефона в поле PhoneNumber.
						// Если ЦСА не возвращает номер Блока или вызов завершается сбоем,
						// алгоритм заканчивает работу с результатом «Блок не найден».
						case ERMB_PHONE:
							registration.setNode(nodeResolver.getNodeByPhone(phone));
							if (registration.getNode() == null)
								// noinspection ThrowCaughtLocally
								throw new IllegalArgumentException("Не найден блок по номеру телефона");
							break;

						// Ф4.4. Если поле FiltrationReasonName равно «Карта в ЕРМБ»,
						// номер Блока определяется запросом в ЦСА по ФИО ДУЛ ДР ТБ,
						// которые в свою очередь определяются запросом CRDWI по карте в поле PaymentCardNumber.
						// Если CRDWI не возвращает ФИО ДУЛ ДР ТБ,
						// либо ЦСА не возвращает номер Блока,
						// либо любой вызов завершаются сбоем,
						// алгоритм заканчивает работу с результатом «Блок не найден».
						case ERMB_CARD:
							registration.setNode(nodeResolver.getNodeByUserInfo(registration.getOwner()));
							if (registration.getNode() == null)
								// noinspection ThrowCaughtLocally
								throw new IllegalArgumentException("Не найден блок по владельцу платёжной карты");
							break;

						// Ф4.5. Если поле FiltrationReasonName равно «Пилотная зона»,
						// номер Блока определяется сначала по номеру телефона (как в п. Ф4.3), а затем по номеру карты (как в п. Ф4.4).
						// Если любой из способов завершается сбоем,
						// алгоритм заканчивает работу с результатом «Блок не найден».
						// Если ни один из способов не возвращает номер Блока, алгоритм возвращает номер Наполняемого Блока.
						case PILOT_ZONE:
							registration.setNode(nodeResolver.getNodeByPhone(phone));
							if (registration.getNode() == null)
								registration.setNode(nodeResolver.getNodeByUserInfo(registration.getOwner()));
							if (registration.getNode() == null)
								registration.setNode(nodeResolver.getNewUserAllowedNode());
							break;

						default:
							// noinspection ThrowCaughtLocally
							throw new UnsupportedOperationException("Неожиданный FiltrationReason " + registration.getFiltrationReason());
					}
				}
				catch (Exception e)
				{
					log.error("Не удалось определить блок ЕРИБ по связке " + registration, e);
					registration.setResultCode(MBKRegistrationResultCode.ERROR_NOT_REG);
					registration.setErrorDescr("Блок не найден");
				}
			}
		}
	}

	/**
	 * Возвращает в МБК обработанные связки
	 * Каждая связка передаётся отдельно
	 * Сбой передачи связки записывается в Системный журнал и далее игнорируется
	 * Успех передачи связки отмечается в связке флажком returnedToMBK
	 * (чтобы в дальнейшем такую связку можно было удалить из Таблицы результатов)
	 * @param registrations - все связки
	 */
	private void returnProcessedRegistrations(List<MBKRegistration> registrations)
	{
		for (MBKRegistration registration : registrations)
		{
			// Обходим только обработанные связки (в т.ч. невалидные)
			if (registration.getResultCode() != null)
			{
				try
				{
					mbkService.sendMbRegistrationProcessingResult(registration.getId(), registration.getResultCode(), registration.getErrorDescr());
					registration.setReturnedToMBK(true);
				}
				catch (Exception e)
				{
					log.error("Не удалось отправить результат по связке " + registration, e);
					registration.setReturnedToMBK(false);
				}
			}
		}
	}

	/**
	 * Удаляет результаты обработки связок, принятые в МБК
	 * Сбой удаления расценивается как общий сбой Загрузчика
	 * @param registrations - все связки
	 */
	private void deleteAcceptedResults(List<MBKRegistration> registrations) throws Exception
	{
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// Обходим только обработанные и принятые в МБК связки
			if (registration.getResultCode() != null && BooleanUtils.isTrue(registration.getReturnedToMBK()))
				ids.add(registration.getId());
		}
		if (ids.isEmpty())
			return;

		resultDatabaseService.removeByIds(ids);
	}

	/**
	 * Подтверждает в МБК приём новых связок
	 * Сбой подтверждения расценивается как общий сбой Загрузчика
	 * @param registrations - все связки
	 */
	private void confirmNewRegistrations(List<MBKRegistration> registrations) throws Exception
	{
		List<Long> ids = new LinkedList<Long>();
		for (MBKRegistration registration : registrations)
		{
			// Собираем необработанные связки, по которым удалось определить блок
			if (registration.getNode() != null && registration.getResultCode() == null)
				ids.add(registration.getId());
		}
		if (ids.isEmpty())
			return;

		mbkService.confirmMbRegistrationsLoading(ids);
	}

	/**
	 * Передаёт связки в обработку в блоках ЕРИБ
	 * Каждая связка передаётся отдельно
	 * Сбой отправки связки записывается в Системный журнал и далее игнорируется
	 * @param registrations - все связки
	 */
	private void passRegistrationsToNodes(List<MBKRegistration> registrations)
	{
		for (MBKRegistration registration : registrations)
		{
			// Отправляем необработанные связки, по которым удалось определить блок
			if (registration.getNode() != null && registration.getResultCode() == null)
			{
				MQInfo mqInfo = registration.getNode().getMbkRegistrationMQ();
				JMSQueueSender sender = new JMSQueueSender(mqInfo.getFactoryName(), mqInfo.getQueueName());

				try
				{
					sender.send(registration);
					registration.setPassedToNode(true);
				}
				catch (JMSException e)
				{
					log.error("Не удалось отправить очередь блока связку " + registration, e);
					registration.setPassedToNode(false);
				}
			}
		}
	}
}
