package com.rssl.phizic.web.security.node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����� �����
 */
public class ChangeNodeForm extends SelfChangeNodeForm
{
	private String action;//����� �� ������� �������� � ����� �����

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

}
