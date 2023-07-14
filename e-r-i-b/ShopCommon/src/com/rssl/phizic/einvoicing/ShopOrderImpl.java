package com.rssl.phizic.einvoicing;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.einvoicing.*;

import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public class ShopOrderImpl implements ShopOrder
{
	private Long id;
	private String uuid;
	private String externalId;
	private TypeOrder type;
	private Calendar date;
	private OrderState state = OrderState.CREATED;
	private ShopProfile profile;
	private String phone;
	private String receiverCode;
	private String receiverName;
	private String facilitatorProviderCode;
	private Money amount;
	private String description;
	private String receiverAccount;
	private String bic;
	private String corrAccount;
	private String inn;
	private String kpp;
	private String printDescription;
	private String backUrl;
	private Long nodeId;
	private String utrrno;
	private OrderKind kind;
	private boolean mobileCheckout;
	private Calendar delayedPayDate;
	private Boolean isNew;

	public String getBackUrl()
	{
		return backUrl;
	}

	public void setBackUrl(String backUrl)
	{
		this.backUrl = backUrl;
	}

	public String getBic()
	{
		return bic;
	}

	public void setBic(String bic)
	{
		this.bic = bic;
	}

	public String getCorrAccount()
	{
		return corrAccount;
	}

	public void setCorrAccount(String corrAccount)
	{
		this.corrAccount = corrAccount;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getInn()
	{
		return inn;
	}

	public void setInn(String inn)
	{
		this.inn = inn;
	}

	public String getKpp()
	{
		return kpp;
	}

	public void setKpp(String kpp)
	{
		this.kpp = kpp;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPrintDescription()
	{
		return printDescription;
	}

	public void setPrintDescription(String printDescription)
	{
		this.printDescription = printDescription;
	}

	public ShopProfile getProfile()
	{
		return profile;
	}

	public void setProfile(ShopProfile profile)
	{
		this.profile = profile;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverCode()
	{
		return receiverCode;
	}

	public void setReceiverCode(String receiverCode)
	{
		this.receiverCode = receiverCode;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public OrderState getState()
	{
		return state;
	}

	public void setState(OrderState state)
	{
		this.state = state;
	}

	public TypeOrder getType()
	{
		return type;
	}

	public void setType(TypeOrder type)
	{
		this.type = type;
	}

	public String getUtrrno()
	{
		return utrrno;
	}

	public void setUtrrno(String utrrno)
	{
		this.utrrno = utrrno;
	}

	public OrderKind getKind()
	{
		return kind;
	}

	public void setKind(OrderKind kind)
	{
		this.kind = kind;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public boolean isMobileCheckout()
	{
		return mobileCheckout;
	}

	public Calendar getDelayedPayDate()
	{
		return delayedPayDate;
	}

	public void setDelayedPayDate(Calendar delayedPayDate)
	{
		this.delayedPayDate = delayedPayDate;
	}

	public void setMobileCheckout(boolean mobileCheckout)
	{
		this.mobileCheckout = mobileCheckout;
	}

	public Boolean getIsNew()
	{
		return isNew;
	}

	public void setIsNew(Boolean aNew)
	{
		isNew = aNew;
	}

	public String getFacilitatorProviderCode()
	{
		return facilitatorProviderCode;
	}

	public void setFacilitatorProviderCode(String facilitatorProviderCode)
	{
		this.facilitatorProviderCode = facilitatorProviderCode;
	}
}
