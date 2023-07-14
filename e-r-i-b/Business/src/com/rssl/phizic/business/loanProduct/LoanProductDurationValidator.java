package com.rssl.phizic.business.loanProduct;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.products.MaxDuration;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.loans.products.YearMonth;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 21.06.2011
 * @ $Author$
 * @ $Revision$
 * ¬алидатор проверки введенного пользователем срока в кредитной за€вки с возможным, согласно кредитному продукту
 */

public class LoanProductDurationValidator extends MultiFieldsValidatorBase
{
	private static final String LOAN_PRODUCT_ID  = "loan";
	private static final String DURATION     = "duration";
	private  static final ModifiableLoanProductService service = new ModifiableLoanProductService();
	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = 999;
	private static final String FROM  = " от ";
	private static final String TO    = " до ";
	private static final String DOT    = ".";


	private ThreadLocal<String> currentMessage  = new ThreadLocal<String>();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long conditionId  = (Long) retrieveFieldValue(LOAN_PRODUCT_ID, values);
		Long duration  = (Long) retrieveFieldValue(DURATION, values);
		boolean isValidate = true;
		YearMonth minDuration = null;
		MaxDuration maxDuration = null;

		try
		{
			ModifiableLoanProduct product = service.getOfferProductByConditionId(conditionId);
			String durationString ="";

			if (product == null)   // кредитного продукта уже нет - значит ошибка
				return false;

			if (!product.getMinDuration().isEmpty() && !product.getMaxDuration().isEmpty())
			{
				minDuration = new YearMonth(product.getMinDuration().getYear(),
					product.getMinDuration().getMonth());
				maxDuration = new MaxDuration(product.getMaxDuration().getYear(),
					product.getMaxDuration().getMonth(), product.getMaxDurationInclude());
				isValidate = minDuration.getDuration() <= duration && maxDuration.getDuration() > duration;
                durationString = FROM +getYear(minDuration.getYear()) + getMonth(minDuration.getMonth()) +
		                         TO + getYear(maxDuration.getYear()) + getMonth(maxDuration.getMonth()) + DOT;

			}
			else if(!product.getMinDuration().isEmpty())
			{
				minDuration = new YearMonth(product.getMinDuration().getYear(),
					product.getMinDuration().getMonth());
				maxDuration = new MaxDuration(0, MAX_VALUE, true);
				isValidate = minDuration.getDuration() <= duration;
				durationString = FROM + getYear(minDuration.getYear()) + getMonth(minDuration.getMonth()) +DOT;
			}
			else if(!product.getMaxDuration().isEmpty())
			{
				minDuration = new YearMonth(0, MIN_VALUE);
				maxDuration = new MaxDuration(product.getMaxDuration().getYear(),
					product.getMaxDuration().getMonth(), product.getMaxDurationInclude());
				isValidate = product.getMaxDuration().getDuration() > duration;
				durationString = TO + getYear(maxDuration.getYear()) + getMonth(maxDuration.getMonth()) +DOT;
			}


			if (!isValidate)
				currentMessage.set(createMessage(durationString));

		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}


		return isValidate;
	}

	public String getMessage()
	{
		return currentMessage.get();
	}
	                                                   
	private String createMessage(String durationString)
	{
		return "¬ы неправильно указали срок кредита.  редит можно открыть на срок" + durationString + " ѕожалуйста, введите срок, который будет соответствовать услови€м выдачи кредита";
	}

	private String getYear(Integer year)
	{
		if (year == null || year == 0)
			return "";
		else if (year == 1)
			return "1 года ";
		else
			return  year + " лет ";
	}

	private String getMonth(Integer month)
	{
		return month == null || month == 0 ? "" : month + " мес";
	}
}
