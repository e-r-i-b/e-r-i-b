package com.rssl.phizic.web.client.payments;

import com.rssl.phizic.web.common.messages.StrutsMessageConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 15.12.2005
 * @ $Author: komarov $
 * @ $Revision: 68594 $
 */

public class StatusHelper
{
	private static StrutsMessageConfig messageConfig = new StrutsMessageConfig();
	private static Map<String,String> stateDescriptions;

	static
	{
		stateDescriptions = new HashMap<String, String>();
		stateDescriptions.put("I", "payment.state.I");
		stateDescriptions.put("W", "payment.state.W");
		stateDescriptions.put("S", "payment.state.S");
		stateDescriptions.put("E", "payment.state.E");
	}


	public static String formatState(String state)
	{
		try
		{
			String key = stateDescriptions.get(state);
			return messageConfig.message("paymentsBundle", key);
		}
		catch (Exception e)
		{
			return "<error>";
		}
	}

}
