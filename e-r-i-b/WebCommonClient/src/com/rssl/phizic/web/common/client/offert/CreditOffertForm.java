package com.rssl.phizic.web.common.client.offert;

import com.rssl.phizgate.ext.sbrf.etsm.OfferOfficePrior;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.etsm.offer.OfferEribPrior;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author Moshenko
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CreditOffertForm extends EditFormBase
{
	//Оферты по заявкам не ериб
	private List<OfferOfficePrior> offertOffice;
	//Оферты по заявкам ериб
	private List<OfferEribPrior> offertErib;
	//Id кредитной заявки
	private Long claimId;
	//Id шаблона оферты
	private Long offertTemplateId;
	//срок кредита
	private Long duration;
	//универсальный  идентификатор оферты
	private String appNum;
	 //Обеспечение
	private Boolean ensuring;
	 // Имя кредитного продукта
	private  String productName;
	 // Счет зачисления
	private String enrollAccount;
	//Информация по офису
	private Department claimDrawDepartment;
	//текст соглашения на оферту
	private String agreementOffertText;
	//соголасие с условияем
	private boolean agree;
	//Шаблон оферты
	private CreditOfferTemplate offertTemplate;
	// Шаблон пдп оферты
	private CreditOfferTemplate pdpOfferTemplate;
	//Признак использования одноразового пароля при входе
	private boolean oneTimePassword;
	//Id выбранной оферты
	private Long offerId;

	private ConfirmStrategy confirmStrategy;
	private ConfirmableObject confirmableObject;
	private LoanOffer loanOffer;

	public List<OfferEribPrior> getOffertErib()
	{
		return offertErib;
	}

	public void setOffertErib(List<OfferEribPrior> offertErib)
	{
		this.offertErib = offertErib;
	}

	public List<OfferOfficePrior> getOffertOffice()
	{
		return offertOffice;
	}

	public void setOffertOffice(List<OfferOfficePrior> offertOffice)
	{
		this.offertOffice = offertOffice;
	}

	public Boolean isEnsuring()
	{
		return ensuring;
	}

	public void setEnsuring(Boolean ensuring)
	{
		this.ensuring = ensuring;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProductName()
	{
		return productName;
	}

	public String getEnrollAccount()
	{
		return enrollAccount;
	}

	public void setEnrollAccount(String enrollAccount)
	{
		this.enrollAccount = enrollAccount;
	}

	public Department getClaimDrawDepartment()
	{
		return claimDrawDepartment;
	}

	public void setClaimDrawDepartment(Department claimDrawDepartment)
	{
		this.claimDrawDepartment = claimDrawDepartment;
	}

	public String getAgreementOffertText()
	{
		return agreementOffertText;
	}

	public void setAgreementOffertText(String agreementOffertText)
	{
		this.agreementOffertText = agreementOffertText;
	}

	public CreditOfferTemplate getOffertTemplate()
	{
		return offertTemplate;
	}

	public void setOffertTemplate(CreditOfferTemplate offertTemplate)
	{
		this.offertTemplate = offertTemplate;
	}

	public boolean isAgree()
	{
		return agree;
	}

	public void setAgree(boolean agree)
	{
		this.agree = agree;
	}

	public String getAppNum()
	{
		return appNum;
	}

	public void setAppNum(String appNum)
	{
		this.appNum = appNum;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}

	public Long getOffertTemplateId()
	{
		return offertTemplateId;
	}

	public void setOffertTemplateId(Long offertTemplateId)
	{
		this.offertTemplateId = offertTemplateId;
	}

	public boolean isOneTimePassword()
	{
		return oneTimePassword;
	}

	public void setOneTimePassword(boolean oneTimePassword)
	{
		this.oneTimePassword = oneTimePassword;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}


	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public Long getOfferId()
	{
		return offerId;
	}

	public void setOfferId(Long offerId)
	{
		this.offerId = offerId;
	}

	public void setLoanOffer(LoanOffer loanOffer)
	{
		this.loanOffer = loanOffer;
	}

	public LoanOffer getLoanOffer()
	{
		return loanOffer;
	}

	public CreditOfferTemplate getPdpOfferTemplate()
	{
		return pdpOfferTemplate;
	}

	public void setPdpOfferTemplate(CreditOfferTemplate pdpOfferTemplate)
	{
		this.pdpOfferTemplate = pdpOfferTemplate;
	}
}
