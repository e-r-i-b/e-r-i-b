package com.rssl.phizic.config.loan;

import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 14.11.2011
 * Time: 10:43:05
 */
public class UnloadConfigImpl extends UnloadConfig
{
	private int loanProductUnloadRepeatInterval;
	private int loanOfferUnloadRepeatInterval;
	private int cardProductUnloadRepeatInterval;
	private int cardOfferUnloadRepeatInterval;
	private int virtualCardUnloadRepeatInterval;
	private int unloadPartCount;

	public UnloadConfigImpl(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public void doRefresh() throws ConfigurationException
	{
		 unloadPartCount =  getIntProperty(UnloadConfig.UNLOAD_PART_COUNT);
		 virtualCardUnloadRepeatInterval = getIntProperty(UnloadConfig.UNLOAD_REPEAT_INTERVAL_VIRTUAL_CARD);
		 cardOfferUnloadRepeatInterval = getIntProperty(UnloadConfig.UNLOAD_REPEAT_INTERVAL_CARD_OFFER);
		 cardProductUnloadRepeatInterval = getIntProperty(UnloadConfig.UNLOAD_REPEAT_INTERVAL_CARD_PRODUCT);
		 loanOfferUnloadRepeatInterval = getIntProperty(UnloadConfig.UNLOAD_REPEAT_INTERVAL_LOAN_OFFER);
		 loanProductUnloadRepeatInterval = getIntProperty(UnloadConfig.UNLOAD_REPEAT_INTERVAL_LOAN_PRODUCT);
	}

	public int getCardOfferUnloadRepeatInterval()
	{
		return cardOfferUnloadRepeatInterval;
	}

	public int getCardProductUnloadRepeatInterval()
	{
		return cardProductUnloadRepeatInterval;
	}

	public int getLoanOfferUnloadRepeatInterval()
	{
		return loanOfferUnloadRepeatInterval;
	}

	public int getLoanProductUnloadRepeatInterval()
	{
		return loanProductUnloadRepeatInterval;
	}

	public int getVirtualCardUnloadRepeatInterval()
	{
		return virtualCardUnloadRepeatInterval;
	}

	public int getUnloadPartCount()
	{
		return unloadPartCount;
	}
}
