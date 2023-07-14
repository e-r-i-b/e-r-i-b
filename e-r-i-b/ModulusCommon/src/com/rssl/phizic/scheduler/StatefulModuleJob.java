package com.rssl.phizic.scheduler;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.module.Module;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author Gulov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ���� ������, ������������ ��������� ���������
 */
public abstract class StatefulModuleJob extends ModuleJob implements StatefulJob
{
}
