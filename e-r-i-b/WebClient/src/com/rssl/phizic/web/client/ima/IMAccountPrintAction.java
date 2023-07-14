package com.rssl.phizic.web.client.ima;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountTransaction;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.ima.IMAbstractFilter;
import com.rssl.phizic.web.common.client.ima.IMAccountGeneralAction;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ author Balovtsev
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 * 
 * @see IMAccountGeneralAction
 */
public class IMAccountPrintAction extends IMAccountGeneralAction
{
	protected Map<String, String> getKeyMethodMap()
	{   
		return new HashMap<String, String>();
	}
	
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		super.updateFormData(operation, frm);
		IMAccountPrintForm form = (IMAccountPrintForm) frm;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	    ActivePerson user = personData.getPerson();
		form.setUser(user);
		GetIMAccountAbstractOperation abstractOperation = (GetIMAccountAbstractOperation) operation;

		IMAccountLink link = abstractOperation.getEntity();
		IMAccountAbstract imAbstract = getAbstract(frm, abstractOperation, null).get(link);

		String mode = form.getMode();
		if (mode.equals("extended"))
		{
			Money openingBalanceInRub = imAbstract.getOpeningBalanceInRub();
			Money closingBalanceInRub = imAbstract.getClosingBalanceInRub();

			BigDecimal rate = imAbstract.getRate();

			Money incomingBalance = convertToPreferedCurrency(openingBalanceInRub, rate, link.getCurrency());
			Money outboxBalance = convertToPreferedCurrency(closingBalanceInRub, rate, link.getCurrency());

			Money totalCreditAmount = null;
			Money totalDebitAmount = null;
			Money totalCreditAmountInPhys = null;
			Money totalDebitAmountInPhys = null;

			for (TransactionBase transaction : imAbstract.getTransactions())
			{
				IMAccountTransaction imATransaction = (IMAccountTransaction) transaction;

				totalCreditAmount = increaseMoney(totalCreditAmount, imATransaction.getCreditSum());
				totalDebitAmount = increaseMoney(totalDebitAmount, imATransaction.getDebitSum());

				totalCreditAmountInPhys = increaseMoney(totalCreditAmountInPhys, imATransaction.getCreditSumInPhizicalForm());
				totalDebitAmountInPhys = increaseMoney(totalDebitAmountInPhys, imATransaction.getDebitSumInPhizicalForm());
			}
			form.setTotalDebitAmount(totalDebitAmount);
			form.setTotalCreditAmount(totalCreditAmount);
			form.setTotalDebitAmountInPhys(totalDebitAmountInPhys);
			form.setTotalCreditAmountInPhys(totalCreditAmountInPhys);
			form.setOutboxBalance(outboxBalance);
			form.setIncomingBalance(incomingBalance);
			form.setOffice(link.getImAccount().getOffice());
		}
		
		form.setImAccountLink(link);
		form.setImAccountAbstract(imAbstract);
	}
	/**
	 * Конвертирует деньги из рублей в драгоценный металл
	 * @param money конвертируемые деньги
	 * @param divisor учётная цена обмена
	 * @param currency валюта в которую производится конвертация
	 * @return результат конвертации
	 */
	private Money convertToPreferedCurrency(Money money, BigDecimal divisor, Currency currency)
	{
		return new Money(money.getDecimal().divide(divisor, 2),currency);
	}

	private Money increaseMoney(Money amountOfIncrease, Money prolonged)
	{
		if(prolonged == null)
		{
			return amountOfIncrease;
		}
		return amountOfIncrease == null ? prolonged : amountOfIncrease.add(prolonged);
	}

	protected IMAbstractFilter getFilter(Map<String, Object> source)
	{
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(source), IMAccountPrintForm.PRINT_FORM);

		Map<String, Object> result = null;
		if(processor.process())
		{
			result = processor.getResult();
		}
		
		return new IMAbstractFilter( result );
	}
}