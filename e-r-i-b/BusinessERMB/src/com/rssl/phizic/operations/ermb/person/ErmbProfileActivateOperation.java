package com.rssl.phizic.operations.ermb.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.CodWayErmbConnectionSender;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.operations.OperationBase;

/**
 * ��������� �������
 * @author Puzikov
 * @ created 26.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbProfileActivateOperation extends OperationBase
{
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private ErmbProfileImpl profile;

	/**
	 * ������������� ��������
	 * @param personId id
	 */
	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl ermbProfile = profileService.findByPersonId(personId);
		if (ermbProfile == null)
			throw new BusinessException("�� ������ �������");

		if (!ermbProfile.isServiceStatus())
			throw new BusinessLogicException("���������� ������������ �������������� �������");
		if (ermbProfile.isCodActivated() && ermbProfile.isWayActivated())
			throw new BusinessLogicException("������� ��� �����������");

		this.profile = ermbProfile;
	}

	/**
	 * ��������� ���������
	 */
	public void activate() throws BusinessException, BusinessLogicException
	{
		boolean needCodActivate = !profile.isCodActivated();
		boolean needWayActivate = !profile.isWayActivated();

		boolean wayActivationEnabled = ConfigFactory.getConfig(ErmbConfig.class).isActivateErmbToWay4();
		boolean onlyWayToActivate = !needCodActivate && needWayActivate;
		if (onlyWayToActivate && !wayActivationEnabled)
			throw new BusinessLogicException("���������� ������������ � Way. ��������� � Way ��������� � ���������� �������");

		CodWayErmbConnectionSender sender = new CodWayErmbConnectionSender();
		sender.sendErmbConnected(profile);

		checkActivationStatus();
	}

	private void checkActivationStatus() throws BusinessLogicException
	{
		if (profile.isWayActivated() && profile.isCodActivated())
			return;

		StringBuilder error = new StringBuilder("�� ������� ������������");
		if (!profile.isCodActivated())
			error.append(" ���");
		if (!profile.isWayActivated())
			error.append(" Way");
		throw new BusinessLogicException(error.toString());
	}
}
