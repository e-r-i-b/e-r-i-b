package com.rssl.phizic.business;

import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoice.FakeInvoice;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.reminders.ReminderHelper;
import com.rssl.phizic.business.reminders.ReminderLink;
import com.rssl.phizic.business.reminders.ReminderLinkService;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.payments.template.ReminderInfo;
import com.rssl.phizic.gate.reminder.ReminderHandlerFactory;
import com.rssl.phizic.gate.reminder.ReminderTypeHandler;
import com.rssl.phizic.gate.reminder.handlers.CompositeStatusFilteringReminderHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author tisov
 * @ created 01.06.14
 * @ $Author$
 * @ $Revision$
 * ������� ����� ������ �������� �������
 */
public abstract class InvoiceDataAbstractBase
{
	private static final InvoiceService invoiceService = new InvoiceService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final ReminderLinkService reminderLinkService = new ReminderLinkService();
	protected static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private int counterNew;
	protected List<FakeInvoice> invoices;

	/**
	 * @return ������� ����� ��������
	 */
	public int getCounterNew()
	{
		return counterNew;
	}

	/**
	 * ���������� ������� ����� ��������
	 * @param counterNew
	 */
	public void setCounterNew(int counterNew)
	{
		this.counterNew = counterNew;
	}

	/**
	 * @return ������ ������-�������� ��� view-�����
	 */
	public List<FakeInvoice> getInvoices()
	{
		return invoices;
	}

	/**
	 * ���������� ������ ������-�������� ��� view-�����
	 * @param invoices
	 */
	public void setInvoices(List<FakeInvoice> invoices)
	{
		this.invoices = invoices;
	}

	/**
	 * @return �����, ��-��������� ������������ ��� ������������� ������� ������
	 */
	protected abstract String getDefaultUnavailableInvoiceText();

	/**
	 * �������� �������, �������� � ������
	 * @param errorCollector
	 * @throws BusinessException
	 */
	abstract void refreshInvoices(Set<String> errorCollector) throws BusinessException;

	/**
	 * @return ������ �������� ��������-�������
	 * @throws BusinessException
	 */
	protected abstract List<ShopProfile> getShopProfiles() throws BusinessException;

	/**
	 * @return �������, � ������� ��������� �������� ��� �������
	 */
	protected abstract ActivePerson getPerson();

	protected void updateRefreshInfo() {}

	protected abstract boolean isRemindersManagementAllowed();

	/**
	 * ���������� ������ �� ��������
	 * @param limit - ����������� �� ���������� ���������� ��������
	 * @param errorCollector - ��������� ��� ������
	 * @param statuses - �������, �� ������� �������� �������
	 * @throws BusinessException
	 */
	protected List<FakeInvoice> refreshData(int limit, Set<String> errorCollector, InvoiceStatus ...statuses) throws BusinessException
	{
		List<FakeInvoice> result = new ArrayList<FakeInvoice>();
		Calendar fromDate = BasketHelper.getEInvoiceFromDate();

		if(isRemindersManagementAllowed())
			result.addAll(getReminderInvoices(errorCollector, statuses));
		result.addAll(getShopInvoices(fromDate, limit, statuses, errorCollector));
		result.addAll(getSubscriptionInvoices(fromDate, limit, statuses, errorCollector));
		Collections.sort(result, Collections.reverseOrder(new Comparator<FakeInvoice>()
		{
			public int compare(FakeInvoice o1, FakeInvoice o2)
			{
				return o1.getCreatingDate().compareTo(o2.getCreatingDate());
			}
		}));

		if(result.size() > limit)
			result = result.subList(0, limit);

		int counter = 0;
		for(FakeInvoice fake: result)
		{
			if(fake.getIsNew())
				counter++;
		}

		this.counterNew = counter;

		return result;
	}

	/**
	 * �������������� ��������-������ � ��������������� ������ �����
	 * @param order - �������� ��������-������
	 * @param providerName - ��� ���������� �����
	 * @param imageId - �� ������ ����������
	 * @return - �������� ��� view
	 */
	private FakeInvoice fromShopOrderToData(ShopOrder order, String providerName, Long imageId)
	{
		FakeInvoice result = new FakeInvoice();
		result.setUuid(order.getUuid());
		result.setName("������ ������ " + order.getReceiverName());
		result.setState(order.getState() == OrderState.DELAYED ? InvoiceStatus.DELAYED : InvoiceStatus.NEW);
		result.setProviderName(providerName);
		result.setImageId(imageId);
		result.setKeyName("����� ������");
		result.setKeyValue(order.getExternalId());
		result.setSum(BigDecimal.valueOf(order.getAmount().getWholePart()));
		result.setType("shopOrder");
		result.setExternalId(order.getUuid());
		result.setIsNew(order.getIsNew());
		result.setCreatingDate(order.getDate());
		result.setDelayedDate(order.getDelayedPayDate());
        result.setId(order.getId());
		return result;
	}

	/**
	 * @param invoiceStatuses - ������� ��������
	 * @return - ������� ��������-�������, ��������������� ������� �������� ��������
	 */
	private OrderState[] getOrderStatesByInvoiceStatuses(InvoiceStatus[] invoiceStatuses)
	{
		List<OrderState> orderStates = new ArrayList<OrderState>();

		for (int i = 0; i < invoiceStatuses.length; i++)
		{
			if (invoiceStatuses[i] == InvoiceStatus.NEW)
			{
				orderStates.add(OrderState.CREATED);
				orderStates.add(OrderState.PAYMENT);
				orderStates.add(OrderState.RELATED);
			}
			else if (invoiceStatuses[i] == InvoiceStatus.DELAYED)
			{
				orderStates.add(OrderState.DELAYED);
			}
		}

		return orderStates.toArray(new OrderState[orderStates.size()]);
	}

	/**
	 * @param fromDate - ����������� ����� �� ���� �������
	 * @param limit - ����������� �� ���������� ���������� �������
	 * @param invoiceStatuses - �������, �� ������� �������� ������
	 * @param errorCollector - ��������� ��� ������
	 * @return View-�������, ��������� �� ��������� �� ���� ��������-�������
	 * @throws BusinessException
	 */
	private List<FakeInvoice> getShopInvoices(Calendar fromDate, Integer limit, InvoiceStatus[] invoiceStatuses, Set<String> errorCollector) throws BusinessException
	{
		List<ShopOrder> shopOrders = new ArrayList<ShopOrder>();
		List<FakeInvoice> result = new ArrayList<FakeInvoice>();

		OrderState[] orderStates = getOrderStatesByInvoiceStatuses(invoiceStatuses);

		try
		{
			ShopOrderService shopOrderService = GateSingleton.getFactory().service(ShopOrderService.class);
			List<ShopProfile> shopProfiles = getShopProfiles();

			//�������� ��������-������.
			shopOrders =  shopOrderService.getOrdersByProfileHistoryForMainPage(
					shopProfiles, fromDate, Calendar.getInstance(), null, null, null, orderStates);
		}
		catch (Exception e)
		{
			//������� ��������� � ������� ������� �� ������ ������� ��������.
			log.error(e.getMessage(), e);
			if(errorCollector != null)
				errorCollector.add(getDefaultUnavailableInvoiceText());
			return Collections.emptyList();
		}

		if(CollectionUtils.isEmpty(shopOrders))
			return Collections.emptyList();

		Collections.sort(shopOrders, Collections.reverseOrder(new ShopOrdersByDateComparator()));
		int shopOrdersLength = Math.min(shopOrders.size(), limit);
		shopOrders = shopOrders.subList(0, shopOrdersLength);
		Set<String> codes = new TreeSet<String>();

		for (ShopOrder item:shopOrders)
			codes.add(item.getReceiverCode());

		List<Object[]> providersList = serviceProviderService.getNameAndImageByCodes(codes);

		HashMap<String, Object[]> providers = new HashMap<String, Object[]>();
		for (Object[] item:providersList)
			providers.put((String)item[0], item);

		for(ShopOrder order : shopOrders)
		{
			String receiverName = order.getReceiverName();
			Long imageId = null;
			Object[] providerInfo = providers.get(order.getReceiverCode());

			if (providerInfo != null)
			{
				//���� ���������-�����������, �� ��� ����� �� ������
				if (!(Boolean) providerInfo[3])
				{
					receiverName = (String) providerInfo[1];
				}

				imageId = (Long) providerInfo[2];
			}
			result.add(fromShopOrderToData(order, receiverName, imageId));
		}

		return result;
	}

	/**
	 * @param fromDate - ����������� ����� �� ���� ��������
	 * @param limit - ����������� �� ���������� ���������� �������
	 * @param invoiceStatuses - �������, � ������� ������ ���� ���������� �������
	 * @param errorCollector - ��������� ��� ������
	 * @return view-�������, ��������� �� ����
	 */
	private List<FakeInvoice> getSubscriptionInvoices(Calendar fromDate, Integer limit, InvoiceStatus[] invoiceStatuses, Set<String> errorCollector)
	{
		try
		{
			//�������� ������� ������� ��������.
			List<FakeInvoice> result = invoiceService.getInvoices(getPerson().getLogin().getId(), Long.valueOf(limit), fromDate, invoiceStatuses);

			if(CollectionUtils.isEmpty(result))
				return Collections.emptyList();

			for(FakeInvoice invoice : result)
			{
				invoice.initialize();
				invoice.setType("invoice");
				//�� ����� ������ ����� ������ ������.
				invoice.setRequisites(null);
			}

			return result;
		}
		catch (Exception e)
		{
			//������� ��������� � ������� ������� �� ������ ������� ��������.
			log.error(e.getMessage(), e);
			errorCollector.add(getDefaultUnavailableInvoiceText());
			return Collections.emptyList();
		}
	}

	private List<FakeInvoice> getReminderInvoices(Set<String> errorCollector, InvoiceStatus ...statuses)
	{
		try
		{
			List<TemplateDocument> templates = TemplateDocumentService.getInstance().getFiltered(
					getPerson().asClient(), ReminderHelper.getReminderTemplateFilter());

			if(CollectionUtils.isEmpty(templates))
				return Collections.emptyList();

			List<FakeInvoice> result = new ArrayList<FakeInvoice>();
			Map<Long, ReminderLink> states = findReminderLink();
			Calendar currentDate = DateHelper.getCurrentDate();

			for(TemplateDocument template : templates)
			{
				ReminderLink state = states.remove(template.getId());
				ReminderInfo reminderInfo = template.getReminderInfo();
				ReminderTypeHandler handler = new CompositeStatusFilteringReminderHandler(ReminderHandlerFactory.getHandler(reminderInfo), statuses);

				if(handler.isNeedRemind(state, reminderInfo, currentDate))
					result.add(ReminderHelper.buildInvoice(template, state));
			}

			// ������� ������, ��� ������� ��� ��������
			if(!states.isEmpty())
				removeDirtyStates(states.keySet());

			return result;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			errorCollector.add(getDefaultUnavailableInvoiceText());
			return Collections.emptyList();
		}
	}

	private Map<Long, ReminderLink> findReminderLink() throws BusinessException
	{
		List<ReminderLink> links = reminderLinkService.getByLogin(getPerson().getLogin().getId());
		if(CollectionUtils.isEmpty(links))
			return Collections.emptyMap();

		Map<Long, ReminderLink>  result = new HashMap<Long, ReminderLink>(links.size());
		for(ReminderLink link : links)
			result.put(link.getReminderId(), link);

		return result;
	}

	private void removeDirtyStates(Set<Long> ids) throws BusinessException
	{
		Long loginId = PersonHelper.getContextPerson().getLogin().getId();
		for(Long id : ids)
			reminderLinkService.deleteById(id, loginId);
	}
}
