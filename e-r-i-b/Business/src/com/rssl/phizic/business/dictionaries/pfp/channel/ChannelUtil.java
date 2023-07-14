package com.rssl.phizic.business.dictionaries.pfp.channel;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Collections;
import java.util.List;

/**
 * Класс для работы с каналами
 * @author komarov
 * @ created 13.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChannelUtil
{
	private static final ChannelService service = new ChannelService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @return список всех каналов
	 */
	public static List<Channel> getAllChannels()
	{
		try
		{
			return service.getChannels();
		}
		catch (BusinessException be)
		{
			log.error("Ошибка получения списка каналов", be);
			return Collections.emptyList();
		}
	}

}
