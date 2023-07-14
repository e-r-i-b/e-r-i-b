package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.owners.person.Profile;

/**
 * ��������� ������� ���������
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocument extends GateTemplate
{
	/**
	 * @return ������� ��������� ���������
	 */
	Profile getProfile();

	/**
	 * ���������� ��������� ������� ���������
	 * @param profile ��������
	 */
	void setProfile(Profile profile);
}
