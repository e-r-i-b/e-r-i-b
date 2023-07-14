package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * ���������� � ������� ��������
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class OperationInfoBase implements OperationInfo
{
	private boolean valid = true;               // ���������� ���������
	private Map<String, Object> confirmParams;    // ��������� �������������
	private String ouid;                        // ���������� ������������� ��������
	private Stage stage;                        // ������� ���
	private String authToken;
	private String redirect;

	public OperationInfoBase(Stage stage)
	{
		this.stage = stage;
	}

	/**
	 * @return true - ������������ ��������
	 */
	public boolean invalid()
	{
		return false;
	}

	public String getOUID()
	{
		return ouid;
	}

	/**
	 * @return ������� ���������
	 */
	public Stage getCurrentStage()
	{
		return stage;
	}

	public String getCurrentName()
	{
		Stage currentStage = getCurrentStage();
		if(currentStage == null)
			return null;

		return currentStage.getName();
	}

	/**
	 * ����������� �� ��������� ���������
	 */
	public void nextStage()
	{
		stage = stage.next(this);
	}

	public void setOUID(String ouid)
	{
		this.ouid = ouid;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	/**
	 * @return ��������� �������������
	 */
	public Map<String, Object> getConfirmParams()
	{
		return confirmParams;
	}

	public void setConfirmParams(Map<String, Object> confirmParams)
	{
		this.confirmParams = confirmParams;
	}

	/**
	 * @return ����� ��������������
	 */
	public String getAuthToken()
	{
		return authToken;
	}

	/**
	 * ������ ����� ��������������
	 * @param authToken ����� ��������������
	 */
	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}
}
