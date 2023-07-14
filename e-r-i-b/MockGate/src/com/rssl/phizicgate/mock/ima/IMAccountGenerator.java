package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountState;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountGenerator extends AbstractIMAGenerator
{
	private String id;
	private String agreementNumber;

	private IMAccountState state;

	public IMAccountGenerator()
	{
		super();
	}

	public String getId()
	{
		if(id == null)
		{
			id = generateNumber(20) + "^externalId";
		}
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNumber()
	{
		return id.substring(0, id.indexOf("^"));
	}

	protected IMAccount generate()
	{
		return new IMAccountImpl
		(
			getId(),
			getNumber(),
			getCurrency(AbstractIMAGenerator.CURRENCY_CODE_AUR),
			generateMoney(AbstractIMAGenerator.CURRENCY_CODE_AUR),
			generateAgreementNumber(),
			generateDate(),
			generateState(),
			null
		);
	}

	private String generateAgreementNumber()
	{
		if(agreementNumber == null)
		{
			agreementNumber = generateNumber(4) + "\\" +generateNumber(2) + "-u2";
		}
		return agreementNumber;
	}

	private IMAccountState generateState()
	{
		if(getRandom().nextBoolean())
		{
			state = IMAccountState.opened;
		}
		else
		{
			state = IMAccountState.closed;
		}
		return state;
	}
}
