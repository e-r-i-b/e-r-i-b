package com.rssl.phizic.operations.validators;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;

/**
 * ��������� ���� ������� ������� � ExternalResourceLink
 * @author Dorzhinov
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class IsOwnLinkValidator
{
	/**
	 * ��������� ���� ������� ������� � ExternalResourceLink
	 * @param link ����
	 * @throws NotOwnLinkException
	 */
	public void validate(ExternalResourceLink link) throws NotOwnLinkException
	{
		if(link.getLoginId() == null)
			throw new NotOwnLinkException("wrong link id");

		Long loginId = link.getLoginId();
		if (!loginId.equals(AuthModule.getAuthModule().getPrincipal().getLogin().getId()))
			throw new NotOwnLinkException("� ������� ������������ ��� ���� �� �������� " +
									"����� � id=" + link.getId());
	}
}
