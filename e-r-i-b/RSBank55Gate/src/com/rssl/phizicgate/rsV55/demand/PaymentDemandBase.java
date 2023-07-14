package com.rssl.phizicgate.rsV55.demand;

import java.math.BigDecimal;
import java.util.Date;

//TODO Странная иерархия!! переделать!!
/**
 * @author Krenev
 * @ created 22.08.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentDemandBase
{
	private ClaimId id;
	private String applicationKey;
	private Long applicationKind;
	private Long codeCurrency;
	private Long codeCurReceiver;
	private Long oper;
	private Long typeOper;
	private Long referenc;
	private String account;
	private Long codClient;
	private String typeAccount;
	private Long department;
	private String accountReceiver;
	private String corAccReceiver;
	private Long officeReceiver;
	private String destinationDepositAccount;
	private Long isCur;
	private Long kindOp;
	private Date dateDocument;
	private String flagRezid;
	private BigDecimal summaIn;
	private BigDecimal summaOut;
	private String ground;
	private Long complexOperationType;
	private Long NDoc;
	private Long NPack;

	public ClaimId getId()
	{
		return id;
	}

	public void setId(ClaimId id)
	{
		this.id = id;
		this.applicationKey = id.getApplicationKey();
		this.applicationKind = id.getApplicationKind();
	}

	public String getApplicationKey()
	{
		return this.applicationKey;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public Long getApplicationKind()
	{
		return this.applicationKind;
	}

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public Long getCurrencyCode()
	{
		return this.codeCurrency;
	}

	public void setCurrencyCode(Long codeCurrency)
	{
		this.codeCurrency=codeCurrency;
	}

	public Long getReceiverCurrencyCode()
		{
			return this.codeCurReceiver;
		}

	public void setReceiverCurrencyCode(Long codeCurReceiver)
	{
		this.codeCurReceiver = codeCurReceiver;
	}

	public Long getOper()
	{
		return this.oper;
	}

	public void setOper(Long oper)
	{
		this.oper=oper;
	}

	public Long getOperationType()
	{
		return this.typeOper;
	}

	public void setOperationType(Long typeOper)
	{
		this.typeOper = typeOper;
	}

	public Long getReferenc()
	{
		return this.referenc;
	}

	public void setReferenc(Long referenc)
	{
		this.referenc=referenc;
	}

	public String getAccount()
	{
		return this.account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public Long getClientCode()
	{
		return this.codClient;
	}

	public void setClientCode(Long codClient)
	{
		this.codClient = codClient;
	}

	public Long getDepartment()
	{
		return this.department;
	}

	public void setDepartment(Long department)
	{
		this.department=department;
	}

	public String getAccountType()
	{
		return this.typeAccount;
	}

	public void setAccountType(String typeAccount)
		{
			this.typeAccount = typeAccount;
		}

	public String getReceiverAccount()
	{
		return this.accountReceiver;
	}

	public void setReceiverAccount(String accountReceiver)
	{
		this.accountReceiver = accountReceiver;
	}

	public String getReceiverCorAccount()
	{
		return this.corAccReceiver;
	}

	public void setReceiverCorAccount(String corAccReceiver)
	{
		this.corAccReceiver = corAccReceiver;
	}

	public Long getReceiverOffice()
	{
		return officeReceiver;
	}

	public void setReceiverOffice(Long officeReceiver)
	{
		this.officeReceiver = officeReceiver;
	}

	public String getDestinationDepositAccount()
	{
		return destinationDepositAccount;
	}

	public void setDestinationDepositAccount(String destinationDepositAccount)
	{
		this.destinationDepositAccount = destinationDepositAccount;
	}

	public Long getIsCur()
	{
		return this.isCur;
	}

	public void setIsCur(Long isCur)
	{
		this.isCur = isCur;
	}

	public void setIsCur(boolean isCur)
	{
		if (isCur)
		   this.isCur = 1L;
		else
		   this.isCur = 0L;
	}

	public Long getOperationKind()
	{
		return this.kindOp;
	}

	public void setOperationKind(Long kindOp)
	{
		this.kindOp = kindOp;
	}

	public void setDocumentDate(Date dateDocument)
	{
		this.dateDocument = dateDocument;
	}

	public Date getDocumentDate()
	{
		return this.dateDocument;
	}
	public void setIsResident(String flagRezid)
	{
		this.flagRezid = flagRezid;
	}

	public void setIsResident(boolean flagRezid)
	{
 //TODO: В retail всегда true=='X' false=''? Может тогда наподобие? И замапить как boolean
// Booleans may be easily used in expressions by declaring HQL query substitutions in Hibernate configuration:
// <property name = "hibernate.query.substitutions" > true 1, false 0 </property >
		if (flagRezid)
		   this.flagRezid = "X";
		else
		   this.flagRezid = "";
	}

	public String getIsResident()
	{
		return this.flagRezid;
	}

	public BigDecimal getSummaIn()
	{
		return summaIn;
	}

	public void setSummaIn(BigDecimal summaIn)
	{
		this.summaIn = summaIn;
	}

	public BigDecimal getSummaOut()
	{
		return summaOut;
	}

	public void setSummaOut(BigDecimal summaOut)
	{
		this.summaOut = summaOut;
	}

	public String getGround()
	{
		return this.ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public Long getComplexOperationType()
	{
		return complexOperationType;
	}

	public void setComplexOperationType(Long complexOperationType)
	{
		this.complexOperationType = complexOperationType;
	}

	public Long getNDoc()
	{
		return NDoc;
	}

	public void setNDoc(Long NDoc)
	{
		this.NDoc = NDoc;
	}

	public Long getNPack()
	{
		return NPack;
	}

	public void setNPack(Long NPack)
	{
		this.NPack = NPack;
	}
}
