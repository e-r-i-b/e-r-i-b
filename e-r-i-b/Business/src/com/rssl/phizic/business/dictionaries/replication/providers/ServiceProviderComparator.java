package com.rssl.phizic.business.dictionaries.replication.providers;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.common.AbstractCompatator;

/**
 * @author khudyakov
 * @ created 11.01.2010
 * @ $Author$
 * @ $Revision$
 *
 *  Сраниваем только биллинговых поставщиков услуг
 */

public class ServiceProviderComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}

		if (o1 == null || o2 == null)
		{
			return -1;
		}

		ServiceProviderForReplicationWrapper wrapper1 = (ServiceProviderForReplicationWrapper) o1;
		ServiceProviderForReplicationWrapper wrapper2 = (ServiceProviderForReplicationWrapper) o2;
		BillingServiceProvider provider1 = wrapper1.getProvider();
		BillingServiceProvider provider2 = wrapper2.getProvider();

		if (!isObjectsEquals(provider1.getClass(), provider2.getClass()))
			return -1;

		if (!isObjectsEquals(provider1.getSynchKey(), provider2.getSynchKey()))
			return -1;

		if (!isObjectsEquals(provider1.getCode(), provider2.getCode()))
			return -1;

		if (!isObjectsEquals(provider1.getCodeRecipientSBOL(), provider2.getCodeRecipientSBOL()))
			return -1;

		if (!isObjectsEquals(provider1.getCodeService(), provider2.getCodeService()))
			return -1;

		if (!isObjectsEquals(provider1.getNameService(), provider2.getNameService()))
			return -1;

		if (!isObjectsEquals(provider1.getName(), provider2.getName()))
			return -1;

		if (!isObjectsEquals(provider1.getLegalName(), provider2.getLegalName()))
			return -1;

		if (!isObjectsEquals(provider1.getAlias(), provider2.getAlias()))
			return -1;

		if (!isObjectsEquals(provider1.getNameOnBill(), provider2.getNameOnBill()))
			return -1;

		if (!isObjectsEquals(provider1.getNameOnBill(), provider2.getNameOnBill()))
			return -1;

		if (!isObjectsEquals(provider1.getDescription(), provider2.getDescription()))
			return -1;

		if (!isObjectsEquals(provider1.getINN(), provider2.getINN()))
			return -1;

		if (!isObjectsEquals(provider1.getAccount(), provider2.getAccount()))
			return -1;

		if (!isObjectsEquals(provider1.getBIC(), provider2.getBIC()))
			return -1;

		if (!isObjectsEquals(provider1.getBankName(), provider2.getBankName()))
			return -1;

		if (!isObjectsEquals(provider1.getCorrAccount(), provider2.getCorrAccount()))
			return -1;

		if (!isObjectsEquals(provider1.getBilling(), provider2.getBilling()))
			return -1;

		if (!isObjectsEquals(wrapper1.getPaymentServices(), wrapper2.getPaymentServices()))
			return -1;

		if (!isObjectsEquals(provider1.getMaxComissionAmount(), provider2.getMaxComissionAmount()))
			return -1;

		if (!isObjectsEquals(provider1.getMinComissionAmount(), provider2.getMinComissionAmount()))
			return -1;

		if (!isObjectsEquals(provider1.getComissionRate(), provider2.getComissionRate()))
			return -1;

		if (!isObjectsEquals(provider1.getDepartmentId(), provider2.getDepartmentId()))
			return -1;

		if (!isObjectsEquals(provider1.getTransitAccount(), provider2.getTransitAccount()))
			return -1;

		if (!isObjectsEquals(provider1.isDeptAvailable(), provider2.isDeptAvailable()))
			return -1;

		if (!isObjectsEquals(provider1.isBankDetails(), provider2.isBankDetails()))
			return -1;

		if (!isObjectsEquals(provider1.isFederal(), provider2.isFederal()))
			return -1;

		if (!isObjectsEquals(provider1.isMobilebank(), provider2.isMobilebank()))
			return -1;

		if (!isObjectsEquals(provider1.isPopular(), provider2.isPopular()))
			return -1;

		if (!isObjectsEquals(provider1.isPropsOnline(), provider2.isPropsOnline()))
			return -1;

		if (!isObjectsEquals(provider1.isVisiblePaymentsForAtmApi(), provider2.isVisiblePaymentsForAtmApi()))
			return -1;

		if (!isObjectsEquals(provider1.isVisiblePaymentsForSocialApi(), provider2.isVisiblePaymentsForSocialApi()))
			return -1;

		if (!isObjectsEquals(provider1.isVisiblePaymentsForInternetBank(), provider2.isVisiblePaymentsForInternetBank()))
			return -1;

		if (!isObjectsEquals(provider1.isVisiblePaymentsForMApi(), provider2.isVisiblePaymentsForMApi()))
			return -1;

		if (!isObjectsEquals(provider1.isAvailablePaymentsForAtmApi(), provider2.isAvailablePaymentsForAtmApi()))
			return -1;

		if (!isObjectsEquals(provider1.isAvailablePaymentsForSocialApi(), provider2.isAvailablePaymentsForSocialApi()))
			return -1;

		if (!isObjectsEquals(provider1.isAvailablePaymentsForInternetBank(), provider2.isAvailablePaymentsForInternetBank()))
			return -1;

		if (!isObjectsEquals(provider1.isAvailablePaymentsForMApi(), provider2.isAvailablePaymentsForMApi()))
			return -1;

		if (!isObjectsEquals(provider1.isAvailablePaymentsForErmb(), provider2.isAvailablePaymentsForErmb()))
			return -1;

		if (!isObjectsEquals(provider1.isGround(), provider2.isGround()))
			return -1;

		if (!isObjectsEquals(provider1.getAttrDelimiter(), provider2.getAttrDelimiter()))
			return -1;

		if (!isObjectsEquals(provider1.getAttrValuesDelimiter(), provider2.getAttrValuesDelimiter()))
			return -1;

		if (!isObjectsEquals(provider1.getNSICode(), provider2.getNSICode()))
			return -1;

		if (!isObjectsEquals(provider1.getState(), provider2.getState()))
			return -1;

		if (!isObjectsEquals(provider1.getCompanyName(), provider2.getCompanyName()))
			return -1;

		if (!isObjectsEquals(provider1.getFieldDescriptions(), provider2.getFieldDescriptions()))
			return -1;

		if (!isObjectsEquals(provider1.getRegions(), provider2.getRegions()))
			return -1;

		return 0;
	}
}
