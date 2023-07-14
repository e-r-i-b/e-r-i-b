package com.rssl.phizic.business.ermb.bankroll.config;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlType(name = "Condition")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductRuleConditionBean
{
	@XmlElementWrapper(name = "terbanks", required = true)
    @XmlElement(name = "terbank")
	private List<String> terbanks;

	@XmlElement(name = "clientCategory")
	private ClientCategoryType clientCategory;

	@XmlElement(name = "age", required = true)
	private Age age;

	@XmlType(name = "AgeType")
	@XmlAccessorType(XmlAccessType.NONE)
	static class Age
	{
        @XmlElement(name = "from")
        private Integer fromValue;

		@XmlElement(name = "until")
        private Integer untilValue;

		Age()
		{
		}

		Age(Integer fromValue, Integer untilValue)
		{
			this.fromValue = fromValue;
			this.untilValue = untilValue;
		}

		public Integer getFromValue()
        {
            return fromValue;
        }

		public void setFromValue(Integer fromValue)
		{
			this.fromValue = fromValue;
		}

		Integer getUntilValue()
		{
			return untilValue;
		}

		void setUntilValue(Integer untilValue)
		{
			this.untilValue = untilValue;
		}
	}

	@XmlElement(name = "bankroll-products", required = true)
    private BankrollProductCriteriaBean bankrollProductCriteria;

	List<String> getTerbanks()
	{
		return terbanks;
	}

	void setTerbanks(List<String> terbanks)
	{
		this.terbanks = terbanks;
	}

	ClientCategoryType getClientCategory()
	{
		return clientCategory;
	}

	void setClientCategory(ClientCategoryType clientCategory)
	{
		this.clientCategory = clientCategory;
	}

	Age getAge()
	{
		return age;
	}

	void setAge(Age age)
	{
		this.age = age;
	}

	BankrollProductCriteriaBean getBankrollProductCriteria()
	{
		return bankrollProductCriteria;
	}

	void setBankrollProductCriteria(BankrollProductCriteriaBean bankrollProductCriteria)
	{
		this.bankrollProductCriteria = bankrollProductCriteria;
	}
}
