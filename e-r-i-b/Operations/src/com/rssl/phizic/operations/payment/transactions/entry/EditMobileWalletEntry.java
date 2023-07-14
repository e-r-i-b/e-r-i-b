package com.rssl.phizic.operations.payment.transactions.entry;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Map;
import java.util.HashMap;

/**
 * @author vagin
 * @ created 12.11.2012
 * @ $Author$
 * @ $Revision$
 * Сущность подтверждаемое изменение размера мобильного кошелька.
 */
public class EditMobileWalletEntry implements ConfirmableObject
{
	private Profile profile;
	private Money wallet;

	EditMobileWalletEntry(Money value)
	{
		profile.setMobileWallet(value);
	}

	EditMobileWalletEntry(Profile profile)
	{
		this.profile = profile;
		this.wallet = profile.getMobileWallet();
	}

	public Long getId()
	{
		return profile.getId();
	}

	public byte[] getSignableObject()
	{
		return new byte[0];  
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

	public Money getMobileWallet()
	{
		return wallet;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setMobileWalletValue(Money value)
	{
		this.wallet = value;
	}
}
