package com.rssl.phizic.business.dictionaries.cellNumberArray;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.Calendar;

/**
 * Номерная ёмкость по DEF-кодам
 * @author shapin
 * @ created 02.04.15
 * @ $Author$
 * @ $Revision$
 */
public class NumberArray extends DictionaryRecordBase
{
	private Long id; //идентификатор
	private long numberFrom; // Начало диапазона нумерации  (из файла ЦБДПН)
	private long numberTo; // Конец диапазона нумерации  (из файла ЦБДПН)
	private String ownerId; // Условное наименование МО–владельца данного диапазона DEF-кодов (из файла ЦБДПН)
	private String orgName; // Юридическое наименование МО (из файла ЦБДПН)
	private String mnc; // Код сети МО
	private String regionCode; // Код региона России
	private String serviceId; //  Цифровой код сервиса (маршрута) получателя в АС SmartVista для безналичных платежей
	private String serviceCode; // Символьный код сервиса (маршрута) получателя в АС SmartVista для безналичных платежей
	private String cashServiceId; // Код сервиса (маршрута) получателя в АС SmartVista для наличных платежей
	private String mbOperatorId; // Код МО в АС «Мобильный банк»
	private String mbOperatorNumber; // Номер мобильного оператора в МБК
	private String providerId; // Код МО в АС «Автоплатежи»

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public long getNumberFrom()
	{
		return numberFrom;
	}

	public void setNumberFrom(long numberFrom)
	{
		this.numberFrom = numberFrom;
	}

	public long getNumberTo()
	{
		return numberTo;
	}

	public void setNumberTo(long numberTo)
	{
		this.numberTo = numberTo;
	}

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getMnc()
	{
		return mnc;
	}

	public void setMnc(String mnc)
	{
		this.mnc = mnc;
	}

	public String getRegionCode()
	{
		return regionCode;
	}

	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}

	public String getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(String serviceId)
	{
		this.serviceId = serviceId;
	}

	public String getServiceCode()
	{
		return serviceCode;
	}

	public void setServiceCode(String serviceCode)
	{
		this.serviceCode = serviceCode;
	}

	public String getCashServiceId()
	{
		return cashServiceId;
	}

	public void setCashServiceId(String cashServiceId)
	{
		this.cashServiceId = cashServiceId;
	}

	public String getMbOperatorId()
	{
		return mbOperatorId;
	}

	public void setMbOperatorId(String mbOperatorId)
	{
		this.mbOperatorId = mbOperatorId;
	}

	public String getMbOperatorNumber()
	{
		return mbOperatorNumber;
	}

	public void setMbOperatorNumber(String mbOperatorNumber)
	{
		this.mbOperatorNumber = mbOperatorNumber;
	}

	public String getProviderId()
	{
		return providerId;
	}

	public void setProviderId(String providerId)
	{
		this.providerId = providerId;
	}

	public Comparable getSynchKey()
	{
		return numberFrom + " " + numberTo + " " + ownerId + " " + mnc + " " + regionCode;
	}
}
