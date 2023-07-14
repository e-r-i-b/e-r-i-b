package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.operations.autopayments.links.ListAutoPaymentLinksOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionLinksOperation;
import com.rssl.phizic.operations.longoffers.links.ListLongOfferLinksOperation;
import com.rssl.phizic.web.common.client.ext.sbrf.LongOfferUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Link;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.AutoPaymentResponse;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует заполнение объекта содержащего данные по автоплатежам личном меню
 *
 * @author Balovtsev
 * @since 24.04.14
 */
public class AutoPaymentResponseConstructor extends JAXBResponseConstructor<Request, AutoPaymentResponse>
{
	private static final String CONNECT_AUTO_PAYMENT_COMMAND_URL = LinkUtils.createRedirectUrl("/private/autopayment/select-category-provider.do", null);
	private static final String MANAGE_AUTO_PAYMENT_COMMAND_URL  = LinkUtils.createRedirectUrl("/private/favourite/list/AutoPayments.do", null);
	private static final String COMMON_AUTO_PAYMENT_LINK_URL     = "/private/autopayments/info.do?id=%d";
	private static final String COMMON_AUTOSUBSCRIPTION_LINK_URL = "/private/autosubscriptions/info.do?id=%s";
	private static final String COMMON_LONG_OFFER_LINK_URL = "/private/longOffers/info.do?id=%d";

	@Override
	protected AutoPaymentResponse makeResponse(Request request) throws Exception
	{
		AutoPaymentResponse response = new AutoPaymentResponse();

		response.setLinks   (createLinks());
		response.setCommands(createCommands());

		return response;
	}

	private List<Link> createLinks() throws BusinessException, BusinessLogicException
	{
		List<EditableExternalResourceLink> autoPaymentLinks = new ArrayList<EditableExternalResourceLink>();
		try
		{
			addLongOfferLinks(autoPaymentLinks);
		}
		catch (IKFLException e)
		{
			Utils.error("Не удалось получить сведения о длительных поручениях", e);
		}
		try
		{
			addAutoPaymentLinks(autoPaymentLinks);
		}
		catch (IKFLException e)
		{
			Utils.error("Не удалось получить сведения об автоплатежах", e);
		}

		try
		{
			addSubscriptionLinks(autoPaymentLinks);
		}
		catch (IKFLException e)
		{
			Utils.error("Не удалось получить сведения о подписках на автоплатежи", e);
		}

		autoPaymentLinks = LongOfferUtils.sortPaymentsByOrder( LongOfferUtils.getOnlyActivePayments(autoPaymentLinks) );

		List<Link> links = new ArrayList<Link>();
		for (EditableExternalResourceLink link : autoPaymentLinks)
		{
			if (link instanceof AutoSubscriptionLink)
				links.add(new Link(LinkUtils.createRedirectUrl(COMMON_AUTOSUBSCRIPTION_LINK_URL, null, link.getNumber()), link.getName()));
			else if (link instanceof LongOfferLink)
				links.add(new Link(LinkUtils.createRedirectUrl(COMMON_LONG_OFFER_LINK_URL, null, link.getId()), link.getName()));
			else
				links.add(new Link(LinkUtils.createRedirectUrl(COMMON_AUTO_PAYMENT_LINK_URL, null, link.getId()), link.getName()));
		}

		return CollectionUtils.isEmpty(links) ? null : links;
	}

	private void addAutoPaymentLinks(final List<EditableExternalResourceLink> list) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (checkAccess(ListAutoPaymentLinksOperation.class))
			{
				ListAutoPaymentLinksOperation listAutoPaymentLinksOperation = createOperation(ListAutoPaymentLinksOperation.class);
				listAutoPaymentLinksOperation.initialize();

				list.addAll( LongOfferUtils.getNotEmptyAutoPaymentLinks(listAutoPaymentLinksOperation.getEntity()) );
			}
		}
		catch (InactiveExternalSystemException e)
		{
			Utils.error("Не удалось получить сведения о автоплатежах", e);
		}
	}

	private void addLongOfferLinks(final List<EditableExternalResourceLink> list) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (checkAccess(ListLongOfferLinksOperation.class))
			{
				ListLongOfferLinksOperation longOfferLinksOperation = createOperation(ListLongOfferLinksOperation.class);
				longOfferLinksOperation.initialize();

				list.addAll(LongOfferUtils.getNotEmptyLongOfferLinks(longOfferLinksOperation.getEntity()));
			}
		}
		catch (InactiveExternalSystemException e)
		{
			Utils.error("Не удалось получить сведения о длительных поручениях", e);
		}
	}

	private void addSubscriptionLinks(final List<EditableExternalResourceLink> list) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (checkAccess(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment"))
			{
				ListAutoSubscriptionLinksOperation listAutoSubscriptionLinksOperation = createOperation(ListAutoSubscriptionLinksOperation.class, "AutoSubscriptionLinkManagment");
				listAutoSubscriptionLinksOperation.initialize(null);

				list.addAll(listAutoSubscriptionLinksOperation.getEntity());
				list.addAll(listAutoSubscriptionLinksOperation.getP2pSubscriptionsLinks());
			}
		}
		catch (InactiveExternalSystemException e)
		{
			Utils.error("Не удалось получить сведения о подписках на автоплатежи", e);
		}
	}

	private List<Link> createCommands()
	{
		List <Link> commands = new ArrayList<Link>();
		commands.add(new Link(CONNECT_AUTO_PAYMENT_COMMAND_URL, "Подключить автоплатеж"));
		commands.add(new Link(MANAGE_AUTO_PAYMENT_COMMAND_URL,  "Управление автоплатежами"));
		return commands;
	}
}
