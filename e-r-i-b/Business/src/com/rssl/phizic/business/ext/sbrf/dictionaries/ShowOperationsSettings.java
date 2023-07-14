package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.config.*;

/**
 * @author Mescheryakova
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����� �������� ������ ����������� �� �������: ������ ��� ��
 */

public class ShowOperationsSettings extends Config
{
	private static final String SHOW_OPERATION = "com.rssl.iccs.show.operations.data.base";

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public ShowOperationsSettings(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * ������ � �� ��� � ������
	 * @return true - � ��, false - ������
	 */
	public static boolean isDataBaseSetting()
	{
		return ConfigFactory.getConfig(ShowOperationsSettings.class).isDataBaseSetting0();
	}

	private boolean isDataBaseSetting0()
	{
		return getBoolProperty(SHOW_OPERATION);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
