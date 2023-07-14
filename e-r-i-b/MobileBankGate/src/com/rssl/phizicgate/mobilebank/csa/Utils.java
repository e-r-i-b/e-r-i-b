package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author krenev
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 * ”тилитный класс дл€ обработки ответов от ћЅ по получению данных о пользователе.
 */
public class Utils
{
	/**
	 * –азобрать и полученный из ћЅ параметр addInfoCn и определить основна€ ли карта скрываетс€ за ним
	 * @param addInfoCn параметр
	 * @return основна€ ли карта соттвествует значению параметра
	 * @throws SystemException
	 */
	static boolean isMainCard(int addInfoCn) throws SystemException
	{
		//AddInfoCn может принимать значени€ 1 - основна€, 2- дополнительна€ карта.
		if (addInfoCn != 1 && addInfoCn != 2)
		{
			throw new SystemException("Ќарушение спецификации взаимодействи€ с ћЅ: параметр addInfoCn=" + addInfoCn);
		}
		return addInfoCn == 1;
	}

	/**
	 *
	 * –азобрать и полученный из ћЅ параметр addInfoCn и определить активна€ ли карта скрываетс€ за ним
	 * @param contrStatus текущий статус карты
	 * @return активн€ ли карта.
	 * @throws SystemException
	 */
	static boolean isCardActive(int contrStatus) throws SystemException
	{
		//ContrStatus - текущий статус карты (ID), дл€ активной карты будет возвращатьс€: 14 или 239.
		return contrStatus == 14 || contrStatus == 239;
	}
}
