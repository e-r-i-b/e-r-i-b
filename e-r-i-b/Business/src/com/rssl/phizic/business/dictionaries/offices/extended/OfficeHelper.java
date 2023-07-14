package com.rssl.phizic.business.dictionaries.offices.extended;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeType;

/**
 * ������ ��� ������ � �������
 * @author niculichev
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */
public class OfficeHelper
{
	private static final AdapterService adapterService = new AdapterService();

	/**
	 * ��������� ������� ����
	 * @param tb ��
	 * @param osb ���
	 * @param vsp ���
	 * @return ������� ����
	 */
	public static String buildSynchKey(String tb, String osb, String vsp)
	{
		return String.format("%1$" + tb.length() + "s|%2$4s|%3$7s", tb, StringHelper.getEmptyIfNull(osb), StringHelper.getEmptyIfNull(vsp));
	}

	/**
	 * ������������ �� ������� ��������� ��������� ������
	 * @param adapterUUID ������������� ��������
	 * @return true - ������������
	 * @throws BusinessException
	 */
	public static boolean isAdapterSupportExternalOffice(String adapterUUID) throws BusinessException
	{
		if(StringHelper.isEmpty(adapterUUID))
			throw new IllegalArgumentException("adapterId �� ����� ���� null");

		try
		{
			Node node = adapterService.getNode(adapterUUID);
			return isSupportExternalOffice(node.getType());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������ �� ���� ��������� ��������� ������
	 * @param nodeType ��������� ������������� ���� ����
	 * @return true - ������������
	 */
	public static boolean isSupportExternalOffice(String nodeType)
	{
		if(StringHelper.isEmpty(nodeType))
			throw new IllegalArgumentException("nodeType �� ����� ���� ������");

		return isSupportExternalOffice(NodeType.valueOf(nodeType));
	}

	/**
	 * ������������ �� ���� ��������� ��������� ������
	 * @param nodeType ��� ����
	 * @return true - ������������
	 */
	public static boolean isSupportExternalOffice(NodeType nodeType)
	{
		return nodeType == NodeType.RETAIL_V6;
	}
}
