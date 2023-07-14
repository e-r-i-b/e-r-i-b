package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.cms.BlockReason;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 12.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class BlockReasonWrapper
{
	private static final Map<BlockReason, String> blockReason = new HashMap<BlockReason, String>();

	static
	{
		blockReason.put(BlockReason.lost, "LOST");
		blockReason.put(BlockReason.steal, "STOLEN");
		blockReason.put(BlockReason.atm, "ATM");
		blockReason.put(BlockReason.other, "HOLDER");
	}

	public static String getBlockReason(BlockReason enumBlockReason)
	{
		if (enumBlockReason == null)
			return null;
		return blockReason.get(enumBlockReason);
	}
}