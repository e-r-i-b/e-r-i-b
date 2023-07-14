package com.rssl.phizic;

import com.rssl.phizic.person.ClientErmbProfile;
import com.rssl.phizic.person.ErmbProfileService;
import com.rssl.phizic.person.QueryErmbProfileOptions;
import com.rssl.phizic.person.UpdateErmbProfileOptions;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */
class RemoveErmbPhoneRequestProcessor extends CSABackRefRequestProcessorBase<RemoveErmbPhoneRequest, VoidResponse>
{
	private final ErmbProfileService ermbProfileService = new ErmbProfileService();

	protected VoidResponse doProcessRequest(RemoveErmbPhoneRequest request)
	{
		PhoneNumber deletingPhone = request.phone;
		if (deletingPhone == null)
			throw new IllegalArgumentException("�� ������ �������");

		// 1. ���� ����-�������
		QueryErmbProfileOptions queryOptions = new QueryErmbProfileOptions();
		queryOptions.findByActualIdentity = true;
		queryOptions.findByOldIdentity = false;
		ClientErmbProfile profile = ermbProfileService.queryProfile(request.clientIdentity, queryOptions);
		if (profile == null)
			return null;

		// 2. ������� ������� �� �������
		// ���� ��������� ������� �� ������ � ������ ������������, ������ �� ������.
		// ���� ��������� ������� ��������� � ������� ���������, ������� ���������� ������ ���������� �� ����������.
		// ���� ��������� ����� �������� �� �������, ������ �����������.
		if (profile.phones == null || !profile.phones.remove(deletingPhone))
			return null;

		if (deletingPhone.equals(profile.mainPhone))
		{
			if (!CollectionUtils.isEmpty(profile.phones))
				profile.mainPhone = profile.phones.iterator().next();
			else profile.mainPhone = null;
		}

		if (profile.mainPhone == null)
			profile.serviceStatus = false;

		// 3. ��������� ��������� � �������
		UpdateErmbProfileOptions updateOptions = new UpdateErmbProfileOptions();
		updateOptions.notifyMSS = true;
		updateOptions.notifyMBK = true;
		updateOptions.notifyCSA = true;
		updateOptions.notifyCOD = true;
		updateOptions.notifyWAY = true;
		updateOptions.notifyClient = true;
		ermbProfileService.updateProfile(profile, updateOptions);
		return null;
	}

	public String getRequestName()
	{
		return RemoveErmbPhoneRequest.REQUEST_NAME;
	}
}
