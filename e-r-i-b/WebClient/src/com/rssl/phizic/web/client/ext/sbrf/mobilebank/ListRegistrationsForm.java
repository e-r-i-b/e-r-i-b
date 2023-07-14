package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankConstants;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Erkin
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class ListRegistrationsForm extends MobileBankFormBase
{
	private RegistrationProfile   confirmableRegistration;
	private Collection<RegistrationProfile> registrations;

	private final String mobileBankDescriptionUrl =
			MobileBankConstants.SBERBANK_MOBILE_BANK_DESCRIPTION_URL;
    private ConfirmStrategy confirmStrategy; //стратегия подтверждения
	private String fo;//Фамилия отчество
	private boolean isTechnoBreak;

	/////////////////////////////////////////////////////////////////////////////////////

	public Collection<RegistrationProfile> getRegistrations()
	{
		if (registrations == null)
			return null;
		else return new ArrayList<RegistrationProfile>(registrations);
	}

	public void setRegistrations(Collection<RegistrationProfile> registrations)
	{
		if (registrations == null)
			this.registrations = null;
		else this.registrations = new ArrayList<RegistrationProfile>(registrations);
	}

	public String getMobileBankDescriptionUrl()
	{
		return mobileBankDescriptionUrl;
	}

	public RegistrationProfile getConfirmableRegistration()
	{
		return confirmableRegistration;
	}

	public void setConfirmableRegistration(RegistrationProfile confirmableRegistration)
	{
		this.confirmableRegistration = confirmableRegistration;
	}

    public ConfirmStrategy getConfirmStrategy()
    {
        return confirmStrategy;
    }

    public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
    {
        this.confirmStrategy = confirmStrategy;
    }

	public String getFo()
	{
		return fo;
	}

	public void setFo(String fo)
	{
		this.fo = fo;
	}

	public boolean isTechnoBreak()
	{
		return isTechnoBreak;
	}

	public void setTechnoBreak(boolean technoBreak)
	{
		isTechnoBreak = technoBreak;
	}
}
