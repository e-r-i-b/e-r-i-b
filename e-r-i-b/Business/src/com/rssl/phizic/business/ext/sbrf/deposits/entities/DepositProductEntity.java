package com.rssl.phizic.business.ext.sbrf.deposits.entities;


import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;

import java.math.BigDecimal;
import java.util.*;

/**
 * Сущность, описывающая вкладной продукт
 *
 * @author EgorovaA
 * @ created 31.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductEntity implements Cloneable
{
	private String id;
	// тип вклада
	private Long depositType;
	// код группы
	private Long groupCode;
	// название группы
	private String groupName;

	private Boolean initialFee;
	private Boolean withMinimumBalance;
	private Boolean allowPensionRates;
	private Boolean promoCodeSupported;
	private Boolean creditOperations;
	private Boolean debitOperations;
	private Boolean interestOperations;
	private Long debitOperationsCode;

	// промо-код вкладного продукта
	private PromoCodeDeposit promoCodeDeposit;
	// актуальные ставки вкладного продукта (Map<Валюта, Map<Мин. сумма, Map<Период, DepositProductRate>>>)
	private Map<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> depositRates;
	// описание "строк" в списке вкладов
	private List<DepositDescriptionRow> depositDescriptions;
	// периоды
	private List<String> periodList;

	public DepositProductEntity clone()
	{
		try
		{
			return (DepositProductEntity) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Long getDepositType()
	{
		return depositType;
	}

	public void setDepositType(Long depositType)
	{
		this.depositType = depositType;
	}

	public Long getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(Long groupCode)
	{
		this.groupCode = groupCode;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public Boolean getInitialFee()
	{
		return initialFee;
	}

	public void setInitialFee(Boolean initialFee)
	{
		this.initialFee = initialFee;
	}

	public Boolean getWithMinimumBalance()
	{
		return withMinimumBalance;
	}

	public void setWithMinimumBalance(Boolean withMinimumBalance)
	{
		this.withMinimumBalance = withMinimumBalance;
	}

	public Boolean getAllowPensionRates()
	{
		return allowPensionRates;
	}

	public void setAllowPensionRates(Boolean allowPensionRates)
	{
		this.allowPensionRates = allowPensionRates;
	}

	public Boolean getPromoCodeSupported()
	{
		return promoCodeSupported;
	}

	public void setPromoCodeSupported(Boolean promoCodeSupported)
	{
		this.promoCodeSupported = promoCodeSupported;
	}

	public Boolean getCreditOperations()
	{
		return creditOperations;
	}

	public void setCreditOperations(Boolean creditOperations)
	{
		this.creditOperations = creditOperations;
	}

	public Boolean getDebitOperations()
	{
		return debitOperations;
	}

	public void setDebitOperations(Boolean debitOperations)
	{
		this.debitOperations = debitOperations;
	}

	public Boolean getInterestOperations()
	{
		return interestOperations;
	}

	public void setInterestOperations(Boolean interestOperations)
	{
		this.interestOperations = interestOperations;
	}

	public Long getDebitOperationsCode()
	{
		return debitOperationsCode;
	}

	public void setDebitOperationsCode(Long debitOperationsCode)
	{
		this.debitOperationsCode = debitOperationsCode;
	}

	public PromoCodeDeposit getPromoCodeDeposit()
	{
		return promoCodeDeposit;
	}

	public void setPromoCodeDeposit(PromoCodeDeposit promoCodeDeposit)
	{
		this.promoCodeDeposit = promoCodeDeposit;
	}

	public Map<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> getDepositRates()
	{
		return depositRates;
	}

	public void setDepositRates(Map<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> depositRates)
	{
		this.depositRates = depositRates;
	}

	public List<DepositDescriptionRow> getDepositDescriptions()
	{
		return depositDescriptions;
	}

	public void setDepositDescriptions(List<DepositDescriptionRow> depositDescriptions)
	{
		this.depositDescriptions = depositDescriptions;
	}

	public List<String> getPeriodList()
	{
		return periodList;
	}

	public void setPeriodList(List<String> periodList)
	{
		this.periodList = periodList;
	}

}
