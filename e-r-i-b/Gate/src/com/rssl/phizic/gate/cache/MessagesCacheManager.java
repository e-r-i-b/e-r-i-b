package com.rssl.phizic.gate.cache;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.security.SecurityAccount;
import org.w3c.dom.Document;

import java.util.*;

/**
 * @author Gainanov
 * @ created 06.12.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class MessagesCacheManager extends AbstractService
{
	private Map<Class, List<MessagesCache> > cachesByClasses = new HashMap<Class, List<MessagesCache> >();

	public MessagesCacheManager(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * заполнение данных для очистки кеша
	 * @param cashes
	 */
	protected void fillRemoveMap(Collection<MessagesCache> cashes)
	{
		for (MessagesCache messagesCache : cashes)
		{
			List<Class> classes = messagesCache.getCacheClasses();
			for (Class aClass : classes)
			{
				List<MessagesCache> cacheList = cachesByClasses.get(aClass);
				if(cacheList == null)
				{
					cacheList = new ArrayList<MessagesCache>();
				}
				cacheList.add(messagesCache);
				cachesByClasses.put(aClass, cacheList);
			}
		}
	}

	/**
	 * Получить документ из кеша, если он там есть
	 * @param message
	 * @return
	 */
	public abstract Document get(Document message);

	/**
	 * Записать в кеш запрос и ответ.
	 * @param request
	 * @param response
	 */
	public abstract void put(Document request, Document response);
	/**
	 * очистить все сообщения из кеша связанные со счетом.
	 * @param account
	 */
	public void clear(Account account) throws GateException, GateLogicException
	{
		clear(Account.class, account);		
	}

	/**
	 * очистить все сообщения из кеша связанные с картой.
	 * @param card
	 */
	public void clear(Card card) throws GateException, GateLogicException
	{
	    clear(Card.class, card);
	}

	/**
	 * очистить все сообщения из кеша связанные со вкладом.
	 * @param deposit
	 */
	public void clear(Deposit deposit) throws GateException, GateLogicException
	{
	    clear(Deposit.class, deposit);
	}

	public void clear(CurrencyRate rate) throws GateException, GateLogicException
	{
		clear(CurrencyRate.class, rate);
	}

	public void clear(IMAccount imAccount)  throws GateException, GateLogicException
	{
		clear(IMAccount.class, imAccount);
	}

	public void clear(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		clear(DepoAccount.class, depoAccount);
	}

	public void clear(Loan loan) throws GateException, GateLogicException
	{
		clear(Loan.class, loan);
	}

	public void clear(LongOffer longOffer) throws GateException, GateLogicException
	{
		clear(LongOffer.class, longOffer);
	}

	public void clear(AutoPayment autoPayment) throws GateException, GateLogicException
	{
		clear(AutoPayment.class, autoPayment);
	}

	public void clear(AutoSubscription autoSubscription) throws GateException, GateLogicException
	{
		clear(AutoSubscription.class, autoSubscription);
	}

	private void clear(Class aClass, Object object) throws GateException, GateLogicException
	{
		List<MessagesCache> cacheList = cachesByClasses.get(aClass);
		if(cacheList==null)
			return;

		for (MessagesCache messagesCache : cacheList)
		{
			messagesCache.clear(object);
		}
	}

	public void clear(InsuranceApp insuranceApp) throws GateException, GateLogicException
	{
		clear(InsuranceApp.class, insuranceApp);
	}

	public void clear(SecurityAccount securityAccount) throws GateException, GateLogicException
	{
		clear(SecurityAccount.class, securityAccount);
	}

}
