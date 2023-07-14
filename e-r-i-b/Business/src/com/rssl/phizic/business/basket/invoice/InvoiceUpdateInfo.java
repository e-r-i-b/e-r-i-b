package com.rssl.phizic.business.basket.invoice;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Информация по инвойсту для операции удаления/приостановки
 * @author niculichev
 * @ created 18.10.14
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceUpdateInfo
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private Long id;
	private String name;
	private InvoiceStatus status;
	private String requisites;
	private Long loginId;
	private Long subscriptionId;
	private ConfirmStrategyType confirmStrategyType;
	private CreationType creationType;

	public InvoiceUpdateInfo()
	{
	}

	public InvoiceUpdateInfo(Invoice invoice)
	{
		this.id = invoice.getId();
		this.name = invoice.getNameService();
		this.status = invoice.getState();
		this.requisites = invoice.getRequisites();
		this.subscriptionId = invoice.getInvoiceSubscriptionId();
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

	public InvoiceStatus getStatus()
	{
		return status;
	}

	public void setStatus(InvoiceStatus status)
	{
		this.status = status;
	}

	public String getRequisites()
	{
		return requisites;
	}

	public void setRequisites(String requisites)
	{
		this.requisites = requisites;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * @return Сумма инвойса
	 * @throws BusinessException
	 */
	public BigDecimal getAmount() throws BusinessException
	{
		List<Field> fields = getRequisitesAsList();
		if(CollectionUtils.isEmpty(fields))
			return null;

		for(Field field : fields)
		{
			if(field.isMainSum())
				return new BigDecimal((String) field.getValue());
		}

		return null;

	}

	/**
	 * @return реквизиты ввиде списка
	 */
	public List<Field> getRequisitesAsList()
	{
		try
		{
			return RequisitesHelper.deserialize(getRequisites());
		}
		catch (DocumentException e)
		{
			log.error("Ошибка приведения реквизитов к списку.", e);
			return Collections.emptyList();
		}
	}

	public CreationType getCreationType()
	{
		return creationType;
	}

	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}
}
