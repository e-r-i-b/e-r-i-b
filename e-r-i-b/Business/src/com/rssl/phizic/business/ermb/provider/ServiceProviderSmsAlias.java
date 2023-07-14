package com.rssl.phizic.business.ermb.provider;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * User: Moshenko
 * Date: 20.05.2013
 * Time: 12:03:39
 * СМС псевдоним постовщика услуг 
 */
public class ServiceProviderSmsAlias extends MultiBlockDictionaryRecordBase
{
	protected Long  id;
	private String  name;                                        //алиас
	private List<ServiceProviderSmsAliasField> smsAliaseFields;  //поля в разрезе алиаса
	private ServiceProviderBase serviceProvider;                 //поставщик услуг которому принадлежит  алиас

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