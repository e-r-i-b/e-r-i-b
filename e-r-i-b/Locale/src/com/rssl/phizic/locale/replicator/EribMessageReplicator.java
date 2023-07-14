package com.rssl.phizic.locale.replicator;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Репликатор текстовок
 * @author komarov
 * @ created 15.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EribMessageReplicator
{
	private ReplicaSource<Map.Entry<String, ERIBStaticMessage>> source;
	private PropertyDestenation destination;
	private SyncMode mode;

	/**
	 * @param source что
	 * @param destination куда
	 * @param mode режим синхронизации
	 */
	public EribMessageReplicator(ReplicaSource<Map.Entry<String, ERIBStaticMessage>> source, PropertyDestenation destination, SyncMode mode)
	{
		this.source = source;
		this.destination = destination;
		this.mode = mode;
	}

	/**
	 *
	 * @throws SystemException
	 */
	public void replicate() throws SystemException
	{

		Map<String, ERIBStaticMessage> bdMessages = destination.getDestenation();

		List<ERIBStaticMessage> addMessages = new ArrayList<ERIBStaticMessage>();
		List<ERIBStaticMessage> updateMessages  = new ArrayList<ERIBStaticMessage>();
		for (Map.Entry<String, ERIBStaticMessage> entry : source.iterator())
		{
			if (bdMessages.containsKey(entry.getKey()))
			{
				ERIBStaticMessage message = bdMessages.get(entry.getKey());
				if(!StringHelper.equalsNullIgnore(message.getMessage(),entry.getValue().getMessage()))
				{
					message.setMessage(entry.getValue().getMessage());
					updateMessages.add(message);
				}
				bdMessages.remove(entry.getKey());
			}
			else
				addMessages.add(entry.getValue());
		}
		removeMessages(bdMessages);
		addNewMessages(addMessages);
		updateMessages(updateMessages);
	}

	private void updateMessages(List<ERIBStaticMessage> updateMessages) throws SystemException
	{
		destination.update(updateMessages);
	}

	private void addNewMessages(List<ERIBStaticMessage> addMessages) throws SystemException
	{
		if (mode == SyncMode.UPDATE_ONLY)
			return;
		destination.add(addMessages);
	}

	private void removeMessages(Map<String, ERIBStaticMessage> bdMessages) throws SystemException
	{
		if (mode == SyncMode.UPDATE_ONLY)
			return;
		for (Map.Entry<String, ERIBStaticMessage> entry : bdMessages.entrySet())
		{
			destination.remove(entry.getValue());
		}
	}
}
