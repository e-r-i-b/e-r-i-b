package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.TerminalConnector;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;

import java.util.List;

/**
 * @author krenev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 * Контекст идентфикации.
 * Абстракция по получению профиля = идентифкации пользователя.
 */

public class IdentificationContext
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private Profile profile;
	private String cbCode;
	private String cardNumber;
	private String userId;
	private List<String> cards;

	private IdentificationContext(Profile profile, String cbCode)
	{
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		this.profile = profile;
		this.cbCode = cbCode;
		fillLogThreadContext(profile);
	}

	private IdentificationContext(Profile profile, String cbCode, String cardNumber, String userId)
	{
		this(profile, cbCode);
		this.cardNumber = cardNumber;
		this.userId = userId;
	}

	private IdentificationContext(Profile profile, String cbCode, String cardNumber, String userId, List<String> cards)
	{
		this(profile, cbCode, cardNumber, userId);
		this.cards = cards;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public String getUserId()
	{
		return userId;
	}

	/**
	 * @return Список карт идентифицированного пользователя. Заполняется по возможности.
	 * В случае null, карты не были получены(контекст строился без интергации с ВС)
	 */
	public List<String> getCards()
	{
		return cards;
	}
	/**
	 * Создать контекст идентифкации по номеру карты
	 * @param cardNumber номер карты
	 * @return контекст идентифкации в случае успешного прохождения идентифцикации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByCardNumber(String cardNumber) throws Exception
	{
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты должен быть задан");
		}
		log.trace("проводим идентификацию пользователя по номеру карты " + Utils.maskCard(cardNumber));
		//получаем по возможности актуальную информацию о пользователе по номеру карты
		CSAUserInfo userInfo = SyncUtil.getUserInfoByCardNumber(cardNumber);
		if (userInfo == null)
		{
			throw new IdentificationFailedException("Не найдены данные о пользователе по карте " + Utils.maskCard(cardNumber));
		}
		Profile profile = SyncUtil.synchronize(userInfo);
		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("пользователь идентифицирован profileId=" + context.getProfile().getId());
		return context;
	}


	/**
	 * Создать контекст идентифкации по логину с непосредственным получением данных из ВС.
	 * @param login логин
	 * @return контекст идентифкации в случае успешного прохождения идентифцикации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByLoginDirect(String login)  throws Exception
	{
		if (StringHelper.isEmpty(login))
		{
			throw new IllegalArgumentException("Логин должен быть задан");
		}
		log.trace("проводим идентификацию пользователя по логину " + login);

		CSAUserInfo userInfo;
		if (Utils.isIPasLogin(login))
		{
			userInfo = SyncUtil.getActualUserInfoByUserId(login);
		}
		else
		{
			Connector connector = Connector.findByAlias(login);
			if (connector == null)
			{
				throw new IdentificationFailedException("Логин " + login + " не найдейн в БД ЦСА и не является логином iPas");
			}
			userInfo = connector.isMigrated() ? SyncUtil.getActualUserInfoByUserId(connector.getUserId()) : SyncUtil.getActualUserInfoByCardNumber(connector.getCardNumber());
		}

		if (userInfo == null)
		{
			throw new IdentificationFailedException("Не найдены данные о пользователе по логину " + login);
		}
		Profile profile = SyncUtil.synchronize(userInfo);

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("пользователь идентифицирован profileId = " + context.getProfile().getId());
		return context;
	}

	/**
	 * Создать контекст идентифкации по логину
	 * @param login логин
	 * @param skipSyncForIPasConnector пропускать ли синхронизацию для ipas логина.
	 * @return контекст идентифкации в случае успешного прохождения идентифцикации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByLogin(String login, boolean skipSyncForIPasConnector) throws Exception
	{
		if (StringHelper.isEmpty(login))
		{
			throw new IllegalArgumentException("Логин должен быть задан");
		}
		log.trace("проводим идентификацию пользователя по логину " + login);

		if (Utils.isIPasLogin(login))
		{
			return createByIPasLogin(login, skipSyncForIPasConnector);
		}

		Connector connector = Connector.findByAlias(login);
		if (connector == null)
		{
			throw new IdentificationFailedException("Логин " + login + " не найдейн в БД ЦСА и не является логином iPas");
		}

		if (connector instanceof TerminalConnector)
		{
			return createByIPasLogin(connector.getUserId(), skipSyncForIPasConnector);
		}

		//получаем по возможности актуальную информацию о пользователе по номеру карты(логину ipas мы не верим и клиент его не предъявлял)
		CSAUserInfo userInfo = SyncUtil.getUserInfoByCardNumber(connector.getCardNumber());
		if (userInfo == null)
		{
			throw new IdentificationFailedException("Не найдены данные о пользователе по логину " + login);
		}
		Profile profile = SyncUtil.synchronize(userInfo);

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("пользователь идентифицирован profileId = " + context.getProfile().getId());
		return context;
	}

	private static IdentificationContext createByIPasLogin(String iPasLogin, boolean skipSync) throws Exception
	{
		//При предъявлении ipas логина (или алиаса к нему) мы можем верить только ipas логину, а не сохранненной с ним карте.
		//В идеале, здесь нужно лезть в вей, чтобы определить истинного владельца, но... Увисас + МБК = нельзя.
		CSAUserInfo userInfo;
		Profile profile;
		if (!skipSync)
		{
			userInfo = SyncUtil.getUserInfoByUserId(iPasLogin);
			profile = SyncUtil.synchronize(userInfo);
		}
		else
		{
			Connector moreActualConnector = SyncUtil.getMoreActualConnector(Connector.findByUserId(iPasLogin));
			userInfo = moreActualConnector == null ? SyncUtil.getUserInfoByUserId(iPasLogin) : moreActualConnector.asUserInfo();
			profile = moreActualConnector == null ? SyncUtil.synchronize(userInfo) : moreActualConnector.getProfile();
		}

		if (userInfo == null)
		{
			throw new IdentificationFailedException("Не найдены данные о пользователе по логину " + iPasLogin);
		}

		IdentificationContext context = new IdentificationContext(profile, userInfo.getCbCode(), userInfo.getCardNumber(), userInfo.getUserId(), userInfo.getCards());
		log.trace("пользователь идентифицирован profileId = " + context.getProfile().getId());
		return context;
	}

	/**
	 * Создать контекст идентификации по номеру телефона
	 * @param phoneNumber номер телефона
	 * @return контекст идентификации
	 * @throws Exception
	 */
	public static IdentificationContext createByPhoneNumber(String phoneNumber) throws Exception
	{
		if (StringHelper.isEmpty(phoneNumber))
		{
			throw new IllegalArgumentException("Номер телефона должен быть задан");
		}

		log.trace("проводим идентификацию пользователя по телефону " + phoneNumber);
		Connector connector = Connector.findByPhoneNumber(phoneNumber);

		if (connector == null)
		{
			throw new IdentificationFailedException("Телефон " + phoneNumber + " не зарегистрирован для работы с ЕРМБ");
		}

		IdentificationContext identificationContext = new IdentificationContext(connector.getProfile(), connector.getCbCode());
		log.trace("пользователь идентифицирован profileId=" + connector.getProfile().getId());
		return identificationContext;
	}

	/**
	 * Создать контекст идентификации по идентифкатору заявки.
	 * Идентификация происходит по овнеру заявки
	 * @param ouid идентифкатор заявки
	 * @return контекст идентификации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByOperationUID(String ouid) throws Exception
	{
		if (StringHelper.isEmpty(ouid))
		{
			throw new IllegalArgumentException("Идентификатор заявки должен быть задан");
		}
		log.trace("проводим идентификацию пользователя по идентифкатору операции " + ouid);
		Operation operation = Operation.findByOUID(ouid);
		if (operation == null)
		{
			throw new IdentificationFailedException("Не найдена заявка с идентфикатором " + ouid);
		}
		IdentificationContext context = new IdentificationContext(operation.getProfile(), operation.getCbCode(), null, null);
		log.trace("пользователь идентифицирован profileId=" + context.getProfile().getId());
		return context;
	}

	/**
	 * Создать контекст идентификации по идентифкатору коннектора
	 * @param guid идентификатор коннектора
	 * @return контекст идентификации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByConnectorUID(String guid) throws Exception
	{
		return createByConnectorUID(guid, false);
	}

	/**
	 * Создать контекст идентификации по идентифкатору коннектора
	 * @param guid идентификатор коннектора
	 * @param withoutSync проводить или нет попытку синхронизации при идентификации
	 * @return контекст идентификации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByConnectorUID(String guid, boolean withoutSync) throws Exception
	{
		if (StringHelper.isEmpty(guid))
		{
			throw new IllegalArgumentException("Идентификатор коннектора должен быть задан");
		}
		log.trace("Проводим идентифкацию пользователя по идентификатору коннектора " + guid);
		Connector connector = Connector.findByGUID(guid);
		if (connector == null)
		{
			throw new IdentificationFailedException("Не найден коннектор с идентификатором " + guid);
		}
		return createByConnector(connector, withoutSync);
	}

	/**
	 * Создать контекст идентификации по идентифкатору сессии
	 * @param sid идентификатор коннектора
	 * @return контекст идентификации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createBySessionId(String sid) throws Exception
	{
		if (StringHelper.isEmpty(sid))
		{
			throw new IllegalArgumentException("Идентификатор сессии должен быть задан");
		}
		log.trace("Проводим идентифкацию пользователя по идентификатору сессии " + sid);
		Session session = Session.findBySid(sid);
		if (session == null)
		{
			throw new IdentificationFailedException("Не найдена сессия " + sid);
		}
		return createByConnectorUID(session.getConnectorGuid(), true);
	}

	/**
	 * Создать конетекст идетификации по ФИО, ДУЛ, ДР, ТБ
	 * Если профиль не найден - создаем
	 * @param userInfo информация о пользователе
	 * @param securityType уровень безопасности для случая создания нового профиля
	 * @return конетекст идентификаиции
	 * @throws Exception
	 */
	public static IdentificationContext createByUserInfo(CSAUserInfo userInfo, SecurityType securityType) throws Exception
	{
		Profile profile = Profile.getByUserInfo(userInfo, true);

		return new IdentificationContext(profile == null ? Profile.create(userInfo, securityType) : profile, userInfo.getCbCode());
	}

	/**
	 * Созщдать контекст идентификации по шаблону профиля.
	 * @param template - шиблон профиля
	 * @return уонтекст идентификации
	 * @throws Exception
	 */
	public static IdentificationContext createByTemplateProfile(Profile template) throws Exception
	{
		Profile profile = Profile.getByTemplate(template, false);
		if (profile == null)
		{
			throw new IdentificationFailedException("Не найдена информация о пользователе");
		}
		return new IdentificationContext(profile, null);
	}

	/**
	 * провести идентифкацию по коннектору
	 * @param connector коннектор, не может быть null
	 * @param withoutSync проводить ли актуализацию инфы?
	 * @return контекст идентифкации
	 * @throws Exception
	 */
	private static IdentificationContext createByConnector(Connector connector, boolean withoutSync) throws Exception
	{
		if (connector == null)
		{
			throw new IllegalArgumentException("Коннектор не может быть null");
		}
		//Будем пробовать проводить синхронизацию?
		if (withoutSync)
		{
			// не будем. строим контекст идентфикации по имеющимся данным.
			IdentificationContext context = new IdentificationContext(connector.getProfile(), connector.getCbCode(), connector.getCardNumber(), connector.getUserId());
			log.trace("пользователь идентифицирован profileId=" + context.getProfile().getId());
			return context;
		}
		//будем синронизироваться. коннектор мигрирован?
		if (connector.isMigrated())
		{
			//да. по карте нельзя, ибо ее может не быть. идентифицируем и сливаем по логину iPas.
			return createByLogin(connector.getUserId(), false);
		}
		//фуф. не мигрирован. карта надежнее: в отличие от логина iPas не меняется (но закрыться может.. но это совсем другая история).
		return createByCardNumber(connector.getCardNumber());
	}

	/**
	 * Инициализация LogThreadContext данными пользователя
	 * @param profile профиль
	 */
	private void fillLogThreadContext(Profile profile)
	{
		LogThreadContext.setFirstName(profile.getFirstname());
		LogThreadContext.setPatrName(profile.getPatrname());
		LogThreadContext.setSurName(profile.getSurname());
		LogThreadContext.setBirthday(profile.getBirthdate());
		LogThreadContext.setNumber(profile.getPassport());
		LogThreadContext.setDepartmentCode(profile.getTb());
	}

	/**
	 * Найти операцию заданного типа по идентифкатору с проверкой принадлежности пользователя. Поиск происходит за время жизни операции, начиная от текущего времени минус lifeTime
	 * @param operationClass класс операции
	 * @param ouid идентфикатор операции
	 * @param lifeTime время жизни операции/глубина поиска в секундах
	 * @return непротухшая операция .
	 * @throws com.rssl.auth.csa.back.exceptions.OperationNotFoundException если операция не найдена.
	 */
	public <T extends Operation> T findOperation(Class<T> operationClass, String ouid, int lifeTime) throws Exception
	{
		log.trace("получаем сериализованную операцию " + operationClass + " по идентифкатору " + ouid);
		T operation = Operation.findLifeByOUID(operationClass, ouid, lifeTime);
		if (!operation.getProfileId().equals(getProfile().getId()))
		{
			throw new OperationNotFoundException("Операция " + ouid + " принадлежит другому пользователю");
		}
		return operation;
	}

	/**
	 * Создать контекст идентификации по токуну аутентификации.
	 * Идентификация происходит по AuthenticationOperation
	 * @param authToken токен аутенитфикации
	 * @return контекст идентификации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public static IdentificationContext createByAuthToken(String authToken) throws Exception
	{
		if (StringHelper.isEmpty(authToken))
			throw new IllegalArgumentException("Токен аутенитфикации должен быть задан");

		log.trace("проводим идентификацию пользователя по токену аутенитфикации " + authToken);
		AuthenticationOperation operation = AuthenticationOperation.findByAuthToken(authToken);
		if (operation == null)
			throw new IdentificationFailedException("Не найдена заявка с токеном " + authToken);

		Connector connector = operation.getConnector();
		IdentificationContext context = new IdentificationContext(operation.getProfile(), operation.getCbCode(), connector.getCardNumber(), connector.getUserId());
		log.trace("пользователь идентифицирован profileId=" + context.getProfile().getId());
		return context;
	}

	/**
	 * Проверить актуальность тоекна аутентификации
	 * @param authToken токен аутенитфикации
	 * @throws Exception внутрениие ошибки
	 * @throws IdentificationFailedException при невозможности идентификации пользователя
	 */
	public void checkAuthToken(String authToken) throws Exception
	{
		log.trace("получаем сериализованную операцию " + AuthenticationOperation.class + " по идентифкатору " + authToken);
		AuthenticationOperation operation = AuthenticationOperation.findLifeByAuthToken(authToken);
		if (!operation.getProfileId().equals(getProfile().getId()))
			throw new OperationNotFoundException("Операция " + authToken + " принадлежит другому пользователю");
	}
}
