package com.rssl.phizic.operations.card.products;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.util.*;

/**
 * @author gulov
 * @ created 04.10.2011
 * @ $Authors$
 * @ $Revision$
 */
public class EditCardProductOperation extends OperationBase implements EditEntityOperation
{
	private static final CardProductService service = new CardProductService();
	private CardProduct product;

	/**
	 * ������������� ���������� ��������
	 * @param id - id ��������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		product = service.findById(id);
		if (product == null)
			throw new BusinessException("�� ������ ��������� ������� � id = " + id);
	}

	/**
	 * �������� ������ ���������� ��������
	 * @throws BusinessException
	 */
	public void initializeNew() throws BusinessException
	{
		product = new CardProduct();
		product.setKindOfProducts(new ArrayList<CASNSICardProduct>());
	}

	/**
	 * �������� ��������� �������
	 * @return - ��������� �������
	 */
	public CardProduct getEntity()
	{
		return product;
	}

	/**
	 * �������� ���� ��������� � ��������� �������
	 * @param stopOpenDate - ���� �������� �������� ��������
	 * @param online - ������� ����������
	 * @param kinds - ���� ��������� ���������
	 * @param currencies - ������   @throws BusinessLogicException
	 */
	public void validateAndWrite(long[] stopOpenDate, Boolean online, Long[] kinds, String[] currencies) throws BusinessLogicException
	{
		if (!ArrayUtils.isEmpty(currencies))
		{
			Set<String> currencySet = new HashSet<String>();
			for (String value : currencies)
			{
				if (!currencySet.add(value))
					throw new BusinessLogicException("� ������ ������ ���� ����� ��������� ������ ���� ������ � ����� ����� ������");
			}
		}
		List<CASNSICardProduct> productKinds = new ArrayList<CASNSICardProduct>();
		if (!ArrayUtils.isEmpty(kinds))
		{
			for (Long value : kinds)
			{
				CASNSICardProduct kind = new CASNSICardProduct();
				kind.setId(value);
				productKinds.add(kind);
			}
		}
		Calendar date = null;
		if (!ArrayUtils.isEmpty(stopOpenDate))
		{
			date = Calendar.getInstance();
			date.setTimeInMillis(NumberUtils.max(stopOpenDate));
		}
		product.setStopOpenDate(date);
		product.setKindOfProducts(productKinds);
	}

	/**
	 * ������������ ������� ��� ����� � ����������
	 * @param online - ������� ����������
	 * @param message - ��������� �� ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void publishOrUnpublish(Boolean online, String message) throws BusinessException, BusinessLogicException
	{
		if (online.equals(product.getOnline()))
			throw new BusinessLogicException(message);
		product.setOnline(online);
		save();
	}

	/**
	 * ��������� ��������� �������
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		if (BooleanUtils.isTrue(product.isOnline()) && CollectionUtils.isEmpty(product.getKindOfProducts()))
			throw new BusinessLogicException("������� �� ����� ���� ����������� �������, ���� ��� ���� �� ������ ���� ����.");
		if (product.getKindOfProducts().size() == 0)
		{
			service.save(product);
			return;
		}
		Calendar temp = product.getKindOfProducts().get(0).getStopOpenDeposit();
		for (CASNSICardProduct kind : product.getKindOfProducts())
		{
			if (kind.getStopOpenDeposit() != null && temp != null && kind.getStopOpenDeposit().after(temp))
			{
				temp = kind.getStopOpenDeposit();
				product.setStopOpenDate(temp);
			}
		}
		service.save(product);
	}
}
