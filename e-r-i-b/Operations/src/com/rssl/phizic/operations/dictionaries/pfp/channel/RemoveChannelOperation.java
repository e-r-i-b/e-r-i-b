package com.rssl.phizic.operations.dictionaries.pfp.channel;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Операция удаления канала
 */

public class RemoveChannelOperation extends RemoveDictionaryEntityOperationBase
{
	private static final ChannelService service = new ChannelService();

	private Channel channel;

	/**
	 * Инициализирует операцию
	 * @param id идентификатор канала
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		channel = service.getChannelById(id, getInstanceName());

		if(channel == null)
			throw new ResourceNotFoundBusinessException("Канал с id = " + id + " не найден", Channel.class);
	}

	public Channel getEntity()
	{
		return channel;
	}

	protected void doRemove() throws BusinessException, BusinessLogicException
	{
		service.remove(channel, getInstanceName());
	}
}
