package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.CardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NullCardFilter;
import com.rssl.phizic.business.resources.external.comparator.CurrencyComparator;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Egorova
 * @ created 30.10.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * Если надо добавить инфу из info или чего-то отличного от Card с обращением к гейту, то используйте наследников.
 */
public class CardListSource extends CachedEntityListSourceBase
{
	protected CardFilter cardFilter;
	protected Comparator comparator;

	public CardListSource(EntityListDefinition definition)
	{
		this(definition, new NullCardFilter() );
	}

    public CardListSource(EntityListDefinition definition, CardFilter cardFilter)
    {
	    super(definition);
        this.cardFilter = cardFilter;
    }

    public CardListSource(EntityListDefinition definition, Map parameters) throws BusinessException
    {
	    super(definition);
        try
        {
            String filterClassName = (String) parameters.get(FILTER_CLASS_NAME_PARAMETER);
            Class  filterClass     = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);
	        String comparatorClassName = (String) parameters.get(COMPARATOR_CLASS_NAME_PARAMETER);
	        if(StringHelper.isNotEmpty(comparatorClassName))
	        {
	            Class comparatorClass = Thread.currentThread().getContextClassLoader().loadClass(comparatorClassName);
		        comparator = (Comparator) comparatorClass.newInstance();
	        }
            cardFilter = (CardFilter) filterClass.newInstance();
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

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		if (provider == null || PersonHelper.isGuest())
		{
			builder.closeEntityListTag();
			return convertToReturnValue(builder.toString());
		}

		try
		{
			List<CardLink> cardLinks = getCards(provider.getPersonData());
			Collections.sort(cardLinks, getCardLinkComparator());

			for (CardLink cardLink : cardLinks)
			{
				Card card = cardLink.getCard();
				if (!MockHelper.isMockObject(card) && skipStoredResource(card) && !isInterestCard(cardLink.getNumber(), params))
				try
				{
					String key = card.getNumber();
					builder.openEntityTag(key);
					builder.appentField("type", card.getDescription());
					builder.appentField("name", cardLink.getName());
					builder.appentField("description", cardLink.getDescription());
					builder.appentField("cardLinkId", cardLink.getId().toString());
					builder.appentField("isMain", Boolean.toString(card.isMain()));
					builder.appentField("isVirtual", Boolean.toString(card.isVirtual()));
					builder.appentField("additionalCardType",      card.getAdditionalCardType() == null ? null : card.getAdditionalCardType().toString());
					builder.appentField("additionalCardTypeValue", card.getAdditionalCardType() == null ? null : card.getAdditionalCardType().getValue());
					String dateString = String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(card.getExpireDate()));
					builder.appentField("expireDate", dateString);
					builder.appentField("displayedExpireDate", card.getDisplayedExpireDate());
					builder.appentField("cardLinkId", cardLink.getId().toString());
					builder.appentField("code", cardLink.getCode());
					builder.appentField("amountDecimal", card.getAvailableLimit() == null ? null : String.valueOf(card.getAvailableLimit().getDecimal()));
					builder.appentField("currencyCode", card.getCurrency().getCode());
					builder.appentField("cardState", card.getCardState().toString());
					builder.appentField("cardAccountState", card.getCardAccountState() != null ? card.getCardAccountState().toString(): null);
					builder.appentField("statusDescription",card.getStatusDescription());
					builder.appentField("cardAccount", cardLink.getCardPrimaryAccount());
					builder.appentField("cardType", card.getCardType().name());
					builder.appentField("cardTypeText", card.getCardType().getDescription());
					builder.appentField("issueDate", String.format("%1$te.%1$tm.%1$tY", DateHelper.toDate(card.getIssueDate())));
					builder.appentField("statusDescExternalCode", card.getStatusDescExternalCode() == null ? "" : card.getStatusDescExternalCode().toString());
					builder.appentField("formatedPersonName", PersonHelper.getFormattedPersonName(PersonContext.getPersonDataProvider().getPersonData().getPerson()));
					builder.appentField("UNICardType", card.getUNICardType());
					builder.appentField("mainCardNumber", card.getMainCardNumber());

					getOfficeInfo(builder, cardLink);
					try
					{
						addOwnerInfo(builder,cardLink);
					}
					catch(Exception e)
					{
						log.error("Ошибка при получении владельца карты № " + cardLink.getNumber(),e);
					}
					
					builder.appentField("isUseStoredResource", String.valueOf(card instanceof AbstractStoredResource));
					addMobileBankRegistrationInfo(builder, cardLink);
				}
				finally
				{
					builder.closeEntityTag();
				}
	        }
		}
		catch(InactiveExternalSystemException ex)
		{
			log.error("Ошибка при добавлении карт", ex);
		}

	    builder.closeEntityListTag();

	    return convertToReturnValue(builder.toString());
    }

	protected boolean skipStoredResource(Card card)
	{
		return !(card instanceof AbstractStoredResource);
	}

	/**
	 * Получение информации о подразделении
	 * @param builder
	 * @param cardLink
	 */
	protected void getOfficeInfo(EntityListBuilder builder, CardLink cardLink)
	{
		return;
	}

	/**
	 * Добавить информацию о владельце
	 * @param builder
	 * @param cardLink
	 */
	protected void addOwnerInfo(EntityListBuilder builder,CardLink cardLink) throws BusinessException, BusinessLogicException
	{

	}

	protected List<CardLink> getCards(PersonData personData) throws BusinessException, BusinessLogicException
	{
		return personData.getCards(cardFilter);
	}

	protected boolean isInterestCard(String cardNum, Map<String, String> params) throws BusinessException
	{
		return false;
	}

	protected Comparator getCardLinkComparator()
	{
		return comparator != null ? comparator : new CurrencyComparator();
	}

	protected void addMobileBankRegistrationInfo(EntityListBuilder builder, CardLink cardLink)
	{
		return;
	}
}
