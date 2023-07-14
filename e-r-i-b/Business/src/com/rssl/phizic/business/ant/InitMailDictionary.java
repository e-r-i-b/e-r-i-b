package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.mail.*;
import com.rssl.phizic.utils.test.SafeTaskBase;

import java.util.List;
import java.util.ArrayList;

/**
 * Класс для загрузки значения Enum в базу.
 * @author komarov
 * @ created 20.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class InitMailDictionary extends SafeTaskBase
{
	private List<MailEnumLoader> list = new ArrayList<MailEnumLoader>();
	private static final SimpleService simpleService = new SimpleService();
	private static final List<Class<? extends Enum<? extends  EnumWithDescription>>> enums =
			new ArrayList<Class<? extends Enum<? extends  EnumWithDescription>>>();
	static
	{
		enums.add(RecipientMailState.class);
		enums.add(MailState.class);
		enums.add(RecipientType.class);
		enums.add(MailType.class);
		enums.add(MailDirection.class);
		enums.add(MailResponseMethod.class);
	}


	private void initList()
	{
		for(Class<? extends Enum<? extends  EnumWithDescription>> mailEnum:enums)
		{
			for(Enum state: mailEnum.getEnumConstants())
			{
				list.add(new MailEnumLoader(state.name(), mailEnum.getName(), ((EnumWithDescription)state).getDescription()));
			}
		}
	}

	public void safeExecute() throws Exception
	{
		initList();
		simpleService.removeList(simpleService.getAll(MailEnumLoader.class));
		for(MailEnumLoader state: list)
		{
			simpleService.addOrUpdate(state);
		}
	}
}
