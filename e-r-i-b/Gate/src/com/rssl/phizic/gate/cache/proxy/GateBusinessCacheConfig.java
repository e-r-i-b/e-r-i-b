package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.cache.proxy.composers.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.depo.DepoDebtInfo;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.ClassHelper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class GateBusinessCacheConfig
{
	//������ ���� ��� ��� ������� ����� ��������� ����, ���� �� �������� ������ ���
	private static final List<Method> EMPTY_METHOD_LIST = new ArrayList<Method>();
	//��� ����� ��������� - ��������, ���� �� ��������� ������ ��� ������ ���������
	private static final Map<Class<? extends CacheKeyComposer>, CacheKeyComposer> composers;
	//������ ��������� �� ������� ���� ������� ���. �.�. ������ account, ���� ������� accountInfo �.�.�.
	private static final Map<Class, Class> linkedItems;
	//������������ ������ ��� ���� �������� ��� �������
	private static final Map<Class, CacheKeyComposer> clearKeyCalculator;
	//��� ������� �� ������������ ���������
	private Map<Object, List<Method>> methodsByReturnType = new HashMap<Object, List<Method>>();

	private static final ClientProductsCacheComposer clientProductsCacheComposer = new ClientProductsCacheComposer();

	static
	{
		AccountCacheKeyComposer accountCacheKeyComposer = new AccountCacheKeyComposer();
		CardCacheKeyComposer cardCacheKeyComposer = new CardCacheKeyComposer();
		LoanCacheKeyComposer loanCacheKeyComposer = new LoanCacheKeyComposer();
		CurrencySimpleRateCacheKeyComposer currencyeRateCacheKeyComposer = new CurrencySimpleRateCacheKeyComposer();
		ClientCacheKeyComposer clientCacheKeyComposer = new ClientCacheKeyComposer();
		IMAccountCacheKeyComposer imAccountCacheKeyComposer = new IMAccountCacheKeyComposer();
		DepoAccountCacheKeyComposer depoAccountCacheKeyComposer = new DepoAccountCacheKeyComposer();
		AutoPaymentCacheKeyComposer autoPaymentCacheKeyComposer = new AutoPaymentCacheKeyComposer();
		LongOfferCacheKeyComposer longOfferCacheKeyComposer = new LongOfferCacheKeyComposer();
		ListCardCacheKeyComposer listCardCacheKeyComposer = new ListCardCacheKeyComposer();
		AutoSubscriptionCacheKeyComposer autoSubCacheKeyComposer = new AutoSubscriptionCacheKeyComposer();
		LoyaltyProgramCacheKeyComposer loyaltyProgramCacheKeyComposer = new LoyaltyProgramCacheKeyComposer();
		InsuranceAppIdCacheKeyComposer insuranceAppIdCacheKeyComposer = new InsuranceAppIdCacheKeyComposer();
		SecurityAccountIdCacheKeyComposer securityAccountIdCacheKeyComposer = new SecurityAccountIdCacheKeyComposer();
		OrdersKeyComposer ordersKeyComposer = new OrdersKeyComposer();
		OrderListKeyComposer orderListKeyComposer = new OrderListKeyComposer();

		composers = new HashMap<Class<? extends CacheKeyComposer>, CacheKeyComposer>(48);
		composers.put(ClientProductsCacheComposer.class, clientProductsCacheComposer);
		composers.put(StringCacheKeyComposer.class, new StringCacheKeyComposer());
		composers.put(AccountCacheKeyComposer.class, accountCacheKeyComposer);
		composers.put(CardCacheKeyComposer.class, cardCacheKeyComposer);
		composers.put(DepositCacheKeyComposer.class, new DepositCacheKeyComposer());
		composers.put(LoanCacheKeyComposer.class, loanCacheKeyComposer);
		composers.put(FullAbstractCacheKeyComposer.class, new FullAbstractCacheKeyComposer());
		composers.put(ClientCacheKeyComposer.class, new ClientCacheKeyComposer());
		composers.put(AccountIDCacheKeyComposer.class, new AccountIDCacheKeyComposer());
		composers.put(NumberAbstractCacheKeyComposer.class, new NumberAbstractCacheKeyComposer());
		composers.put(AccountNumberCacheKeyComposer.class, new AccountNumberCacheKeyComposer());
		composers.put(CardIDCacheKeyComposer.class, new CardIDCacheKeyComposer());
		composers.put(CardOverdraftCacheKeyComposer.class, new CardOverdraftCacheKeyComposer());
		composers.put(ObjectToEntityCachKeyComposer.class, new ObjectToEntityCachKeyComposer());
		composers.put(PairCacheKeyComposer.class, new PairCacheKeyComposer());
		composers.put(CurrencySimpleRateCacheKeyComposer.class, new CurrencySimpleRateCacheKeyComposer());
		composers.put(ClientByTemplateCacheKeyComposer.class, new ClientByTemplateCacheKeyComposer());
		composers.put(CurrencyIsoCodeCacheKeyComposer.class, new CurrencyIsoCodeCacheKeyComposer());
		composers.put(ClientIDCacheKeyComposer.class, new ClientIDCacheKeyComposer());
		composers.put(CurrencyNumberCacheKeyComposer.class, new CurrencyNumberCacheKeyComposer());
		composers.put(CurrencyIdCacheKeyComposer.class, new CurrencyIdCacheKeyComposer());
		composers.put(NationalCurrencyCacheKeyComposer.class, new NationalCurrencyCacheKeyComposer());
		composers.put(NullCacheKeyComposer.class, new NullCacheKeyComposer());
		composers.put(OfficeCacheKeyComposer.class, new OfficeCacheKeyComposer());
		composers.put(BilingCacheKeyComposer.class, new BilingCacheKeyComposer());
		composers.put(LongCacheKeyComposer.class, new LongCacheKeyComposer());
		composers.put(IMAccountCacheKeyComposer.class, new IMAccountCacheKeyComposer());
		composers.put(IMAccountIDCacheKeyComposer.class, new IMAccountIDCacheKeyComposer());
		composers.put(IMAccountByNumberCacheKeyComposer.class, new IMAccountByNumberCacheKeyComposer());
		composers.put(LoanIdCacheKeyComposer.class, new LoanIdCacheKeyComposer());
		composers.put(LoanScheduleCacheKeyComposer.class, new LoanScheduleCacheKeyComposer());
		composers.put(LoanMoneyCacheKeyComposer.class, new LoanMoneyCacheKeyComposer());
		composers.put(DepoAccountCacheKeyComposer.class, depoAccountCacheKeyComposer);
		composers.put(DepoAccountIdCacheKeyComposer.class, new DepoAccountIdCacheKeyComposer());
		composers.put(DepoDebtItemCacheKeyComposer.class, new DepoDebtItemCacheKeyComposer());		
		composers.put(DebtServiceCacheKeyComposer.class, new DebtServiceCacheKeyComposer());
		composers.put(CardByNumberCacheKeyComposer.class, new CardByNumberCacheKeyComposer());
		composers.put(ListCardCacheKeyComposer.class, listCardCacheKeyComposer);
		composers.put(AutoPaymentExternalIdCacheKeyComposer.class, new AutoPaymentExternalIdCacheKeyComposer());
		composers.put(AutoPaymentCacheKeyComposer.class, new AutoPaymentCacheKeyComposer());
		composers.put(CheckStopListCacheKeyComposer.class, new CheckStopListCacheKeyComposer());
		composers.put(LongOfferCacheKeyComposer.class, longOfferCacheKeyComposer);
		composers.put(LongOfferExternalIdCacheKeyComposer.class, new LongOfferExternalIdCacheKeyComposer());
		composers.put(LongOfferByClientCacheKeyComposer.class, new LongOfferByClientCacheKeyComposer());
		composers.put(AllowedAutoPaymentTypesComposer.class, new AllowedAutoPaymentTypesComposer());
		// ��������� ��� AutoSubscriptionService
		composers.put(AutoSubPaymentCacheKeyComposer.class, new AutoSubPaymentCacheKeyComposer());
		composers.put(AutoSubscriptionCacheKeyComposer.class, autoSubCacheKeyComposer);
		composers.put(ListAutoSubscriptionComposer.class, new ListAutoSubscriptionComposer());
		// ��������� ��� LoyaltyProgramService
		composers.put(LoyaltyProgramCardCacheKeyComposer.class, new LoyaltyProgramCardCacheKeyComposer());
		composers.put(LoyaltyProgramIdCacheKeyComposer.class, new LoyaltyProgramIdCacheKeyComposer());
		composers.put(LoyaltyProgramCacheKeyComposer.class, loyaltyProgramCacheKeyComposer);
		//�������� ��� SecurityAccountService
		composers.put(InsuranceAppIdCacheKeyComposer.class, insuranceAppIdCacheKeyComposer);
		//�������� ��� InsuranceService
		composers.put(SecurityAccountIdCacheKeyComposer.class, securityAccountIdCacheKeyComposer);
		composers.put(OrdersKeyComposer.class, ordersKeyComposer);
		composers.put(OrderListKeyComposer.class, orderListKeyComposer);
		composers.put(OrdersInternalKeyComposer.class, new OrdersInternalKeyComposer());
		composers.put(OrderListKeyForMainPageComposer.class, new OrderListKeyForMainPageComposer());
		composers.put(AutoSubscriptionExternalIdCacheKeyComposer.class, new AutoSubscriptionExternalIdCacheKeyComposer());
		composers.put(LoanPrivateCacheKeyComposer.class, new LoanPrivateCacheKeyComposer());

		//��� ���������� �� ��������, ��� ������ �� ������� ���� ������� ������ ����� ����� � CacheService
		//� ��������� ������ ��������� ���������� ����, ��������� Serializable, Comparable �.�.�. ��� ���� �� ������
		linkedItems = new HashMap<Class, Class>(15);
		clearKeyCalculator = new HashMap<Class, CacheKeyComposer>(10);

		//�.�. ��� ������� ���� �� ����� ������ ��� � ��� �� AccountInfo
		linkedItems.put(AccountAbstract.class, Account.class);
		linkedItems.put(Account.class, Client.class );

		clearKeyCalculator.put(Account.class,accountCacheKeyComposer);

		linkedItems.put(CardAbstract.class, Card.class);
		linkedItems.put(Card.class,Client.class);
		linkedItems.put(AutoSubscription.class,Card.class);

		clearKeyCalculator.put(Card.class,cardCacheKeyComposer);
		clearKeyCalculator.put(CurrencyRate.class,currencyeRateCacheKeyComposer);
		clearKeyCalculator.put(Client.class,clientCacheKeyComposer);
		clearKeyCalculator.put(ShopOrder.class,ordersKeyComposer);

	    clearKeyCalculator.put(IMAccount.class, imAccountCacheKeyComposer);
		linkedItems.put(IMAccountAbstract.class, IMAccount.class);

		clearKeyCalculator.put(Loan.class,loanCacheKeyComposer);

		linkedItems.put(ScheduleAbstract.class, Loan.class);
		linkedItems.put(ScheduleItem.class, Loan.class);

		clearKeyCalculator.put(DepoAccount.class, depoAccountCacheKeyComposer);

		linkedItems.put(DepoDebtInfo.class, DepoAccount.class);
		linkedItems.put(DepoAccountPosition.class, DepoAccount.class);
		linkedItems.put(Client.class, DepoAccount.class);

		clearKeyCalculator.put(AutoPayment.class, autoPaymentCacheKeyComposer);
		clearKeyCalculator.put(ListCardCacheKeyComposer.class, listCardCacheKeyComposer);

		linkedItems.put(com.rssl.phizic.gate.longoffer.autopayment.ScheduleItem.class, AutoPayment.class);

		clearKeyCalculator.put(LongOffer.class, longOfferCacheKeyComposer);
		
		linkedItems.put(com.rssl.phizic.gate.longoffer.ScheduleItem.class, LongOffer.class);
		linkedItems.put(AbstractTransfer.class, LongOffer.class);

		clearKeyCalculator.put(AutoSubscription.class, autoSubCacheKeyComposer);
		linkedItems.put(CardPaymentSystemPaymentLongOffer.class, AutoSubscription.class);
		linkedItems.put(com.rssl.phizic.gate.autopayments.ScheduleItem.class, AutoSubscription.class);

		clearKeyCalculator.put(LoyaltyProgram.class, loyaltyProgramCacheKeyComposer);
		linkedItems.put(LoyaltyProgramOperation.class, LoyaltyProgram.class);
		linkedItems.put(LoyaltyOffer.class, LoyaltyProgram.class);

		clearKeyCalculator.put(InsuranceApp.class, insuranceAppIdCacheKeyComposer);
		clearKeyCalculator.put(SecurityAccount.class, securityAccountIdCacheKeyComposer);
	}
	
	public GateBusinessCacheConfig()
	{

	}

	/**
	 * �������� ������ ��������� �������, �� ������� ����� ���� �������� ���
	 * @return ������ ��������� ������� ��� null
	 */
	public static Set<Class> getLinkedItems()
	{
		return linkedItems.keySet();
	}

	/**
	 * �������� �������� �������� �� ���������
	 * @param linked ��������� �����
	 * @return ����� ��� null
	 */
	public static Class getCleanableClassByLinkedItems(Class linked)
	{
		return linkedItems.get(linked);
	}

	/**
	 * ����� ������� �� ������� ���������� ������� ���.
	 * @return
	 */
	public static Collection<Class> getCleanableClasses()
	{
		return clearKeyCalculator.keySet();
	}

	/**
	 * �������� ��� ���������� ����� ��� �������� ������ �� ������� ��� �������.
	 * @param itemClass
	 * @return
	 */
	public static CacheKeyComposer getCleanableComposer(Class itemClass)
	{
		return clearKeyCalculator.get(itemClass);
	}

	/**
	 * �������� ��� �������� ���� �� ��������� �������
	 * @return
	 */
	public static CacheKeyComposer getCleanableClientProductComposer()
	{
		return clientProductsCacheComposer;
	}

	/**
	 * �������� ������� ��������� �� ��� ������
	 * @param key
	 * @return
	 */
	public static CacheKeyComposer getComposer(Class<? extends CacheKeyComposer> key)
	{
		return composers.get(key);
	}

	/**
	 * ���������������� �����
	 * @param method
	 */
	public void addMethod(Method method)
	{
		addReturnType(method);
	}

	/**
	 * �������� ����� � ����� �� ������������ ���������.
	 * ��� ��� ������
	 * ������������ ������ ��������� � ��������� �������� ������ ������ ������������
	 * @param returnType ������ �������, ��� ������ ������, ���� ����� ���.
	 * @return
	 */
	public List<Method> getMethodsByReturnType(Class returnType)
	{
		List<Method> methods = methodsByReturnType.get(returnType);
		return (methods!=null)?methods:EMPTY_METHOD_LIST;
	}

	/**
	 * �������� ���������� � ����������� �������� ������, ��� ��� ������
	 * ������������ ������ ��������� � ��������� �������� ������ ������ ������������
	 * @param method
	 */
	private void addReturnType(Method method)
	{
		//��� ����� ������ ��������� ��������� �� ����, �.�. �� �������� ������.
		if(ClassHelper.isGetClientProduct(method))
			return;

		Class returnTypePar = ClassHelper.getReturnClassIgnoreCollections(method);

		List<Method> list = methodsByReturnType.get(returnTypePar);
		//���� ����� ��������� ������, �� �� ��������� �������.
		// �.�. ���� ������ �������� ���������� - � �� ������� ��� ������ � �������.

		if(list==null)
		{
			list = new ArrayList<Method>(1);
		}
		list.add(method);
		methodsByReturnType.put(returnTypePar, list);
	}

	/**
	 * �������� ��� ���� �� ������ �����
	 * @param interfaceMethod ����� �����
	 * @return ������
	 */
	public static String getCacheNameByMethod(Method interfaceMethod)
	{
		return Integer.toHexString(interfaceMethod.toString().hashCode());
	}
}
