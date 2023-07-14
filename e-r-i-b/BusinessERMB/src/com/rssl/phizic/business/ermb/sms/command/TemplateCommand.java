package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.annotation.OptionalParameter;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Gulov
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда ШАБЛОНЫ.
 */
class TemplateCommand extends CommandBase
{
	private static final ServiceProviderSmsAliasService providerSmsAliasService = new ServiceProviderSmsAliasService();

	/**
	 * Алиас получателя, по которому запрашиваются шаблоны
	 * null - возвращаются доступные в смс-канале шаблоны
	 */
	private String receiverAlias;

	@OptionalParameter
	void setReceiverAlias(String receiverAlias)
	{
		this.receiverAlias = receiverAlias;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		try
		{
			Client client = new ClientImpl(getErmbProfile().getPerson());

			if (receiverAlias == null)
			{
				Set<String> smsCommandAliases = ConfigFactory.getConfig(SmsConfig.class).getAllCommandAliases();
				List<TemplateDocument> templates =	TemplateDocumentService.getInstance().findReadyToPaymentInSmsChannel(client, smsCommandAliases);
				sendMessage(getMessage(templates));
				return;
			}

			ServiceProviderBase receiver = providerSmsAliasService.findServiceProviderByAlias(receiverAlias);
			if (receiver == null)
			{
				throw new UserErrorException(messageBuilder.buildAliasTemplateNotFoundMessage(receiverAlias));
			}

			List<TemplateDocument> templates = TemplateDocumentService.getInstance().findByReceiverIdInChannel(client, CreationType.sms, receiver.getId());
			sendMessage(getMessage(templates));
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new UserErrorException(e);
		}
	}

	private TextMessage getMessage(List<TemplateDocument> templates)
	{
		if (CollectionUtils.isEmpty(templates))
			return receiverAlias == null ? messageBuilder.buildTemplateNotFoundMessage() :
				messageBuilder.buildAliasTemplateNotFoundMessage(receiverAlias);
		return new TextMessage(messageBuilder.buildTemplateMessage(templates));
	}

	@Override
	public String toString()
	{
		if (receiverAlias != null)
			return String.format("ШАБЛОНЫ[алиас:%s]", receiverAlias);
		else
			return "ШАБЛОНЫ[]";
	}

	public String getCommandName()
	{
		return "TEMPLATE";
	}
}
