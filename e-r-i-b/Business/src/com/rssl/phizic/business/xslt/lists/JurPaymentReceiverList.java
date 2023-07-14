package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.dictionaries.PaymentReceiverService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Omeliyanchuk
 * @ created 16.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class JurPaymentReceiverList implements EntityListSource
{
	private EntityListBuilder getEntityListBuilder ( final Map<String,String> params ) throws BusinessException
	{
		PaymentRecipientGateService service = GateSingleton.getFactory().service(PaymentRecipientGateService.class);

		String appointment = params.get("appointment");
		String receiverIdStr = params.get("ReceiverId");

		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		if (appointment.equals("gorod"))
		{
			try
			{
				Recipient recipient = service.getRecipient(receiverIdStr);
				RecipientInfo recipientInfo = service.getRecipientInfo(recipient, null, null);
				builder.openEntityTag((String) recipient.getSynchKey());
				builder.appentField("receiverName", recipient.getName());
				builder.appentField("account", recipientInfo.getAccount());
				builder.appentField("bankName", recipientInfo.getBank().getName());
				builder.appentField("bankCode", recipientInfo.getBank().getBIC());
				builder.appentField("correspondentAccount", recipientInfo.getBank().getAccount());
				builder.appentField("INN", recipientInfo.getINN());
				builder.appentField("KPP", recipientInfo.getKPP());
				builder.closeEntityTag();
				builder.closeEntityListTag();
				return builder;
			}
			catch (TemporalGateException e)
			{
				throw new TemporalBusinessException(e);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessException(e);
			}
		}
		PaymentReceiverJur receiver = null;

		if (receiverIdStr != null && receiverIdStr.length() != 0)
		{
			Long id= Long.parseLong(receiverIdStr);
			PaymentReceiverService recieverService = new PaymentReceiverService();
			receiver = (PaymentReceiverJur) recieverService.findReceiver(id);
		}

		if (receiver != null)
		{
			builder.openEntityTag(receiver.getId().toString());
			builder.appentField("receiverName", receiver.getName());
			builder.appentField("account", receiver.getAccount());
			builder.appentField("bankName", receiver.getBankName());
			builder.appentField("bankCode", receiver.getBankCode());
			builder.appentField("correspondentAccount", receiver.getCorrespondentAccount());
			builder.appentField("INN", receiver.getINN());
			builder.appentField("KPP", receiver.getKPP());
			builder.closeEntityTag();
		}

		builder.closeEntityListTag();

		return builder;
	}

    public Source getSource( Map<String,String> params ) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument ( Map<String,String> params ) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}
}
