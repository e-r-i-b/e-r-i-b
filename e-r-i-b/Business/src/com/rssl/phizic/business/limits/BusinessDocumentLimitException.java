package com.rssl.phizic.business.limits;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Money;

/**
 * Логическое исключение при проверке лимитов
 * @author niculichev
 * @ created 19.01.14
 * @ $Author$
 * @ $Revision$
 */
public class BusinessDocumentLimitException extends BusinessLogicException
{
	private Limit limit; // сработавший лимит
	private Money amount; // накопленная сумма
	private Long count;  // накопленное количество операций

	public BusinessDocumentLimitException(String message, Limit limit)
	{
		super(message);
		this.limit = limit;
	}

	public BusinessDocumentLimitException(String message, Limit limit, Money amount)
	{
		super(message);
		this.limit = limit;
		this.amount = amount;
	}

	public BusinessDocumentLimitException(String message, Limit limit, Long count)
	{
		super(message);
		this.limit = limit;
		this.count = count;
	}

	public BusinessDocumentLimitException(Throwable cause, Limit limit)
	{
		super(cause);
		this.limit = limit;
	}

	public BusinessDocumentLimitException(String message, Throwable cause, Limit limit)
	{
		super(message, cause);
		this.limit = limit;
	}

	public Limit getLimit()
	{
		return limit;
	}

	public Money getAmount()
	{
		return amount;
	}

	public Long getCount()
	{
		return count;
	}

	public String getLogMessage(Long documentId)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[ ");
		if(limit != null)
		{
			builder.append("limitId = ");
			builder.append(limit.getId());
			builder.append("; ");
		}

		if(amount != null)
		{
			builder.append("accumulateAmount = ");
			builder.append(amount.getDecimal());
			builder.append(" ");
			builder.append(amount.getCurrency().getCode());
			builder.append("; ");
		}

		if(count != null)
		{
			builder.append("accumulateCount = ");
			builder.append(count);
			builder.append("; ");
		}

		builder.append("]");
		return builder.toString();
	}
}
