package com.rssl.phizic.business.ext.sbrf.technobreaks;

import com.rssl.phizic.business.ext.sbrf.technobreaks.event.UpdateTechnoBreaksEvent;
import com.rssl.phizic.events.EventHandler;

/**
 * @author akrenev
 * @ created 20.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� ��������� ������ ���. ��������� � �������
 */

public class UpdateTechnoBreaksHandler implements EventHandler<UpdateTechnoBreaksEvent>
{
	public void process(UpdateTechnoBreaksEvent event) throws Exception
	{
		TechnoBreaksService.doClearCache(event.getExternalSystemCode());
	}
}