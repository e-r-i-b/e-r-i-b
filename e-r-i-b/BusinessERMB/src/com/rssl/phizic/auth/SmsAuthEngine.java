package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Properties;

/**
 * @author Erkin
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация движка аутентификации для СМС-канала ЕРМБ.
 * Сводится к поиску профиля ЕРМБ по номеру телефона.
 */
public class SmsAuthEngine extends AuthEngineBase
{
	private static final String MB_SERVICE_IS_BLOCKED_MESSAGE = "Операция не выполнена. " +
			"Для проведения операции необходимо  разблокировать услугу Мобильный банк. " +
			"Обратитесь в контактный центр Сбербанка.";

	private static final AccessPolicyService accessPolicyService = new AccessPolicyService();

	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private static final PersonService personService = new PersonService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param manager - менеджер движков
	 */
	public SmsAuthEngine(EngineManager manager)
	{
		super(manager);
	}

	public PersonSession authenticateByPhone(PhoneNumber phone)
	{
		// Ищем ЕРМБ профиль клиента
		ErmbProfileImpl profile = findProfile(phone);
		return createSession(profile);
	}

	public PersonSession authenticateByPerson(Person person)
	{
		// Ищем ЕРМБ профиль клиента
		ErmbProfileImpl profile = findProfile(person);
		return createSession(profile);
	}

	private PersonSession createSession(ErmbProfileImpl profile)
	{
		// 1. Проверяем статус подключения, блокировку клиента и блокировку по неоплате.
		if (!profile.isServiceStatus() || profile.isClientBlocked() || profile.isPaymentBlocked())
			throw new UserErrorException(new TextMessage(MB_SERVICE_IS_BLOCKED_MESSAGE));

		if (log.isDebugEnabled())
			log.debug("К нам пришёл клиент УИК=" + profile.getId());

		// 2. Проверяем наличие завершённой аутентификации
		PersonSession session = getExistsAuth(profile);

		// (3) Создаём новую аутентификацию
		if (session == null)
			session = createNewAuth(profile);

		// 4. Установка уровня безопасности в зависимости от доступности быстрых сервисов
		SecurityType securityType = profile.getFastServiceAvailable() ? SecurityType.LOW : SecurityType.HIGHT;
		Person person = session.getPerson();
		person.setSecurityType(securityType);
		person.saveStoreSecurityType(securityType);
		try
		{
			personService.update(person);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка при сохранения персоны", e);
		}

		return session;
	}

	private PersonSession getExistsAuth(ErmbProfileImpl profile)
	{
		SessionEngine sessionEngine = getModule().getSessionEngine();
		PersonSession session = sessionEngine.getSession(profile.getPerson(), false);

		if (session != null) {
			// Если движок сессий вернул сессию, значит, эта сессия уже лежит в контексте потока
			if (SecurityUtil.isAuthenticationComplete())
				return session;
		}

		return null;
	}

	private PersonSession createNewAuth(ErmbProfileImpl profile)
	{
		SessionEngine sessionEngine = getModule().getSessionEngine();

		Person person = profile.getPerson();
		Login login = person.getLogin();

		AuthenticationConfig authConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
		AccessPolicy policy = authConfig.getPolicy(AccessType.simple);

		PersonSession session = null;
		try
		{
			// 0. Получаем и чистим сессию
			session = sessionEngine.getSession(profile.getPerson(), true);
			session.clear();

			// 1. Устанавливаем контекст аутентификации
			AuthenticationContext context = new AuthenticationContext(policy);
			context.setVisitingMode(UserVisitingMode.BASIC);
			AuthenticationContext.setContext(context);

			// 2. Получаем права
			Properties properties = accessPolicyService.getProperties(login, policy.getAccessType());
			UserPrincipal principal = new PrincipalImpl(login, policy, properties);

			// 3. Устанавливаем модуль аутентификации
			SecurityUtil.createAuthModule(principal);
			SecurityUtil.completeAuthentication();

			// 4. Устанавливаем персон-дату
			PersonContext.getPersonDataProvider().setPersonData(new StaticPersonData((ActivePerson) person));

			// 5. Заполняем контекст лога
			ContextFillHelper.fillContextByLogin(login);

			if (log.isDebugEnabled())
				log.debug("Аутентифицировался ЕРМБ-клиент УИК=" + profile.getId());

			return session;
		}
		catch (SecurityDbException e)
		{
			// Аутентификация не удалась => бьём сессию
			sessionEngine.destroySession(session);
			throw new InternalErrorException(e);
		}
		catch (RuntimeException e)
		{
			// Аутентификация не удалась => бьём сессию
			sessionEngine.destroySession(session);
			throw e;
		}
	}

	private ErmbProfileImpl findProfile(PhoneNumber phone)
	{
		ErmbProfileImpl result = null;
		try
		{
			result = profileService.findByPhone(phone);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка получения профиля по телефону " + phone, e);
		}
		if (result == null)
			throw new UserErrorException(new MessageBuilder().buildProfileNotFoundErrorMessage());
		return result;
	}

	private ErmbProfileImpl findProfile(Person person)
	{
		ErmbProfileImpl result = null;
		try
		{
			result = profileService.findByUser(person);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException("Ошибка получения профиля по клиенту " + person, e);
		}
		if (result == null)
			throw new UserErrorException(new MessageBuilder().buildProfileNotFoundErrorMessage());
		return result;
	}
}
