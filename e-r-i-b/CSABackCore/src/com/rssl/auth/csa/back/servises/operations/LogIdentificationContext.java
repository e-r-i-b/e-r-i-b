package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author vagin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 * Обертка над IdentificationContext. С параметрами идентификации для логирования.
 */
public class LogIdentificationContext implements LogProfileIdentificationContext
{
	private IdentificationContext identificationContext;
	private IdentificationType identificationType;
	private String identificationParam;

	LogIdentificationContext(IdentificationContext context, IdentificationType type, String param)
	{
		identificationContext = context;
		identificationType = type;
		identificationParam = param;
	}

	public String getIdentificationParam()
	{
		return identificationParam;
	}

	public IdentificationType getIdentificationType()
	{
		return identificationType;
	}

	public IdentificationContext getIdentificationContext()
	{
		return identificationContext;
	}

	public static LogIdentificationContext createByCardNumber(String cardNumber) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByCardNumber(cardNumber), IdentificationType.cardNumber, cardNumber);
	}

	public static LogIdentificationContext createByLogin(String login, boolean skipSynkForIPasConnector) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByLogin(login, skipSynkForIPasConnector), IdentificationType.login, login);
	}

	public static LogIdentificationContext createByLoginDirect(String login) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByLoginDirect(login), IdentificationType.login, login);
	}

	public static LogIdentificationContext createByPhoneNumber(String phoneNumber) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByPhoneNumber(phoneNumber), IdentificationType.phoneNumber, phoneNumber);
	}

	public static LogIdentificationContext createByOperationUID(String ouid) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByOperationUID(ouid), IdentificationType.OUID, ouid);
	}

	public static LogIdentificationContext createByConnectorUID(String guid) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByConnectorUID(guid), IdentificationType.connector, guid);
	}

	public static LogIdentificationContext createByConnectorUID(String guid, boolean withoutSync) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByConnectorUID(guid, withoutSync), IdentificationType.connector, guid);
	}

	public static LogIdentificationContext createBySessionId(String sid) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createBySessionId(sid), IdentificationType.sessionId, sid);
	}

	public static LogIdentificationContext createByTemplateProfile(Profile template) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByTemplateProfile(template), IdentificationType.UID, identParamToString(template));
	}

	public static LogIdentificationContext createByAuthToken(String authToken) throws Exception
	{
		return new LogIdentificationContext(IdentificationContext.createByAuthToken(authToken), IdentificationType.authToken, authToken);
	}

	/**
	 * Преобразование идентифицирующего объекта в читабельную для лога форму.
	 * @param profile - профиль пользователя.
	 * @return строка вида ФИО:<фио>;ДУЛ:<паспорт>...
	 */
	private static String identParamToString(Profile profile)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ФИО:").append(profile.getSurname()).append(" ").append(profile.getFirstname()).append(" ").append(profile.getPatrname()).append(";");
		sb.append("ДУЛ:").append(profile.getPassport()).append(";");
		sb.append("Дата рождения:").append(DateHelper.formatDateToString(profile.getBirthdate())).append(";");
		sb.append("ТБ:").append(profile.getTb());
		return sb.toString();
	}
}
