package com.rssl.phizic.operations.person.search.multinode;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author mihaylov
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 * Исключение сигнализирующее о необходимости смены блока
 */
public class ChangeNodeLogicException extends BusinessLogicException
{
	private Long nodeId;//идентификатор блока, в который необходимо перейти

	/**
	 * Конструктор
	 * @param message - сообщение
	 */
	public ChangeNodeLogicException(String message)
	{
		super(message);
	}

	/**
	 * Конструктор
	 * @param message - сообщение
	 * @param nodeId - идентификатор блока, в который необходимо перейти
	 */
	public ChangeNodeLogicException(String message, Long nodeId)
	{
		super(message);
		this.nodeId = nodeId;
	}

	/**
	 * @return идентификатор блока, в который необходимо перейти
	 */
	public Long getNodeId()
	{
		return nodeId;
	}
}
