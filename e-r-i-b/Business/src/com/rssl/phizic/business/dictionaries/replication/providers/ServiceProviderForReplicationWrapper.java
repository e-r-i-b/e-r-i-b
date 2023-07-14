package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;

import java.util.Set;

/**
 * сущность, необходимая ТОЛЬКО для репликации поставщиков.
 * @author lukina
 * @ created 03.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderForReplicationWrapper  extends MultiBlockDictionaryRecordBase implements DictionaryRecord
{
	private Long id;
	private BillingServiceProvider provider;   //поставщик
	private Set<PaymentService> paymentServices; //список услуг потавщика

	public ServiceProviderForReplicationWrapper()
	{}
	public ServiceProviderForReplicationWrapper(BillingServiceProvider provider, Set<PaymentService> paymentServices)
	{
		this.provider = provider;
		this.paymentServices = paymentServices;
	}

	public BillingServiceProvider getProvider()
	{
		return provider;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setProvider(BillingServiceProvider provider)
	{
		this.provider = provider;
	}

	public Set<PaymentService> getPaymentServices()
	{
		return paymentServices;
	}

	public void setPaymentServices(Set<PaymentService> paymentServices)
	{
		this.paymentServices = paymentServices;
	}

	public Comparable getSynchKey()
	{
		return provider.getSynchKey();
	}

	public void updateFrom(DictionaryRecord that)
	{
		ServiceProviderForReplicationWrapper wrapper = (ServiceProviderForReplicationWrapper) that;
		provider.updateFrom(wrapper.getProvider());
		BeanHelper.copyPropertiesFull(paymentServices, wrapper.getPaymentServices());
	}

	public void updateFrom(DictionaryRecord that, ReplicationType type)
	{
		ServiceProviderForReplicationWrapper wrapper = (ServiceProviderForReplicationWrapper) that;
		provider.updateFrom(wrapper.getProvider(), type);
		paymentServices.clear();
		paymentServices.addAll(wrapper.getPaymentServices());
	}

	public String getMultiBlockRecordId()
	{
		return provider.getMultiBlockRecordId();
	}

	@Override
	public String getUuid()
	{
		return provider.getUuid();
	}

	@Override
	public void setUuid(String uuid)
	{
		provider.setUuid(uuid);
	}
}
