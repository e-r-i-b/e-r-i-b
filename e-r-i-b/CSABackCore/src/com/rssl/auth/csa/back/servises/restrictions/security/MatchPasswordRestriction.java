package com.rssl.auth.csa.back.servises.restrictions.security;

import com.rssl.auth.csa.back.exceptions.PasswordRestrictionException;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;

import java.util.List;

/**
 * @author osminin
 * @ created 17.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ����������� �� ������������ � ������� �������
 */
public abstract class MatchPasswordRestriction implements Restriction<String>
{
	private Long profileId;

	/**
	 * ctor
	 * @param profileId ������������� �������
	 */
	protected MatchPasswordRestriction(Long profileId)
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("������� �� ����� ���� null");
		}
		this.profileId = profileId;
	}

	public void check(String object) throws Exception
	{
		List<CSAConnector> connectors = CSAConnector.findNotClosedByProfileID(profileId);

		for (CSAConnector connector : connectors)
		{
			if (connector.checkPassword(object) != null)
			{
				throw new PasswordRestrictionException(getMessage());
			}
		}
	}

	protected abstract String getMessage();
}
