package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.csa.ProfileType;

/**
 * @author koptyaev
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 */
public class SynchProfileInfoStageVerifier implements StageVerifier
{
	/**
	 * @param context �������� ��������������
	 * @param stage ������ ��������������
	 * @return true == ������ ���������� � ���� ���������
	 */
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		return context.getProfileType() == ProfileType.TEMPORARY;
	}
}
