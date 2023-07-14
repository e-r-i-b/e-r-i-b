package com.rssl.phizic.business.ext.sbrf.mobilebank;

/**
 * @author Erkin
 * @ created 28.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankConstants
{
	/**
	 * ����������� ���������� ���-�� �������� ���-�������� �� ������ ���������� (��� ������ ������������)
	 * ��� ��������� �������� ����� ����� �������� � ��������
	 * com.rssl.phizicgate.mobilebank.MobileBankServiceImpl.MAX_PAYERS_PER_RECIPIENT
	 */
	public static final int MAX_PAYERS_PER_RECIPIENT = 5;

	/**
	 * ������ �� ���� ���������, �� ������� �����
	 * ������������ � ������� "��������� ����"
	 */
	public static final String SBERBANK_MOBILE_BANK_DESCRIPTION_URL =
			"http://www.sberbank.ru/ru/person/dist_services/inner_mbank/";

	/**
	 * ������ �� ���� ���������, �� ������� �����
	 * ����������� ������ ����������� ����� "���������� �����"
	 */
	public static final String SBERBANK_SERVICE_PROVIDERS_LIST_URL =
			"http://www.sberbank.ru/ru/person/dist_services/list_organisations/";

	public static final String DEFAULT_CARD_NAME = "���������� �����";
}
