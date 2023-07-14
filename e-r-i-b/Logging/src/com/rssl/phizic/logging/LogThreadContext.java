package com.rssl.phizic.logging;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.quick.pay.Constants;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.MDC;

import java.util.Calendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контекст для хранения информации для записи логов
 * Существует в рамках нити
 */
public class LogThreadContext
{
	private static final String IP_ADDRESS  = "IP_ADDRESS";
	private static final String SESSION_ID  = "SESSION_ID";
	private static final String LOGIN_ID    = "LOGIN_ID";
	private static final String LOGIN       = "LOGIN";
	private static final String SYSTEM_LOG_WRITER_KEY = "SystemLogWriter";
	private static final Object FLAG                  = new Object();


	private static final String PERSON_ID    = "PERSON_ID";//ид клиента или сотрудника
	private static final String USER_ID    = "USER_ID";//userId клиента или сотрудника
	private static final String PERSON_SURNAME    = "PERSON_SURNAME";//фамилия клиента или сотрудника
	private static final String PERSON_PATRNAME    = "PERSON_PATRNAME";//имя клиента или сотрудника
	private static final String PERSON_FIRSTNAME    = "PERSON_FIRSTNAME";//отчество клиента или сотрудника
	private static final String PERSON_SERIES    = "PERSON_SERIES";//серии документов клиента одной строкой через разделитель
	private static final String PERSON_NUMBER    = "PERSON_NUMBER";//номера документов клиента одной строкой через разделитель
	private static final String DEPARTMENT_CODE    = "DEPARTMENT_CODE";//ТБ клиента
	private static final String PERSON_BIRTHDAY   = "PERSON_BIRTHDAY";//дата рождения клиента
	private static final String DEPARTMENT_NAME    = "DEPARTMENT_NAME";//Имя департамента клиента или сотрудника
	private static final String PROC_NAME  = "PROC_NAME";
	private static final String CODE_ATM    = "CODE_ATM";// Номер устройства самообслуживания, с которого осуществляется вход
	private static final String CHIP_CARD_SIGN = "CHIP_CARD_SIGN"; // Признак того, что карта по которой зашёл клиент с чипом
	private static final String PROMOTER_ID = "PROMOTER_ID"; // идентификатор промоутера
	private static final String MGUID = "MGUID"; // mGUID
	private static final String LOG_UID = "LOG_UID"; // уникальный идентификатор действия для логирования.
	private static final String NODE_ID = "NODE_ID"; // идентификатор блока
	private static final String PERSON_TB_NUMBER = "PERSON_TB_NUMBER";
	private static final String PERSON_OSB_NUMBER = "PERSON_OSB_NUMBER";
	private static final String PERSON_VSP_NUMBER = "PERSON_VSP_NUMBER";

	private static final String INITIATOR_APPLICATION = "INITIATOR_APPLICATION";
	private static final String APP_SERVER_INFO = "APP_SERVER_INFO";
	private static final String GUEST_LOGIN = "GUEST_LOGIN";
	private static final String GUEST_PHONE_NUMBER  = "GUEST_PHONE_NUMBER";
	private static final String GUEST_CODE          = "GUEST_CODE";

	/**
	 * текущий ip-адрес
	 * @return
	 */
	public static String getIPAddress()
	{
		return (String)MDC.get(IP_ADDRESS);
	}

	public static void setIPAddress(String ipAddress)
	{
		if(ipAddress!=null)
			MDC.put(IP_ADDRESS, ipAddress);
	}

	/**
	 * текущий идентификатор сессии
	 * @return
	 */
	public static String getSessionId()
	{
		return (String)MDC.get(SESSION_ID);
	}

	public static void setSessionId(String sessionId)
	{
		MDC.put(SESSION_ID, sessionId);
	}

	/**
	 * идентификатор логина текущего пользоватля
	 * @return
	 */
	public static Long getLoginId()
	{
		return (Long)MDC.get(LOGIN_ID);
	}

	public static void setLoginId(Long loginId)
	{
		if (loginId == null)
		{
			MDC.remove(LOGIN_ID);
		}
		else
		{
			MDC.put(LOGIN_ID, loginId);
		}
	}

	public static String getLogin()
	{
		return (String)MDC.get(LOGIN);
	}

	public static void setLogin(String login)
	{
		if (login == null)
		{
			MDC.remove(LOGIN);
		}
		else
		{
			MDC.put(LOGIN, login);
		}
	}

	/**
	 * Текущее приложение: PhizIA, PhizIC,...
	 * @return
	 */
	public static Application getApplication()
	{
		return ApplicationInfo.getCurrentApplication();
	}

	/**
	 * флаг, о том что находимся внутри SystemLogWriter
	 */
	public static void setSystemLogWriter()
	{
		MDC.put(SYSTEM_LOG_WRITER_KEY, FLAG);
	}

	public static void removeSystemLogWriter()
	{
		MDC.remove(SYSTEM_LOG_WRITER_KEY);
	}

	public static boolean isSystemLogWriter()
	{
		return !(MDC.get(SYSTEM_LOG_WRITER_KEY)==null);
	}

	/**
	 * очистка контекста
	 */
	public static void clear()
	{
		if(MDC.getContext()!=null)
			MDC.getContext().clear();
	}

	public static Long getPersonId()
	{
		return (Long)MDC.get(PERSON_ID);
	}

	public static void setPersonId(Long personId)
	{
		if(personId!=null)
			MDC.put(PERSON_ID, personId);
	}

	public static String getUserId()
	{
		return (String)MDC.get(USER_ID);
	}

	public static void setUserId(String userId)
	{
		if(userId!=null)
			MDC.put(USER_ID, userId);
	}

	public static String getSurName()
	{
		return (String)MDC.get(PERSON_SURNAME);
	}

	public static void setSurName(String surName)
	{
		if(surName!=null)
			MDC.put(PERSON_SURNAME, surName);
	}

	public static String getPatrName()
	{
		return (String)MDC.get(PERSON_PATRNAME);
	}

	public static void setPatrName(String patrName)
	{
		if(patrName!=null)
			MDC.put(PERSON_PATRNAME, patrName);
	}

	public static String getFirstName()
	{
		return (String)MDC.get(PERSON_FIRSTNAME);
	}

	public static void setFirstName(String firstName)
	{
		if(firstName!=null)
			MDC.put(PERSON_FIRSTNAME, firstName);
	}

	public static String getDepartmentName()
	{
		return (String)MDC.get(DEPARTMENT_NAME);
	}

	public static void setDepartmentName(String departmentName)
	{
		if(departmentName!=null)
			MDC.put(DEPARTMENT_NAME, departmentName);
	}

	public static String getDepartmentCode()
	{
		return (String)MDC.get(DEPARTMENT_CODE);
	}

	public static void setDepartmentCode(String departmentCode)
	{
		if(departmentCode!=null)
			MDC.put(DEPARTMENT_CODE, departmentCode);
	}

	public static String getSeries()
	{
		return (String)MDC.get(PERSON_SERIES);
	}

	public static void setSeries(String series)
	{
		if(series!=null)
			MDC.put(PERSON_SERIES, series);
	}

	public static String getNumber()
	{
		return (String)MDC.get(PERSON_NUMBER);
	}

	public static void setNumber(String number)
	{
		if(number!=null)
			MDC.put(PERSON_NUMBER, number);
	}

	public static Calendar getBirthday()
	{
		return (Calendar)MDC.get(PERSON_BIRTHDAY);
	}

	public static void setBirthday(Calendar birthday)
	{
		if(birthday!=null)
			MDC.put(PERSON_BIRTHDAY, birthday);
	}

	public static String getProcName()
	{
		return (String) MDC.get(PROC_NAME);
	}

	public static void setProcName(String procName)
	{
		if(procName!=null)
			MDC.put(PROC_NAME, procName);
	}

	public static String getCodeATM()
	{
		return (String) MDC.get(CODE_ATM);
	}

	public static void setCodeATM(String codeATM)
	{
		if(codeATM != null)
			MDC.put(CODE_ATM, codeATM);
	}

	public static List<Long> getClickedBlockIds()
	{
		return (List<Long>) MDC.get(Constants.CLICKED_BLOCK_IDS);
	}

	public static void setClickedBlockIds( List<Long> panelIds)
	{
		if(CollectionUtils.isNotEmpty(panelIds))
			MDC.put(Constants.CLICKED_BLOCK_IDS, panelIds);
	}

	public static void setPromoterID(String promoterId)
	{
		if (promoterId != null)
			MDC.put(PROMOTER_ID, promoterId);
	}

	public static String getPromoterID()
	{
		return (String)MDC.get(PROMOTER_ID);
	}

	public static void setMGUID(String mGUID)
	{
		if (mGUID != null)
			MDC.put(MGUID, mGUID);
	}

	public static String getMGUID()
	{
		return (String)MDC.get(MGUID);
	}

	public static void setChipCard(String isChipCard)
	{
		MDC.put(CHIP_CARD_SIGN, Boolean.parseBoolean(isChipCard));
	}

	public static Boolean isChipCard()
	{
		return (Boolean) MDC.get(CHIP_CARD_SIGN);
	}

	public static void setLogUID(String logUID)
	{
		MDC.put(LOG_UID, logUID);
	}

	public static String getLogUID()
	{
		return (String)MDC.get(LOG_UID);
	}

	public static String getDepartmentRegion()
	{
		return (String) MDC.get(PERSON_TB_NUMBER);
	}

	public static void setDepartmentRegion(String tbNumber)
	{
		if(tbNumber != null)
			MDC.put(PERSON_TB_NUMBER, tbNumber);
	}

	public static String getDepartmentOSB()
	{
		return (String) MDC.get(PERSON_OSB_NUMBER);
	}

	public static void setDepartmentOSB(String osbNumber)
	{
		if(osbNumber != null)
			MDC.put(PERSON_OSB_NUMBER, osbNumber);
	}

	public static String getDepartmentVSP()
	{
		return (String) MDC.get(PERSON_VSP_NUMBER);
	}

	public static void setDepartmentVSP(String vspNumber)
	{
		if(vspNumber != null)
			MDC.put(PERSON_VSP_NUMBER, vspNumber);
	}

	/**
	 * задать приложение, инициировавшее обращение к текущему приложению
	 * @param application приложение
	 */
	public static void setInitiatorApplication(Application application)
	{
		if (application == null)
			MDC.remove(INITIATOR_APPLICATION);
		else
			MDC.put(INITIATOR_APPLICATION, application);
	}

	/**
	 * @return приложение, инициировавшее обращение к текущему приложению
	 */
	public static Application getInitiatorApplication()
	{
		Application application = (Application) MDC.get(INITIATOR_APPLICATION);
		if (application != null)
			return application;

		return getApplication();
	}

	/**
	 * Имя, порт сервера и IP DataPower в формате: AAA…AAA;BBBB;CCC…CCC; - где
	 * AAA…AAA и BBBB – соответственно имя и порт сервера;
	 * ССС…ССС – IP DataPower, через который работает клиент.
	 * @return AAA…AAA;BBBB;CCC…CCC; - имя, порт сервера и IP DataPower в формате
	 */
	public static String getAppServerInfo()
	{
		return (String) MDC.get(APP_SERVER_INFO);
	}

	public static void setAppServerInfo(String appServerInfo)
	{
		if (StringHelper.isNotEmpty(appServerInfo))
			MDC.put(APP_SERVER_INFO, appServerInfo);
	}

	public static String getGuestLogin()
	{
		return (String) MDC.get(GUEST_LOGIN);
	}

	public static void setGuestLogin(String guestLogin)
	{
		MDC.put(GUEST_LOGIN, guestLogin);
	}

	/**
	 * @return номер телефона гостевого клиента
	 */
	public static String getGuestPhoneNumber()
	{
		return (String) MDC.get(GUEST_PHONE_NUMBER);
	}

	/**
	 * @param guestPhoneNumber номер телефона гостевого клиента
	 */
	public static void setGuestPhoneNumber(String guestPhoneNumber)
	{
		if (StringHelper.isNotEmpty(guestPhoneNumber))
		{
			MDC.put(GUEST_PHONE_NUMBER, guestPhoneNumber);
		}
	}

	/**
	 * @return является контекст гостевым
	 */
	public static boolean isGuest()
	{
		return StringHelper.isNotEmpty(getGuestPhoneNumber());
	}

	/**
	 * @return код гостевого клиента
	 */
	public static Long getGuestCode()
	{
		return (Long) MDC.get(GUEST_CODE);
	}

	/**
	 * @param guestCode код гостевого клиента
	 */
	public static void setGuestCode(Long guestCode)
	{
		MDC.put(GUEST_CODE, guestCode);
	}

	/**
	 * Возвращает идентификатор блока
	 * (Обратите внимание, что в блоке номер получается через ApplicationAutoRefreshConfig)
	 *
	 * @return идентификатор блока
	 */
	public static Long getNodeId()
	{
		return (Long) MDC.get(NODE_ID);
	}

	/**
	 * Установить идентификатор блока
	 * @param nodeId идентификатор блока
	 */
	public static void setNodeId(Long nodeId)
	{
		if (nodeId == null)
		{
			MDC.remove(NODE_ID);
			return;
		}

		MDC.put(NODE_ID, nodeId);
	}
}
