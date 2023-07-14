package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelUtil;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.utils.LinkUtils;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Picture;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.FastPaymentResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel.CustomerPhonePayment;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel.FastPaymentContainer;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.fastpaymentpanel.Supplier;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс реализует заполнение объекта содержащего данные по панеле быстрой оплаты
 *
 * @author Balovtsev
 * @since 25.04.14
 */
public class FastPaymentResponseConstructor extends JAXBResponseConstructor<Request, FastPaymentResponse>
{
	protected static final String REDIRECT_URL        = "/private/payments/servicesPayments/edit.do?&fromResource=&recipient=%1$d";
	protected static final String ADDITION_URL_PARAMS = "&phoneFieldParam=field(%2$s)&field(%3$s)=%4$.2f";
	protected static final String MOCK_IMAGE_URL      = "/images/logotips/IQWave/IQWave-other.jpg";

	@Override
	protected FastPaymentResponse makeResponse(Request request) throws Exception
	{
		FastPaymentResponse  response = new FastPaymentResponse();
		List<PanelBlock>     panels   = QuickPaymentPanelUtil.getQuickPaymentPanel();

		if (CollectionUtils.isNotEmpty(panels))
		{
			FastPaymentContainer container = new FastPaymentContainer();
			CustomerPhonePayment payment   = new CustomerPhonePayment();
			List<Supplier>       suppliers = new ArrayList<Supplier>();

			for (PanelBlock panel : panels)
			{
				/*
				 * Косвенный признак того что данный экземпляр служит для быстрой оплаты телефона
				 */
				String redirectUrl = createRedirectUrl(panel);
				if (StringHelper.isNotEmpty(panel.getProviderFieldName()))
				{
					Picture picture = new Picture();
					picture.setUrl(LinkUtils.createPictureUrl(MOCK_IMAGE_URL, panel.getImage()));
					picture.setLink(redirectUrl);

					payment.setPicture(picture);
					payment.setPhoneNumber(PhoneNumberUtil.getCutPhoneNumber(QuickPaymentPanelUtil.getPhoneNumber()));
					payment.setUrl(redirectUrl);
				}
				else
				{
					Picture picture = new Picture();
					picture.setUrl(LinkUtils.createPictureUrl(MOCK_IMAGE_URL, panel.getImage()));
					picture.setLink(redirectUrl);

					Supplier supplier = new Supplier();
					supplier.setId(panel.getProviderId().intValue());
					supplier.setTitle(panel.getProviderName());
					supplier.setPicture(picture);
					supplier.setUrl(redirectUrl);

					suppliers.add(supplier);
				}
			}

			if (CollectionUtils.isNotEmpty(suppliers))
			{
				container.setSuppliers(suppliers);
			}

			container.setCustomerPhonePayment(payment);
			response.setFastPaymentContainer(container);
		}

		return response;
	}

	private String createRedirectUrl(final PanelBlock panel)
	{
		if (StringHelper.isNotEmpty(panel.getProviderFieldName()))
		{
			// Локаль выставлена, чтобы точка на запятую при форматировании суммы не заменялась
			return LinkUtils.createRedirectUrl(REDIRECT_URL + ADDITION_URL_PARAMS, Locale.ENGLISH, panel.getProviderId(),
																								   panel.getProviderFieldName(),
																								   panel.getProviderFieldAmount(),
																								   panel.getSumm().doubleValue());
		}

		return LinkUtils.createRedirectUrl(REDIRECT_URL, null, panel.getProviderId());
	}
}
