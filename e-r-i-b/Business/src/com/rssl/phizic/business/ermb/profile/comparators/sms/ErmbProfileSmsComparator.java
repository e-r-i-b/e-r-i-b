package com.rssl.phizic.business.ermb.profile.comparators.sms;

import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.HashSet;
import java.util.Set;

/**
 * ����������, ����������� ����� �� ���������� ��� �� ��������� ������ � �������
 * @author Puzikov
 * @ created 12.03.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbProfileSmsComparator implements ErmbSmsChangesComparator<ErmbProfileImpl>
{
	/**
	 * ���������� 2 ������� � ���������� �������� ���������� ����������
	 *
	 * @param oldProfile ������ �������
	 * @param newProfile ����� �������
	 * @return ���������� ���������
	 */
	public Set<String> compare(ErmbProfileImpl oldProfile, ErmbProfileImpl newProfile)
	{
		Set<String> resultFields = new HashSet<String>();

		if (oldProfile == null || newProfile == null)
			return resultFields;

		ErmbPersonSmsComparator comparator = new ErmbPersonSmsComparator();
		Set<String> fioDulDrChanged = comparator.compare(oldProfile.getPerson(), newProfile.getPerson());
		resultFields.addAll(fioDulDrChanged);

		if (oldProfile.getTarif() != null && newProfile.getTarif() != null
				&& !oldProfile.getTarif().getId().equals(newProfile.getTarif().getId()))
			resultFields.add(TARIFF);

		if (oldProfile.isSuppressAdv() != newProfile.isSuppressAdv())
			resultFields.add(ADV);

		CardLink oldForeginProduct = ErmbHelper.getForeginProduct(oldProfile);
		CardLink newForeginProduct = ErmbHelper.getForeginProduct(newProfile);
		Long oldCardId = oldForeginProduct != null ? oldForeginProduct.getId() : null;
		Long newCardId = newForeginProduct != null ? newForeginProduct.getId() : null;
		if ((oldCardId == null && newCardId != null) || (oldCardId != null && !oldCardId.equals(newCardId)))
			resultFields.add(PAYMENT_CARD);

		if (!oldProfile.getPhoneNumbers().equals(newProfile.getPhoneNumbers()))
			resultFields.add(PHONES);
		return resultFields;
	}
}
