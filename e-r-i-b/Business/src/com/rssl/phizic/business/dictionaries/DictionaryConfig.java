package com.rssl.phizic.business.dictionaries;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * ��������� ������������� ������������
 */
public abstract class DictionaryConfig extends Config
{
	protected DictionaryConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
     * �������� ���������� �� ��� �����
     * @param name ��� �����������
     * @return ����������
     */
    public abstract DictionaryDescriptor getDescriptor(String name);

    /**
     * @return ������ ���� ������������
     */
    public abstract List<DictionaryDescriptor> getDescriptors();

	/**
	 * �������� ����������� ������ ������������ �� � �����
	 * @param name ��� �����������
	 * @return ����������
	 */
	public abstract List<String> getGroupDescriptorNames(String name);
}
