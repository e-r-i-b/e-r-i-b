package com.rssl.phizic.config.loan;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * User: Moshenko
 * Date: 14.11.2011
 * Time: 10:38:56
 * ��������� �������� ��������� ����������� ��������� �������� ��������� �����������
 */
public abstract class UnloadConfig  extends Config
{
	public static final String UNLOAD_REPEAT_INTERVAL_LOAN_PRODUCT = "com.rssl.iccs.max.day.unload.repeat.interval.loanProduct";
	public static final String UNLOAD_REPEAT_INTERVAL_VIRTUAL_CARD = "com.rssl.iccs.max.day.unload.repeat.interval.virtualCard";
	public static final String UNLOAD_REPEAT_INTERVAL_LOAN_OFFER = "com.rssl.iccs.max.day.unload.repeat.interval.loanOffer";
	public static final String UNLOAD_REPEAT_INTERVAL_CARD_PRODUCT = "com.rssl.iccs.max.day.unload.repeat.interval.cardProduct";
	public static final String UNLOAD_REPEAT_INTERVAL_CARD_OFFER = "com.rssl.iccs.max.day.unload.repeat.interval.cardOffer";
	public static final String UNLOAD_PART_COUNT =  "com.rssl.iccs.unload.product.part.count";

	protected UnloadConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
 	 * @return ������ ����������� � ���� ����� ��������� 
	 */
	public abstract int getUnloadPartCount();

	/**
	 * @return #���������� ���� �� ������� ������������ ��������� ��������(CardOffer)
	 */
	public abstract int getCardOfferUnloadRepeatInterval();
	/**
	 * @return #���������� ���� �� ������� ������������ ��������� �������� (CardProduct)
	 */
	public abstract int getCardProductUnloadRepeatInterval();
	/**
	 * @return #���������� ���� �� ������� ������������ ��������� �������� ������(LoanOffer)
	 */
	public abstract int getLoanOfferUnloadRepeatInterval();
	/**
	 * @return #���������� ���� �� ������� ������������ ��������� �������� ������(loanProduct)
	 */
	public abstract int getLoanProductUnloadRepeatInterval();
	/**
	 * @return #���������� ���� �� ������� ������������ ��������� ��������������(VirtualCard)
	 */
	public abstract int getVirtualCardUnloadRepeatInterval();


}
