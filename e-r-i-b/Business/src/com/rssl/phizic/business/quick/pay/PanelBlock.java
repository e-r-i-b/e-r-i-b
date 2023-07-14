package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.image.Image;

import java.math.BigDecimal;

/**
 * Сущность блок поставщика услуг.
 * @author komarov
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class PanelBlock extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private Boolean show;//Отображать или нет
	private Long order;  //Порядок отображения
	private Long providerId;//Поставщик
	private String providerName;//Название поставщика, которое будет отображаться под иконкой.
	private Boolean showName;//отображаем или нет название провайдера.
	private String providerFieldName;//Название ключевого поля поставщика.
	private String providerFieldAmount;//Название поля для ввода суммы.
	private BigDecimal summ = new BigDecimal(250L);//Сумма по умолчанию.
	private Image image;//картинка

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Boolean isShow()
	{
		return show;
	}
	//для jsp
	public Boolean getShow()
	{
		return show;
	}

	public void setShow(Boolean show)
	{
		this.show = show;
	}

	public Long getOrder()
	{
		return order;
	}

	public void setOrder(Long order)
	{
		this.order = order;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public String getProviderFieldName()
	{
		return providerFieldName;
	}

	public void setProviderFieldName(String providerFieldName)
	{
		this.providerFieldName = providerFieldName;
	}

	public String getProviderFieldAmount()
	{
		return providerFieldAmount;
	}

	public void setProviderFieldAmount(String providerFieldAmount)
	{
		this.providerFieldAmount = providerFieldAmount;
	}

	public BigDecimal getSumm()
	{
		return summ;
	}

	public void setSumm(BigDecimal summ)
	{
		this.summ = summ;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image imageId)
	{
		this.image = imageId;
	}

	public Boolean isShowName()
	{
		return showName;
	}

	public Boolean getShowName()
	{
		return showName;
	}

	public void setShowName(Boolean showName)
	{
		this.showName = showName;
	}
}
