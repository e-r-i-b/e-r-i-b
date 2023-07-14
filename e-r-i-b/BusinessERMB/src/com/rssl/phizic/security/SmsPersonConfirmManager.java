package com.rssl.phizic.security;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.payment.BlockingCardTaskImpl;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ��������� ������������� ��� ���-������ ����
 *  
 * ���������� �� ����, � ��� confirm-����:
 * ������ � ���� "��� �������������" �������� ���������� ��� �������������
 * ������ � ���� "��� �������������" �������� ����� ���-��������� � ����� �������������
 * ��������� ����� � ����� � ��������:
 * - ��������� � ����� �������������, ��������� �������, ����� �������� ����� �������������
 */
public class SmsPersonConfirmManager extends PersonConfirmManagerImpl
{
	private final OneTimePasswordGeneratorSmsChannel passwordGenerator = new OneTimePasswordGeneratorSmsChannel();

	/**
	 * ctor
	 * @param module - ������
	 * @param person - ������
	 */
	public SmsPersonConfirmManager(Module module, Person person)
	{
		super(module, person);
	}

	@Override
	protected ConfirmCodeMessage createConfirmCode(final ConfirmableTask confirmableTask, String phoneNumber)
	{
		ConfirmBean confirmBean = createConfirmBean(confirmableTask);
		confirmBean.setPhone(phoneNumber);

		ConfirmCodeMessage confirmCodeMessage = null;
		for (int i=0; i < GENERATE_CODE_MAX_ATTEMPTS; i++)
		{
			String confirmCode = passwordGenerator.generate();
			confirmCodeMessage = buildConfirmCodeMessage(confirmCode, confirmableTask);
			String confirmText = confirmCodeMessage.getText();
			//����� ��� ����������� ������ ��������� ��� �������������, �.�. �� ��� ������� �������� ������������� � ���-������.
			//������� � �����������:
			//"�� ������� ����� ��������� ������������� �������� � SMS-���������, ��������� ����������� ���������, ������������ �� ����� ��� ���������� ��� �������������"
			if (confirmText == null || !confirmText.contains(confirmCode))
				throw new InternalErrorException("��� � ����� ������������� �� �������� ������ ���� �������������");

			confirmBean.setPrimaryConfirmCode(confirmCode);
			confirmBean.setSecondaryConfirmCode(confirmText);

			if (confirmBeanService.addConfirmBean(confirmBean))
				break;

			confirmBean.setPrimaryConfirmCode(null);
			confirmBean.setSecondaryConfirmCode(null);
			confirmCodeMessage = null;
		}

		if (confirmCodeMessage == null)
		{
			// ���� ���� ������, ������, ��� ������� ��������� ������ ����������� ConstraintViolationException
			throw new InternalErrorException("�� ������� ������������� ��� ������������� ��� " + confirmBean);
		}

		return confirmCodeMessage;
	}

	public ConfirmToken captureConfirm(String confirmCode, String phone, boolean primary)
	{
		ConfirmBean confirmBean = confirmBeanService.captureConfirmBean(getPerson(), confirmCode, phone, primary);
		if (confirmBean == null)
			return null;

		boolean isBlockingCard = StringHelper.equals(confirmBean.getConfirmableTaskClass().getName(), BlockingCardTaskImpl.class.getName());
		// ���� confirm-��� �������, �������, ��� ��� ������������� �� ������, ����� ���������� ����, �� ��������� ����
		if (confirmBean.isExpired() && !isBlockingCard)
			return null;

		return confirmBean;
	}
}
