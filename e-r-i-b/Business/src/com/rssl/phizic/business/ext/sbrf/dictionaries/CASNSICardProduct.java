package com.rssl.phizic.business.ext.sbrf.dictionaries;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.utils.BeanHelper;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 *
 * Сущность, реализующая загруженную из справочника ЦАС НСИ карту
 */

public class CASNSICardProduct extends DictionaryRecordBase
{
	private Long id;                    // идентификатор
	private String name;                // название карточного продукта из справочника
	private Long productId;             // вид
	private Long productSubId;          // подвид
	private Calendar stopOpenDeposit;   // дата прекращения открытия вкладов
	private Currency currency;          // валюта
	private Calendar lastUpdateDate;    // последняя дата изменения в бд

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Long getProductSubId()
	{
		return productSubId;
	}

	public void setProductSubId(Long productSubId)
	{
		this.productSubId = productSubId;
	}

	public Calendar getStopOpenDeposit()
	{
		return stopOpenDeposit;
	}

	public void setStopOpenDeposit(Calendar stopOpenDeposit)
	{
		this.stopOpenDeposit = stopOpenDeposit;
	}

	public Currency getCurrency()
	{
		return currency;
	}

	public void setCurrency(Currency currency)
	{
		this.currency = currency;
	}

	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * Ключ для сравнения объектов: содержит в себе productId-productSubId-код валюты
	 * @return ключ синхронизации
	 */
    public Comparable getSynchKey()
	{
		class CASNSICSynchKey implements Comparable<CASNSICSynchKey>
		{
			   private CASNSICSynchKey() {}

			   public  int compareTo(CASNSICSynchKey o)
			   {
				   String synchKey = this.getSynchKey();
				   if (synchKey.equals(o.getSynchKey()))
					   return 0;
				   return -1;
			   }

			   public String toString()
			   {
				   return getSynchKey();
			   }

			   private String getSynchKey()
			   {
				   return productId + "-" + productSubId + "-" + currency.getCode();
			   }
		}

		return new CASNSICSynchKey();
	}
}
