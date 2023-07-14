package com.rssl.phizic.business.xslt.lists;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanCardClaimSourceFilter;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Jatsky
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardClaimSource extends CachedEntityListSourceBase
{
	private static final CreditCardProductService service = new CreditCardProductService();

	protected LoanCardClaimSourceFilter cardFilter;

	public LoanCardClaimSource(EntityListDefinition definition)
	{
		this(definition, new LoanCardClaimSourceFilter());
	}

	public LoanCardClaimSource(EntityListDefinition definition, LoanCardClaimSourceFilter cardFilter)
	{
		super(definition);
		this.cardFilter = cardFilter;
	}

	public LoanCardClaimSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition);
		try
		{
			String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
			Class filterClass = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

			cardFilter = (LoanCardClaimSourceFilter) filterClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
	}

	public Pair<String, List<Object>> buildEntityList(Map<String, String> params) throws BusinessException, BusinessLogicException
	{

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		PersonDataProvider provider   = PersonContext.getPersonDataProvider();
		if (provider == null)
		{
			builder.closeEntityListTag();
			return convertToReturnValue(builder.toString());
		}

		PersonData    personData    = provider.getPersonData();
		LoanCardOffer loanCardOffer = null;
		if (!PersonHelper.isGuest() || PersonHelper.isRegisteredGuest())
		{
			if (StringHelper.isEmpty(params.get("offerId")))
			{
				loanCardOffer = personData.getNewLoanCardOffer();
			}
			else
			{
				loanCardOffer = personData.findLoanCardOfferById(OfferId.fromString(params.get("offerId")));
			}
		}

		List<CreditCardProduct> creditCardProductList = new ArrayList<CreditCardProduct>();
		if (loanCardOffer == null)
		{
			creditCardProductList = filter(service.getAll(), StringHelper.isEmpty(params.get("offerId")));

			CollectionUtils.filter(creditCardProductList, new Predicate()
			{
				public boolean evaluate(Object object)
				{
					CreditCardProduct             product    = (CreditCardProduct) object;
					List<CreditCardCondition>     conditions = product.getConditions();
					Iterator<CreditCardCondition> iterator   = conditions.listIterator();

					while (iterator.hasNext())
					{
						if (!"RUB".equals(iterator.next().getCurrency().getCode()))
						{
							iterator.remove();
						}
					}

					return conditions.size() > 0 && product.getPublicity() == Publicity.PUBLISHED;
				}
			});
		}
		else
		{
			creditCardProductList = service.getPublicApprovedProducts(loanCardOffer.getMaxLimit().getCurrency().getCode(), loanCardOffer.getMaxLimit().getDecimal());
		}

		for (CreditCardProduct creditCardProduct : creditCardProductList)
		{
			EntityBuilder entityBuilder = new EntityBuilder();
			entityBuilder.openEntityTag("CreditCardProduct");
			entityBuilder.appentField("id", creditCardProduct.getId().toString());
			entityBuilder.appentField("name", creditCardProduct.getName().toString());
			entityBuilder.appentField("period", creditCardProduct.getGracePeriodDuration() == null ? null : creditCardProduct.getGracePeriodDuration().toString());
			entityBuilder.appentField("interestRate", creditCardProduct.getGracePeriodInterestRate() == null ? null : creditCardProduct.getGracePeriodInterestRate().toString());
			entityBuilder.appentField("additionalTerms", creditCardProduct.getAdditionalTerms());
			entityBuilder.appentField("default", creditCardProduct.getDefaultForPreapprovedOffers().toString());

			List<CreditCardCondition> conditions = creditCardProduct.getConditions();
			for (CreditCardCondition condition : conditions)
			{
				if (loanCardOffer == null || condition.getCurrency().getCode().equals(loanCardOffer.getMaxLimit().getCurrency().getCode()))
				{
					entityBuilder.appentField("minLimit",  condition.getMinCreditLimit().getValue().getDecimal().toString());
					if (loanCardOffer == null)
                        entityBuilder.appentField("maxLimit",  condition.getMaxCreditLimit().getValue().getDecimal().toString());
					entityBuilder.appentField("currency",  condition.getMaxCreditLimit().getValue().getCurrency().getCode());
					entityBuilder.appentField("rate",      condition.getInterestRate().toString());
					entityBuilder.appentField("firstYear", condition.getFirstYearPayment().getDecimal().toString());
					entityBuilder.appentField("nextYear",  condition.getNextYearPayment().getDecimal().toString());
				}
			}

			if (loanCardOffer != null)
			{
				entityBuilder.appentField("maxLimit", loanCardOffer.getMaxLimit().getDecimal().toString());
				entityBuilder.appentField("passport-number", loanCardOffer.getSeriesAndNumber());
				entityBuilder.appentField("offerId",         loanCardOffer.getOfferId().toString());
				entityBuilder.appentField("offerType",       loanCardOffer.getOfferType().getValue());
			}

			String cardNumber = null;
			if (loanCardOffer != null && loanCardOffer.getCardNumber() != null)
			{
				cardNumber = MaskUtil.getCutCardNumber(loanCardOffer.getCardNumber());
			}
			else
			{
				CardLink creditCard = CardsUtil.getClientCreditCard();

				if (creditCard != null)
				{
					cardNumber = MaskUtil.getCutCardNumber(creditCard.getCard().getNumber());
				}
			}

			entityBuilder.appentField("cardTypeCode", creditCardProduct.getCardTypeCode().toString());
			entityBuilder.appentField("cardNumber",   cardNumber);
			entityBuilder.closeEntityTag();
			builder.addEntity(entityBuilder);
		}

		builder.closeEntityListTag();

		return convertToReturnValue(builder.toString());
	}

	/**
	 * Фильтр кредитных карточных продуктов доступных для выбота
	 * @param products
	 * @param isLeadClaim - признак ЛИД-заявки (т.е. без предодобренного предложения)
	 * @return
	 */
	private List<CreditCardProduct> filter(List<CreditCardProduct> products, boolean isLeadClaim)
	{
		Iterator<CreditCardProduct> iterator = products.listIterator();

		while (iterator.hasNext())
		{
			CreditCardProduct product = iterator.next();
			//Если нет доступных условий по продуктам или продукт используется для предодобренных, но сейчас делается ЛИД-заявка, то пропускаем
			if (product.getConditions().isEmpty() && isLeadClaim)
			{
				iterator.remove();
				continue;
			}
			if (PersonHelper.isGuest())
			{
				// Зарегистрированный гость - доступны заявки как на предодобренных, так и на обычных условиях
				if (PersonHelper.isRegisteredGuest())
				{
					if (!product.getGuestLead() && !product.getGuestPreapproved())
					{
						iterator.remove();
					}
				}
				// Незарегистрируемому только на обычных условиях
				else
				{
					if (!product.getGuestLead())
					{
						iterator.remove();
					}
				}
			}
			else
			{
				if (!product.getUseForPreapprovedOffers() && !product.getCommonLead())
				{
					iterator.remove();
				}
			}
		}

		return products;
	}
}
