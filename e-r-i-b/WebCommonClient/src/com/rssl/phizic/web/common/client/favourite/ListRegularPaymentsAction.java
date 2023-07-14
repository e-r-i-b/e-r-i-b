package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cache.BusinessWaitCreateCacheException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.autopayments.links.ListAutoPaymentLinksOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionLinksOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.CreateESBAutoPayOperation;
import com.rssl.phizic.operations.longoffers.links.ListLongOfferLinksOperation;
import com.rssl.phizic.operations.payment.CreateAutoPaymentOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.ext.sbrf.LongOfferUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListRegularPaymentsAction extends OperationalActionBase
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String CREATE_AUTO_PAY_ALLOWED_EMPTY_LIST_MESSAGE  = "У Вас нет ни одного автоплатежа. Для подключения услуги используйте кнопку «Подключить автоплатеж».";
	private static final String DEFAULT_EMPTY_LIST_MESSAGE                  = "У Вас нет ни одного автоплатежа. Для подключения услуги используйте кнопку «Подключить автоплатеж».";
	protected static final String ERROR_MESSAGE       = "В списке показаны не все Ваши автоплатежи. Информация по ним временно недоступна.";

	private static enum AutoPaymentTypes
	{
		longOffer,
		autoPayment,
        autoTransfer,
		autoSubscription;
	}

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListRegularPaymentsForm frm = (ListRegularPaymentsForm) form;
		//обновляем длительные поручения и автоплатежи
		try
		{
			updateFormRegularPayments(frm);
		}
		catch (Exception e)
		{
			//jsp падать не должна, исключение пишем в лог
			saveMessage(currentRequest(), ERROR_MESSAGE);
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormRegularPayments(ListRegularPaymentsForm frm) throws BusinessException
	{
		List<EditableExternalResourceLink> regularPayments = new ArrayList<EditableExternalResourceLink>();
		List<EditableExternalResourceLink> p2pSubscriptionLinks = new ArrayList<EditableExternalResourceLink>();
		addPayments(frm, regularPayments, p2pSubscriptionLinks);
		try
		{
			// Установить сообщения, отображаемые пользователю при пустых списках
			updateEmptyListMessage(frm);
		}
		catch (BusinessException e)
		{
			//jsp падать не должна, исключение пишем в лог
			saveMessage(currentRequest(), ERROR_MESSAGE);
			log.error(e.getMessage(), e);
		}

		RegularPaymentsListContainer autoPaymentsContainer = new RegularPaymentsListContainer(regularPayments);
		RegularPaymentsListContainer autoTransfersContainer = new RegularPaymentsListContainer(p2pSubscriptionLinks);

		ApplicationConfig applicationConfig = ApplicationConfig.getIt();

		if (applicationConfig.getApplicationInfo().isMobileApi() || applicationConfig.getApplicationInfo().isATM())
		{
			initializePaymentsInMapiOrAtmCase(frm, autoPaymentsContainer, autoTransfersContainer);
		}
		//иначе разделяем на 4 списка: активные, ожидают подтверждения, приостановленные и архив
		else
		{
			initializePaymentsInOtherCase(frm, autoPaymentsContainer, autoTransfersContainer);
		}
	}

	/**
	 * Заполнить форму автоплатежами, в случае если работаем с мАПИ или АТМ
	 * @param frm - форма
	 * @param autoPaymentsContainer - контейнер автоплатежей за услуги
	 * @param autoTransfersContainer - контейнер автопереводов на карты
	 */
	private void initializePaymentsInMapiOrAtmCase(ListRegularPaymentsForm frm, RegularPaymentsListContainer autoPaymentsContainer, RegularPaymentsListContainer autoTransfersContainer)
	{
		frm.setRegularPayments(autoPaymentsContainer.getPayments());
		frm.setP2pRegularPayments(autoTransfersContainer.getPayments());

		Set<EditableExternalResourceLink> activeSet = autoPaymentsContainer.getActiveSet();
		activeSet.addAll(autoTransfersContainer.getActiveSet());
		frm.setActivePaymentSet(activeSet);
	}

	/**
	 * Заполнение формы автоплатежами, если работаем не с мАПИ или АТМ
	 * @param frm - форма
	 * @param autoPaymentsContainer - контейнер автоплатежей за услуги
	 * @param autoTransfersContainer - контейнер автопереводов на карты
	 * @throws BusinessException
	 */
	private void initializePaymentsInOtherCase(ListRegularPaymentsForm frm, RegularPaymentsListContainer autoPaymentsContainer, RegularPaymentsListContainer autoTransfersContainer) throws BusinessException
	{
		frm.setActivePayments(LongOfferUtils.sortPaymentsByOrder(autoPaymentsContainer.getOnlyActualPayments()));
		frm.setSuspendedPayments(LongOfferUtils.sortPaymentsByOrder(autoPaymentsContainer.getOnlySuspendedPayments()));
		frm.setWaitingConfirmPayments(LongOfferUtils.sortPaymentsByOrder(autoPaymentsContainer.getOnlyWaitingPayments()));
		frm.setArchivePayments(LongOfferUtils.sortPaymentsByOrder(autoPaymentsContainer.getOnlyArchivePayments()));

		frm.setActiveP2PPayments(LongOfferUtils.sortPaymentsByOrder(autoTransfersContainer.getOnlyActualPayments()));
		frm.setSuspendedP2PPayments(LongOfferUtils.sortPaymentsByOrder(autoTransfersContainer.getOnlySuspendedPayments()));
		frm.setWaitingConfirmP2PPayments(LongOfferUtils.sortPaymentsByOrder(autoTransfersContainer.getOnlyWaitingPayments()));
		frm.setArchiveP2PPayments(LongOfferUtils.sortPaymentsByOrder(autoTransfersContainer.getOnlyArchivePayments()));
	}

	private void addPayments(ListRegularPaymentsForm frm, List<EditableExternalResourceLink> regularPayments, List<EditableExternalResourceLink> p2pSubscriptionLinks) throws BusinessException
	{
		Set<AutoPaymentTypes> autoPaymentsTypes = getAutoPaymentTypes(frm);
		if (autoPaymentsTypes.contains(AutoPaymentTypes.autoPayment))
			// Добавить автоплатежи
			regularPayments.addAll(getAutoPaymentLinks(frm));

		if (autoPaymentsTypes.contains(AutoPaymentTypes.longOffer))
			// Добавить длительные поручения
			regularPayments.addAll(getLongOfferLinks(frm));

		if (autoPaymentsTypes.contains(AutoPaymentTypes.autoSubscription) || autoPaymentsTypes.contains(AutoPaymentTypes.autoTransfer))
		{
			// Добавить автоподписки
			List<EditableExternalResourceLink> subscriptions = new ArrayList<EditableExternalResourceLink>();
			List<EditableExternalResourceLink> autoTransfers = new ArrayList<EditableExternalResourceLink>();

			getAutoSubscriptionLinks(frm, subscriptions, autoTransfers);

			if (autoPaymentsTypes.contains(AutoPaymentTypes.autoSubscription))
			{
				regularPayments.addAll(subscriptions);
			}
			if (autoPaymentsTypes.contains(AutoPaymentTypes.autoTransfer))
			{
				p2pSubscriptionLinks.addAll(autoTransfers);
			}
		}
	}

	protected String getHelpId(ActionMapping mapping, ActionForm form) throws Exception
	{
		return "/help";
	}

	/**
	 * Установить сообщения, отображаемые пользователю при пустых списках
	 * @param frm форма
	 * @throws BusinessException
	 */
	protected void updateEmptyListMessage(ListRegularPaymentsForm frm) throws BusinessException
	{
		//доступно ли клиенту создание автоплатежей.
		boolean isCreateAllowed = checkAccess(CreateESBAutoPayOperation.class, "ClientCreateAutoPayment") || checkAccess(CreateAutoPaymentOperation.class, "CreateAutoPaymentPayment");

		frm.setEmptyListMessage(isCreateAllowed ? CREATE_AUTO_PAY_ALLOWED_EMPTY_LIST_MESSAGE : DEFAULT_EMPTY_LIST_MESSAGE);

		if(frm.isAccount())
			frm.setEmptyListMessage(DEFAULT_EMPTY_LIST_MESSAGE);
	}

	/**
	 * @param frm форма
	 * @return список автоплатежей
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected List<AutoPaymentLink> getAutoPaymentLinks(ListRegularPaymentsForm frm)
	{
		try
		{
			boolean showAllPayments = frm.getProductId() == null;
			boolean isCardPayment = frm.isCard() && !showAllPayments;

			if (checkAccess(ListAutoPaymentLinksOperation.class) && (showAllPayments || isCardPayment))
			{
				ListAutoPaymentLinksOperation listAutoPaymentLinksOperation = createOperation(ListAutoPaymentLinksOperation.class);
				listAutoPaymentLinksOperation.initialize(frm.getProductId());
				return LongOfferUtils.getNotEmptyAutoPaymentLinks(listAutoPaymentLinksOperation.getEntity());
			}
		}
		catch (BusinessWaitCreateCacheException e)
		{
			saveMessage(currentRequest(), e.getMessage());
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (Exception e)
		{
			//jsp падать не должна, исключение пишем в лог
			saveMessage(currentRequest(), ERROR_MESSAGE);
			log.error(e.getMessage(), e);
		}

		return Collections.emptyList();
	}

	/**
	 * @param frm  - форма
	 * @param regularPayments - автоплатежи за услуги
	 * @param p2pSubscriptionLinks - автоплатежи на карты
	 */
	protected void getAutoSubscriptionLinks(ListRegularPaymentsForm frm, List<EditableExternalResourceLink> regularPayments, List<EditableExternalResourceLink> p2pSubscriptionLinks)
	{
		try
		{
			boolean showAllPayments = frm.getProductId() == null;
			boolean isCardPayment = frm.isCard() && !showAllPayments;

			if ((checkAccess(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment")
					|| checkAccess(ListAutoSubscriptionLinksOperation.class, "ClientAutotransfersManagement"))
				&& (showAllPayments || isCardPayment))
			{
				ListAutoSubscriptionLinksOperation listAutoSubscriptionLinksOperation = createOperation(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment");
				listAutoSubscriptionLinksOperation.initialize(frm.getProductId());

				if (PermissionUtil.impliesService("AutoSubscriptionLinkManagment"))
					regularPayments.addAll(listAutoSubscriptionLinksOperation.getEntity());

				if (PermissionUtil.impliesService("ClientAutotransfersManagement"))
					p2pSubscriptionLinks.addAll(listAutoSubscriptionLinksOperation.getP2pSubscriptionsLinks());
			}
		}
		catch (BusinessWaitCreateCacheException e)
		{
			saveMessage(currentRequest(), e.getMessage());
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (Exception e)
		{
			//jsp падать не должна, исключение пишем в лог
			saveMessage(currentRequest(), ERROR_MESSAGE);
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @param frm форма
	 * @return список длительных поручений
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected List<LongOfferLink> getLongOfferLinks(ListRegularPaymentsForm frm)
	{
		try
		{
			if (checkAccess(ListLongOfferLinksOperation.class))
			{
				ListLongOfferLinksOperation longOfferLinksOperation = createOperation(ListLongOfferLinksOperation.class);
				longOfferLinksOperation.initialize(frm.getProductId(), frm.isCard());

				List<LongOfferLink> links = longOfferLinksOperation.getEntity();

				if (longOfferLinksOperation.isUseStoredResource())
				{
					for (LongOfferLink link : links)
					{
						LongOffer longOffer = link.getValue();
						if (!MockHelper.isMockObject(longOffer))
						{
							saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) longOffer));
							break;
						}
					}
				}

				return LongOfferUtils.getNotEmptyLongOfferLinks(links);
			}
		}
		catch (BusinessWaitCreateCacheException e)
		{
			saveMessage(currentRequest(), e.getMessage());
		}
		catch (InactiveExternalSystemException e)
		{
			saveInactiveESMessage(currentRequest(), e);
		}
		catch (Exception e)
		{
			//jsp падать не должна, исключение пишем в лог
			saveMessage(currentRequest(), ERROR_MESSAGE);
			log.error(e.getMessage(), e);
		}

		return Collections.emptyList();
	}

	private Set<AutoPaymentTypes> getAutoPaymentTypes(ListRegularPaymentsForm frm) throws BusinessException
	{
		String[] autoPaymentTypes = frm.getType();
		Set<AutoPaymentTypes> autoPayments = EnumSet.noneOf(AutoPaymentTypes.class);
		if (ArrayUtils.isNotEmpty(autoPaymentTypes))
		{
			for (String autoPaymentType : autoPaymentTypes)
			{
				try
				{
					autoPayments.add(AutoPaymentTypes.valueOf(autoPaymentType));
				}
				catch (IllegalArgumentException ex)
				{
					throw new BusinessException(ex.getMessage(), ex);
				}
			}
			return autoPayments;
		}
		else
		{
			if ( !ApplicationUtil.isATMApi() &&
				 (ApplicationUtil.isNotMobileApi() || MobileApiUtil.isMobileApiLT(MobileAPIVersions.V8_00)) )
			{
				autoPayments.add(AutoPaymentTypes.longOffer);
			}

			autoPayments.add(AutoPaymentTypes.autoPayment);
			autoPayments.add(AutoPaymentTypes.autoTransfer);
			autoPayments.add(AutoPaymentTypes.autoSubscription);
			return autoPayments;
		}
	}
}
