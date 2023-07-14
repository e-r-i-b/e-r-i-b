package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoansService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Валидатор суммы погашения
 * Created with IntelliJ IDEA.
 * User: petuhov
 * Date: 19.06.15
 * Time: 14:19 
 */
public class EarlyLoanRepaymentAmountValidator extends MultiFieldsValidatorBase
{
	private static BigDecimal HUNDRED = BigDecimal.valueOf(100);

	/**
	 * Получить рекомендованный платеж по кредиту
	 * @param loan кредит
	 * @return рекомендовнный платеж
	 */
	private BigDecimal getMinimalAmount(Loan loan)
	{
		return loan.getRecPayment().getDecimal().divide(HUNDRED).multiply(BigDecimal.valueOf(ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinAmount()))
				.round(new MathContext(10, RoundingMode.CEILING));
	}

	/**
	 * Получить сумму полного погашения
	 * @param loan кредит
	 * @return сумма полного погашения
	 */
	private BigDecimal getFullPayment(Loan loan) throws TemporalDocumentException
	{
		Loan detailedLoan = GateSingleton.getFactory().service(LoansService.class).getLoan(loan.getId()).getResult(loan.getId());
		if(detailedLoan==null)
			throw new TemporalDocumentException("Не удалось получить детальные данные по кредиту");
		return detailedLoan.getFullRepaymentAmount().getDecimal();
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		BigDecimal amount = (BigDecimal) values.get("amount");
		if(amount==null)
		{
			// Предположительно заявка на полное погашение, поле amount в таком случае не заполняется, но, очевидно, сумма полного погашения будет лежать в пределах от минимального допустимого до самого себя.
			// если все же частичное, то тогда сработает проверка на обязательность поля, так что можно все равно вернуть true
			return true;
		}
		Long loanLinkId = (Long) values.get("loanLinkId");
		try
		{
			Loan loan = PersonContext.getPersonDataProvider().getPersonData().getLoan(loanLinkId).getLoan();
			if(amount.compareTo(getMinimalAmount(loan))<0)
			{
				return false;
			}
			if(amount.compareTo(getFullPayment(loan))>0)
			{
				return false;
			}
			return true;
		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
