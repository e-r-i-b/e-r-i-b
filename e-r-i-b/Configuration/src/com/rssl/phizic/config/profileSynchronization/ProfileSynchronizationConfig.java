package com.rssl.phizic.config.profileSynchronization;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author lepihina
 * @ created 23.05.14
 * $Author$
 * $Revision$
 * ������ ��� ������ � ����������� ������������� ������� �������
 */
public class ProfileSynchronizationConfig extends Config
{
	private static final String PERSON_SETTINGS_USE_REPLICATE_NAME = "com.rssl.phizic.config.person.settings.use.replicate";
	private boolean useReplicatePersonSettings = false;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public ProfileSynchronizationConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		useReplicatePersonSettings = getBoolProperty(PERSON_SETTINGS_USE_REPLICATE_NAME);
	}

	/**
	 * @return true - ������� �������� ���������� ������ ������� ������� � ��������� ���������
	 */
	public boolean isUseReplicatePersonSettings()
	{
		return useReplicatePersonSettings;
	}
}
