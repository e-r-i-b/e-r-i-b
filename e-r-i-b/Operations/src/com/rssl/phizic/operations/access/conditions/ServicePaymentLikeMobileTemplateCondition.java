package com.rssl.phizic.operations.access.conditions;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationProfile;
import com.rssl.phizic.business.ext.sbrf.mobilebank.SmsCommand;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * �������� ������� ���������� �� ���������� � �������������� �������� ���������� �����
 * @author Pankin
 * @ created 20.07.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */
@Deprecated
//todo CHG059738 �������
public class ServicePaymentLikeMobileTemplateCondition implements StrategyCondition
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final String REC_IDENTIFIER = "RecIdentifier";

	public String getWarning()
	{
		return null;
	}

	@SuppressWarnings({"OverlyLongMethod", "OverlyNestedMethod"})
	public boolean checkCondition(ConfirmableObject object)
	{
		if (!(object instanceof JurPayment) && !(object instanceof RurPayment))
			return true;
		AbstractPaymentDocument payment = (AbstractPaymentDocument) object;

		// ���� ������ ������������ �� � �����, ��������� ������� ���������� ����� �� �����
		if (payment.getChargeOffResourceType() != ResourceType.CARD)
			return true;

		ServiceProviderShort serviceProvider;
		try
		{
			if(payment instanceof JurPayment)
				serviceProvider = ServiceProviderHelper.getServiceProvider((((JurPayment)payment).getReceiverInternalId()));
			else //RurPayment
			{
				ExtendedAttribute providerAttribute = payment.getAttribute(PaymentFieldKeys.PROVIDER_KEY);
				if(providerAttribute == null)
					return true;
				String providerId = providerAttribute.getStringValue();
				if(StringHelper.isEmpty(providerId))
					return true;
				serviceProvider = ServiceProviderHelper.getServiceProvider((Long.valueOf(providerId)));
			}
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ������ ���������� �����.", e);
			return true;
		}

		if(serviceProvider==null)
		{
			return true;
		}

		if (!(serviceProvider.getKind().equals("B")))
			return true;


		// ��������� �� ��������� � ��������� �����
		if (StringHelper.isEmpty(serviceProvider.getMobilebankCode()))
			return true;

		// ���� �������� ���� RecIdentifier � ������� �� ������, �� � ����� �� �������� �� ��������
		String recIdentifier;
		if(payment instanceof JurPayment)
		{
			ExtendedAttribute identifierAttr = payment.getAttribute(REC_IDENTIFIER);
			recIdentifier = (identifierAttr == null) ? null : identifierAttr.getStringValue();
		}
		else //RurPayment
			recIdentifier = ((RurPayment)payment).getReceiverCard();
		if (StringHelper.isEmpty(recIdentifier))
			return true;

		List<RegistrationProfile> registrationProfiles;
		try
		{
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
			registrationProfiles = mobileBankService.getRegistrationProfiles(documentOwner.getLogin());
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ����������� � ��������� �����.", e);
			return true;
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��� ��������� ����������� � ��������� �����.", e);
			return true;
		}

		if (registrationProfiles == null)
			return true;

		for (RegistrationProfile registrationProfile : registrationProfiles)
		{
			// ���� ��� �����, � ������� ������������ ������, ���� ����������� � ��������� �����, ��������� �������
			if (registrationProfile.getMainCard().getCardNumber().equals(payment.getChargeOffCard()))
			{
				List<SmsCommand> templates = registrationProfile.getMainCard().getPaymentSmsTemplates();
				if (templates != null)
				{
					for (SmsCommand template : templates)
					{
						// �������� ���� ���������� ���������� �����
						if (template.getRecipientCode().equals(serviceProvider.getMobilebankCode()))
						{
							String templateIdentifier = template.getPayerCode();
							if (recIdentifier.equals(templateIdentifier))
								return false;
						}
					}
				}
			}
		}

		return true;
	}
}
