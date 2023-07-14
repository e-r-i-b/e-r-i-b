package com.rssl.phizicgate.esberibgate.cache;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;

import java.util.Calendar;

/**
 * ��������� ����� ��� ��������� ������ ���� ��� ����������� ��������� ����
 * @author Pankin
 * @ created 29.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ESBCacheKeyGenerator
{
	private static final String ITEMS_DELIMITER = "-";

	/**
	 * �������� ���� ���� �������� ��������� ����������
	 * @param productId ��������� ������������� �������� (����� ��� ������)
	 * @return ���� ��� ����
	 */
	public static String getCardOrAccountDetailsKey(String productId)
	{
		EntityCompositeId commonCompositeId = EntityIdHelper.getCommonCompositeId(productId);
		StringBuilder sb = new StringBuilder();
		addKeyElementToCacheKey(commonCompositeId.getEntityId(), sb);
		sb.append(commonCompositeId.getLoginId());
		return sb.toString();
	}

	/**
	 * �������� ���� ���� �������� ��������� ����������
	 * @param number ����� �����
	 * @param clazz �������
	 * @param rbTbBrunch rbTbBrunch
	 * @return ���� ��� ����
	 */
	public static String getProductDetailsKey(Class clazz, String number, String rbTbBrunch)
	{
		StringBuilder sb = new StringBuilder();
		addKeyElementToCacheKey(clazz.getSimpleName(), sb);
		addKeyElementToCacheKey(number, sb);
		sb.append(rbTbBrunch);

		return sb.toString();
	}
	/**
	 * �������� ���� ���� �������� ��������� ����������
	 * @param clazz �������
	 * @param externalId ������� ������������� ��������
	 * @return ���� ��� ����
	 */
	public static String getProductDetailsKey(Class clazz, String externalId)
	{
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(externalId);
		return getProductDetailsKey(clazz, compositeId.getEntityId(), compositeId.getRbBrchId());
	}

	/**
	 * ������ ���� ��� ����������� ������� ���������� ������
	 * @param imAccount ���
	 * @param fromDate ��������� ���� �������
	 * @param toDate �������� ���� �������
	 * @return ���� ��� ����
	*/
	public static String getIMAPeriodKey(IMAccount imAccount, Calendar fromDate, Calendar toDate)
	{
		StringBuilder sb = new StringBuilder();
		addKeyElementToCacheKey(imAccount.getNumber(), sb);
		addKeyElementToCacheKey(String.format("%1$td.%1$tm.%1$tY", fromDate.getTime()), sb);
		addKeyElementToCacheKey(String.format("%1$td.%1$tm.%1$tY", toDate.getTime()),   sb);
		return sb.toString();
	}

	/**
	 * ������� ���� ��������� ������������� ������� � ���� �� ��� ����������
	 * @param client ������
	 * @param document �������� �������
	 * @return ���� ��� ����
	 */
	public static String getIMAClientDocumentKey(Client client, ClientDocument document)
	{
		StringBuilder sb = new StringBuilder();
		addKeyElementToCacheKey(StringHelper.getEmptyIfNull(client.getFirstName()).replace(" ", "").toUpperCase(), sb);
		addKeyElementToCacheKey(StringHelper.getEmptyIfNull(client.getSurName()).replace(" ", "").toUpperCase(), sb);
		addKeyElementToCacheKey(StringHelper.getEmptyIfNull(client.getPatrName()).replace(" ", "").toUpperCase(), sb);
		addKeyElementToCacheKey(StringHelper.getEmptyIfNull(document.getDocSeries()).replace(" ", ""), sb);
		addKeyElementToCacheKey(StringHelper.getEmptyIfNull(document.getDocNumber()).replace(" ", ""), sb);
		addKeyElementToCacheKey(client.getBirthDay() == null ? "" : String.format("%1$td.%1$tm.%1$tY", client.getBirthDay()), sb);
		return sb.toString();
	}

	private static void addKeyElementToCacheKey(String value, StringBuilder sb)
	{
		sb.append(StringHelper.getEmptyIfNull(value)).append(ITEMS_DELIMITER);
	}
}
