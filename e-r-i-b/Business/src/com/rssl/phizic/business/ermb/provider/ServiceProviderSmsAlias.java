package com.rssl.phizic.business.ermb.provider;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * User: Moshenko
 * Date: 20.05.2013
 * Time: 12:03:39
 * ��� ��������� ���������� ����� 
 */
public class ServiceProviderSmsAlias extends MultiBlockDictionaryRecordBase
{
	protected Long  id;
	private String  name;                                        //�����
	private List<ServiceProviderSmsAliasField> smsAliaseFields;  //���� � ������� ������
	private ServiceProviderBase serviceProvider;                 //��������� ����� �������� �����������  �����

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<ServiceProviderSmsAliasField> getSmsAliaseFields()
	{
		return smsAliaseFields;
	}

	public void setSmsAliaseFields(List<ServiceProviderSmsAliasField> smsAliaseFields)
	{
		this.smsAliaseFields = smsAliaseFields;
	}

	public ServiceProviderBase getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProviderBase serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}
}