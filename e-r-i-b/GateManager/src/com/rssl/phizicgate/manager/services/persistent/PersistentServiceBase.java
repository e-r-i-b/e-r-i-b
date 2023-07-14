package com.rssl.phizicgate.manager.services.persistent;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.CancelationCallBack;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.IDHelper;
import com.rssl.phizicgate.manager.services.objects.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hudyakov
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class PersistentServiceBase<E extends Service> extends AbstractService
{
	protected E delegate;

	protected PersistentServiceBase(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Устанавливает сервис-исполнитель конечного адаптера
	 * @param delegate
	 */
	public void setDelegate(E delegate)
	{
		this.delegate = delegate;
	}

	/**
	 * Сохранение информации о маршрутизации в идентификаторе объекта маршрутизации
	 * @param id - идентификатор объект
	 * @return - оъбект
	 */
	protected String storeRouteInfo(String id) throws GateLogicException, GateException
	{
		return IDHelper.storeRouteInfo(id, getRouteInfo());
	}

	protected String removeRouteInfo(String data)
	{
		return IDHelper.restoreOriginalId(data);
	}

	protected String[] removeRouteInfo(String... data)
	{
		return IDHelper.restoreOriginalId(data);
	}

	protected String getRouteInfo() throws GateLogicException, GateException
	{
		return getFactory().service(GateInfoService.class).getUID(null);
	}

	protected ClientWithoutRouteInfo removeRouteInfo(Client client)
	{
		return new ClientWithoutRouteInfo(client);
	}

	protected ClientWithRouteInfo storeRouteInfo(Client client) throws GateLogicException, GateException
	{
		return new ClientWithRouteInfo(client, getRouteInfo());
	}

	protected AccountWithoutRouteInfo removeRouteInfo(Account account)
	{
		return new AccountWithoutRouteInfo(account);
	}

	protected AccountWithRouteInfo storeRouteInfo(Account account) throws GateLogicException, GateException
	{
		return new AccountWithRouteInfo(account, getRouteInfo());
	}

	protected AccountWithoutRouteInfo[] removeRouteInfo(Account... account)
	{
		AccountWithoutRouteInfo[] accounts = new AccountWithoutRouteInfo[account.length];
		for (int i=0;i<account.length;i++)
		{
			accounts[i] = new AccountWithoutRouteInfo(account[i]);
		}
		return accounts;
	}

	protected CardWithRouteInfo storeRouteInfo(Card card) throws GateLogicException, GateException
	{
		return new CardWithRouteInfo(card, getRouteInfo());
	}

	protected CardWithoutRouteInfo removeRouteInfo(Card card)
	{
		return new CardWithoutRouteInfo(card);
	}

	protected CardWithoutRouteInfo[] removeRouteInfo(Card... card)
	{
		CardWithoutRouteInfo[] cards= new CardWithoutRouteInfo[card.length];
		for (int i=0;i<card.length;i++)
		{
			cards[i] = new CardWithoutRouteInfo(card[i]);
		}
		return cards;
	}

	protected LoanWithoutRouteInfo removeRouteInfo(Loan loan)
	{
		return new LoanWithoutRouteInfo(loan);
	}

	protected LoanWithRouteInfo storeRouteInfo(Loan loan) throws GateLogicException, GateException
	{
		return new LoanWithRouteInfo(loan, getRouteInfo());
	}

	protected LoanWithoutRouteInfo[] removeRouteInfo(Loan... loan)
	{
		LoanWithoutRouteInfo[] loans = new LoanWithoutRouteInfo[loan.length];
		for (int i=0; i<loan.length; i++)
		{
			loans[i] = new LoanWithoutRouteInfo(loan[i]);
		}
		return loans;
	}

	protected List storeRouteInfo(List list) throws GateLogicException, GateException
	{
		List<Card> cardList = new ArrayList<Card>();
		for (int i=0; i< list.size(); i++)
		{
			if (list.get(i) instanceof Card) cardList.set(i, storeRouteInfo((Card)list.get(i)));
		}
		return cardList.size()>0 ? cardList: list;
	}

	protected GroupResult storeRouteInfo(GroupResult groupResult) throws GateLogicException, GateException
	{
		GroupResult res = new GroupResult();
		for (Object key: groupResult.getKeys())
		{
			try
			{
			    Object value = GroupResultHelper.getResult(groupResult, key);
				res.putResult(storeRouteInfo(key), storeRouteInfo(value));
			}
			catch (IKFLException e)
			{
				res.putException(storeRouteInfo(key), e);
			}
		}
		return res;
	}

	protected OfficeWithRouteInfo storeRouteInfo(Office office) throws GateLogicException, GateException
	{
		return new OfficeWithRouteInfo(office, getRouteInfo());
	}

	protected OfficeWithoutRouteInfo removeRouteInfo(Office office)
	{
		return new OfficeWithoutRouteInfo(office);
	}

	protected DepositWithoutRouteInfo removeRouteInfo(Deposit deposit)
	{
		return new DepositWithoutRouteInfo(deposit);
	}

	protected DepositWithRouteInfo storeRouteInfo(Deposit deposit) throws GateLogicException, GateException
	{
		return new DepositWithRouteInfo(deposit, getRouteInfo());
	}

	protected RecipientWithoutRouteInfo removeRouteInfo(Recipient recipient)
	{
		return new RecipientWithoutRouteInfo(recipient);
	}

	protected RecipientWithRouteInfo storeRouteInfo(Recipient recipient) throws GateLogicException, GateException
	{
		return new RecipientWithRouteInfo(recipient, getRouteInfo());
	}

	protected CancelationCallBack storeRouteInfo(CancelationCallBack callBack) throws GateLogicException, GateException
	{
		return new CancelationCallBackWithRouteInfo(callBack, getRouteInfo());
	}

	protected BillingWithoutRouteInfo removeRouteInfo(Billing billing)
	{
		return new BillingWithoutRouteInfo(billing);
	}

	protected BillingWithRouteInfo storeRouteInfo(Billing billing) throws GateLogicException, GateException
	{
		return new BillingWithRouteInfo(billing, getRouteInfo());
	}

	protected AutoPaymentWithoutRouteInfo removeRouteInfo(AutoPayment autoPayment)
	{
		return new AutoPaymentWithoutRouteInfo(autoPayment);
	}

	protected AutoPaymentWithRouteInfo storeRouteInfo(AutoPayment autoPayment) throws GateLogicException, GateException
	{
		return new AutoPaymentWithRouteInfo(autoPayment, getRouteInfo());
	}

	protected LoyaltyProgramWithRouteInfo storeRouteInfo(LoyaltyProgram loyaltyProgram) throws GateLogicException, GateException
	{
		if (loyaltyProgram == null)
			return null;
		return new LoyaltyProgramWithRouteInfo(loyaltyProgram, getRouteInfo());
	}

	protected LoyaltyProgramWithoutRouteInfo removeRouteInfo(LoyaltyProgram loyaltyProgram)
	{
		return new LoyaltyProgramWithoutRouteInfo(loyaltyProgram);
	}

	protected Pair removeRouteInfo(Pair pair)
	{
		Pair pairWithRouteInfo = new Pair();
		pairWithRouteInfo.setFirst(removeRouteInfo(pair.getFirst()));
		pairWithRouteInfo.setSecond(removeRouteInfo(pair.getSecond()));
		return pairWithRouteInfo;
	}

	protected Object removeRouteInfo(Object object)
	{
		if (object instanceof Account)
		{
			return removeRouteInfo((Account) object);
		}
		else if (object instanceof Card)
		{
			return removeRouteInfo((Card) object);
		}
		else if (object instanceof Loan)
		{
			return removeRouteInfo((Loan) object);
		}
		else if (object instanceof Deposit)
		{
			return removeRouteInfo((Deposit) object);
		}
		else if (object instanceof Client)
		{
			return removeRouteInfo((Client) object);
		}
		else if (object instanceof Office)
		{
			return removeRouteInfo((Office) object);
		}
		else if (object instanceof Billing)
		{
			return removeRouteInfo((Billing) object);
		}
		else if (object instanceof Recipient)
		{
			return removeRouteInfo((Recipient) object);
		}
		else if (object instanceof Pair)
		{
			return removeRouteInfo((Pair) object);
		}
		else if (object instanceof String)
		{
			return removeRouteInfo((String) object);
		}
		else if (object instanceof AutoPayment)
		{
			return removeRouteInfo((AutoPayment) object);
		}
		else if (object instanceof LoyaltyProgram)
		{
			return removeRouteInfo((LoyaltyProgram) object);
		}
		else if (object instanceof List)
		{
			List list = new ArrayList();
			for (Object obj : (List) object)
			{
				//noinspection unchecked
				list.add(removeRouteInfo(obj));
			}
			return list;
		}
		throw new IllegalArgumentException("Неизвестный тип " + object);
	}

	protected Object[] removeRouteInfo(Object... objects)
	{
		Object[] objs = new Object[objects.length];
		for (int i=0; i<objects.length; i++)
		{
			objs[i] = removeRouteInfo(objects[i]);
		}
		return objs;
	}

	protected Pair[] removeRouteInfo(Pair... objects)
	{
		for (int i=0; i<objects.length; i++)
		{
			objects[i] = removeRouteInfo(objects[i]);
		}
		return objects;
	}

	protected Object storeRouteInfo(Object object) throws GateLogicException, GateException
	{
		if (object instanceof Account)
		{
			return storeRouteInfo((Account) object);
		}
		else if (object instanceof Card)
		{
			return storeRouteInfo((Card) object);
		}
		else if (object instanceof Loan)
		{
			return storeRouteInfo((Loan) object);
		}
		else if (object instanceof Deposit)
		{
			return storeRouteInfo((Deposit) object);
		}
		else if (object instanceof Client)
		{
			return storeRouteInfo((Client) object);
		}
		else if (object instanceof Office)
		{
			return storeRouteInfo((Office) object);
		}
		else if (object instanceof Billing)
		{
			return storeRouteInfo((Billing) object);
		}
		else if (object instanceof Recipient)
		{
			return storeRouteInfo((Recipient) object);
		}
		else if (object instanceof String)
		{
			return storeRouteInfo((String) object);
		}
		else if (object instanceof AutoPayment)
		{
			return storeRouteInfo((AutoPayment) object);
		}
		else if (object instanceof LoyaltyProgram)
		{
			return storeRouteInfo((LoyaltyProgram) object);
		}
		else if (object instanceof List)
		{
			List list = new ArrayList();
			for (Object obj : (List) object)
			{
				//noinspection unchecked
				list.add(storeRouteInfo(obj));
			}
			return list;
		}
		return object;
	}

}
