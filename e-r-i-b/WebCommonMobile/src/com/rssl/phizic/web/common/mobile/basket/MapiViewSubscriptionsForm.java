package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Balovtsev
 * @since 20.10.14.
 */
public class MapiViewSubscriptionsForm extends EditFormBase
{
	private Map<String, List<InvoiceSubscription>> activeSubscriptions      = new TreeMap<String, List<InvoiceSubscription>>();
	private Map<String, List<InvoiceSubscription>> stoppedSubscriptions     = new TreeMap<String, List<InvoiceSubscription>>();
	private Map<String, List<InvoiceSubscription>> recommendedSubscriptions = new TreeMap<String, List<InvoiceSubscription>>();
	private List<AutoSubscriptionLink>             autoSubscriptions        = new ArrayList<AutoSubscriptionLink>();

	private Map<String, String>      imageIds   = new TreeMap<String, String>();
	private Map<String, List<Field>> requisites = new TreeMap<String, List<Field>>();

	public Map<String, List<InvoiceSubscription>> getActiveSubscriptions()
	{
		return Collections.unmodifiableMap(activeSubscriptions);
	}

	public void setActiveSubscriptions(Map<String, List<InvoiceSubscription>> activeSubscriptions)
	{
		this.activeSubscriptions.putAll(activeSubscriptions);
	}

	public Map<String, List<InvoiceSubscription>> getStoppedSubscriptions()
	{
		return Collections.unmodifiableMap(stoppedSubscriptions);
	}

	public void setStoppedSubscriptions(Map<String, List<InvoiceSubscription>> stoppedSubscriptions)
	{
		this.stoppedSubscriptions.putAll(stoppedSubscriptions);
	}

	public Map<String, List<InvoiceSubscription>> getRecommendedSubscriptions()
	{
		return Collections.unmodifiableMap(recommendedSubscriptions);
	}

	public void setRecommendedSubscriptions(Map<String, List<InvoiceSubscription>> recommendedSubscriptions)
	{
		this.recommendedSubscriptions.putAll(recommendedSubscriptions);
	}

	public Map<String, String> getImageIds()
	{
		return Collections.unmodifiableMap(imageIds);
	}

	public void setImageIds(Map<String, String> imageIds)
	{
		this.imageIds.putAll(imageIds);
	}

	public Map<String, List<Field>> getRequisites()
	{
		return Collections.unmodifiableMap(requisites);
	}

	public void setRequisites(Map<String, List<Field>> requisites)
	{
		this.requisites.putAll(requisites);
	}

	public List<AutoSubscriptionLink> getAutoSubscriptions()
	{
		return Collections.unmodifiableList(autoSubscriptions);
	}

	public void setAutoSubscriptions(List<AutoSubscriptionLink> autoSubscriptions)
	{
		this.autoSubscriptions.addAll(autoSubscriptions);
	}
}
