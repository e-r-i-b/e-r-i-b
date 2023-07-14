package com.rssl.phizic.business.ext.sbrf.mobilebank.validator;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author Erkin
 * @ created 23.06.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������, ��� � ��������� ���� ������ �������,
 * ������� ����������� ��������� �������� (iqwave, ��������)
 */
public class CardTransferAdapterRequiredValidator extends MultiFieldsValidatorBase
{
	private static final BillingService billingService = new BillingService();

	/**
	 * ��� ����, � ������� ������ ����������� ID ��������
	 */
	public static final String FIELD_BILLING_ID = "id";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		AdaptersConfig adaptersConfig = ConfigFactory.getConfig(AdaptersConfig.class);
		if (adaptersConfig == null)
			throw new TemporalDocumentException("� ������� ����������� ������������ ���������");

		Adapter cardTransferAdapter = adaptersConfig.getCardTransfersAdapter();
		if (cardTransferAdapter == null)
			throw new TemporalDocumentException("� ������� �� ������ �������, " +
					"������� ����������� ��������� �������� (iqwave, ��������)");

		try
		{
			BigInteger billingId = (BigInteger) retrieveFieldValue(FIELD_BILLING_ID, values);
			if (billingId == null)
				return false;
			Billing billing = billingService.getById(billingId.longValue(), MultiBlockModeDictionaryHelper.getDBInstanceName());
			if (billing == null)
				throw new TemporalDocumentException("�� ������ ������� �� ID " + billingId);
			String billingAdapterUUID = billing.getAdapterUUID();
			if (billingAdapterUUID == null)
				throw new TemporalDocumentException("��� �������� " + billingId + " �� ������ �������");
			return billingAdapterUUID.equals(cardTransferAdapter.getUUID());
		}
		catch (BusinessException ex)
		{
			throw new TemporalDocumentException(ex);
		}
	}
}
