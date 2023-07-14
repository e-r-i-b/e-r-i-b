package com.rssl.phizic.web.security.node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма смены блока
 */
public class ChangeNodeForm extends SelfChangeNodeForm
{
	private String action;//экшен на который перейдем в новом блоке

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

}
