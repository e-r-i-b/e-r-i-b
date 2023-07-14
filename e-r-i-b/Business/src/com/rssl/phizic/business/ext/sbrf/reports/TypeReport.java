package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Mescheryakova
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class TypeReport
{
	/**
	 * -----------------------------------------
	 *  Бизнес-отчеты
	 * -----------------------------------------
	 */

	/*
	 Количество договоров по ТБ
	*/
	public final static char CONTRACT_TB = 't';

	/*
	Количество договоров по ОСБ
	*/
	public final static char CONTRACT_OSB = 'o';

	/*
	Количество договоров по ВСП
	*/
	public final static char CONTRACT_VSP = 'v';

	/*
	Количество договоров по OKP
	*/
	public final static char CONTRACT_OKR = 'c';

	/*
	 Активные пользователи по ТБ
	 */
	public final static char ACTIVE_USERS_TB = 'a';

	/*
	 Активные пользователи по ВСП
	 */
	public final static char ACTIVE_USERS_VSP = 'b';

	/*
	 Количество операций по СБРФ
	 */
	public final static char OPERATIONS_SBRF = 'i';

	/*
	 Количество операций по тб
	 */
	public final static char OPERATIONS_TB = 'd';

	/*
	 Количество операций по ВСП
	 */
	public final static char OPERATIONS_VSP = 'e';



	/**
	 * -----------------------------------------
	 *  ИТ-отчеты
	 * -----------------------------------------
	 */

	/*
		Отчет по бизнес – параметрам на за период
	 */
	public final static char BUSINESS_PARAMS = 'k';

	/*
		Отчет о качестве выполнения операций за период
	 */
	public final static char QUALITY_PERIOD = 'l';

	/*
		Отчет о качестве выполнения операций на дату
	 */
	public final static char QUALITY_DATE = 'm';		

	/*
		Отчет по проактивному мониторингу
	 */
	public final static char PROACTIVE_MONITORING = 'n';

	/*
	 Отчет о времени простоя системы
	 */
	public final static char SYSTEM_IDLE = 'p';

	/*
	Отчет о количестве пользователей iOS
	 */
	public final static char COUNT_IOS_USER = 'x';

	/*
		Соответсвие кода отчета и назавния отчета 
	 */
	public final static Map<Object, String> NAME_TYPE_REPORT_BY_CODE = createNameTypeReportByCodeMap();

	/**
	 * Соответсвие кода отчета и ссылки на полный отчет
	 */
	public final static Map<Object, String> LINK_REPORT_BY_CODE = createLinkReportByCodeMap();

	/**
	 * Возвращает хеш соответствий названий отчета для вывода в jsp	 по коду отчета
	 */
	private static  Map<Object, String> createNameTypeReportByCodeMap()
	{
		Map<Object, String> map = new HashMap<Object, String>();

		map.put(CONTRACT_TB,        "Количество договоров по ТБ");
		map.put(CONTRACT_OSB,       "Количество договоров по ОСБ");
		map.put(CONTRACT_VSP,       "Количество договоров по ВСП");
		map.put(CONTRACT_OKR,       "Количество договоров по OKP");
		map.put(ACTIVE_USERS_TB,    "Количество активных пользователей по ТБ");
		map.put(ACTIVE_USERS_VSP,   "Количество активных пользователей по ВСП");
		map.put(OPERATIONS_SBRF,    "Количество операций по СБРФ");
		map.put(OPERATIONS_TB,      "Количество операций по ТБ");
		map.put(OPERATIONS_VSP,     "Количество операций по ВСП");
		map.put(BUSINESS_PARAMS,    "Отчет по бизнес – параметрам за период");
		map.put(QUALITY_DATE,       "Отчет о качестве выполнения операций на дату (ежедневный)");
		map.put(QUALITY_PERIOD,     "Отчет о качестве выполнения операций за период");
		map.put(PROACTIVE_MONITORING,     "Отчет по проактивному мониторингу");
		map.put(SYSTEM_IDLE,        "Отчет о времени простоя системы за период");
		map.put(COUNT_IOS_USER,     "Отчет о количестве пользователей iOS");

		return map;
	}

	/**
	 * 	Возвращает хеш соответсвтий кода типа отчета и ссылки на подробный отчет (используется в jsp)
	 */
	private static Map<Object, String> createLinkReportByCodeMap()
	{
		Map<Object, String> map = new HashMap<Object, String>();

		map.put(CONTRACT_TB,        "/reports/contract/tb/report.do");
		map.put(CONTRACT_OSB,       "/reports/contract/osb/report.do");
		map.put(CONTRACT_VSP,       "/reports/contract/vsp/report.do");
		map.put(CONTRACT_OKR,       "/reports/contract/okr/report.do");
		map.put(ACTIVE_USERS_TB,    "/reports/active_users/tb/report.do");
		map.put(ACTIVE_USERS_VSP,   "/reports/active_users/vsp/report.do");
		map.put(COUNT_IOS_USER,     "/reports/count/ios/report/view.do");
		map.put(OPERATIONS_SBRF,    "/reports/operations/sbrf/report.do");
		map.put(OPERATIONS_TB,      "/reports/operations/tb/report.do");
		map.put(OPERATIONS_VSP,     "/reports/operations/vsp/report.do");
		map.put(BUSINESS_PARAMS,    "/reports/it/business_params/report.do");
		map.put(QUALITY_PERIOD,     "/reports/it/quality_operations_period/report.do");
		map.put(QUALITY_DATE,       "/reports/it/quality_operations_date/report.do");
		map.put(PROACTIVE_MONITORING, "/reports/it/proactive/report.do");
		map.put(SYSTEM_IDLE,        "/reports/it/system_idle/report.do");

		return map;
	}
}
