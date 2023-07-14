package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;

/**
 * @author akrenev
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записей каналов
 */

public class ChannelProcessor extends PFPProcessorBase<Channel>
{
	@Override
	protected Class<Channel> getEntityClass()
	{
		return Channel.class;
	}

	@Override
	protected Channel getNewEntity()
	{
		return new Channel();
	}

	@Override
	protected void update(Channel source, Channel destination) throws BusinessException
	{
		super.update(source, destination);
		destination.setName(source.getName());
		destination.setDeleted(source.isDeleted());
	}

	@Override
	protected void doRemove(Channel localEntity) throws BusinessException, BusinessLogicException
	{
		localEntity.setDeleted(true);
		doSave(localEntity);
	}
}
