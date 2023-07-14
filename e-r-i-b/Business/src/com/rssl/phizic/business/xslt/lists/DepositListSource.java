package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * @author Kidyaev
 * @ created 12.02.2007
 * @ $Author$
 * @ $Revision$
 */
//TODO привести к формату справочникка (EntityList)
public class DepositListSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException, BusinessLogicException
	{
		return new DOMSource(getDocument(params));
    }

	/**
	 *
	 * @param params
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		List<? extends DepositLink> deposits        = getClientDeposits();
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document        document        = documentBuilder.newDocument();
		Element         root            = document.createElement("deposits");

		document.appendChild(root);

		for ( DepositLink depositLink : deposits )
		{
			if(depositLink.getDeposit().getCloseDate() == null )
				createDepositElement(root, depositLink);
		}
		return document;
	}

	private void createDepositElement(Element root, DepositLink depositLink) throws BusinessException, BusinessLogicException
	{
		Deposit deposit = depositLink.getDeposit();
		DepositInfo depositInfo = depositLink.getDepositInfo();

		Element depositElement = XmlHelper.appendSimpleElement(root, "deposit");
		depositElement.setAttribute("id", String.valueOf(depositLink.getId()));

		depositElement.setAttribute("external_id", deposit.getId());
		depositElement.setAttribute("type", deposit.getDescription());

		Calendar openingDate = deposit.getOpenDate();
		depositElement.setAttribute("opening-date", DateHelper.toXMLDateFormat(openingDate.getTime()));

		Long period  = deposit.getDuration();
		if (period != null)
		{
		   Date closingDate = DateHelper.add(openingDate.getTime(), new DateSpan(0,0, period.intValue()));
		   depositElement.setAttribute("finishing-date", DateHelper.toXMLDateFormat(closingDate));
		}
		else
		   depositElement.setAttribute("finishing-date", "");

//TODO ---------------------------->убрать отсюда
		//todo заплатка, если у депозита не установлен счет, дл€ зачисление средств при закрытии, то зачисл€ем на любой счет довостреб.
		Account finalAccount = null;
		if (!depositInfo.getFinalAccounts().keySet().isEmpty())
			finalAccount = depositInfo.getFinalAccounts().keySet().iterator().next();

		String destinationAccount = "";
		if (finalAccount != null)
		{
			destinationAccount = finalAccount.getNumber();
		}
		Account depositAccount = depositInfo.getAccount();
		if (depositAccount == null){
			throw new BusinessException("Ќе найден депозитный счет (depositId="+ deposit.getId()+")");
		}


		Money depositAmount = deposit.getAmount();
		if((destinationAccount==null || destinationAccount.length()==0)&&(depositAmount != null))
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

				List<AccountLink> accounts = personData.getAccounts();
				for (AccountLink account : accounts)
				{
					String number = account.getNumber();
					Account accountInt = account.getAccount();
					if(MockHelper.isMockObject(accountInt))
						throw new BusinessException("ќшибка при получении счета є" +number);
					
					Currency accountCurrency = accountInt.getCurrency();
					if( (number.startsWith("42301") || number.startsWith("42601")) && (accountCurrency.compare(depositAmount.getCurrency())) )
					{
						destinationAccount = number;
						break;
					}
				}
		}
//TODO <----------------------------убрать отсюда

		depositElement.setAttribute("contract-number", depositInfo.getAgreementNumber());
		depositElement.setAttribute("final-account", finalAccount == null? "" :finalAccount.getNumber());
		depositElement.setAttribute("deposit-account", depositAccount.getNumber());
		depositElement.setAttribute("destination-account", destinationAccount);//TODO убрать!!!
		depositElement.setAttribute("min-amount", (depositInfo.getMinReplenishmentAmount() != null ? depositInfo.getMinReplenishmentAmount().getDecimal().toPlainString() : ""));
		depositElement.setAttribute("min-balance", (depositInfo.getMinBalance() != null ? depositInfo.getMinBalance().getDecimal().toPlainString() : ""));
		depositElement.setAttribute("currency", (deposit.getAmount() != null ? deposit.getAmount().getCurrency().getCode() : ""));
		depositElement.setAttribute("amount", (deposit.getAmount() != null ? deposit.getAmount().getDecimal().toString() : ""));
		depositElement.setAttribute("is-anticipatory-removal", Boolean.toString(depositInfo.isAnticipatoryRemoval()));
		depositElement.setAttribute("is-open", Boolean.toString(DepositState.open.equals(deposit.getState())));
		depositElement.setAttribute("is-additional-fee", Boolean.toString(depositInfo.isAdditionalFee()));
	}

	private List<? extends DepositLink> getClientDeposits() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData.getDeposits();
	}
}
