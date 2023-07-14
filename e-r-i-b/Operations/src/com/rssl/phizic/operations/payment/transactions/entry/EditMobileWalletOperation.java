package com.rssl.phizic.operations.payment.transactions.entry;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.limits.*;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.SecurityLogicException;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author osminin
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletOperation extends ConfirmableOperationBase
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ProfileService profileService = new ProfileService();
	private static final LimitService limitService = new LimitService();

	private static final String LIMIT_ERROR_MESSAGE = "ћаксимальный размер мобильного кошелька  %s. ¬ы превысили максимальный размер мобильного кошелька.";

	private Money mobileWaletAmount;
	private Profile profile;
	private Limit limit;
	private EditMobileWalletEntry editMobileWalletEntry;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();
		Profile temp = profileService.findByLogin(person.getLogin());
		if (temp == null)
			throw new BusinessException("Ќе найден профиль клиента");

		this.profile = temp;
		this.editMobileWalletEntry = new EditMobileWalletEntry(temp);

		List<Limit> limits = limitService.findLimits(departmentService.findById(person.getDepartmentId()), LimitType.USER_POUCH, ChannelType.MOBILE_API, null, Status.ACTIVE, person.getSecurityType());
		if (CollectionUtils.isEmpty(limits))
			throw new BusinessException("не задано ограничение на размер ћобильного кошелька дл€ подразделени€ departmentId = " + person.getDepartmentId());

		if (limits.size() != 1)
		    throw new BusinessException("дл€ подразделени€ departmentId = " + person.getDepartmentId() + " найдено больше одного ћобильного кошелька");

		this.limit = limits.get(0);
	}

	public void initialize(String value) throws BusinessException, BusinessLogicException
	{
		initialize();

		Money newAmount = new Money(new BigDecimal(value.replace(" ","")), MoneyUtil.getNationalCurrency());
		if (newAmount.compareTo(limit.getAmount()) > 0)
			throw new BusinessLogicException(String.format(LIMIT_ERROR_MESSAGE, limit.getAmount().getDecimal()));

		this.mobileWaletAmount = newAmount;
		editMobileWalletEntry.setMobileWalletValue(newAmount);
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		profileService.updateMobileWallet(profile, mobileWaletAmount);
	}

	public Limit getLimit()
	{
		return limit;	
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return new SmsPasswordConfirmStrategy();
	}

	public ConfirmableObject getConfirmableObject()
	{
		return editMobileWalletEntry;
	}
}
