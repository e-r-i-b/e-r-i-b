package com.rssl.phizic.business.loanclaim.officeClaim;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.etsm.offer.service.OfferPriorWebService;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProductService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductType;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditProductTypeService;
import com.rssl.phizic.business.loanclaim.creditProduct.type.CreditSubProductType;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * @author Nady
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для получения заявок на кредит созданных в каналах отличных от УКО
 */
public class OfficeLoanClaimCaheService extends SimpleService
{
	private static final Cache OFFICE_LOAN_CLAIM_CACHE;
	private static final Object LOCKER = new Object();
	public static final String OFFICE_LOAN_CLAIM_ELEMENT_CACHE = "officeLoanClaimCache";
	public static final String OFFICE_LOAN_CLAIM_CACHE_KEY = "OfficeLoanClaimCacheService.getOfficeLoanClaimFromCache";

	private static final CreditProductTypeService creditProductTypeService = new CreditProductTypeService();
	private static final CreditProductService creditProductService = new CreditProductService();

	static
	{
		OFFICE_LOAN_CLAIM_CACHE = CacheProvider.getCache(OFFICE_LOAN_CLAIM_CACHE_KEY);

		if (OFFICE_LOAN_CLAIM_CACHE == null)
			throw new RuntimeException("Не найден кеш для заявок на кредит");
	}

	public static void doClearCache(String key)
	{
		synchronized (LOCKER)
		{
			OFFICE_LOAN_CLAIM_CACHE.remove(OFFICE_LOAN_CLAIM_ELEMENT_CACHE + key);
		}
	}

	public List<OfficeLoanClaim> getOfficeLoanClaimList()
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		String fio =  person.getSurName()+person.getFirstName()+person.getPatrName();
		String doc="";
		String cacheKey =  person.getLogin().getId().toString();
		for (PersonDocument document : person.getPersonDocuments())
		{
			if (document.getDocumentType().equals(PersonDocumentType.REGULAR_PASSPORT_RF))
			{
				doc = document.getDocumentSeries() + document.getDocumentNumber();

			}
		}
		try
		{
			List<OfficeLoanClaim> officeLoanClaimList = getOfficeLoanClaimListFromCache(OFFICE_LOAN_CLAIM_ELEMENT_CACHE + cacheKey);
			if (CollectionUtils.isNotEmpty(officeLoanClaimList))
				return officeLoanClaimList;

			synchronized (LOCKER)
			{
				List<OfficeLoanClaim> officeLoanClaims = getOfficeLoanClaimListFromCache(OFFICE_LOAN_CLAIM_ELEMENT_CACHE + cacheKey);
				if (CollectionUtils.isNotEmpty(officeLoanClaims))
					return officeLoanClaims;

				return addToCache(OFFICE_LOAN_CLAIM_ELEMENT_CACHE + cacheKey, getActualOfficeLoanClaimList(fio, doc, person.getBirthDay()));
			}
		}
		catch (BusinessException e)
		{
			log.error(e);
		}
		return null;
	}

	public List<OfficeLoanClaim> getActualOfficeLoanClaimList(String FIO, String Doc, Calendar birthday) throws BusinessException
	{
		OfferPriorWebService offerPriorWebService = new OfferPriorWebService();
		List<OfficeLoanClaim> officeLoanClaimList = offerPriorWebService.getOfficeLoanClaims(FIO, Doc, birthday);
		for (OfficeLoanClaim claim : officeLoanClaimList)
		{
			List<CreditProduct> creditProductList = creditProductService.findByCode(claim.getProductCode());
			boolean isFindCreditProduct = false;
			if (CollectionUtils.isNotEmpty(creditProductList))
			{
				for (CreditProduct creditProduct : creditProductList)
				{
					Set<CreditSubProductType> creditSubProductTypes = creditProduct.getCreditSubProductTypes();
					if (CollectionUtils.isNotEmpty(creditSubProductTypes))
					{
						for (CreditSubProductType creditSubProductType : creditSubProductTypes)
						{
							if (claim.getSubProductCode().equals(creditSubProductType.getCode()))
							{
								claim.setProductName(creditProduct.getName());
								isFindCreditProduct = true;
							}
						}
					}
				}
			}
			if (!isFindCreditProduct)
			{
				CreditProductType creditProductType = creditProductTypeService.findeByCode(claim.getType());
				if (creditProductType!=null)
					claim.setProductName(creditProductType.getName());
			}
		}
		return officeLoanClaimList;
	}

	public OfficeLoanClaim getOfficeLoanClaimByAppNum(String appNumber)
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		String cacheKey =  person.getLogin().getId().toString();
		Element element  = OFFICE_LOAN_CLAIM_CACHE.get(OFFICE_LOAN_CLAIM_ELEMENT_CACHE + cacheKey);
		if ( element != null )
		{
			List<OfficeLoanClaim> officeLoanClaimList =  (List<OfficeLoanClaim>) element.getObjectValue();
			return getOfficeLoanClaimByAppNumberFronList(officeLoanClaimList, appNumber);

		} else
		{
			return getOfficeLoanClaimByAppNumberFronList(getOfficeLoanClaimList(), appNumber);
		}
	}

	private List<OfficeLoanClaim> getOfficeLoanClaimListFromCache(String key)
	{
		Element element  = OFFICE_LOAN_CLAIM_CACHE.get(key);
		if ( element != null )
		{
			return (List<OfficeLoanClaim>) element.getObjectValue();
		}
		return null;
	}

	private List<OfficeLoanClaim> addToCache(String key, List<OfficeLoanClaim> officeLoanClaimList)
	{
		if (CollectionUtils.isNotEmpty(officeLoanClaimList))
			OFFICE_LOAN_CLAIM_CACHE.put(new Element(key, officeLoanClaimList));
		return officeLoanClaimList;
	}

	private OfficeLoanClaim getOfficeLoanClaimByAppNumberFronList(List<OfficeLoanClaim> officeLoanClaimList, String appNumber)
	{
		if (officeLoanClaimList != null)
			for (OfficeLoanClaim officeLoanClaim : officeLoanClaimList)
			{
				if (officeLoanClaim.getApplicationNumber().equals(appNumber))
					return officeLoanClaim;
			}
		return null;
	}
}
