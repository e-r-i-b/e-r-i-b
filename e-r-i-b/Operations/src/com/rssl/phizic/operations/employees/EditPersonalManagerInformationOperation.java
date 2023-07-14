package com.rssl.phizic.operations.employees;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.ChannelService;

import java.util.List;

/**
 * @author komarov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPersonalManagerInformationOperation extends EditEmployeeOperation
{
	private static final ChannelService  channelService   = new ChannelService();

	private List<Channel> channels;

	/**
	 * ���������������� �������� ������� �����������
	 */
    public void initialize() throws BusinessException, BusinessLogicException
    {
	    initializeCurrentEmployee();
	    channels = channelService.getActiveChannels();
    }

	/**
	 * ���������������� �������� ������������ �����������
	 * @param employeeId - id ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException ��������� �� ������
	 */
    public void initialize(Long employeeId) throws BusinessException, BusinessLogicException
    {
	    super.initialize(employeeId);
	    channels = channelService.getActiveChannels();
    }

	/**
	 * @return ������ ��������� �������
	 */
	public List<Channel> getChannels()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return channels;
	}
}
