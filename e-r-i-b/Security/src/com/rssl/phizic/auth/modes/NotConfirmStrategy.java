package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.readers.NullConfirmResponseReader;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.common.forms.doc.DocumentSignature;

import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: erkin $
 * @ $Revision: 48487 $
 */

public class NotConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.none;
	}

	public boolean hasSignature()
	{
		return false;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value,String sessionId,PreConfirmObject preConfirm) throws SecurityException
	{
		return new NotConfirmRequest();
	}


	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityException, SecurityLogicException
	{
		return new ConfirmStrategyResult(false);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		// ничего делать не надо
		return null;
	}

	/**
	 * Получить SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		throw new UnsupportedOperationException();
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new NullConfirmResponseReader();
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		//nothing
	}

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		if (conditions!=null)
		{
			if (conditions.get(getType())!=null)
				{
					for (StrategyCondition condition: conditions.get(getType()))
					{
						if (!condition.checkCondition(object))
						{
							if (condition.getWarning()!=null)
								setWarning(new SecurityLogicException(condition.getWarning()));
							return false;
						}
					}
				}
		}
		return true;
	}
}