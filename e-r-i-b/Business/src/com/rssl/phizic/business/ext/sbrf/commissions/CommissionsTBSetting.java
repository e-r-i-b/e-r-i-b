package com.rssl.phizic.business.ext.sbrf.commissions;

/**
 * @author vagin
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 * Настройка отображения сумм микроопераций в платеже.
 */
public class CommissionsTBSetting
{
	private Long id;
	private String TB;          //номер ТБ, в разрезе которого задается настройка
	private String paymentType; //тип документа GateDocument.getType()
    private boolean show;       //отображать комиссию в платеже для операций с валютных ресурсов(рассчитывать в ЦОД?)
	private boolean showRub;    //отображать комиссию в платеже для операций с рублевого ресурса списания (рассчитывать в ЦОД?)

	/**
	 * Конструктор настройки отображения сумм комиссий в плетеже.
	 * @param TB  - номер ТБ
	 * @param paymentType - тип платежа(гейтовый тип)
	 * @param show - true/false(отображать/не оображать сумму комиссий)
	 */
	public CommissionsTBSetting(String TB, String paymentType, boolean show, boolean showRub)
	{
		this.TB = TB;
		this.paymentType = paymentType;
		this.show = show;
		this.showRub = showRub;
	}

	public CommissionsTBSetting() {}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public boolean isShow()
	{
		return show;
	}

	public void setShow(boolean show)
	{
		this.show = show;
	}

	public String getTB()
	{
		return TB;
	}

	public void setTB(String TB)
	{
		this.TB = TB;
	}

	public boolean isShowRub()
	{
		return showRub;
	}

	public void setShowRub(boolean showRub)
	{
		this.showRub = showRub;
	}
}
