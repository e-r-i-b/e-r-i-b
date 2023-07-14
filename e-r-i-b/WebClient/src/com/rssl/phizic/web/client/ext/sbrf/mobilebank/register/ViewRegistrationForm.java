package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewRegistrationForm extends ActionFormBase implements RegistrationForm
{
	/**
	 * ID заявки на подключение МБ (MobileBankRegistrationClaim)
	 */
	private Long id;

	private MobileBankTariff tariff;

	private String maskedPhone;

	private String maskedCard;

	private String returnURL;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public MobileBankTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	public String getMaskedPhone()
	{
		return maskedPhone;
	}

	public void setMaskedPhone(String maskedPhone)
	{
		this.maskedPhone = maskedPhone;
	}

	public String getMaskedCard()
	{
		return maskedCard;
	}

	public void setMaskedCard(String maskedCard)
	{
		this.maskedCard = maskedCard;
	}

	public String getReturnURL()
	{
		return returnURL;
	}

	public void setReturnURL(String returnURL)
	{
		this.returnURL = returnURL;
	}
}