package com.rssl.phizic.business.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author tisov
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 * Псевдо-сущность для отображения инвойсов и интернет-заказов на форме "счета к оплате"
 */

public class FakeInvoice implements Serializable
{
	private Long id;                //идентификатор экземпляра (ид инвойса или внешний ид заказа)
	private String name;            //имя
	private InvoiceStatus state;    //состояние
	private String requisites;      //реквизиты
	private Calendar creatingDate;  //дата создания
	private String entityName;      //имя объекта учёта (только для инвойса)
	private String providerName;    //имя поставщика
	private Long imageId;           //идентификатор иконки
	private String externalId;
	private Calendar delayedDate;   //дата отложеннсти

	private String keyName;         //имя ключевого поля
	private String keyValue;        //значение ключевого поля
	private BigDecimal sum;               //сумма
	private String type;            //тип экземпляра ("invoice" или "интернет-заказ")
	private String uuid;
	private boolean isNew;          //признако нового инвойса(клиент его еще не видел)
	private FormType formType;
	private String autoSubExternalId;

	public FakeInvoice(Long id, String name, InvoiceStatus state, String requisites, Calendar creatingDate, String entityName, String providerName, Long imageId, boolean isNew) throws DocumentException
	{
		this(id, name, state, requisites, creatingDate, entityName, providerName, imageId, isNew, null);
	}


	public FakeInvoice(Long id, String name, InvoiceStatus state, String requisites, Calendar creatingDate, String entityName, String providerName, Long imageId, boolean isNew, Calendar delayedDate) throws DocumentException
	{
		this.id = id;
		this.name = name;
		this.state = state;
		this.requisites = requisites;
		this.creatingDate = creatingDate;
		this.entityName = entityName;
		this.providerName = providerName;
		this.imageId = imageId;
		this.uuid = id.toString();
		this.isNew = isNew;
		this.delayedDate = delayedDate;
		initialize();
	}

	public FakeInvoice() {}

	/**
	 * вытягиваем имя и значение ключевого поля и сумму по реквизитам объекта
	 * @throws DocumentException
	 */
	public void initialize() throws DocumentException
	{
		setKeyNameAndValue(requisites);
		setSumValue(requisites);
	}
	private void setKeyNameAndValue(String requisites) throws DocumentException
	{
		List<Field> fields = RequisitesHelper.deserialize(requisites);
		this.keyName = "";
		this.keyValue ="";
		for (Field item:fields)
		{
			if (item.isKey())
			{
				keyName = item.getName();
				keyValue = item.getValue().toString();
				break;
			}
		}
	}

	private void setSumValue(String requisites) throws DocumentException
	{
		sum = BigDecimal.ZERO;
		List<Field> fields = RequisitesHelper.deserialize(requisites);
		for (Field item:fields)
			{
				if (item.isMainSum())
					sum = BigDecimal.valueOf(Double.valueOf(item.getValue().toString()));
			}
	}

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

	public InvoiceStatus getState()
	{
		return state;
	}

	public void setState(InvoiceStatus state)
	{
		this.state = state;
	}

	public String getRequisites()
	{
		return requisites;
	}

	public void setRequisites(String requisites)
	{
		this.requisites = requisites;
	}

	public Calendar getCreatingDate()
	{
		return creatingDate;
	}

	public void setCreatingDate(Calendar creatingDate)
	{
		this.creatingDate = creatingDate;
	}

	public String getEntityName()
	{
		return entityName;
	}

	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public String getKeyName()
	{
		return keyName;
	}

	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}

	public String getKeyValue()
	{
		return keyValue;
	}

	public void setKeyValue(String keyValue)
	{
		this.keyValue = keyValue;
	}

	public BigDecimal getSum()
	{
		return sum;
	}

	public void setSum(BigDecimal sum)
	{
		this.sum = sum;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Boolean getIsNew()
	{
		return isNew;
	}

	public void setIsNew(Boolean aNew)
	{
		isNew = aNew;
	}

	public FormType getFormType()
	{
		return formType;
	}

	public void setFormType(FormType formType)
	{
		this.formType = formType;
	}

	public Calendar getDelayedDate()
	{
		return delayedDate;
	}

	public void setDelayedDate(Calendar delayedDate)
	{
		this.delayedDate = delayedDate;
	}

	/**
	 * @return идентификатор автоподписки, по которой создан автопоиск данного инвойса
	 */
	public String getAutoSubExternalId()
	{
		return autoSubExternalId;
	}

	public void setAutoSubExternalId(String autoSubExternalId)
	{
		this.autoSubExternalId = autoSubExternalId;
	}
}
