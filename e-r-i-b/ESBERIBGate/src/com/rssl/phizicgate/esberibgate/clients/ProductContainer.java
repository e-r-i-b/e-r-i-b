package com.rssl.phizicgate.esberibgate.clients;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * Вспомогательный класс для работы с продуктами
 *
 * @author khudyakov
 * @ created 12.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ProductContainer
{
	public static final String DEFAULT_ERROR_MESSAGE = "Информация из АБС временно недоступна.";

	private List<BankProductType> products;                             //список активных на данный момент продуктов
	private Map<BankProductType, String> errors;                        //ошибки доступа к продуктам в разрезе продукта
	private IFXRq_Type ifxRq_type;                                      //сформированный запрос

	public ProductContainer(List<BankProductType> products, Map<BankProductType, String> errors)
	{
		this.products = products;
		this.errors = errors;
	}

	public List<BankProductType> getProducts()
	{
		return products;
	}

	public void setProducts(List<BankProductType> products)
	{
		this.products = products;
	}

	public Map<BankProductType, String> getErrors()
	{
		return errors;
	}

	public void setErrors(Map<BankProductType, String> errors)
	{
		this.errors = errors;
	}

	public String getProductError(BankProductType productType)
	{
		String error = errors.get(productType);
		if (StringHelper.isEmpty(error))
			return DEFAULT_ERROR_MESSAGE;

		return error;
	}

	public IFXRq_Type getIfxRq_type()
	{
		return ifxRq_type;
	}

	public void setIfxRq_type(IFXRq_Type ifxRq_type)
	{
		this.ifxRq_type = ifxRq_type;
	}

	/**
	 * Объединяет ошибки в разрезе продукта
	 * @param errors контейнер ошибок
	 * @param newErrors новые ошибки
	 */
	public void joinErrors(Map<BankProductType, String> errors, Map<BankProductType, String> newErrors)
	{
		if (errors == null)
			throw new IllegalArgumentException("Некорректный парамметр errors.");

		//если мап новых ошибок пуст ничего не делаем
		if (MapUtils.isEmpty(newErrors))
			return;

		for (Map.Entry<BankProductType, String> newError : newErrors.entrySet())
		{
			String value = errors.get(newError.getKey());
			if (StringHelper.isEmpty(value))
			{
				errors.put(newError.getKey(), newError.getValue());
			}
		}
	}
}
