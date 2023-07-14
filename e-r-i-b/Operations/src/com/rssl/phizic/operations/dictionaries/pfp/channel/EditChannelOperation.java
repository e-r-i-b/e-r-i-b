package com.rssl.phizic.operations.dictionaries.pfp.channel;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * Редактирование канала
 * @author komarov
 * @ created 12.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class EditChannelOperation extends EditDictionaryEntityOperationBase
{
	private static final ChannelService service = new ChannelService();

	private Channel channel;

	/**
	 * Инициализирует операцию
	 */
	public void initialize()
	{
		channel = new Channel();
	}

	/**
	 * Инициализирует операцию
	 * @param id идентификатор канала
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		channel = service.getChannelById(id, getInstanceName());

		if(channel == null)
			throw new ResourceNotFoundBusinessException("Канал с id = " + id + " не найден", Channel.class);
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdate(channel, getInstanceName());
	}

	public Channel getEntity()
	{
		return channel;
	}
}
