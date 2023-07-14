package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.ClientDataImpl;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.SecurityTypeHelper;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.auth.csa.back.servises.connectors.SocialAPIConnector;
import com.rssl.auth.csa.back.servises.restrictions.operations.MobileConnecotrsCountRestriction;
import com.rssl.auth.csa.back.servises.restrictions.security.MAPIPasswordSecurityRestriction;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.push.PushDeviceStatus;
import com.rssl.phizic.messaging.push.PushTransportService;
import com.rssl.phizic.messaging.push.PushTransportServiceImpl;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileRegistrationOperation extends ConfirmableOperationBase
{
	private static final String CARD_NUMBER_PARAM = "cardNumber";
	private static final String DEVICE_INFO_PARAM = "deviceInfo";
	private static final String DEVICE_ID_PARAM = "deviceId";
	private static final String USER_ID_PARAM = "userId";
	private static final String REGISTRATION_LOGIN_TYPE_PARAM = "registrationLoginType";

	private String appName;

	private boolean isPasswordChanged;
	private boolean isExecuted;
    private List<String> userCards;
	private static final String ANDROID_DEVICE = "android";

	public MobileRegistrationOperation() {}

	public MobileRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
		setCardNumber(identificationContext.getCardNumber());
		userCards = identificationContext.getCards();

		setUserId(identificationContext.getUserId());
	}

    protected void setAppName(String appName)
    {
        this.appName = appName;
    }

    public void setPasswordChanged(boolean passwordChanged)
    {
        isPasswordChanged = passwordChanged;
    }

    public void setExecuted(boolean executed)
    {
        isExecuted = executed;
    }

    protected String getUserId()
	{
		return getParameter(USER_ID_PARAM);
	}

    protected void setUserId(String userId)
	{
		addParameter(USER_ID_PARAM, userId);
	}

    protected void setDeviceInfo(String deviceInfo)
	{
		addParameter(DEVICE_INFO_PARAM, deviceInfo);
	}

    protected String getDeviceInfo()
	{
		return getParameter(DEVICE_INFO_PARAM);
	}

    protected String getCardNumber()
	{
		return getParameter(CARD_NUMBER_PARAM);
	}

    protected void setCardNumber(String cardNumber)
	{
		addParameter(CARD_NUMBER_PARAM, cardNumber);
	}

    protected void setDeviceId(String deviceId)
	{
		addParameter(DEVICE_ID_PARAM, deviceId);
	}

	public String getDeviceId()
	{
		return getParameter(DEVICE_ID_PARAM);
	}

    public List<String> getUserCards()
    {
        return userCards;
    }

    protected void setRegistrationLoginType(LoginType registrationLoginType)
	{
		addParameter(REGISTRATION_LOGIN_TYPE_PARAM, registrationLoginType.name());
	}

    protected LoginType getRegistrationLoginType()
	{
		return LoginType.valueOf(getParameter(REGISTRATION_LOGIN_TYPE_PARAM));
	}

	/**
	 * ѕроинициализировать операцию
	 * @param deviceInfo информаци€ об устройстве.
	 * @param confirmStrategyType тип подтверждени€
	 * @param deviceId - уникальный идентификатор устройства
	 * @param registrationLoginType тип логина, по которому регистрируетс€ приложение
	 * @throws TooManyMobileConnectorsException при превышении допустимого количества мобильных устройств
	 * @throws com.rssl.auth.csa.back.exceptions.TooManyRequestException при превышении допустимого количества наподтврежденных запросов.
	 */
	public void initialize(String deviceInfo, ConfirmStrategyType confirmStrategyType, String deviceId, String appName, LoginType registrationLoginType) throws Exception
	{
		setDeviceInfo(deviceInfo);
		setRegistrationLoginType(registrationLoginType);
		if (StringHelper.isNotEmpty(deviceId))
			setDeviceId(deviceId);
		this.appName = appName;
		super.initialize(confirmStrategyType);
	}

	public Connector execute(final String passwordValue, final String deviceState, final String deviceId, final String appName) throws Exception
	{
		MAPIPasswordSecurityRestriction.getInstance().check(passwordValue);
		this.appName = appName;
		Connector connector = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Profile profile = lockProfile();
				preExecute(deviceId);
                MobileConnecotrsCountRestriction.getInstance().check(MobileRegistrationOperation.this);

				PasswordBasedConnector result = new MAPIConnector(getOuid(), getUserId(), getCbCode(), getCardNumber(), getDeviceInfo(), deviceState, profile, deviceId, getRegistrationLoginType());
				result.save();

				if (profile.getSecurityType() == null)
				{
					//≈сли клиент зарегистрировалс€ до переноса уровн€ безопасности в ÷—ј и ни разу не входил, то у профил€ уровень безопасности не выставлен
					profile.setSecurityType(SecurityTypeHelper.calcSecurityType(profile, getCardNumber(), result.getType()));
					profile.save();
				}

				isPasswordChanged = result.changePassword(passwordValue);
				return result;
			}
		});
		isExecuted = true;

		sendNotification(getCardNumber());
		return connector;
	}

	protected void sendNotification(String cardNumber) throws Exception
	{
		try
		{
			String key = getClass().getName() + ".notification";
			Map<String, String> params = new HashMap<String, String>();
			params.put("{applicationName}", appName);
			params.put("{0}", DateHelper.formatDateToStringWithSlash2(Calendar.getInstance()));
			params.put("{2}", getIO());

			String text = CSASmsResourcesOperation.getFormattedSmsResourcesText(key, params);
			SendMessageInfo sendMessageInfo = new SendMessageInfo(getProfileId(), cardNumber, new MessageInfoImpl(text, text), false, GetRegistrationMode.SOLF);

			SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
		}
		catch (Exception e)
		{
			log.error("Ќе удалось отправить оповещени€ по номеру карты " + Utils.maskCard(cardNumber), e);
		}
	}

	/**
	 * Ќайти и закрыть все коннекторы, у которых дублируетс€ deviceId
	 * @param deviceId - уникальный идентификатор устройства
	 * @throws Exception
	 */
    protected void preExecute(String deviceId) throws Exception
	{
		List<Connector> duplicateConnectors = Connector.findByDeviceId(deviceId);
		PushTransportService pushTransportService = new PushTransportServiceImpl();
		for (Connector connector : duplicateConnectors)
		{
			connector.close();
			if (connector.getPushSupported())
				pushTransportService.registerEvent(connector.getDeviceId(), new ClientDataImpl(connector.getProfile()), PushDeviceStatus.DELETE, null, connector.getGuid());		}
	}

	/**
	 * Ќайти и закрыть все коннекторы той же платформы, что и регистрируема€ (дл€ социальных)
	 * @param profile - профиль
	 * @throws Exception
	 */
    protected void preSocialExecute(Profile profile) throws Exception
	{
		List<SocialAPIConnector> connectors = SocialAPIConnector.findNotClosedByProfileID(profile.getId());
		PushTransportService pushTransportService =  new PushTransportServiceImpl();
		for (SocialAPIConnector connector : connectors)
		{
			if(connector.getDeviceInfo().equals(getDeviceInfo()))
			{
				connector.close();
				if (connector.getPushSupported())
					pushTransportService.registerEvent(connector.getDeviceId(), new ClientDataImpl(connector.getProfile()), PushDeviceStatus.DELETE, null, connector.getGuid());
			}
		}

	}

	/**
	 * ѕолучить информацию о том помен€лс€ ли пароль дл€ доступа к мобильному приложению.
	 * ћетод можно выполн€ть только после успешного вызова execute
	 * @return информаци€ о том помен€лс€ ли пароль дл€ доступа к мобильному приложению.
	 * @throws IllegalStateException в случае, если Ќе был вызван метод execute или или при его вызове произошла ошибка
	 */
	public boolean isPasswordChanged() throws IllegalStateException
	{
		if (!isExecuted)
		{
			throw new IllegalStateException("Ќе был вызван метод execute или или при его вызове произошла ошибка");
		}
		return isPasswordChanged;
	}

	public String getCardNumberFoSendConfirm()
	{
		return getCardNumber();
	}

	protected boolean useAlternativeRegistrationsMode()
	{
		return true;
	}

	/**
	 * ѕроверка IMSI дл€ андроида определ€етс€ настройкой
	 * @return true == проверка IMSI дл€ Android нужна или не знаем какое приложение или приложение не андроид
	 */
	@Override
	protected boolean useIMSICheck()
	{
		boolean needCheckIMSIForAndroid = ConfigFactory.getConfig(DocumentConfig.class).isNeedCheckImsiForAndroid();
		return needCheckIMSIForAndroid || getDeviceInfo() == null || !getDeviceInfo().toLowerCase().equals(ANDROID_DEVICE);
	}

	/**
	 * @return врем€ жизни за€вки
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getMobileRegistrationTimeout();
	}

	public String getAppName()
	{
		return appName;
	}
}
