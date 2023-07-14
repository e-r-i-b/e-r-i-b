package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizicgate.esberibgate.bankroll.BankrollRequestHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.AccountResponseSerializer;
import com.rssl.phizicgate.esberibgate.messaging.IMAResponseSerializer;
import com.rssl.phizicgate.esberibgate.ws.TransportProvider;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import org.apache.commons.collections.MapUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author basharin
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CheckNeedWaitConfirmUDBOClaimCondition implements StateObjectCondition
{
	private static final PersonService personService = new PersonService();
	private static final BankrollRequestHelper BANKROLL_REQUEST_HELPER = new BankrollRequestHelper(GateSingleton.getFactory());
	private static final AccountResponseSerializer accountResponseSerializer = new AccountResponseSerializer();
	private static final IMAResponseSerializer imaResponseSerializer = new IMAResponseSerializer();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);
		if (config.isAllConnectionUDBONeedWaitConfirmState())
			return true;

		if (stateObject instanceof RemoteConnectionUDBOClaim)
		{
			RemoteConnectionUDBOClaim document = (RemoteConnectionUDBOClaim) stateObject;
			Calendar birthday = document.getPersonBirthday();
			birthday.add(Calendar.YEAR, config.getAgeClientForNeedWaitConfirmState());
		    if (birthday.before(Calendar.getInstance()))
			    return true;

			try
			{
				ActivePerson person = personService.findByLoginId(document.getLoginId());
				if (person != null)
				{
					if (isOverLimit(person, document, config))
						return true;
				}
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}
		return false;
	}

	private boolean isOverLimit (ActivePerson person, RemoteConnectionUDBOClaim document, RemoteConnectionUDBOConfig config) throws BusinessException, GateLogicException, GateException, BusinessLogicException
	{
		Client client = person.asClient();
		ProductContainer productContainer = BANKROLL_REQUEST_HELPER.createBankAcctInqRq(client, client.getDocuments().get(0),
				BANKROLL_REQUEST_HELPER.getRbTbBrch(client), person.getLogin().getLastLogonCardNumber(), true, BankProductType.Deposit, BankProductType.IMA);

		if (MapUtils.isNotEmpty(productContainer.getErrors()))
		{
			//неактивности внешних систем
			//todo Разобраться что делать если нет ответа. Исполнитель: Богданов Игорь.
			return true;
		}

		boolean requestTimeOut = false;

		IFXRs_Type ifxRs = null;
		try
		{
			ifxRs = TransportProvider.doRequest(productContainer.getIfxRq_type());
		}
		catch (GateTimeOutException ignored)
		{
			requestTimeOut = true;
		}

		//Если случился таймаут.
		if (requestTimeOut)
		{
			//todo Разобраться что делать если нет ответа. Исполнитель: Богданов Игорь.
			return true;
		}

		Long internalOwnerId = client.getInternalOwnerId();

		//Список счетов клиента.
		List<Pair<Account, AdditionalProductData>> accounts = accountResponseSerializer.fillAccounts(ifxRs, internalOwnerId);

		//Список металлических счетов клиента.
		List<IMAccount> imAccounts = imaResponseSerializer.extractIMAccountsFormResponse(ifxRs, internalOwnerId);

		Money allMoney = new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
		for (Pair<Account, AdditionalProductData> account : accounts)
		{
			allMoney = addMoney(document, allMoney, account.getFirst().getBalance());
		}

		for (IMAccount imAccount: imAccounts)
		{
			allMoney = addMoney(document, allMoney,  imAccount.getBalance());
		}

		return allMoney.getAsCents() > 100 * config.getMoneyClientForNeedWaitConfirmState();
	}

	private Money addMoney(RemoteConnectionUDBOClaim document, Money allMoney, Money money) throws BusinessException, BusinessLogicException
	{
		Money addMoney = money;
		if (addMoney == null || addMoney.getCurrency() == null)
			return allMoney;
		if (!allMoney.getCurrency().getCode().equals(addMoney.getCurrency().getCode()))
		{
			addMoney = DocumentHelper.moneyConvert(addMoney, MoneyUtil.getNationalCurrency(), document);
		}
		return allMoney.add(addMoney);
	}
}
