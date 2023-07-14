package com.rssl.phizic.gate.config.csaadmin;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ������� � ���������� ��� �����
 */

public abstract class CSAAdminGateConfig extends Config
{
	protected CSAAdminGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ��� �� ������� ���������� ���������� ��� �����
	 */
	public abstract String getListenerUrl();

	/**
	 * @return ������� �� ���������� � ��� �����
	 */
	public abstract int getListenerTimeout();

	/**
	 * @return ����� ������ (true -- ������������ �����)
	 */
	public abstract boolean isMultiBlockMode();

	/**
	 * @return ��� �������� ������ �� ��� �����
	 */
	public abstract String getLoginUrl();

	/**
	 * @return ��� �������� ��, � ������� �������� ����������� � ������������ ������.
	 */
	public abstract String getDictionaryInstance();

	/**
	 * @return ����� ������ ���(true - ������������)
	 */
	public abstract boolean isMailMultiBlockMode();
}
