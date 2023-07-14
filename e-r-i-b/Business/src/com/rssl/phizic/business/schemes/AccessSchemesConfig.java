package com.rssl.phizic.business.schemes;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * @author Roshka
 * @ created 26.02.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class AccessSchemesConfig extends Config
{
	protected AccessSchemesConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @param accessType ��� �������
	 * @return ����� �� ��������� ��� ���� �������
	 */
	public abstract SharedAccessScheme getDefaultAccessScheme(AccessType accessType);

	/**
	 * @return ��� ����� �������
	 */
	public abstract List<SharedAccessScheme> getSchemes();

	/**
	 * ����� ����� �� �� ����
	 * @param code ��� �����
	 * @return �����
	 */
	public abstract SharedAccessScheme getByCode(String code);

	/**
	 * @return ����� ������� ���������� ������� ������ ��������������
	 */
	public abstract SharedAccessScheme getBuildinAdminAccessScheme();

	/**
	 * @return ����� ������� ��� ���������� �������
	 */
	public abstract SharedAccessScheme getAnonymousClientAccessScheme();
}
