package com.rssl.phizic.auth.modes;

import com.octo.captcha.service.CaptchaServiceException;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.readers.CaptchaConfirmResponseReader;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.common.forms.doc.DocumentSignature;

import java.util.List;
import java.util.Map;

/**
 * @author Krenev
 * @ created 26.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CaptchaConfirmStrategy implements ConfirmStrategy
{
	private Exception warning;

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.captcha;
	}

	/**
	 * ���������� �������, ������� ����� ��������� � ������ DocumentSignature
	 * @return true=����������
	 */
	public boolean hasSignature()
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * ������� ������ �� �������������  (���� requireValues() == true)
	 * @param login ����� ��� �������� ��������� ������
	 * @param value �������� ��� �������
	 * @param sessionId ������������� ������� ������
	 * @return ������
	 * @throws SecurityException ���������� ������� ������
	 */
	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfim) throws SecurityException
	{
		String code = RandomHelper.rand(10);

		return new CaptchaConfirmRequest(code);
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (request == null)
			throw new SecurityException("�� ���������� ������ �� �������������");

		if (response == null)
			throw new SecurityException("�� ���������� ����� �� �������������");

		CaptchaConfirmRequest req = (CaptchaConfirmRequest) request;
		CaptchaConfirmResponse res = (CaptchaConfirmResponse) response;

		String captchaId = req.getCaptchaId();
		String code = res.getCode();
		try
		{
			if (!validateResponseForID(captchaId, code))
			{
				throw new SecurityLogicException("�������� ������");
			}
		}
		catch (CaptchaServiceException e)
		{
			//should not happen, may be thrown if the id is not valid
			throw new RuntimeException(e);
		}
		return new ConfirmStrategyResult(false);
	}

	/**
	 * ��������, ������� ����� ��������� ����� ��������������
	 * @param callBackHandler CallBackHandler
	 * @throws com.rssl.phizic.security.SecurityLogicException, SecurityException �������� ����� (������, ������� etc)
	 */
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
		return new CaptchaConfirmResponseReader();
	}

	public static boolean validateResponseForID(String captchaId, String captchaCode)
	{
		return CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captchaCode);
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
