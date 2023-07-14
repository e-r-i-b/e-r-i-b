package com.rssl.phizic.business.externalsystem;

/**
 * Таблица соответствий
 * (у нас в базе лежит соответствие между ТБ, продуктами и внешними системами, настраивается внедрением)
 *
 *
 * @author khudyakov
 * @ created 02.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExternalSystemRouteInfo
{
	private Long id;                    //идентификатор записи
	private String systemId;            //код внешней системы
	private String productType;         //тип продукта
	private String tbCode;              //тб подразделения
	private boolean udboRequest;        //поддерживается ли запрос удбо


	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getProductType()
	{
		return productType;
	}

	public void setProductType(String productType)
	{
		this.productType = productType;
	}

	public String getTbCode()
	{
		return tbCode;
	}

	public void setTbCode(String tbCode)
	{
		this.tbCode = tbCode;
	}

	public boolean isUdboRequest()
	{
		return udboRequest;
	}

	public void setUdboRequest(boolean udboRequest)
	{
		this.udboRequest = udboRequest;
	}
}
