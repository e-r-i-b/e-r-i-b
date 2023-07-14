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
	 *  ������-������
	 * -----------------------------------------
	 */

	/*
	 ���������� ��������� �� ��
	*/
	public final static char CONTRACT_TB = 't';

	/*
	���������� ��������� �� ���
	*/
	public final static char CONTRACT_OSB = 'o';

	/*
	���������� ��������� �� ���
	*/
	public final static char CONTRACT_VSP = 'v';

	/*
	���������� ��������� �� OKP
	*/
	public final static char CONTRACT_OKR = 'c';

	/*
	 �������� ������������ �� ��
	 */
	public final static char ACTIVE_USERS_TB = 'a';

	/*
	 �������� ������������ �� ���
	 */
	public final static char ACTIVE_USERS_VSP = 'b';

	/*
	 ���������� �������� �� ����
	 */
	public final static char OPERATIONS_SBRF = 'i';

	/*
	 ���������� �������� �� ��
	 */
	public final static char OPERATIONS_TB = 'd';

	/*
	 ���������� �������� �� ���
	 */
	public final static char OPERATIONS_VSP = 'e';



	/**
	 * -----------------------------------------
	 *  ��-������
	 * -----------------------------------------
	 */

	/*
		����� �� ������ � ���������� �� �� ������
	 */
	public final static char BUSINESS_PARAMS = 'k';

	/*
		����� � �������� ���������� �������� �� ������
	 */
	public final static char QUALITY_PERIOD = 'l';

	/*
		����� � �������� ���������� �������� �� ����
	 */
	public final static char QUALITY_DATE = 'm';		

	/*
		����� �� ������������ �����������
	 */
	public final static char PROACTIVE_MONITORING = 'n';

	/*
	 ����� � ������� ������� �������
	 */
	public final static char SYSTEM_IDLE = 'p';

	/*
	����� � ���������� ������������� iOS
	 */
	public final static char COUNT_IOS_USER = 'x';

	/*
		����������� ���� ������ � �������� ������ 
	 */
	public final static Map<Object, String> NAME_TYPE_REPORT_BY_CODE = createNameTypeReportByCodeMap();

	/**
	 * ����������� ���� ������ � ������ �� ������ �����
	 */
	public final static Map<Object, String> LINK_REPORT_BY_CODE = createLinkReportByCodeMap();

	/**
	 * ���������� ��� ������������ �������� ������ ��� ������ � jsp	 �� ���� ������
	 */
	private static  Map<Object, String> createNameTypeReportByCodeMap()
	{
		Map<Object, String> map = new HashMap<Object, String>();

		map.put(CONTRACT_TB,        "���������� ��������� �� ��");
		map.put(CONTRACT_OSB,       "���������� ��������� �� ���");
		map.put(CONTRACT_VSP,       "���������� ��������� �� ���");
		map.put(CONTRACT_OKR,       "���������� ��������� �� OKP");
		map.put(ACTIVE_USERS_TB,    "���������� �������� ������������� �� ��");
		map.put(ACTIVE_USERS_VSP,   "���������� �������� ������������� �� ���");
		map.put(OPERATIONS_SBRF,    "���������� �������� �� ����");
		map.put(OPERATIONS_TB,      "���������� �������� �� ��");
		map.put(OPERATIONS_VSP,     "���������� �������� �� ���");
		map.put(BUSINESS_PARAMS,    "����� �� ������ � ���������� �� ������");
		map.put(QUALITY_DATE,       "����� � �������� ���������� �������� �� ���� (����������)");
		map.put(QUALITY_PERIOD,     "����� � �������� ���������� �������� �� ������");
		map.put(PROACTIVE_MONITORING,     "����� �� ������������ �����������");
		map.put(SYSTEM_IDLE,        "����� � ������� ������� ������� �� ������");
		map.put(COUNT_IOS_USER,     "����� � ���������� ������������� iOS");

		return map;
	}

	/**
	 * 	���������� ��� ������������ ���� ���� ������ � ������ �� ��������� ����� (������������ � jsp)
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
