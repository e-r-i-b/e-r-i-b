package com.rssl.phizic.operations.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.ext.sbrf.mobilebank.*;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankConstants.MAX_PAYERS_PER_RECIPIENT;

/**
 * @author Erkin
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */

/**
 * �������� �������������� �������� �������� ��
 */
@Deprecated
//todo CHG059738 �������
public class EditSmsTemplateOperation extends MobileBankOperationBase
{
	private static final ServiceProviderService providerService
			= new ServiceProviderService();

	private PaymentTemplateUpdate update;

	private CardLink cardLink = null;

	private List<SmsCommand> newSmsCommands = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� �������� ���������� ������ �������
	 * @param phoneCode - ��� ������ ��������
	 * @param cardCode - ��� ������ �����
	 * @param recipientId - ID ���������� ����� � ����
	 * @param keyFieldValue - �������� ��������� ���� (��� ���� ���������)
	 */
	public void initializeNewAdditiveUpdate(String phoneCode, String cardCode, long recipientId, String keyFieldValue)
			throws BusinessException, BusinessLogicException
	{
		// 1. ���������� ����������
		ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(recipientId);
		if (provider == null)
			throw new BusinessException("�� ������ ��������� ID: " + recipientId);

		// 2. ��������� ����������� ���������� �������
		testAddSmsTemplateAbility(provider.getMobilebankCode(), phoneCode, cardCode);

		// 3. ��������� ��� ���������� � ��� �����������
		String recipientCode = provider.getMobilebankCode();
		@SuppressWarnings({"UnnecessaryLocalVariable"})
		String payerCode = keyFieldValue;

		initializeNewUpdate(UpdateType.ADD, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * ������������� �������� ���������� ������ �������
	 * @param phoneCode - ��� ������ ��������
	 * @param cardCode - ��� ������ �����
	 * @param recipientCode - ��� ���������� ����� � ��������� �����
	 * @param keyFieldValue - �������� ��������� ���� (��� ���� ���������)
	 */
	public void initializeNewAdditiveUpdate(String phoneCode, String cardCode, String recipientCode, String keyFieldValue)
			throws BusinessException, BusinessLogicException
	{
		// 1. ��������� ����������� ���������� �������
		testAddSmsTemplateAbility(recipientCode, phoneCode, cardCode);

		// 2. ��������� ��� �����������
		@SuppressWarnings({"UnnecessaryLocalVariable"})
		String payerCode = keyFieldValue;

		initializeNewUpdate(UpdateType.ADD, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * ������������� �������� �������� �������
	 * @param phoneCode - ��� ������ ��������
	 * @param cardCode - ��� ������ �����
	 * @param recipientCode - ��� ���������� ����� � ��������� �����
	 * @param payerCode - ������������� ���������� � �������� ��
	 */
	public void initializeNewRemovingUpdate(String phoneCode, String cardCode, String recipientCode, String payerCode)
			throws BusinessException, BusinessLogicException
	{
		initializeNewUpdate(UpdateType.REMOVE, phoneCode, cardCode, recipientCode, payerCode);
	}

	/**
	 * ������������� �������� ��� ������ �������
	 * @param updateType - ��� ������� ������ �������� (��������, ������� � �.�.)
	 * @param phoneCode - ��� ������ ��������
	 * @param cardCode - ��� ������ �����
	 * @param recipientCode - ��� ���������� ����� � ��������� �����
	 * @param payerCode - ������������� ���������� � �������� ��
	 */
	private void initializeNewUpdate(
			UpdateType updateType,
			String phoneCode,
			String cardCode,
			String recipientCode,
			String payerCode) throws BusinessException, BusinessLogicException
	{
		if (updateType == null)
			throw new NullPointerException("�������� 'updateType' �� ����� ���� null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("�������� 'phoneCode' �� ����� ���� ������");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("�������� 'cardCode' �� ����� ���� ������");
		if (StringHelper.isEmpty(recipientCode))
			throw new IllegalArgumentException("�������� 'recipientCode' �� ����� ���� ������");
		if (StringHelper.isEmpty(payerCode))
			throw new IllegalArgumentException("�������� 'payerCode' �� ����� ���� ������");

		super.initialize();

		RegistrationShortcut registrationShortcut = getRegistrationShortcut(phoneCode, cardCode);
		if (registrationShortcut == null)
			throw new BusinessException("����������� ����������� � ���������� ����� " +
					"� ����� ������ �������� " + phoneCode + " " +
					"� ����� ������ ����� " + cardCode + ". " +
					"LOGIN_ID=" + getLogin().getId());

		Collection<Pair<String, String>> samples =
				Collections.singleton(new Pair<String, String>(recipientCode, payerCode));
		String destlist = MobileBankUtils.encodeDestlist(samples);

		update = new PaymentTemplateUpdate();
		update.setLogin(getLogin());
		update.setPhoneNumber(registrationShortcut.getPhoneNumber());
		update.setCardNumber(registrationShortcut.getCardNumber());
		update.setDestlist(destlist);
		update.setType(updateType);
	}

	/**
	 * ������������� �������� ��� ����������� ������� SMS-��������
	 * @param id - ID ������� (��. PaymentTemplateUpdate)
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			throw new NullPointerException("Argument 'id' cannot be null");

		super.initialize();

		update = mobileBankService.getUpdate(getLogin(), id);
		if (update == null)
			throw new BusinessException("�� ������ ������ �������� SMS-�������� " +
					"(PaymentTemplateUpdate) ID = " + id);
	}

	public Long getUpdateId()
	{
		return update.getId();
	}

	public PaymentTemplateUpdate getUpdate()
	{
		return update;
	}

	public CardLink getCardLink() throws BusinessException, BusinessLogicException
	{
		if (cardLink == null) {
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			cardLink = MobileBankUtils.selectCardLinkByCardNumber(personData.getCards(), update.getCardNumber(), true);
		}
		return cardLink;
	}

	public List<SmsCommand> getNewSmsCommands() throws BusinessException, BusinessLogicException
	{
		if (newSmsCommands == null)
			newSmsCommands = mobileBankService.getUpdateSmsCommands(update);
		return Collections.unmodifiableList(newSmsCommands);
	}

	/**
	 * ���������� ������� (� ���� ����)
	 */
	@Transactional
	public void saveUpdate() throws SecurityLogicException, BusinessException
	{
		if (update == null)
			throw new IllegalStateException("Operation is not initialized yet");

		mobileBankService.saveUpdate(update);
	}

	/**
	 * �������� ��������� � ��
	 * @throws BusinessException
	 */
	@Transactional
	public void applyUpdate() throws BusinessException, BusinessLogicException
	{
		if (update == null)
			throw new IllegalStateException("Operation is not initialized yet");

		mobileBankService.applyUpdate(update);
	}

	/**
	 * �������� ������� (�� ���� ����)
	 * @throws BusinessException
	 */
	@Transactional
	public void deleteUpdate() throws BusinessException
	{
		if (update != null)
			mobileBankService.deleteUpdate(update);
	}

	private void testAddSmsTemplateAbility(String recipientMobileBankCode, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		RegistrationProfile registration = mobileBankService.getRegistrationProfile(person.getLogin(), phoneCode, cardCode);
		List<SmsCommand> smsPaymentTemplates = registration.getMainCard().getPaymentSmsTemplates();
		int count = 0;
		for (SmsCommand smsPaymentTemplate : smsPaymentTemplates) {
			if (recipientMobileBankCode.equals(smsPaymentTemplate.getRecipientCode()))
				count ++;
		}
		if (count >= MAX_PAYERS_PER_RECIPIENT)
			throw new BusinessLogicException("�� ������ ������� " + MAX_PAYERS_PER_RECIPIENT + " �������� ��� ������ ����������. " +
					"��� ����, ����� �������� ����� ������, ������� ���� �� ������");
	}
}
