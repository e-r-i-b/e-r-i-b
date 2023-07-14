package com.rssl.phizic.gate.monitoring.fraud.utils;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author khudyakov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientTransactionCompositeId
{
	private static final String DELIMITER                                   = "\\|";

	private String groupId;
	private Long CSAProfileId;
	private Long nodeId;
	private Long nodeProfileId;
	private Long nodeLoginId;

	public ClientTransactionCompositeId(String id)
	{
		if (StringHelper.isEmpty(id))
		{
			throw new IllegalArgumentException("Аргумент ClientTransactionId не может быть пуст.");
		}

		String[] parameters = id.split(DELIMITER);
		groupId         = parameters.length > 1 && StringHelper.isNotEmpty(parameters[1]) ? parameters[1] : null;
		nodeId          = parameters.length > 2 && StringHelper.isNotEmpty(parameters[2]) ? Long.valueOf(parameters[2]) : null;
		CSAProfileId    = parameters.length > 3 && StringHelper.isNotEmpty(parameters[3]) ? Long.valueOf(parameters[3]) : null;
		nodeProfileId   = parameters.length > 4 && StringHelper.isNotEmpty(parameters[4]) ? Long.valueOf(parameters[4]) : null;
		nodeLoginId     = parameters.length > 5 && StringHelper.isNotEmpty(parameters[5]) ? Long.valueOf(parameters[5]) : null;
	}

	/**
	 * @return groupId блока
	 */
	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @return идентификатор блока
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @return profileId в ЦСА
	 */
	public Long getCSAProfileId()
	{
		return CSAProfileId;
	}

	/**
	 * @return profileId в блоке (таблица USERS)
	 */
	public Long getNodeProfileId()
	{
		return nodeProfileId;
	}

	/**
	 * @return loginId в блоке
	 */
	public Long getNodeLoginId()
	{
		return nodeLoginId;
	}
}
