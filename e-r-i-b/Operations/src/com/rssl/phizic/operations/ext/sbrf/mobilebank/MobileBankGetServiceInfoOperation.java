package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.IdsTemplatesFilter;
import com.rssl.phizic.business.documents.templates.service.filters.MobileBankTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateShortcut;
import com.rssl.phizic.business.ext.sbrf.mobilebank.RegistrationShortcut;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.operations.ext.sbrf.payment.ConstantServicePaymentInfoSource;
import com.rssl.phizic.operations.ext.sbrf.payment.GetPaymentServiceInfoOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ServicePaymentInfoSource;
import com.rssl.phizic.utils.MaskUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankGetServiceInfoOperation extends GetPaymentServiceInfoOperation
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private CardLink cardlink;
	private ServicePaymentInfoSource paymentInfoSource;
	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� ��������
	 * @param serviceId - id ������
	 * @param phoneCode - ��� ������ �������� ����������� ��-�����������
	 * @param cardCode - ��� ������ ����� ����������� ��-�����������
	 * @param recipientId - id ����������
	 * @param keyFields - �������� ����
	 * @throws BusinessException
	 */
	public void initialize(Long serviceId,
	                       String phoneCode,
	                       String cardCode,
	                       Long recipientId,
	                       Map<String, Object> keyFields)
			throws BusinessException, BusinessLogicException
	{
		super.initialize(serviceId);
		setMobilebank(true);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();

		RegistrationShortcut registration = mobileBankService.getRegistrationShortcut(person.getLogin(), phoneCode, cardCode);
		if (registration == null)
			throw new BusinessException("�� ������� ����������� � ���������� ����� " +
					"�� ���� ������ �������� " + phoneCode + " � ���� ����� " + cardCode);

		String cardNumber = registration.getCardNumber();
		cardlink = personData.findCard(cardNumber);
		if (cardlink == null)
			throw new BusinessException("�� ������� ����� " + MaskUtil.getCutCardNumber(cardNumber));
		CardState cardState = cardlink.getCard().getCardState();
		if(cardState == CardState.blocked || cardState == CardState.delivery)
			throw new BusinessLogicException("�� �� ������ ������� SMS-������, ������ ��� �� ����� ����� ��������� ��������� ��������.");
		paymentInfoSource =
				new ConstantServicePaymentInfoSource(serviceId, recipientId, keyFields);
	}

	public void initialize(Long templateId, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		PaymentTemplateShortcut template = findPaymentTemplate(templateId);
		Map<String,Object> keyFields = new HashMap<String,Object>();
		keyFields.put(template.getKeyFieldName(),template.getPayerCode());
		initialize(null,phoneCode,cardCode,template.getProviderId(),keyFields);
	}

	public List<PaymentAbilityERL> getChargeOffResources() throws BusinessException
	{
		if (cardlink == null)
			throw new IllegalStateException("�� ������� �����");
		return Collections.singletonList((PaymentAbilityERL)cardlink);
	}

	/**
	 * ���������� source, ������� ������ ��� ���� ������������������ � initialize
	 * @return source
	 */
	public ServicePaymentInfoSource getPaymentInfoSource()
	{
		return paymentInfoSource;
	}

	/**
	 * ���������� ������ ������� �������� ������������ �� ��������� ID
	 * @param id - ID ������� �������
	 * @return ������ �������
	 * @throws BusinessException
	 */
	private PaymentTemplateShortcut findPaymentTemplate(final long id) throws BusinessException, BusinessLogicException
	{
		TemplateDocument template = TemplateDocumentService.getInstance().findSingle(PersonHelper.getContextPerson().asClient(), new IdsTemplatesFilter(Collections.<Long>singletonList(id)), new ReadyToPaymentTemplateFilter(), new MobileBankTemplateFilter());
		if (template == null)
		{
			throw new BusinessException("�� ������ ������ �������. ID = " + id);
		}

		return new PaymentTemplateShortcut(template);
	}
}
