package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.hibernate.LockMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 19.12.2012
 * @ $Author$
 * @ $Revision$
 * Утилитный класс для синхронизации ЦСА с ВС при изменении данных о клиенте.
 */
public class SyncUtil
{

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Получить актуальную информацию о пользователе без вязких офлайновостей.
	 * @param cardNumber номер карты клиента, по которому требуется получить данные
	 * @return информация о пользователе или null
	 * @throws SystemException
	 */
	public static CSAUserInfo getActualUserInfoByCardNumber(String cardNumber) throws SystemException, GateLogicException
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
		if (userInfo == null)
		{
			return null;
		}
		return new CSAUserInfo(userInfo, CSAUserInfo.Source.WAY4U);
	}

	/**
	 * Получить информацию о пользователе по номеру карты.
	 * По возможности используются актуальные данные из ВС( при выставленной настройке com.rssl.auth.csa.back.config.common.additional.request.for.synchronization.allowed).
	 * Если данные не получены из ВС, информация о пользователе строится на основе данных из БД ЦСА (возможно неактуальных)
	 * @param cardNumber номер карты клиента, по которому требуется получить данные
	 * @return информация о пользователе или null
	 */
	public static CSAUserInfo getUserInfoByCardNumber(String cardNumber) throws Exception
	{
		//Определяем “возможность отправки дополнительных запросов для синхронизации”
		if (ConfigFactory.getConfig(Config.class).isAdditionalRequestForSyncAllowed())
		{
			//Да разрешено. пробуем получить актуальную инфу из МБ.
			try
			{
				return getActualUserInfoByCardNumber(cardNumber);
			}
			catch (Exception e)
			{
				//Что за беда. МБ лежит. залогируем и будем пробовать автономно получать инфу на основе данных ЦСА.
				log.error("Ошибка получения информации о пользователе по номеру карты " + Utils.maskCard(cardNumber), e);

				CSAUserInfo userInfo = getUserInfoByCardNumberFromDB(cardNumber);
				if (userInfo != null)
				{
					return  userInfo;
				}
				//автономно не получилось. ловите исключение.
				throw e;
			}
		}
		//банк переживает за вей, ограничивая запросы... будем пробовать работать автономно...
		CSAUserInfo userInfo = getUserInfoByCardNumberFromDB(cardNumber);
		if (userInfo != null)
		{
			return  userInfo;
		}
		//автономно не получается... инфа нужна из ВС.
		return getActualUserInfoByCardNumber(cardNumber);
	}

	private static CSAUserInfo getUserInfoByCardNumberFromDB(String cardNumber) throws Exception
	{
		List<Connector> connectors = Connector.findByCardNumber(cardNumber);
		Connector connector = getMoreActualConnector(connectors);
		if (connector != null)
		{
			return connector.asUserInfo();
		}
		return null;
	}

	/**
	 * Получить актуальную информацию о пользователе без вязких офлайновостей.
	 * @param userId логин iPas клиента, по которому требуется получить данные
	 * @return информация о пользователе или null
	 * @throws SystemException
	 */
	public static CSAUserInfo getActualUserInfoByUserId(String userId) throws SystemException, GateLogicException
	{
		UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByUserId(userId);
		if (userInfo == null)
		{
			return null;
		}
		return new CSAUserInfo(userInfo, CSAUserInfo.Source.WAY4U);
	}

	/**
	 * Получить информацию о пользователе по логину iPas (используем всегда прямой запрос в вей, т.к. логины ipas могут передаваться другим клиентам).
	 * @param userId логин iPas клиента, по которому требуется получить данные
	 * @return информация о пользователе или null
	 */
	public static CSAUserInfo getUserInfoByUserId(String userId) throws Exception
	{
		return getActualUserInfoByUserId(userId);
	}

	/**
	 * Получить наиболее актуальный коннектор из имеющегося списка.
	 * Более актуальным считается немигрированный коннектор с макс датой создания
	 * @param connectors список конекторов
	 * @return самый актуальный конектор или nul.
	 */
	public static Connector getMoreActualConnector(List<Connector> connectors)
	{
		if (connectors == null)
		{
			return null;
		}
		Connector actual = null;
		for (Connector connector : connectors)
		{
			if (connector.isMigrated())
			{
				continue; //мигрированный по-любому ни разу не актуален.
			}
			//Немигрирован. Поехали сравнивать даты создания
			if (actual == null || actual.getCreationDate().before(connector.getCreationDate()))
			{
				actual = connector;
			}
		}
		return actual;
	}

	/**
	 * Синхронизировать профили по переданной в параметре информации о пользователе.
	 * @param userInfo инфа о пользоватле
	 * @return профиль - итог синхронизации
	 * @throws Exception
	 */
	public static Profile synchronize(final CSAUserInfo userInfo) throws Exception
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		if (userInfo.getSource() == CSAUserInfo.Source.CSA)
		{
			log.trace("Пропускаем синхронизацию, т.к. данные получены из ЦСА.");
			return Profile.getByUserInfo(userInfo, false);
		}
		if (StringHelper.isEmpty(userInfo.getUserId()))
		{
			throw new IllegalArgumentException("Логин iPas не может быть null");
		}
		if (StringHelper.isEmpty(userInfo.getCardNumber()))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}

		return ActiveRecord.executeAtomic(new HibernateAction<Profile>()
		{
			public Profile run(org.hibernate.Session session) throws Exception
			{
				actualizeConnectorsInfo(userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCbCode());

				//получаем все профили, у которых есть коннектор c заданной картой
				Set<Profile> profiles = new HashSet<Profile>();
				//profiles.addAll(Profile.findByUserId(userInfo.getUserId()));//Логин ipas использовать нельзя для идентификации клиента.
				profiles.addAll(Profile.findByCardNumber(userInfo.getCardNumber()));

				log.trace("Получаем профиль по информации о пользователе");
				Profile actualProfile = Profile.getByUserInfo(userInfo, true);
				if (actualProfile == null)
				{
					log.trace("Пользователеля в БД нет. Проверяем наличие профиля, связанного с логином ipas " + userInfo.getUserId() + " или картой " + Utils.maskCard(userInfo.getCardNumber()));
					if (profiles.isEmpty())
					{
						log.trace("Нет ни одного профиля с привязанным логином " + userInfo.getUserId() + " или картой " + Utils.maskCard(userInfo.getCardNumber()) + ". Создаем профиль.");
						actualProfile = Profile.create(userInfo);
					}
					else
					{
						actualProfile = profiles.iterator().next();// Берем первый попавшийся профиль для обновления
						session.lock(actualProfile, LockMode.UPGRADE_NOWAIT);
						log.trace("Обновляем существующий профиль " + actualProfile.getId() + " новыми данными о пользователе");
						actualProfile.update(userInfo);
					}
				}
				//Надо ли обновлять уровень безопасности коннекторов (если происходило слияние профилей, надо разнести уровень безопасности актуального профиля по коннекторам)
				boolean needConnectorsSecurityTypeUpdate = false;
				//начинаем перепривязку коннекторов и заявок для каждого профиля из списка к актуальному
				for (Profile profile : profiles)
				{
					if (profile.getId().equals(actualProfile.getId()))
					{
						//сами к себе не будем перепривязывать.
						continue;
					}
					//удаляем узлы, привязанные к профилю
					ProfileNode.removeProfileNodes(profile);
					//переносим все связанное с неактуальным профилем в новый и удаляем старый
					profile.delete(actualProfile);
					needConnectorsSecurityTypeUpdate = true;
				}

				//если происходило слияние профилей, необходимо разнести уровнеь безопасности по коннекторам
				if (needConnectorsSecurityTypeUpdate)
				{
					//устанавливаем уровень безопасности актуального профиля всем незакрытым ЦСА и МАПИ коннекторам актуального профиля
					CSAConnector.setSecurityTypeToNotClosed(actualProfile);
					MAPIConnector.setSecurityTypeToNotClosed(actualProfile.getId(), actualProfile.getSecurityType());
					//затираем уровень безопасности актуального профиля
					actualProfile.setSecurityType(null);
					actualProfile.save();
				}

				log.trace("Проверяем наличие TERMINAL-коннектора для логина " + userInfo.getUserId());
				TerminalConnector terminalConnectorByUserId = TerminalConnector.findNotClosedByUserId(userInfo.getUserId());
				if (terminalConnectorByUserId == null)
				{
					TerminalConnector result = new TerminalConnector(userInfo.getUserId(), userInfo.getCbCode(), userInfo.getCardNumber(), actualProfile);
					result.save();
					log.trace("Автоматически создан TERMINAL-коннектор " + result.getGuid() + " на доступ в вебприложение по логину " + result.getUserId());
				}

				return actualProfile;
			}
		});
	}

	private static void actualizeConnectorsInfo(String cardNumber, String userId, String cbCode) throws Exception
	{
		log.trace("Начинаем актуализацию мигрированных коннекторов и закрытие коннекторов для логина iPas " + userId);
		TerminalConnector terminalConnector = TerminalConnector.findNotClosedByUserId(userId);
		if (terminalConnector != null)
		{
			if (terminalConnector.isMigrated())
			{
				log.info("Актуализируем в мигрированном коннекторе " + terminalConnector.getGuid() + " информацию о карте и подразделении ее выдавшем");
				terminalConnector.setCardNumber(cardNumber);
				terminalConnector.setCbCode(cbCode);
				terminalConnector.save();
			}
			else if (!terminalConnector.getCardNumber().equals(cardNumber))
			{
				log.info("Для логина iPas " + userId + " сменился номер карты(" + terminalConnector.getCardNumber() + "->" + cardNumber + "). Закрываем коннектор " + terminalConnector.getGuid());
				terminalConnector.close();
			}
		}

		log.trace("Начинаем актуализацию логинов iPas для карты " + Utils.maskCard(cardNumber));
		for (Connector connector : Connector.findByCardNumber(cardNumber))
		{
			//коннекторы без userId не должны попадать в эту выборку(BUG087098). Но на всякий случай их исключаем.
			if (connector.getUserId() != null && !connector.getUserId().equals(userId))
			{
				log.info("Актуализируем в коннекторе " + connector.getGuid() + " информацию о логине iPas. Изменяем userId c " + connector.getUserId() + " на " + userId);
				connector.setUserId(userId);
				connector.save();
			}
		}
	}
}
