package com.rssl.phizic.web.security.node;

import org.apache.struts.action.ActionForm;

/**
 * @author mihaylov
 * @ created 08.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма самостоятельной смены блока
 */
public class SelfChangeNodeForm extends ActionForm
{
	private Long nodeId; //идентификатор блока на который переходим

	/**
	 * @return идентификатор блока на который переходим
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * Установить идентификатор блока
	 * @param nodeId - идентификатор
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

}
