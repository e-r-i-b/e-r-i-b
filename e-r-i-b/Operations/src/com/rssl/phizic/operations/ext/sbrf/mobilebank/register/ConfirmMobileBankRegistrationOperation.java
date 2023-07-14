package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.PhoneNumberUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.Map;

/**
 * @author Erkin
 * @ created 12.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmMobileBankRegistrationOperation extends ConfirmableOperationBase
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private RegistrationHelper helper;

	private MobileBankRegistrationClaim claim;

	private String maskedCard = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������������� �������� ��� ������������� �� ����� ��������������
	 * @param claimId - ID ������
	 * @param authContext - �������� ��������������
	 */
	public void initializeForLogin(Long claimId, AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init(claimId);
	}

	/**
	 * ���������������� �������� ��� ������������� ����� �������� ��������������
	 * @param claimId - ID ������
	 */
	public void initialize(Long claimId) throws BusinessException, BusinessLogicException
	{
		helper = new AuthenticatedRegistrationHelper();

		init(claimId);
	}

	private void init(Long claimId) throws BusinessException
	{
		claim = mobileBankService.getRegistrationClaim(claimId);
		if (claim == null)
			throw new NotFoundException("�� ������� ������ �� ����������� ��. id=" + claimId);
	}

	/**
	 * @return ID ������
	 */
	public long getId()
	{
		return claim.getId();
	}

	/**
	 * @return �����
	 */
	public MobileBankTariff getTariff()
	{
		return claim.getTariff();
	}

	/**
	 * @return ������������� ����� ��������
	 */
	public String getMaskedPhone()
	{
		return PhoneNumberUtil.getCutPhoneNumber(claim.getPhoneNumber());
	}

	/**
	 * @return ������������� ����� �����
	 */
	public String getMaskedCard() throws BusinessException
	{
		if (maskedCard == null)
		{
			Map<String, String> number2maskCardsMap = helper.buildNumber2MaskCardsMap();
			maskedCard = number2maskCardsMap.get(claim.getCardNumber());
			if (maskedCard == null)
				throw new NotFoundException("�� ������� ����� " + claim.getCardNumber());
		}
		return maskedCard;
	}

	public PreConfirmObject preConfirm() throws SecurityLogicException, BusinessLogicException, BusinessException
	{
		CallBackHandler callBackHandler = new CallBackHandlerSmsImpl();
		callBackHandler.setLogin(helper.getPerson().getLogin());
		callBackHandler.setConfirmableObject(getConfirmableObject());
		// ��� �������� ��� ���������� ������ ������� � ������ �������
		callBackHandler.setUseRecipientMobilePhoneOnly(true);

		return preConfirm(callBackHandler);
	}

	public MobileBankRegistrationClaim getConfirmableObject()
	{
		return claim;
	}

	/**
	 * ��������� � ��������� ������
	 * ����� � ����� ���� �� ������� ����������, ������� - �� ������ �������
	 */
	protected void saveConfirm() throws BusinessException, BusinessLogicException
	{
		// 1. ���������� ������ � ��
		try
		{
			mobileBankService.sendRegistrationClaim(helper.getPerson(), claim);
		}
		catch (BusinessException e)
		{
			log.error("���� ��� �������� ������ �� ����������� � ��", e);
			throw new BusinessLogicException(formatBadNewsMessage(), e);
		}

		// 2. ��������� ������
		claim.setCompleted(true);
		mobileBankService.addOrUpdateRegistrationClaim(claim);
	}

	/**
	 * @return ����� ��������� �� ������� ������������ ������
	 */
	public String formatGoodNewsMessage() throws BusinessException
	{
		return "������ �� ����������� ������ ��������� ���� " +
				"�� ����� " + StringEscapeUtils.escapeHtml(getMaskedCard()) + " " +
				"�� ������� " + StringEscapeUtils.escapeHtml(getMaskedPhone()) + " �������. " +
				"��������� � ����������� ����� ���������� � ������� �����. " +
				"��������� ����������� ������� ����� �������� ������ ����� ����������� ������ ��������� ����.";
	}

	private String formatBadNewsMessage()
	{
		return "������ �� ����������� ������ ��������� ���� �� ����������������. " +
				"����������, ��������� �������� �����.";
	}
}
