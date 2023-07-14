package com.rssl.phizic.business.einvoicing;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.shop.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ������ ��� �������� ���������� ������ ������/�������� ������
 *
 * @author gladishev
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShopWithdrawDocumentExecutor extends ShopDocumentInBlockExecutorBase
{
	private static final Object LOCK = new Object();
	private static volatile ShopWithdrawDocumentExecutor it;

	/**
	 * @return ������� ������
	 */
	public static ShopWithdrawDocumentExecutor getIt()
	{
		if (it != null)
			return it;

		synchronized (LOCK)
		{
			if (it != null)
				return it;

			it = new ShopWithdrawDocumentExecutor();
			return it;
		}
	}

	/**
	 * ������ ������ �� ������/������� ������
	 * @param amount ����� ��������
	 * @param currencyCode ������ ����� ��������
	 * @param orderUuid ������� �������������� ������.
	 * @param refundUuid uuid �������� ������� ��� ������ �������.
	 * @param withdrawMode Full = ������ ������, Partial = ������� ������  @return ��� ���������� ��������� �������
	 * @param returnedGoods ������ ������������ �������
	 */
	public void withdraw(BigDecimal amount, String currencyCode, String orderUuid,
	                            String refundUuid, WithdrawMode withdrawMode, String returnedGoods)
			throws BusinessException, BusinessTimeOutException
	{
		ApplicationInfo.setCurrentApplication(Application.WebShopListener);
		BusinessDocument parent = DocumentHelper.getPaymentByOrder(orderUuid);
		//������� � ���������� ������ � �������
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("parentId", parent.getId());
		values.put("refundUuid", refundUuid);
		if (withdrawMode == WithdrawMode.Partial)
			values.put("returnedGoods", returnedGoods);

		FieldValuesSource valuesSource = new MapValuesSource(values);
		try
		{
			ShopOrder order = ShopHelper.get().getShopOrder(orderUuid);
			if (order == null)
				throw new BusinessException("�� ������ ����� � uuin=" + orderUuid);
			initPersonContext(order);
			Currency currency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(currencyCode);
			ShopHelper.get().withdrawDocument(withdrawMode, valuesSource, parent, new Money(amount, currency), orderUuid);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			clearPersonContext();
			ApplicationInfo.setDefaultApplication();
		}
	}
}
