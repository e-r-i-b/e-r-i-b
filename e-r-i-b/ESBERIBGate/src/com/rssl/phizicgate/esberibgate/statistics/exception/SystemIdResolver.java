package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 22.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Класс вытаскивающий systemId из IFXRq
 */

@SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
public class SystemIdResolver
{
	private String systemId;
	//ACC_DI
	private SystemIdResolver acctInfoRq;
	private SystemIdResolver depAcctRec;
	private SystemIdResolver depAcctId;
	//IRB
	private SystemIdResolver acctInqRq;
	private SystemIdResolver bankAcctId;
	//DAS_s
	private SystemIdResolver depAcctStmtInqRq;
//	private SystemIdResolver depAcctRec;
//	private SystemIdResolver depAcctId;
	//IMA_IS_s
	private SystemIdResolver imaAcctInRq;
	private SystemIdResolver bankAcctRec;
	private SystemIdResolver imsAcctId;
	//IAS_s
	private SystemIdResolver bankAcctStmtInqRq;
//	private SystemIdResolver bankAcctRec;
//	private SystemIdResolver bankAcctId;
	//IAS_F
	private SystemIdResolver bankAcctFullStmtInqRq;
//	private SystemIdResolver bankAcctRec;
//	private SystemIdResolver bankAcctId;
	//CRDWI
	private SystemIdResolver cardAcctDInqRq;
	private SystemIdResolver cardInfo;
	private SystemIdResolver cardAcctId;
	//SCREP
	private SystemIdResolver bankAcctStmtImgInqRq;
//	private SystemIdResolver cardAcctId;
	//IIC
	private SystemIdResolver loanInqRq;
	private SystemIdResolver loanAcctId;
	//LN_PSC
	private SystemIdResolver loanPaymentRq;
//	private SystemIdResolver loanAcctId;
	//DEPO_DS
	private SystemIdResolver depoDeptsInfoRq;
	private SystemIdResolver depoAcctId;
	//DEPO_DDS
	private SystemIdResolver depoDeptDetInfoRq;
//	private SystemIdResolver depoAcctId;
	//DEPO_DEPTP
	private SystemIdResolver depoDeptCardPayRq;
	private SystemIdResolver payInfo;
//	private SystemIdResolver cardAcctId;
	private SystemIdResolver acctId;
	//DEPO_AC
	private SystemIdResolver depoAccSecInfoRq;
//	private SystemIdResolver depoAcctId;
	//DEPO_SRD
	private SystemIdResolver depoAccTranRq;
//	private SystemIdResolver depoAcctId;
	//DEPO_RNS
	private SystemIdResolver depoAccSecRegRq;
//	private SystemIdResolver depoAcctId;
	//DEPO_AR
	private SystemIdResolver depoArRq;
//	private SystemIdResolver depoAcctId;
	//TCD, TDD (TID,TDI), TDC, TCP, ТСС, TDDC, TDСC
	private SystemIdResolver xferAddRq;
	private SystemIdResolver xferInfo;
	private SystemIdResolver cardAcctIdFrom;
	private SystemIdResolver depAcctIdFrom;
	//SDP, SDС
	private SystemIdResolver svcAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver depAcctIdFrom;
	//ESK_SAS
	private SystemIdResolver serviceStmtRq;
	private SystemIdResolver svcAcctId;
	//COD_GSI
	private SystemIdResolver svcAcctAudRq;
	private SystemIdResolver svcAcct;
//	private SystemIdResolver svcAcctId;
	//COD_ASD
	private SystemIdResolver svcAcctDelRq;
//	private SystemIdResolver svcAcct;
//	private SystemIdResolver svcAcctId;
	//ACC_STP
	private SystemIdResolver accStopDocRq;
	private SystemIdResolver docInfo;
//	private SystemIdResolver depAcctId;
	//CRBLOCK
	private SystemIdResolver cardBlockRq;
//	private SystemIdResolver cardAcctId;
	//TDDO
	private SystemIdResolver depToNewDepAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver depAcctIdFrom;
	//TCDO
	private SystemIdResolver cardToNewDepAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver cardAcctIdFrom;
	//SACS
	private SystemIdResolver setAccountStateRq;
//	private SystemIdResolver depAcctId;
	//TDIO
	private SystemIdResolver depToNewIMAAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver depAcctIdFrom;
	//TCIO
	private SystemIdResolver cardToNewIMAAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver cardAcctIdFrom;
	//TCI
	private SystemIdResolver cardToIMAAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver cardAcctIdFrom;
	//TIC
	private SystemIdResolver IMAToCardAddRq;
//	private SystemIdResolver xferInfo;
//	private SystemIdResolver IMAAcctIdFrom;
	//CRP
	private SystemIdResolver cardReissuePlaceRq;
	//SrvBSGetInfo
	private SystemIdResolver securitiesInfoInqRq;
	//SrvChangeAccountInfo
	private SystemIdResolver changeAccountInfoRq;
//	private SystemIdResolver depAcctId;

	//TBP_PR
	private SystemIdResolver billingPayPrepRq;
	//TBP_PR
	private SystemIdResolver billingPayExecRq;

	//Запрос интерфейса LN_CI получения детальной информации по кредиту
	private SystemIdResolver loanInfoRq;
	//Получение информации по счетам ДЕПО
	private SystemIdResolver depoAccInfoRq;

	/**
	 * @return идентификатор системы
	 */
	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	private String getEsbSystemCode()
	{
		return ExternalSystemHelper.getESBSystemCode();
	}

	private void setBean(SystemIdResolver[] beans)
	{
		if (beans == null)
			return;
		if (beans.length == 1)
			setBean(beans[0]);
		else
			systemId = getEsbSystemCode();
	}

	private void setBean(SystemIdResolver bean)
	{
		if (bean == null)
			return;
		if (StringHelper.isEmpty(systemId))
			systemId = bean.getSystemId();
		else
			systemId = getEsbSystemCode();
	}

	public void setChangeAccountInfoRq(SystemIdResolver changeAccountInfoRq)
	{
		setBean(changeAccountInfoRq);
	}

	public void setAcctInfoRq(SystemIdResolver acctInfoRq)
	{
		setBean(acctInfoRq);
	}

	public void setBankAcctId(SystemIdResolver bankAcctId)
	{
		setBean(bankAcctId);
	}

	public void setDepAcctId(SystemIdResolver depAcctId)
	{
		setBean(depAcctId);
	}

	public void setCardAcctId(SystemIdResolver cardAcctId)
	{
		setBean(cardAcctId);
	}

	public void setAcctInqRq(SystemIdResolver acctInqRq)
	{
		setBean(acctInqRq);
	}

	public void setDepAcctRec(SystemIdResolver depAcctRec)
	{
		setBean(depAcctRec);
	}

	public void setDepAcctRec(SystemIdResolver[] depAcctRec)
	{
		setBean(depAcctRec);
	}

	public void setDepAcctStmtInqRq(SystemIdResolver depAcctStmtInqRq)
	{
		setBean(depAcctStmtInqRq);
	}

	public void setImaAcctInRq(SystemIdResolver imaAcctInRq)
	{
		setBean(imaAcctInRq);
	}

	public void setBankAcctRec(SystemIdResolver bankAcctRec)
	{
		setBean(bankAcctRec);
	}

	public void setBankAcctRec(SystemIdResolver[] bankAcctRec)
	{
		setBean(bankAcctRec);
	}

	public void setImsAcctId(SystemIdResolver imsAcctId)
	{
		setBean(imsAcctId);
	}

	public void setBankAcctStmtInqRq(SystemIdResolver bankAcctStmtInqRq)
	{
		setBean(bankAcctStmtInqRq);
	}

	public void setBankAcctFullStmtInqRq(SystemIdResolver bankAcctFullStmtInqRq)
	{
		setBean(bankAcctFullStmtInqRq);
	}

	public void setCardAcctDInqRq(SystemIdResolver cardAcctDInqRq)
	{
		setBean(cardAcctDInqRq);
	}

	public void setCardInfo(SystemIdResolver[] cardInfo)
	{
		setBean(cardInfo);
	}

	public void setBankAcctStmtImgInqRq(SystemIdResolver bankAcctStmtImgInqRq)
	{
		setBean(bankAcctStmtImgInqRq);
	}

	public void setLoanInqRq(SystemIdResolver loanInqRq)
	{
		setBean(loanInqRq);
	}

	public void setLoanAcctId(SystemIdResolver loanAcctId)
	{
		setBean(loanAcctId);
	}

	public void setLoanPaymentRq(SystemIdResolver loanPaymentRq)
	{
		setBean(loanPaymentRq);
	}

	public void setDepoDeptsInfoRq(SystemIdResolver depoDeptsInfoRq)
	{
		setBean(depoDeptsInfoRq);
	}

	public void setDepoAcctId(SystemIdResolver[] depoAcctId)
	{
		setBean(depoAcctId);
	}

	public void setDepoDeptDetInfoRq(SystemIdResolver depoDeptDetInfoRq)
	{
		setBean(depoDeptDetInfoRq);
	}

	public void setDepoDeptCardPayRq(SystemIdResolver depoDeptCardPayRq)
	{
		setBean(depoDeptCardPayRq);
	}

	public void setPayInfo(SystemIdResolver payInfo)
	{
		setBean(payInfo);
	}

	public void setAcctId(SystemIdResolver acctId)
	{
		setBean(acctId);
	}

	public void setAcctId(String acctId)
	{}

	public void setDepoAccSecInfoRq(SystemIdResolver depoAccSecInfoRq)
	{
		setBean(depoAccSecInfoRq);
	}

	public void setDepoAccTranRq(SystemIdResolver depoAccTranRq)
	{
		setBean(depoAccTranRq);
	}

	public void setDepoAcctId(SystemIdResolver depoAcctId)
	{
		setBean(depoAcctId);
	}

	public void setDepoAccSecRegRq(SystemIdResolver depoAccSecRegRq)
	{
		setBean(depoAccSecRegRq);
	}

	public void setDepoArRq(SystemIdResolver depoArRq)
	{
		setBean(depoArRq);
	}

	public void setXferAddRq(SystemIdResolver xferAddRq)
	{
		setBean(xferAddRq);
	}

	public void setXferInfo(SystemIdResolver xferInfo)
	{
		setBean(xferInfo);
	}

	public void setCardAcctIdFrom(SystemIdResolver cardAcctIdFrom)
	{
		setBean(cardAcctIdFrom);
	}

	public void setDepAcctIdFrom(SystemIdResolver depAcctIdFrom)
	{
		setBean(depAcctIdFrom);
	}

	public void setSvcAddRq(SystemIdResolver svcAddRq)
	{
		setBean(svcAddRq);
	}

	public void setServiceStmtRq(SystemIdResolver serviceStmtRq)
	{
		setBean(serviceStmtRq);
	}

	public void setSvcAcctId(SystemIdResolver svcAcctId)
	{
		setBean(svcAcctId);
	}

	public void setSvcAcctAudRq(SystemIdResolver svcAcctAudRq)
	{
		setBean(svcAcctAudRq);
	}

	public void setSvcAcct(SystemIdResolver[] svcAcct)
	{
		setBean(svcAcct);
	}

	public void setSvcAcctDelRq(SystemIdResolver svcAcctDelRq)
	{
		setBean(svcAcctDelRq);
	}

	public void setSvcAcct(SystemIdResolver svcAcct)
	{
		setBean(svcAcct);
	}

	public void setAccStopDocRq(SystemIdResolver accStopDocRq)
	{
		setBean(accStopDocRq);
	}

	public void setDocInfo(SystemIdResolver docInfo)
	{
		setBean(docInfo);
	}

	public void setCardBlockRq(SystemIdResolver cardBlockRq)
	{
		setBean(cardBlockRq);
	}

	public void setDepToNewDepAddRq(SystemIdResolver depToNewDepAddRq)
	{
		setBean(depToNewDepAddRq);
	}

	public void setCardToNewDepAddRq(SystemIdResolver cardToNewDepAddRq)
	{
		setBean(cardToNewDepAddRq);
	}

	public void setSetAccountStateRq(SystemIdResolver setAccountStateRq)
	{
		setBean(setAccountStateRq);
	}

	public void setDepToNewIMAAddRq(SystemIdResolver depToNewIMAAddRq)
	{
		setBean(depToNewIMAAddRq);
	}

	public void setCardToNewIMAAddRq(SystemIdResolver cardToNewIMAAddRq)
	{
		setBean(cardToNewIMAAddRq);
	}

	public void setCardToIMAAddRq(SystemIdResolver cardToIMAAddRq)
	{
		setBean(cardToIMAAddRq);
	}

	public void setIMAToCardAddRq(SystemIdResolver IMAToCardAddRq)
	{
		setBean(IMAToCardAddRq);
	}

	public void setCardReissuePlaceRq(SystemIdResolver cardReissuePlaceRq)
	{
		setBean(cardReissuePlaceRq);
	}

	public void setSecuritiesInfoInqRq(SystemIdResolver securitiesInfoInqRq)
	{
		setBean(securitiesInfoInqRq);
	}

	public void setBillingPayPrepRq(SystemIdResolver billingPayPrepRq)
	{
		setBean(billingPayPrepRq);
	}

	public void setBillingPayExecRq(SystemIdResolver billingPayExecRq)
	{
		setBean(billingPayExecRq);
	}

	public void setLoanInfoRq(SystemIdResolver loanInfoRq)
	{
		setBean(loanInfoRq);
	}

	public void setDepoAccInfoRq(SystemIdResolver depoAccInfoRq)
	{
		setBean(depoAccInfoRq);
	}
}
