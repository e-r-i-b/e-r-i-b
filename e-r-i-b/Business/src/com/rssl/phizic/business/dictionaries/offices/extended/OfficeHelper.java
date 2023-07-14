package com.rssl.phizic.business.dictionaries.offices.extended;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeType;

/**
 * Хелпер для работы с офисами
 * @author niculichev
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */
public class OfficeHelper
{
	private static final AdapterService adapterService = new AdapterService();

	/**
	 * Построить внешний ключ
	 * @param tb тб
	 * @param osb осб
	 * @param vsp всп
	 * @return внешний ключ
	 */
	public static String buildSynchKey(String tb, String osb, String vsp)
	{
		return String.format("%1$" + tb.length() + "s|%2$4s|%3$7s", tb, StringHelper.getEmptyIfNull(osb), StringHelper.getEmptyIfNull(vsp));
	}

	/**
	 * Поддерижвает ли адаптер интерфейс получения офисов
	 * @param adapterUUID идентификатор адаптера
	 * @return true - поддерживает
	 * @throws BusinessException
	 */
	public static boolean isAdapterSupportExternalOffice(String adapterUUID) throws BusinessException
	{
		if(StringHelper.isEmpty(adapterUUID))
			throw new IllegalArgumentException("adapterId не может быть null");

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
	 * Поддерижвает ли узел интерфейс получения офисов
	 * @param nodeType строковое представление типа узла
	 * @return true - поддерживает
	 */
	public static boolean isSupportExternalOffice(String nodeType)
	{
		if(StringHelper.isEmpty(nodeType))
			throw new IllegalArgumentException("nodeType не может быть пустым");

		return isSupportExternalOffice(NodeType.valueOf(nodeType));
	}

	/**
	 * Поддерижвает ли узел интерфейс получения офисов
	 * @param nodeType тип узла
	 * @return true - поддерживает
	 */
	public static boolean isSupportExternalOffice(NodeType nodeType)
	{
		return nodeType == NodeType.RETAIL_V6;
	}
}
