package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class StartRegistrationForm extends ActionFormBase implements RegistrationForm
{
	private String returnURL;

	/**
	 * Адрес экшена следующего шага
	 */
	private String nextActionPath;

	private boolean canRepeatClaim;

	private boolean phoneAvailable;

	private boolean cardsAvailable;

	private boolean isAuthenticationComplete;

	private MobileBankRegistrationClaim previousClaim;

	///////////////////////////////////////////////////////////////////////////

	public String getNextActionPath()
	{
		return nextActionPath;
	}

	public void setNextActionPath(String nextActionPath)
	{
		this.nextActionPath = nextActionPath;
	}

	public String getReturnURL()
	{
		return returnURL;
	}

	public void setReturnURL(String returnURL)
	{
		this.returnURL = returnURL;
	}

	public boolean isCanRepeatClaim()
	{
		return canRepeatClaim;
	}

	public void setCanRepeatClaim(boolean canRepeatClaim)
	{
		this.canRepeatClaim = canRepeatClaim;
	}

	public boolean isPhoneAvailable()
	{
		return phoneAvailable;
	}

	public void setPhoneAvailable(boolean phoneAvailable)
	{
		this.phoneAvailable = phoneAvailable;
	}

	public boolean isCardsAvailable()
	{
		return cardsAvailable;
	}

	public void setCardsAvailable(boolean cardsAvailable)
	{
		this.cardsAvailable = cardsAvailable;
	}

	public boolean isAuthenticationComplete()
	{
		return isAuthenticationComplete;
	}

	public void setAuthenticationComplete(boolean authenticationComplete)
	{
		isAuthenticationComplete = authenticationComplete;
	}

	public MobileBankRegistrationClaim getPreviousClaim()
	{
		return previousClaim;
	}

	public void setPreviousClaim(MobileBankRegistrationClaim previousClaim)
	{
		this.previousClaim = previousClaim;
	}

}
