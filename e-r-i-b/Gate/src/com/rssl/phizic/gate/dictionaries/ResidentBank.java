package com.rssl.phizic.gate.dictionaries;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Справочник банков резидентов
 *
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 24.10.2005
 * Time: 20:59:00
 */
public class ResidentBank extends Bank implements Serializable
{
	//БИК
    private String BIC;
	//Краткое наименование
	private String shortName;
	//Является ли банк "нашим"
	private Boolean our;
	//Адрес банка
	private String address;
	//Код типа участника расчетов в сети БР
	private String participantCode;
	//ИНН
	private String INN;
	//КПП
	private String KPP;
	//контрольная дата
	private Calendar dateCh;

	public ResidentBank()
	{
		this.BIC = "";
		this.shortName = "";
	}

	/**
	 * @param name название
	 * @param bic  бик
	 * @param account кор. счет
	 */
	public ResidentBank(String name, String bic, String account)
	{
		setName(name);
		setBIC(bic);
		setAccount(account);
	}

	public String getBIC()
    {
        return BIC;
    }

    public void setBIC(String BIC)
    {
        this.BIC = BIC;
    }

	public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

	public Boolean isOur()
	{
		return our;
	}

	public void setOur(Boolean our)
	{
		this.our = our;
	}

	//Для нужд web-сервисов
	public Boolean getOur()
	{
		return our;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getParticipantCode()
	{
		return participantCode;
	}

	public void setParticipantCode(String participantCode)
	{
		this.participantCode = participantCode;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
	}

	public Calendar getDateCh()
	{
		return dateCh;
	}

	public void setDateCh(Calendar dateCh)
	{
		this.dateCh = dateCh;
	}
}
