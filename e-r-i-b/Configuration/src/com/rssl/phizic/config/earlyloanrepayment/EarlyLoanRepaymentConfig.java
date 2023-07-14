package com.rssl.phizic.config.earlyloanrepayment;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ������ ��� ������ � �������� �� ��������� ��������� �������
 * User: petuhov
 * Date: 08.05.15
 * Time: 15:58 
 */
public class EarlyLoanRepaymentConfig extends Config
{
	public static final String EARLY_REPAYMENT_ALLOWED_KEY = "com.rssl.phizic.config.earlyloanrepayment.allowed";
	public static final String EARLY_REPAYMENT_MIN_DATE_KEY = "com.rssl.phizic.config.earlyloanrepayment.minDate";
	public static final String EARLY_REPAYMENT_MAX_DATE_KEY = "com.rssl.phizic.config.earlyloanrepayment.maxDate";
	public static final String EARLY_REPAYMENT_CANCEL_ERIB_ALLOWED_KEY = "com.rssl.phizic.config.earlyloanrepayment.cancelERIBAllowed";
	public static final String EARLY_REPAYMENT_CANCEL_VSP_ALLOWED_KEY = "com.rssl.phizic.config.earlyloanrepayment.cancelVSPAllowed";
	public static final String EARLY_REPAYMENT_MIN_AMOUNT_KEY = "com.rssl.phizic.config.earlyloanrepayment.minAmount";
	public static final String EARLY_REPAYMENT_TIME_LIMITATION = "com.rssl.phizic.config.earlyloanrepayment.timeLimitation";

	private boolean allowed;
	private int minDate;
	private int maxDate;
	private boolean cancelERIBAllowed;
	private boolean cancelVSPllowed;
	private int minAmount;
	private int timeLimitation;

	/**
	 * ����� ������ ������ ����������� ������ �����������.
	 *
	 * @param reader �����.
	 */
	public EarlyLoanRepaymentConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	public void doRefresh() throws ConfigurationException
	{
		allowed = getBoolProperty(EARLY_REPAYMENT_ALLOWED_KEY);
		minDate = getIntProperty(EARLY_REPAYMENT_MIN_DATE_KEY);
		maxDate = getIntProperty(EARLY_REPAYMENT_MAX_DATE_KEY);
		cancelERIBAllowed = getBoolProperty(EARLY_REPAYMENT_CANCEL_ERIB_ALLOWED_KEY);
		cancelVSPllowed = getBoolProperty(EARLY_REPAYMENT_CANCEL_VSP_ALLOWED_KEY);
		minAmount = getIntProperty(EARLY_REPAYMENT_MIN_AMOUNT_KEY);
		timeLimitation = getIntProperty(EARLY_REPAYMENT_TIME_LIMITATION);
	}

	/**
	 * @return ������������ ����������� ������� ������ �� ��������������� ��������� �������
	 */
	public boolean isAllowed(){
		return allowed;
	}

	/**
	 * @return ���� ������ ��������� ������ ��������� ��
	 */
	public int getMinDate()
	{
		return minDate;
	}

	/**
	 * @return ���� ��������� ��������� ������ ��������� ��
	 */
	public int getMaxDate()
	{
		return maxDate;
	}

	/**
	 * @return ����������� �������� ������ �� ��, ��������� � ������ ����
	 */
	public boolean isCancelERIBAllowed()
	{
		return cancelERIBAllowed;
	}

	/**
	 * @return ����������� �������� ������ �� ��, ��������� � ������ ���
	 */
	public boolean isCancelVSPllowed()
	{
		return cancelVSPllowed;
	}

	/**
	 * @return ����������� ����� ���������� ��������� ������� (� ��������� �� ���������������� �������)
	 */
	public int getMinAmount()
	{
		return minAmount;
	}

	/**
	 * @return ����� (� �����) ������� �� �������� ����� ������
	 */
	public int getTimeLimitation(){
		return timeLimitation;
	}
}
