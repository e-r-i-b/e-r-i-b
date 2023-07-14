package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;

import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * композер для методов, которые в качестве параметра могут получать разные сущности, т.е в описании метода Object
 */
public class ObjectToEntityCachKeyComposer extends AbstractCacheKeyComposer
{
	AccountCacheKeyComposer accountCacheKeyComposer;
	CardCacheKeyComposer cardCacheKeyComposer;
	DepositCacheKeyComposer depositCacheKeyComposer;
	IMAccountCacheKeyComposer imAccountCacheKeyComposer;
	AutoPaymentCacheKeyComposer autoPaymentCacheKeyComposer;
	LongOfferCacheKeyComposer longOfferCacheKeyComposer;
	AutoSubscriptionCacheKeyComposer autoSubscriptionCacheKeyComposer;

	public ObjectToEntityCachKeyComposer()
	{
		accountCacheKeyComposer = new AccountCacheKeyComposer();
		cardCacheKeyComposer = new CardCacheKeyComposer();
		depositCacheKeyComposer = new DepositCacheKeyComposer();
		imAccountCacheKeyComposer = new IMAccountCacheKeyComposer();
		autoPaymentCacheKeyComposer = new AutoPaymentCacheKeyComposer();
		longOfferCacheKeyComposer = new LongOfferCacheKeyComposer();
		autoSubscriptionCacheKeyComposer = new AutoSubscriptionCacheKeyComposer();
	}

	public String getKey(Object[] args, String params)
	{
		StringBuilder key = new StringBuilder();
		int mainParamNum = getOneParam(params);
		//получаем ключ по сущности
		Object obj = args[mainParamNum];
		parseObject(key, obj);
		return key.toString();
	}

	private void parseObject(StringBuilder key, Object obj)
	{
		if(obj instanceof Account)
		{
			key.append(accountCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof Card)
		{
			key.append(cardCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof Deposit)
		{
			key.append(cardCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof IMAccount)
		{
			key.append(imAccountCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof AutoPayment)
		{
			key.append(autoPaymentCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof LongOffer)
		{
			key.append(longOfferCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof AutoSubscription)
		{
			key.append(autoSubscriptionCacheKeyComposer.getKey(new Object[]{obj},""));
			return;
		}
		if(obj instanceof String)
		{
			key.append((String)obj);
		}

	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(result instanceof Account)
		{
			return accountCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if(result instanceof Card)
		{
			return cardCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if(result instanceof Deposit)
		{
			return cardCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if(result instanceof IMAccount)
		{
			return imAccountCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if (result instanceof AutoPayment)
		{
			return autoPaymentCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if(result instanceof LongOffer)
		{
			return longOfferCacheKeyComposer.getClearCallbackKey(result, params);
		}
		if(result instanceof AutoSubscription)
		{
			return autoSubscriptionCacheKeyComposer.getClearCallbackKey(result, params);

		}
		return null;
	}
}