package com.rssl.phizic.business.deposits;

import com.rssl.phizic.business.BusinessException;

/**
 * Исключение - продукт не найден.
 * @author Roshka
 * @ created 24.05.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductNotFoundException extends BusinessException
{
	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "Депозитный продукт (id: %S) не найден.";
	private Long productId;

	public DepositProductNotFoundException(Long productId)
	{
		super(String.format(DEPOSIT_PRODUCT_NOT_FOUND, productId));
		this.productId = productId;
	}

	public DepositProductNotFoundException(Long productId, Throwable cause)
	{
		super(String.format(DEPOSIT_PRODUCT_NOT_FOUND, productId) + cause);
		this.productId = productId;
	}

	public DepositProductNotFoundException(Long productId, String message)
	{
		super( String.format(DEPOSIT_PRODUCT_NOT_FOUND, productId) + message);
		this.productId = productId;
	}

	public DepositProductNotFoundException(Long productId, String message, Throwable cause)
	{
		super(String.format(DEPOSIT_PRODUCT_NOT_FOUND, productId) + message, cause);
		this.productId = productId;
	}

	public Long getProductId()
	{
		return productId;
	}
}
