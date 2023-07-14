package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.payments.AccountJurIntraBankTransfer;
import com.rssl.phizic.gate.payments.AccountJurTransfer;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;

/**
 * ������ �� ������������ ����������� �����
 * ������ ���������� �����, ���� ������ ��� �����, ���. ����������� ������ ����.
 *
 * @author egorova
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class SpecificGateConfig extends Config
{
	public static final String CREDITS_ROUTES_DICTIONARY_PATH = "com.rssl.gate.credits.routes.dictionary.path";
	public static final String DOCUMENT_UPDATE_WAITING_TIME = "com.rssl.gate.document.waiting.time";
	public static final String RECEIVERS_ROUTES_DICTIONARY_PATH = "com.rssl.gate.receivers.routes.dictionary.path";

	public static final String USE_PAYMENT_ORDER_ACCOUNT_JUR_TRANSFER = AccountJurTransfer.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_ACCOUNT_JUR_INTRA_BANK_TRANSFER = AccountJurIntraBankTransfer.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_RUS_TAX_PAYMENT = AccountRUSTaxPayment.class.getName() + ".usePaymentOrder";
	public static final String USE_PAYMENT_ORDER_ACCOUNT_PAYMENT = AccountPaymentSystemPayment.class.getName() + ".usePaymentOrder";

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	protected SpecificGateConfig(PropertyReader reader)
	{
		super(reader);
	}

	//���� � ����������� ��������� ��������
	public abstract String getCreditsRoutesDictionaryPath();

	/**
	 * @return ���� � ����������� ��������� ����������� ��������
	 */
	public abstract String getReceiversRoutesDictionaryPath();

	/**
	 * @return ������ ������������ ���������� ��������� ��� ��������� �� ���� �� ����� �� ���� � ������ ����.
	 */
	public abstract String getUsePaymentOrderForAccountJurTransfer();
	/**
	 * @return ������ ������������ ���������� ��������� ��� ��������� �� ���� �� ����� �� ���� ������ ���������.
	 */
	public abstract String getUsePaymentOrderForAccountJurIntrabankTransfer();
	/**
	 * @return ������ ������������ ���������� ��������� ��� ������ �������.
	 */
	public abstract String getUsePaymentOrderForRUSTaxPayment();
	/**
	 * @return ������ ������������ ���������� ��������� ��� ����������� �������� �� �����.
	 */
	public abstract String getUsePaymentOrderForAccountPaymentSystemPayment();
	/**
	 * @return ����� ��������, ����� �������� �������� �������� ������ ��������� (� ��������)
	 */
	public abstract Integer getDocumentUpdateWaitingTime();
}
