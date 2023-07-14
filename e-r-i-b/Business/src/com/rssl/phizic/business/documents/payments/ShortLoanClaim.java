package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.ikfl.crediting.ClaimNumberGenerator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;

/**
 * @author Balovtsev
 * @since 14.01.14
 */
public class ShortLoanClaim extends GateExecutableDocument implements LoanClaim
{
    protected static final String ATTRIBUTE_PERIOD             = "loanPeriod";
    protected static final String ATTRIBUTE_LOAN_AMOUNT        = "loanAmount";
    protected static final String ATTRIBUTE_LOAN_OFFER_ID      = "loanOfferId";
    protected static final String ATTRIBUTE_LOAN_CURRENCY      = "loanCurrency";
    protected static final String ATTRIBUTE_ONLY_SHORT_CLAIM   = "onlyShortClaim";
	protected static final String ATTRIBUTE_PROCESSING_ENABLED = "processingEnabled";
	protected static final String ATTRIBUTE_LOAN_RATE          = "loanRate";

	protected static final String ATTRIBUTE_CONDITION_ID      = "condId";
	protected static final String ATTRIBUTE_JSON_CONDITION      = "jsonCondition";
	protected static final String ATTRIBUTE_JSON_CURR_CONDITION = "jsonCurrCondition";
    protected static final String ATTRIBUTE_CONDITION_CURR_ID = "condCurrId";
    protected static final String ATTRIBUTE_COND_MIN_PERCENT_RATE   = "currMinPercentRate";
    protected static final String ATTRIBUTE_COND_MAX_PERCENT_RATE   = "currMaxPercentRate";
    protected static final String ATTRIBUTE_PRODUCT_NAME            = "productName";
    protected static final String ATTRIBUTE_PRODUCT_TYPE_NAME       = "productTypeName";
    protected static final String ATTRIBUTE_TB                      = "tb";
    protected static final String ATTRIBUTE_SUR_NAME                = "surName";
    protected static final String ATTRIBUTE_FIRST_NAME              = "firstName";
    protected static final String ATTRIBUTE_PATR_NAME               = "patrName";
    protected static final String ATTRIBUTE_END_DATE                = "endDate";
	protected static final String ATTRIBUTE_PHONE_NUMBER            = "phoneNumber";

	private static final String ATTRIBUTE_CLAIM_NUMBER = "claimNumber";

	public TypeOfPayment getTypeOfPayment()
    {
        return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
    }

    public Class<? extends GateDocument> getType()
    {
        return ShortLoanClaim.class;
    }



	public void setCurrMinPercentRate(final BigDecimal minPercentRate)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_COND_MIN_PERCENT_RATE, minPercentRate);
	}


	public void setCurrMaxPercentRate(final BigDecimal maxPercentRate)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_COND_MAX_PERCENT_RATE, maxPercentRate);
	}

	public void setProductName(final String productName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_NAME, productName);
	}

	public void setProductTypeName(final String productTypeName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_TYPE_NAME, productTypeName);
	}

	public void setLoanAmount(final BigDecimal loanAmount)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_LOAN_AMOUNT, loanAmount);
	}

	public void setLoanCurrency(final String loanCurrency)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_LOAN_CURRENCY, loanCurrency);
	}

	public void setPeriod(final Long duration)
	{
		setNullSaveAttributeLongValue(ATTRIBUTE_PERIOD, duration);
	}

	public void setLoanRate(final Double percentRate)
	{
		setNullSaveAttributeDecimalValue(ATTRIBUTE_LOAN_RATE, percentRate == null ? null : new BigDecimal(percentRate));
	}

	/**
	 * @param jsonCondition Кредитное условие в формате json
	 */
	public void setJsonCondition(final String jsonCondition)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_JSON_CONDITION, jsonCondition);
	}
	/**
	 * @return Кредитное условие в формате json
	 */
	public String getJsonCondition()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_JSON_CONDITION);
	}

	/**
	 * @param jsonCondition По валютное кредитное условие в формате json
	 */
	public void setJsonConditionCurrency(final String jsonCondition)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_JSON_CURR_CONDITION, jsonCondition);
	}
	/**
	 * @return По валютное кредитное условие в формате json
	 */
	public String getJsonConditionCurrency()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_JSON_CURR_CONDITION);
	}

	public LoanRate getLoanRate()
	{
		try
		{
			if (StringHelper.isNotEmpty(getLoanOfferId()))
			{
				BigDecimal loanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_RATE));
				return new LoanRate(loanRate);
			}

			BigDecimal minLoanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_COND_MIN_PERCENT_RATE));
			BigDecimal maxLoanRate = NumericUtil.parseBigDecimal(getNullSaveAttributeStringValue(ATTRIBUTE_COND_MAX_PERCENT_RATE));

			return new LoanRate(minLoanRate, maxLoanRate);
		}
		catch (ParseException e)
		{
			throw new InternalErrorException("Не удалось распарсить процентную ставку по кредиту");
		}
	}

    public String getLoanOfferId()
    {
	    return getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_OFFER_ID);
    }

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		setClaimNumber();
	}

	private void setClaimNumber() throws DocumentException
	{
		ClaimNumberGenerator generator = new ClaimNumberGenerator();
		try
		{
			setOperationUID(generator.execute());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException
	{
		ExtendedLoanClaim claim = (ExtendedLoanClaim) super.createCopy(instanceClass);
		setClaimNumber();
		return claim;
	}

	public void setClaimNumber(String claimNumber)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER, claimNumber);
	}

	public String getClaimNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_CLAIM_NUMBER);
	}

	public Long getConditionId()
    {
	    return getNullSaveAttributeLongValue(ATTRIBUTE_CONDITION_ID);
    }

    public Long getConditionCurrencyId()
    {
        return getNullSaveAttributeLongValue(ATTRIBUTE_CONDITION_CURR_ID);
    }

	public Money getLoanAmount()
	{
		String amountAsString = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_AMOUNT);
		String currencyCode = getNullSaveAttributeStringValue(ATTRIBUTE_LOAN_CURRENCY);
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			Currency currency = currencyService.findByAlphabeticCode(currencyCode);
			return new Money(NumericUtil.parseBigDecimal(amountAsString), currency);
		}
		catch (ParseException e)
		{
			throw new InternalErrorException("Не удалось распарсить сумму кредита: " + amountAsString, e);
		}
		catch (GateException e)
		{
			throw new InternalErrorException(e);
		}
	}

	/**
	 * @return Разрешена ли обработка персональных данных.
	 */
	public Boolean getProcessingEnabled()
	{
		return (Boolean)getAttribute(ATTRIBUTE_PROCESSING_ENABLED).getValue();
	}

	/**
	 * @return Наминование кредита.
	 */
	public String getProductName()
	{
		String productName = getNullSaveAttributeStringValue(ATTRIBUTE_PRODUCT_NAME);
		return productName == null ? "": productName;
	}

    public void setOnlyShortClaim(boolean onlyShortClaim)
    {
        setNullSaveAttributeBooleanValue(ATTRIBUTE_ONLY_SHORT_CLAIM, onlyShortClaim);
    }
	/**
	 * @return Срок кредита.
	 */
	public Long getLoanPeriod()
	{
		return getNullSaveAttributeLongValue(ATTRIBUTE_PERIOD);
	}

	public void setTb(final String tb)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_TB, tb);
	}

	public String getTb()
	{
		return (String)getNullSaveAttributeStringValue(ATTRIBUTE_TB);
	}

	public Money getExactAmount()
	{
		return null;
	}

	public void setSurName(final String surName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_SUR_NAME, surName);
	}

	public String getSurName()
	{
		return (String)getNullSaveAttributeStringValue(ATTRIBUTE_SUR_NAME);
	}

	public void setFirstName(final String firstName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_FIRST_NAME, firstName);
	}

	public String getFirstName()
	{
		return (String)getNullSaveAttributeStringValue(ATTRIBUTE_FIRST_NAME);
	}

	public void setPatrName(final String patrName)
	{
		setNullSaveAttributeStringValue(ATTRIBUTE_PATR_NAME, patrName);
	}

	public String getPatrName()
	{
		return (String)getNullSaveAttributeStringValue(ATTRIBUTE_PATR_NAME);
	}

	/**
	 * @return Дата окончания действия предложения
	 */
	public Calendar getEndDate()
	{
		return getNullSaveAttributeCalendarValue(ATTRIBUTE_END_DATE);
	}

	public void setEndDate(Calendar endDate)
	{
		setNullSaveAttributeCalendarValue(ATTRIBUTE_END_DATE, endDate);
	}

	public Boolean getOnlyShortClaim()
	{
		return (Boolean)getAttribute(ATTRIBUTE_ONLY_SHORT_CLAIM).getValue();
	}

	public String getPhoneNumber()
	{
		return getNullSaveAttributeStringValue(ATTRIBUTE_PHONE_NUMBER);
	}
}
