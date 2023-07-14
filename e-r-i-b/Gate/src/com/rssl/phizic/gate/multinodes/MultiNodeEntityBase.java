package com.rssl.phizic.gate.multinodes;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовая сущность для списков из нескольких блоков
 */
public abstract class MultiNodeEntityBase implements Serializable
{

	private Long nodeId;//идентификатор блока

	/**
	 * @return идентификатор блока
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * Установить идентификатор блока
	 * @param nodeId - идентификатор блока
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
}
