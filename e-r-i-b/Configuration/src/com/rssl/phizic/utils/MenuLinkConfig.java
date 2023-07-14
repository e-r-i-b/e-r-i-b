package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author bogdanov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MenuLinkConfig extends Config
{
	private static final String MAIN_MENU_LINKS_COUNT              = "com.rssl.iccs.mainMenu.count";
	public static final String MAIN_MENU_LINK_MODULE               = "com.rssl.iccs.mainMenu.module.";
	public static final String MAIN_MENU_LINK_SERVICE              = "com.rssl.iccs.mainMenu.service.";
	public static final String MAIN_MENU_LINK_NEGATIVE_SERVICE     = "com.rssl.iccs.mainMenu.negative.service.";
	public static final String MAIN_MENU_LINK_ADDITIONAL_SERVICE   = "com.rssl.iccs.mainMenu.additional.service.";
	public static final String DELIMITER                           = ",";
	public static final String MAIN_MENU_LINK_OPERATION            = "com.rssl.iccs.mainMenu.operation.";
	public static final String MAIN_MENU_LINK_TEXT                 = "com.rssl.iccs.mainMenu.text.";
	public static final String MAIN_MENU_LINK_ACTION               = "com.rssl.iccs.mainMenu.action.";
	public static final String MAIN_MENU_LINK_ACTIVITY             = "com.rssl.iccs.mainMenu.activity.";
	public static final String MAIN_MENU_RESOURCE_TYPE             = "com.rssl.iccs.mainMenu.resourceType.";
	public static final String MAIN_MENU_LINK_NOVELTY              = "com.rssl.iccs.mainMenu.link.novelty.";
	public static final String MAIN_MENU_LINK_CONDITION            = "com.rssl.iccs.mainMenu.link.condition.";
	public static final String MAIN_MENU_AND_ONE_OF                = "com.rssl.iccs.mainMenu.and.one.of.service.";

	private int linkCount;

	public MenuLinkConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		linkCount = getIntProperty(MAIN_MENU_LINKS_COUNT);
	}

	public int getLinkCount()
	{
		return linkCount;
	}
}
