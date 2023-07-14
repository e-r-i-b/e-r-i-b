package com.rssl.phizicgate.rsV55.demand;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 04.04.2007
 * Time: 11:08:15
 * To change this template use File | Settings | File Templates.
 */
public class Remittee
{
	private ClaimId id;
    private Long branch;
	private String applicationKey;
	private Long applicationKind;
	private String attributID;
	private Long refValue = 0L;
    private String ground;
    private String bic;
	private String bank;
	private String corAccount;
    private Long bankCode;
	private String payerInn;
	private Long notResident= 0L;
	private String receiverAccount;
	private String receiverInn;
	private String receiverKpp;
	private String receiverName;
	private String receiverCountryCode; // 3х значный код страны получателя
    private String KBK;
    private String OKATO;
    private String taxAssignment;// Основание налогового платежа
    private String taxPeriod;// Налоговый период
    private String taxDocumentNumber;// Номер налогового документа
    private Date taxDocumentDate;// Дата налогового документа
    private String taxPaymentType;
    private String taxAuthorState;
    private Long priority;// Очередность платежа
	private BigDecimal sumNS;
	private BigDecimal sumAV;
	private BigDecimal sumPE;
	private BigDecimal sumPC;
	private BigDecimal sumSA;
	private BigDecimal sumASh;
	private BigDecimal sumISh;

	public BigDecimal getSumASh()
	{
		return sumASh;
	}

	public void setSumASh(BigDecimal sumASh)
	{
		this.sumASh = sumASh;
	}

	public BigDecimal getSumAV()
	{
		return sumAV;
	}

	public void setSumAV(BigDecimal sumAV)
	{
		this.sumAV = sumAV;
	}

	public BigDecimal getSumISh()
	{
		return sumISh;
	}

	public void setSumISh(BigDecimal sumISh)
	{
		this.sumISh = sumISh;
	}

	public BigDecimal getSumNS()
	{
		return sumNS;
	}

	public void setSumNS(BigDecimal sumNS)
	{
		this.sumNS = sumNS;
	}

	public BigDecimal getSumPC()
	{
		return sumPC;
	}

	public void setSumPC(BigDecimal sumPC)
	{
		this.sumPC = sumPC;
	}

	public BigDecimal getSumPE()
	{
		return sumPE;
	}

	public void setSumPE(BigDecimal sumPE)
	{
		this.sumPE = sumPE;
	}

	public BigDecimal getSumSA()
	{
		return sumSA;
	}

	public void setSumSA(BigDecimal sumSA)
	{
		this.sumSA = sumSA;
	}

	public ClaimId getId()
	{
		return id;
	}

	public void setId(ClaimId id)
	{
		this.id = id;
		this.applicationKey = (id != null) ? id.getApplicationKey() : "";
		this.applicationKind = (id != null) ? id.getApplicationKind() : 0L;
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getAttributID()
	{
		return attributID;
	}

	public void setAttributID(String attributID)
	{
		this.attributID = attributID;
	}

	public Long getBranch()
	{
		return branch;
	}

	public void setBranch(Long branch)
	{
		this.branch = branch;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public Long getApplicationKind()
	{
		return applicationKind;
	}

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public Long getRefValue()
	{
		return refValue;
	}

	public void setRefValue(Long refValue)
	{
		this.refValue = refValue;
	}

	public String getBic()
	{
		return bic;
	}

	public void setBic(String bic)
	{
		this.bic = bic;
	}

	public String getBank()
	{
		return bank;
	}

	public void setBank(String bank)
	{
		this.bank = bank;
	}

	public String getCorAccount()
	{
		return corAccount;
	}

	public void setCorAccount(String corAccount)
	{
		this.corAccount = corAccount;
	}

	public Long getBankCode()
	{
		return bankCode;
	}

	public void setBankCode(Long bankCode)
	{
		this.bankCode = bankCode;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverInn()
	{
		return receiverInn;
	}

	public void setReceiverInn(String receiverInn)
	{
		this.receiverInn = receiverInn;
	}

	public String getReceiverKpp()
	{
		return receiverKpp;
	}

	public void setReceiverKpp(String receiverKpp)
	{
		this.receiverKpp = receiverKpp;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * @return код страны получателя
	 */
	 public String getReceiverCountryCode()
	 {
		 return receiverCountryCode;
	 }
	 /**
	 * @param receiverCountryCode код страны получателя
	 */
	 public void setReceiverCountryCode(String receiverCountryCode)
	 {
		 this.receiverCountryCode = receiverCountryCode;
	 }

	public String getKBK()
	{
		return KBK;
	}

	public void setKBK(String KBK)
	{
		this.KBK = KBK;
	}

	public String getOKATO()
	{
		return OKATO;
	}

	public void setOKATO(String OKATO)
	{
		this.OKATO = OKATO;
	}

	public String getTaxAssignment()
	{
		return taxAssignment;
	}

	public void setTaxAssignment(String taxAssignment)
	{
		this.taxAssignment = taxAssignment;
	}

	public String getTaxPeriod()
	{
		return taxPeriod;
	}

	public void setTaxPeriod(String taxPeriod)
	{
		this.taxPeriod = taxPeriod;
	}

	public String getTaxDocumentNumber()
	{
		return taxDocumentNumber;
	}

	public void setTaxDocumentNumber(String taxDocumentNumber)
	{
		this.taxDocumentNumber = taxDocumentNumber;
	}

	public Date getTaxDocumentDate()
	{
		return taxDocumentDate;
	}

	public void setTaxDocumentDate(Date taxDocumentDate)
	{
		this.taxDocumentDate = taxDocumentDate;
	}

	public String getTaxPaymentType()
	{
		return taxPaymentType;
	}

	public void setTaxPaymentType(String taxPaymentType)
	{
		this.taxPaymentType = taxPaymentType;
	}

	public String getTaxAuthorState()
	{
		return taxAuthorState;
	}

	public void setTaxAuthorState(String taxAuthorState)
	{
		this.taxAuthorState = taxAuthorState;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public String getPayerInn()
	{
		return payerInn;
	}

	public void setPayerInn(String payerInn)
	{
		this.payerInn = payerInn;
	}

	/**
	*  признак резидентности
	* @return резидентность
	*/
   public Long getNotResident()
   {
	   return notResident;
   }
	/**
	 *  признак резидентности
	 * 88 - в Реетйле это соответствует rt_paym_1.notRees = 'X' (тип char(1))
	 * 0  - в Реетйле это соответствует rt_paym_1.notRees = 0 (тип char(1))
	 * @param notResident резидентность
	 */
	public void setNotResident(Long notResident)
	{
		this.notResident = notResident;
	}
    /**
	 *  признак резидентности
	 * 88 - в Реетйле это соответствует rt_paym_1.notRees = 'X' (тип char(1))
	 * 0  - в Реетйле это соответствует rt_paym_1.notRees = 0 (тип char(1))
	 * @param notResident резидентность
	 */
	public void setNotResident(boolean notResident)
	{
		if (notResident)
		   this.notResident = 88L;
		else
		   this.notResident = 0L;
	}
}
