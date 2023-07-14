package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.longoffer.autopayment.ThresholdAutoPayScheme;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.text.ParseException;

/**
 * Получение информации о поставщике услуг по id
 * @author niculichev
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderSource extends CachedEntityListSourceBase
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final DepartmentService departmentService = new DepartmentService();

	public ServiceProviderSource(EntityListDefinition definition)
	{
		super(definition);
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException
	{
		String strId = params.get("id");

		if(StringHelper.isEmpty(strId))
		{
			throw new BusinessException("Не задан поставщик услуг");
		}

		ServiceProviderBase provider = serviceProviderService.findById(Long.valueOf(strId));
		if (provider == null)
		{
			throw new BusinessException("Не найден получатель с идентфикатором " + strId);
		}

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		try
		{
			builder.openEntityTag(provider.getId().toString());
			builder.appentField("name", provider.getName());
			builder.appentField("account", provider.getAccount());
			builder.appentField("bankName", provider.getBankName());
			builder.appentField("bic", provider.getBIC());
			builder.appentField("inn", provider.getINN());
			builder.appentField("kpp", provider.getKPP());
			builder.appentField("code", provider.getCode());
			builder.appentField("corrAccount", provider.getCorrAccount());
			builder.appentField("creationDate", DateHelper.toString(provider.getCreationDate().getTime()));
			Department providerDepartment = departmentService.findById(provider.getDepartmentId());
			builder.appentField("departmentName", providerDepartment.getName());
			builder.appentField("description", provider.getDescription());
			builder.appentField("NSICode", provider.getNSICode());
			builder.appentField("officeName", providerDepartment.getName());
			builder.appentField("transitAccount", provider.getTransitAccount());
			builder.appentField("synchKey", provider.getSynchKey().toString());
			builder.appentField("bankDetails", Boolean.toString(provider.isBankDetails()));
			builder.appentField("state", StringHelper.getEmptyIfNull(provider.getState()));
			builder.appentField("residentBank", provider.getBank() != null ? provider.getBank().getName() : null);
			builder.appentField("officeName", providerDepartment.getName());
			builder.appentField("phoneNumber", provider.getPhoneNumber());
			builder.appentField("isCreditCardSupported", Boolean.toString(provider.isCreditCardSupported()));

			if(provider instanceof BillingServiceProvider)
			{
				BillingServiceProvider billingProvider = (BillingServiceProvider) provider;
				builder.appentField("accountType", StringHelper.getEmptyIfNull(billingProvider.getAccountType()));
				builder.appentField("maxComissionAmount", StringHelper.getEmptyIfNull(billingProvider.getMaxComissionAmount()));
				builder.appentField("minComissionAmount", StringHelper.getEmptyIfNull(billingProvider.getMinComissionAmount()));
				builder.appentField("comissionRate", StringHelper.getEmptyIfNull(billingProvider.getComissionRate()));
				builder.appentField("comissionRate", Boolean.toString(billingProvider.isGround()));
				builder.appentField("deptAvailable", Boolean.toString(billingProvider.isDeptAvailable()));
				builder.appentField("codeService", billingProvider.getCodeService());
				builder.appentField("nameService", billingProvider.getService().getName());
				builder.appentField("companyName", billingProvider.getCompanyName());
				builder.appentField("attrDelimiter", billingProvider.getAttrDelimiter());
				builder.appentField("attrValuesDelimiter", billingProvider.getAttrValuesDelimiter());
				builder.appentField("imageId", StringHelper.getEmptyIfNull(billingProvider.getImageId()));
				builder.appentField("popular", Boolean.toString(billingProvider.isPopular()));
				builder.appentField("propsOnline", Boolean.toString(billingProvider.isPropsOnline()));
				builder.appentField("mobilebank", Boolean.toString(billingProvider.isMobilebank()));
				builder.appentField("federal", Boolean.toString(billingProvider.isFederal()));
				builder.appentField("visiblePaymentsForInternetBank", Boolean.toString(billingProvider.isVisiblePaymentsForInternetBank()));
				builder.appentField("visiblePaymentsForMApi", Boolean.toString(billingProvider.isVisiblePaymentsForMApi()));
				builder.appentField("visiblePaymentsForAtmApi", Boolean.toString(billingProvider.isVisiblePaymentsForAtmApi()));
				builder.appentField("visiblePaymentsForSocialApi", Boolean.toString(billingProvider.isVisiblePaymentsForSocialApi()));
				builder.appentField("availablePaymentsForInternetBank", Boolean.toString(billingProvider.isAvailablePaymentsForInternetBank()));
				builder.appentField("availablePaymentsForMApi", Boolean.toString(billingProvider.isAvailablePaymentsForMApi()));
				builder.appentField("availablePaymentsForAtmApi", Boolean.toString(billingProvider.isAvailablePaymentsForAtmApi()));
				builder.appentField("availablePaymentsForSocialApi", Boolean.toString(billingProvider.isAvailablePaymentsForSocialApi()));
				builder.appentField("availablePaymentsForErmb", Boolean.toString(billingProvider.isAvailablePaymentsForErmb()));
				builder.appentField("mobilebankCode", billingProvider.getMobilebankCode());
				builder.appentField("BillingName", billingProvider.getBilling().getName());
				builder.appentField("codeRecipientSBOL", billingProvider.getCodeRecipientSBOL());
				builder.appentField("nameService", billingProvider.getNameService());
				builder.appentField("alias", billingProvider.getAlias());
				builder.appentField("legalName", billingProvider.getLegalName());
				builder.appentField("nameOnBill", billingProvider.getNameOnBill());
				builder.appentField("isAutoPaymentSupported", Boolean.toString(billingProvider.isAutoPaymentSupported()));
				builder.appentField("isAutoPaymentSupportedInApi", Boolean.toString(billingProvider.isAutoPaymentSupportedInApi()));
				builder.appentField("isAutoPaymentSupportedInATM", Boolean.toString(billingProvider.isAutoPaymentSupportedInATM()));
				builder.appentField("isAutoPaymentSupportedInSocialApi", Boolean.toString(billingProvider.isAutoPaymentSupportedInSocialApi()));
				ThresholdAutoPayScheme thresholdAutoPayScheme = billingProvider.getThresholdAutoPayScheme();
				builder.appentField("isThresholdAutoPaySupported", Boolean.toString(thresholdAutoPayScheme != null));
				if (thresholdAutoPayScheme != null && !thresholdAutoPayScheme.isInterval())
				{
					for (BigDecimal limit : thresholdAutoPayScheme.getAccessValuesAsList())
						builder.appentField("thresholdAutoPayLimit", StringHelper.getEmptyIfNull(limit));
				}
				if(thresholdAutoPayScheme != null && thresholdAutoPayScheme.isAccessTotalMaxSum())
				{
					builder.appentField("IsTotalMaxSumSupported", Boolean.TRUE.toString(), "ThresholdAutoPayScheme#");
					builder.appentField("TotalMaxSumPeriod", thresholdAutoPayScheme.getPeriodMaxSum().name(), "ThresholdAutoPayScheme#");
				}

				builder.appentField("isAlwaysAutoPaySupported", Boolean.toString(billingProvider.getAlwaysAutoPayScheme() != null));
				builder.appentField("isInvoiceAutoPayScheme", Boolean.toString(billingProvider.getAlwaysAutoPayScheme() != null));
			}

			if(provider instanceof TaxationServiceProvider)
			{
				TaxationServiceProvider taxProvider = (TaxationServiceProvider) provider;
				builder.appentField("payType", taxProvider.getPayType().toString());
				builder.appentField("nameOnBill", Boolean.toString(taxProvider.isFullPayment()));
			}
			if(provider instanceof InternetShopsServiceProvider)
			{
				InternetShopsServiceProvider shopsServiceProvider = (InternetShopsServiceProvider) provider;
				builder.appentField("isFacilitator", Boolean.toString(shopsServiceProvider.isFacilitator()));
			}

		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();
		return convertToReturnValue(builder.toString(), provider);
	}
}
