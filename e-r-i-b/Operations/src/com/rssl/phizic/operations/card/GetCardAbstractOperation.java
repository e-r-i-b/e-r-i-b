package com.rssl.phizic.operations.card;

import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bankroll.TransactionComparator;
import com.rssl.phizic.business.operations.restrictions.CardRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * User: Kidyaev
 * Date: 18.10.2005
 * Time: 19:05:07
 */
public class GetCardAbstractOperation extends OperationBase<CardRestriction> implements ListEntitiesOperation<CardRestriction>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

	private List<CardLink>  cardLinks;
	private CardLink        cardLink;
    private Calendar        dateFrom;
    private Calendar        dateTo;
	private boolean         withCardUseInfo;

	private boolean isBackError;
	private boolean isUseStoredResource;
	private boolean isError;

	//Текстовое сообщение об ошибке для отображения пользователю при получении выписки по карте
	private final Map<CardLink, String> cardAbstractMsgErrorMap = new HashMap<CardLink, String>();

    public void initialize(Long cardLinkId) throws BusinessException
    {
	    PersonDataProvider provider = PersonContext.getPersonDataProvider();
	    CardLink tmpCardLink = provider.getPersonData().getCard(cardLinkId);
	    CardRestriction restriction = getRestriction();
	    Card card = tmpCardLink.getCard();
	    if(MockHelper.isMockObject(card))
	    {
		    //не можем проверить доступ, поэтому выписку давать не будем
	        isBackError = true;
	    }
	    else
	    {
			if( !restriction.accept(card) )
				log.error("Ошибка доступа. Карта "+card.getNumber()+" исключена из списка",new RestrictionViolationException(" Карта: " + card.getNumber()));
			else
			{
				cardLink = tmpCardLink;
				cardLinks = new ArrayList<CardLink>();
				cardLinks.add(cardLink);
			}
	    }

	    isUseStoredResource = (card instanceof AbstractStoredResource);
    }

	public void initialize(List<CardLink> cardLinks) throws BusinessException
    {
		isUseStoredResource = false;

	    for (CardLink cardLink : cardLinks)
	    {
		    Card card = cardLink.getCard();

		    isUseStoredResource = !isUseStoredResource ? (card instanceof AbstractStoredResource) : isUseStoredResource;
		    
		    if(MockHelper.isMockObject(card))
		    {
		        isBackError = true;
		    }
		    else
		    {
				if( !getRestriction().accept(card) )
				{
					log.error("Ошибка доступа. Карта "+card.getNumber()+" исключена из списка",new RestrictionViolationException(" Карта: " + card.getNumber()));
				}
			    else
				{
					if(this.cardLinks==null)
						this.cardLinks = new ArrayList<CardLink>();	
					this.cardLinks.add(cardLink);
				}
		    }
	    }
    }

    public Calendar getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(Calendar dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public Calendar getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(Calendar dateTo)
    {
        this.dateTo = dateTo;
    }

	public boolean isWithCardUseInfo()
	{
		return withCardUseInfo;
	}

	public void setWithCardUseInfo(boolean withCardUseInfo)
	{
		this.withCardUseInfo = withCardUseInfo;
	}

	public CardLink getCard()
    {
        return cardLink;
    }

	public Map<CardLink, String> getCardAbstractMsgErrorMap()
	{
		return  Collections.unmodifiableMap(cardAbstractMsgErrorMap);
	}

	/**
	 * @return true, если есть возможность получить расширенную выписку
	 */
	public boolean isExtendedCardAbstractAvailable()
	{
		try
		{
			if(getCard() != null)
			{
				//если картсчет есть в БД, то в шлюз не лезем.
				if(!StringHelper.isEmpty(getCard().getCardPrimaryAccount()))
					return true;
				else
				{
					Account account = getCardAccount();
					return (account != null && !StringHelper.isEmpty(account.getNumber()));
				}
			}
			else
				return false;
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при получении карт-счёта CARDLINK_ID=" + cardLink.getId(), e);
			return false;
		}
		catch (BusinessLogicException e)
		{
			log.error("Ошибка при получении карт-счёта CARDLINK_ID=" + cardLink.getId(), e);
			return false;
		}
	}

    public CardAbstract getCardAbstract() throws BusinessLogicException, BusinessException
    {
        try
        {
	        if(cardLink==null)
	            return null;
	        Card card = cardLink.getCard();
	        if(MockHelper.isMockObject(card))
	        {
		        isBackError = true;
	            return null;
	        }
            return (CardAbstract)bankrollService.getAbstract(card, dateFrom, dateTo, withCardUseInfo );
        }
        catch (GateException ge)
        {
	        log.error("Ошибка при получении выписки по карте "+cardLink.getNumber(),new BusinessException(ge));
			isBackError = true;
	        return null;
        }
	    catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
    }
	//Временная заглушка. Пока не реализуем полноценную выписку по карте.
	public Account getCardAccount() throws BusinessLogicException, BusinessException
	{
		return cardLink.getCardAccount();
	}

	//Временная заглушка. Пока не реализуем полноценную выписку по карте.
    public AccountAbstract getCardAccountAbstract() throws BusinessLogicException, BusinessException
    {
        try
        {
		   Account account = getCardAccount();
		   if(!MockHelper.isMockObject(account))
		   {
		   	   ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);

				/*
				 * Проверяем доступность ЦОД
				 */
			   ExternalSystemHelper.check(externalSystemGateService.findByProduct(account.getOffice(), BankProductType.Deposit));

			   this.isUseStoredResource = false;
			   return (AccountAbstract)bankrollService.getAbstract(account, dateFrom, dateTo, withCardUseInfo);
		   }
	       else
		   {
			   isBackError=true;
			   return null;

		   }
        }
        catch (GateException ge)
        {
	        isBackError=true;
            log.error("Невозможно получить выписку по СКС карты №"+cardLink.getNumber(), ge);
	        return null;
        }
	    catch (GateLogicException e)
		{
			isBackError=true;
			log.error("Невозможно получить выписку по СКС карты №"+cardLink.getNumber(), e);
			return null;
		}
        catch (InactiveExternalSystemException ignored)
        {
	        isUseStoredResource = true;
			return null;
        }
    }

	/**
	 * @return список карт для получения выписки
	 */
	private Card[] cardArray()
	{
		if (cardLinks == null || cardLinks.isEmpty())
		{
			return null;
		}
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < cardLinks.size(); i++)
		{
			CardLink cardLink = cardLinks.get(i);
			Card card = cardLink.getCard();
			if(!MockHelper.isMockObject(card))
			{
				cards.add(card);
			}
			else
			{
				isBackError = true;
			}
		}
		return cards.toArray(new Card[ cards.size()]);
	}

	/**
	 * @param count - количество операций в выписке
	 * @return мап из кард линка и выписки
	 */
	public Map<CardLink, CardAbstract> getCardAbstract(Long count)
    {
	    Card[] cards = cardArray();

	    if (ArrayUtils.isEmpty(cards))
	    {
		    return Collections.emptyMap();
	    }

	    if (isUseStoredResource())
	    {
		    isError = true;
		    for (CardLink link : cardLinks)
		    {
			    cardAbstractMsgErrorMap.put(link, StoredResourceMessages.getUnreachableStatement());
		    }
		    return Collections.emptyMap();
	    }

		GroupResult<Object, AbstractBase> baseAbsract = bankrollService.getAbstract(count, cards);
		//Переформатируем
		Map<Object, AbstractBase> results = baseAbsract.getResults();
	    Map<Object, IKFLException> exceptions = baseAbsract.getExceptions();
	    Map<CardLink, CardAbstract> cardAbstract = new HashMap<CardLink, CardAbstract>();
		CardLink currentCardLink;
	    for (Map.Entry<Object, AbstractBase> resultEntry : results.entrySet())
		{
			Card card = (Card) resultEntry.getKey();
			currentCardLink = BankrollServiceHelper.findCardLinkByNumber(card.getNumber(), cardLinks);
			if (currentCardLink == null)
			{
				log.error("Получена выписка не по той карте. Карта №" + card.getNumber());
				continue;
			}

			CardAbstract resultCardAbstract = (CardAbstract) resultEntry.getValue();
			if (resultCardAbstract != null)
			{
				List<TransactionBase> transactions = resultCardAbstract.getTransactions();
				//сортируем, если есть что сортировать
				if (transactions != null)
					Collections.sort(resultCardAbstract.getTransactions(), new TransactionComparator());
			}

			cardAbstract.put(currentCardLink, resultCardAbstract);
		}

	    for (Map.Entry<Object, IKFLException> exceptionEntry : exceptions.entrySet())
		{
			Card card = (Card) exceptionEntry.getKey();
			currentCardLink = BankrollServiceHelper.findCardLinkByNumber(card.getNumber(), cardLinks);
			IKFLException ikflException = exceptionEntry.getValue();
			if (currentCardLink != null)
			{
				cardAbstract.put(currentCardLink, null);
				if (ikflException instanceof GateLogicException)
					cardAbstractMsgErrorMap.put(currentCardLink,ikflException.getMessage());
			}
								
			log.error("Произошла ошибка при получении выписки по карте №" + card.getNumber(), ikflException);
		}

	    //Общая ошибка только если по всем картам пришла ошибка
	    if (MapUtils.isEmpty(results) && !MapUtils.isEmpty(exceptions))
		    isBackError = true;

        return cardAbstract;
    }

	/**
	 * была ли ошибка связанная с бэк-офисом
	 * @return
	 */
	public boolean isBackError()
	{
		return isBackError;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	/**
	 *
	 * @return флаг наличия ошибок
	 */
	public boolean isError()
	{
		return isError;
	}
}
