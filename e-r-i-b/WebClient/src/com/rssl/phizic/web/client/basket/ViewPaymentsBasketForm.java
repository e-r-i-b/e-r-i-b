package com.rssl.phizic.web.client.basket;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityBase;
import com.rssl.phizic.business.basket.config.ServiceCategory;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра страницы "корзина платежей"
 */
public class ViewPaymentsBasketForm extends EditFormBase
{
	private List<AccountingEntityBase> accountingEntities = new ArrayList();
	private Map<String, List<ServiceCategory>> serviceCategories = new TreeMap();
	private Map<String, List<InvoiceSubscription>> activeSubscriptions = new TreeMap();
	private Map<String, List<InvoiceSubscription>> stoppedSubscriptions = new TreeMap();
	private Map<String, List<InvoiceSubscription>> recommendedSubscriptions = new TreeMap();
	private Set<InvoiceSubscription> allSubscriptions = new TreeSet<InvoiceSubscription>();
	private List<AutoSubscriptionLink> autoSubscriptions = new ArrayList<AutoSubscriptionLink>();
	private Map<String, AutoSubscriptionDetailInfo> detailInfo = new TreeMap<String, AutoSubscriptionDetailInfo>();


	private Map<String, String> imageIds = new TreeMap();
	private Map<String, List<Field>> requisites = new TreeMap();

	private Long subscriptionId;
	private Long entityId;
	private String type;

	public Map<String, List<ServiceCategory>> getServiceCategories()
	{
		return serviceCategories;
	}

	public void setServiceCategories(Map<String, List<ServiceCategory>> serviceCategories)
	{
		this.serviceCategories = serviceCategories;
	}

	public Map<String, List<InvoiceSubscription>> getActiveSubscriptions()
	{
		return activeSubscriptions;
	}

	public void setActiveSubscriptions(Map<String, List<InvoiceSubscription>> activeSubscriptions)
	{
		this.activeSubscriptions = activeSubscriptions;
	}

	public Map<String, List<InvoiceSubscription>> getStoppedSubscriptions()
	{
		return stoppedSubscriptions;
	}

	public void setStoppedSubscriptions(Map<String, List<InvoiceSubscription>> stoppedSubscriptions)
	{
		this.stoppedSubscriptions = stoppedSubscriptions;
	}

	public Map<String, List<InvoiceSubscription>> getRecommendedSubscriptions()
	{
		return recommendedSubscriptions;
	}

	public void setRecommendedSubscriptions(Map<String, List<InvoiceSubscription>> recommendedSubscriptions)
	{
		this.recommendedSubscriptions = recommendedSubscriptions;
	}

	public List<AccountingEntityBase> getAccountingEntities()
	{
		return accountingEntities;
	}

	public void setAccountingEntities(List<AccountingEntityBase> accountingEntities)
	{
		this.accountingEntities = accountingEntities;
	}

	public Map<String, String> getImageIds()
	{
		return imageIds;
	}

	public void setImageIds(Map<String, String> imageIds)
	{
		this.imageIds = imageIds;
	}

	public Map<String, List<Field>> getRequisites()
	{
		return requisites;
	}

	public void setRequisites(Map<String, List<Field>> requisites)
	{
		this.requisites = requisites;
	}

	public List<AutoSubscriptionLink> getAutoSubscriptions()
	{
		return autoSubscriptions;
	}

	public void setAutoSubscriptions(List<AutoSubscriptionLink> autoSubscriptions)
	{
		this.autoSubscriptions = autoSubscriptions;
	}

	public void setAllSubscriptions(Set<InvoiceSubscription> allSubscriptions)
	{
		this.allSubscriptions = allSubscriptions;
	}

	public InvoiceSubscription getInvoiceSubscriptionById(Long id)
	{
		for (InvoiceSubscription subscription:allSubscriptions)
		{
			if (subscription.getId() == id)
				return subscription;
		}

		return null;
	}

	public Long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public Long getEntityId()
	{
		return entityId;
	}

	public void setEntityId(Long entityId)
	{
		this.entityId = entityId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Map<String, AutoSubscriptionDetailInfo> getDetailInfo()
	{
		return detailInfo;
	}

	public void setDetailInfo(Map<String, AutoSubscriptionDetailInfo> detailInfo)
	{
		this.detailInfo = detailInfo;
	}
}
