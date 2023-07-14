package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.List;
import java.util.Map;

/**
 * @author basharin
 * @ created 23.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class CardClientConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.plasticCardClient;
	}

	public boolean hasSignature()
	{
		return false;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		return new CardClientConfirmRequest();
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (request == null)
			throw new SecurityException("�� ���������� ������ �� �������������");

		if (response == null)
			throw new SecurityException("�� ���������� ����� �� �������������");

		CardClientConfirmRequest req = (CardClientConfirmRequest) request;
		CardClientConfirmResponse res = (CardClientConfirmResponse) response;

		String cardNumber = res.getCardNumber();
		if (cardNumber == null)
		{
			throw new SecurityLogicException("����� ����� ������� �� �������");
		}
		if (!cardNumber.equals(login.getLastLogonCardNumber()))
		{
			throw new SecurityLogicException("������ ����� ����������� �������� ������ �� �����, �� ������� ���������� ��� �����");
		}
		return new ConfirmStrategyResult(false);
	}

	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		// ������ ������ �� ����
		return null;
	}

	/**
	 * �������� SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		throw new UnsupportedOperationException();
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new CardClientConfirmResponseReader();
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
		if(conditions!=null)
		{
			if (conditions.get(getType())!=null)
				{
					for (StrategyCondition condition: conditions.get(getType()))
					{
						if (!condition.checkCondition(object))
						{
							String message = condition.getWarning();
							setWarning(message == null ? null : new SecurityLogicException(message));
							return false;
						}
					}
				}
		}
		return true;
	}
}
