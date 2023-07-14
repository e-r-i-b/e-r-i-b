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
 * Операция редактирования заявки на подключения услуги "Мобильный Банк"
 */
public class EditMobileBankRegistrationOperation extends OperationBase implements EditEntityOperation
{
	private static final MobileBankBusinessService mobileBankService = new MobileBankBusinessService();

	private static final DepartmentService departmentService = new DepartmentService();

	private RegistrationHelper helper;

	/**
	 * мапа "номер_карты -> маскированный_номер_карты"
	 */
	private Map<String, String> number2maskCardsMap;

	private MobileBankRegistrationClaim claim;

	private MobileBankTariff tariff;

	private String maskedCardNumber;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализировать операцию для использования на этапе аутентификации
	 * @param authContext - контекст аутентификации
	 */
	public void initializeNewForLogin(AuthenticationContext authContext) throws BusinessException, BusinessLogicException
	{
		helper = new LoginRegistrationHelper(authContext);

		init();
	}

	/**
	 * Инициализировать операцию для использования после успешной аутентификации
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
	 * @return маскированный номер мобильного телефона
	 */
	public String getMaskedPhone()
	{
		return PhoneNumberUtil.getCutPhoneNumber(helper.getPhone());
	}

	/**
	 * @return список маскированных номеров карт
	 */
	public Collection<String> getMaskedCards()
	{
		return number2maskCardsMap.values();
	}

	/**
	 * @param tariff - тариф
	 */
	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	/**
	 * @param card - маскированный номер карты
	 */
	public void setCard(String card)
	{
		this.maskedCardNumber = card;
	}

	/**
	 * Сохранить заявку в базу
	 */
	@Transactional
	public void save() throws BusinessException
	{
		ActivePerson person = helper.getPerson();

		// 1. Определяем реквизиты заявки
		// 1.1 Тариф уже есть

		// 1.2 Номер телефона
		String phoneNumber = helper.getPhone();

		// 1.3 Номер карты
		String cardNumber = null;
		for (Map.Entry<String, String> entry : number2maskCardsMap.entrySet()) {
			if (maskedCardNumber.equals(entry.getValue()))
				cardNumber = entry.getKey();
		}
		if (StringHelper.isEmpty(cardNumber))
			throw new NotFoundException("Не найдена карта " + maskedCardNumber);

		// 1.4 Получаем департамент клиента
		Department department = departmentService.findById(person.getDepartmentId());
		if (department == null)
			throw new NotFoundException("Не найден департамент id=" + person.getDepartmentId());

		// 2. Заполняем заявку
		claim.setLoginId(person.getLogin().getId());
		claim.setDate(Calendar.getInstance());
		claim.setTb(department.getCode().getFields().get("region"));
		claim.setTariff(tariff);
		claim.setPhoneNumber(phoneNumber);
		claim.setCardNumber(cardNumber);
		claim.setCompleted(false);

		// 3. Удаляем предыдущие незаконченные заявки
		mobileBankService.removeUncompletedRegistrationClaims(person.getId());

		// 4. Сохраняем заявку
		mobileBankService.addOrUpdateRegistrationClaim(claim);
	}
}
