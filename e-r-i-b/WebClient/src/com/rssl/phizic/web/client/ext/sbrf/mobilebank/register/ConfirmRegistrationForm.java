package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Erkin
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmRegistrationForm extends ActionFormBase implements RegistrationForm
{
	/**
	 * ID заявки на подключение МБ (MobileBankRegistrationClaim)
	 */
	private Long id;

	private MobileBankTariff tariff;

	private String maskedPhone;

	private String maskedCard;

	private ConfirmRequest confirmRequest;

	private String returnURL;
	
	/**
	 * Адрес экшена предыдущего шага
	 */
	private String backActionPath;

	/**
	 * Адрес экшена с окном ввода пароля
	 */
	private String confirmActionPath;

	/**
	 * Адрес экшена следующего шага
	 */
	private String nextActionPath;

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

	public ConfirmRequest getConfirmRequest()
	{
		return confirmRequest;
	}

	public void setConfirmRequest(ConfirmRequest confirmRequest)
	{
		this.confirmRequest = confirmRequest;
	}

	public String getReturnURL()
	{
		return returnURL;
	}

	public void setReturnURL(String returnURL)
	{
		this.returnURL = returnURL;
	}

	public String getBackActionPath()
	{
		return backActionPath;
	}

	public void setBackActionPath(String backActionPath)
	{
		this.backActionPath = backActionPath;
	}

	public String getConfirmActionPath()
	{
		return confirmActionPath;
	}

	public void setConfirmActionPath(String confirmActionPath)
	{
		this.confirmActionPath = confirmActionPath;
	}

	public String getNextActionPath()
	{
		return nextActionPath;
	}

	public void setNextActionPath(String nextActionPath)
	{
		this.nextActionPath = nextActionPath;
	}
}
