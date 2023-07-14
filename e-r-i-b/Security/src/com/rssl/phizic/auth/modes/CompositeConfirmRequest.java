package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.*;

/**                 1
 * @author emakarov
 * @ created 18.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CompositeConfirmRequest implements ConfirmRequest
{
	Map<ConfirmStrategyType, ConfirmRequest> requests;

	public CompositeConfirmRequest()
	{
		requests = new HashMap<ConfirmStrategyType, ConfirmRequest>();
	}

	public ConfirmStrategyType getStrategyType()
	{
		return ConfirmStrategyType.composite;
	}

	public boolean isError()
	{
		Set<ConfirmStrategyType> strategyTypes = requests.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			if (requests.get(type).isError())
			{
				return true;
			}
		}
		return false;
	}

	public boolean isErrorFieldPassword()
	{
		return false;
	}

	public void setErrorFieldPassword(boolean error)
	{
		
	}

	public String getErrorMessage()
	{
		Set<ConfirmStrategyType> strategyTypes = requests.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			if (requests.get(type).isError())
			{
				return requests.get(type).getErrorMessage(); // возвращаем только первую ошибку
			}
		}
		return null;
	}

	public void setErrorMessage(String errorMessage)
	{
		Set<ConfirmStrategyType> strategyTypes = requests.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			requests.get(type).setErrorMessage(errorMessage);
		}
	}

	public List<String> getMessages()
	{
		return null;
	}

	public void addMessage(String message)
	{		
	}

	public List<String> getAdditionInfo()
	{
		return null;  
	}

	public void addConfirmRequest(ConfirmRequest confirmRequest)
	{
		requests.put(confirmRequest.getStrategyType(), confirmRequest);
	}

	public ConfirmRequest getConfirmRequest(ConfirmStrategyType type)
	{
		return requests.get(type);
	}

	public List<ConfirmRequest> getRequests()
	{
		List<ConfirmRequest> reqs = new ArrayList<ConfirmRequest>();
		Set<ConfirmStrategyType> strategyTypes = requests.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			reqs.add(requests.get(type));
		}
		return reqs;
	}

	public boolean isPreConfirm()
	{
		return true;
	}

	public void setPreConfirm(boolean isPreConfirm)
	{		
	}

	public void resetMessages()
	{
		Set<ConfirmStrategyType> strategyTypes = requests.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			requests.get(type).resetMessages();
		}
	}
}
