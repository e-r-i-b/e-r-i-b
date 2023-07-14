package com.rssl.phizic.business.cards;

import com.rssl.phizic.utils.StringHelper;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 04.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class CardDescriptionWrapper
{
	private static final Map<String, String> imageName = new HashMap<String, String>();
	public static final String DEFAULT_CARD_IMAGE_NAME = "default";

	static
	{
		imageName.put("MASTERCARD GOLD", "mg");
		imageName.put("MASTERCARD PLATINUM", "mp");
		imageName.put("MASTERCARD WORLD BLACK EDITION PREMIER", "mpb");
		imageName.put("MASTERCARD WORLD ELITE SBERBANK1", "mcwe");
		imageName.put("MAESTRO", "cm");
		imageName.put("ELECTRON", "ve");
		imageName.put("VISA INFINITE", "vi");
		imageName.put("VISA INFINITE SBERBANK1", "vis");
		imageName.put("PRO100 INST ISS", "pr");
		imageName.put("PRO100", "ps");
		imageName.put("VISA CLASSIC", "vc");
		imageName.put("VISA BUSINESS", "vb");
		imageName.put("VISA GOLD", "vg");
		imageName.put("VISA PLATINUM", "vp");
		imageName.put("VISA PLATINUM PREMIER", "vpp");
		imageName.put("MASTERCARD MASS", "ms");
		imageName.put("MASTERCARD BUSINESS", "mb");
		imageName.put("WORLD MASTERCARD «ŒÀŒ“Œ…", "mg");
		imageName.put("VISA GOLD «ŒÀŒ“Œ…", "vg");
		imageName.put("VISA CLASSIC ¡≈— ŒÕ“¿ “Õ¿ﬂ", "vc");
	}

	public static String getImageName(String cardDescription)
	{
		String key = StringHelper.isNotEmpty(cardDescription) ? cardDescription.toUpperCase() : cardDescription;
		if (key.contains("VISA GOLD"))
			key = "VISA GOLD";
		if (key.contains("WORLD MASTERCARD"))
			key = "MASTERCARD GOLD";
		String imName = imageName.get(key);
		return StringHelper.isEmpty(imName) ? DEFAULT_CARD_IMAGE_NAME : imName;
	}
}
