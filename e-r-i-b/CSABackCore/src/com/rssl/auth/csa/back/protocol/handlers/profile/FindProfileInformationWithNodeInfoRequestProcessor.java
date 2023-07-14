package com.rssl.auth.csa.back.protocol.handlers.profile;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.transmiters.Pair;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 */

public class FindProfileInformationWithNodeInfoRequestProcessor extends FindProfileInformationRequestProcessor
{
	private static final String REQUEST_TYPE  = "findProfileInformationWithNodeInfoRq";
	private static final String RESPONSE_TYPE = "findProfileInformationWithNodeInfoRs";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected void addNodeInfo(ResponseBuilderHelper responseBuilder, Profile profile) throws Exception
	{
		Pair<ProfileNode, ProfileNode> profileNodes = Utils.getFullNodeInfo(profile);
		ProfileNode mainProfileNode = profileNodes.getFirst();
		ProfileNode waitMigrationProfileNode = profileNodes.getSecond();

		responseBuilder.openTag(Constants.NODE_INFO_TAG)
				.addParameter(Constants.NODE_ID_TAG, mainProfileNode.getNode().getId());

		if (waitMigrationProfileNode != null)
			responseBuilder.addParameter(Constants.WAIT_MIGRATION_NODE_ID_TAG, waitMigrationProfileNode.getNode().getId());

		responseBuilder.closeTag();
	}
}
