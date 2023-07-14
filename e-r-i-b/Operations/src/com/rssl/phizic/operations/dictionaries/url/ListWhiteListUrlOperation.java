package com.rssl.phizic.operations.dictionaries.url;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrl;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrlService;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author lukina
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListWhiteListUrlOperation  extends OperationBase
{
	private static final WhiteListUrlService service = new WhiteListUrlService();

	public void save(List<WhiteListUrl> newUrlList, List<Long> removeUrlList) throws Exception
	{
		service.addList(newUrlList, removeUrlList);
	}

	/**
	 * Получить список названий банеров, в которых есть урлы не подходящие под маску
	 * @param regexpString - регулярное выражение списока масок для проверки кнопок баннеров и ссылок самого баннера
	 * @param regexpStringForText - регулярное выражение списока масок для проверки внутри текста
	 * @return список названий банеров, в которых есть урлы не подходящие под маску
	 */
	public List<String>  canNotEditAdvertisingList(String regexpString, String regexpStringForText) throws BusinessException
	{
		return service.canNotEditAdvertisingList(regexpString, regexpStringForText);
	}

	public List<WhiteListUrl> getMaskUrlList() throws BusinessException, BusinessLogicException
	{
		return service.getMaskUrlList();
	}

}
