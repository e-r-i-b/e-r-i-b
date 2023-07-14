package com.rssl.phizic.operations.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.NotFoundException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService;
import com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankRegistrationClaim;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * @author Erkin
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������������� ������ �� ����������� ������ "��������� ����"
 */
public class EditMobileBankRegistrationOperation extends OperationBase implements EditEntityOperation
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private static final DepartmentService departmentService = new DepartmentService();

	private RegistrationHelper helper;

	/**
	 * ���� "�����_����� -> �������������_�����_�����"
	 */
	private Map<String, String> number2maskCardsMap;

	private MobileBankRegistrationClaim claim;

	private MobileBankTariff tariff;

	private String maskedCardNumber;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������������� �������� ��� ������������� �� ����� ��������������
	 * @param authContext - �������� ��������������
	 */
	public void initializeNewForLogin(AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init();
	}

	/**
	 * ���������������� �������� ��� ������������� ����� �������� ��������������
	 */
	public void initializeNew() throws BusinessException, BusinessLogicException
	{
		helper = new AuthenticatedRegistrationHelper();

		init();
	}

	private void init() throws BusinessException
	{
		claim = new MobileBankRegistrationClaim();
		number2maskCardsMap = helper.buildNumber2MaskCardsMap();
	}

	public MobileBankRegistrationClaim getEntity()
	{
		return claim;
	}

	/**
	 * @return ������������� ����� ���������� ��������
	 */
	public String getMaskedPhone()
	{
		return PhoneNumberUtil.getCutPhoneNumber(helper.getPhone());
	}

	/**
	 * @return ������ ������������� ������� ����
	 */
	public Collection<String> getMaskedCards()
	{
		return number2maskCardsMap.values();
	}

	/**
	 * @param tariff - �����
	 */
	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	/**
	 * @param card - ������������� ����� �����
	 */
	public void setCard(String card)
	{
		this.maskedCardNumber = card;
	}

	/**
	 * ��������� ������ � ����
	 */
	@Transactional
	public void save() throws BusinessException
	{
		ActivePerson person = helper.getPerson();

		// 1. ���������� ��������� ������
		// 1.1 ����� ��� ����

		// 1.2 ����� ��������
		String phoneNumber = helper.getPhone();

		// 1.3 ����� �����
		String cardNumber = null;
		for (Map.Entry<String, String> entry : number2maskCardsMap.entrySet()) {
			if (maskedCardNumber.equals(entry.getValue()))
				cardNumber = entry.getKey();
		}
		if (StringHelper.isEmpty(cardNumber))
			throw new NotFoundException("�� ������� ����� " + maskedCardNumber);

		// 1.4 �������� ����������� �������
		Department department = departmentService.findById(person.getDepartmentId());
		if (department == null)
			throw new NotFoundException("�� ������ ����������� id=" + person.getDepartmentId());

		// 2. ��������� ������
		claim.setLoginId(person.getLogin().getId());
		claim.setDate(Calendar.getInstance());
		claim.setTb(department.getCode().getFields().get("region"));
		claim.setTariff(tariff);
		claim.setPhoneNumber(phoneNumber);
		claim.setCardNumber(cardNumber);
		claim.setCompleted(false);

		// 3. ������� ���������� ������������� ������
		mobileBankService.removeUncompletedRegistrationClaims(person.getId());

		// 4. ��������� ������
		mobileBankService.addOrUpdateRegistrationClaim(claim);
	}
}
