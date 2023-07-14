package com.rssl.phizic.job.fpp;

import com.rssl.phizic.business.ermb.ErmbChargeDateUpdater;
import com.rssl.phizic.business.ermb.ErmbCostCalculator;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbSubscribeFeeConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * User: Moshenko
 * Date: 04.06.14
 * Time: 10:32
 * Джобы по выгрузке ФПП. Запускаются джобом-координатором
 * Выполняют непосредственно выгрузку.
 * Одновременно может быть запущено несколько джобов выгрузки.
 */
public class FPPUnloadJob  implements Job
{
	private final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);
	private final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final ErmbChargeDateUpdater updater = new ErmbChargeDateUpdater();


	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		ApplicationInfo.setCurrentApplication(Application.Scheduler);

		try
		{
			//количество процессов выгрузки ФПП
			int fppProcTotalCount = ConfigFactory.getConfig(ErmbSubscribeFeeConfig.class).getFppProcTotalCount();
			//размер пачки для выбора профилей
			int oneJobPackSize = 10000/fppProcTotalCount;
			//максимальное количество профилей для которых будет производится сортировка через dbms_random
			int allJobPackSize = fppProcTotalCount * oneJobPackSize;
			doExecute(allJobPackSize,oneJobPackSize);
		}
		catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            ApplicationInfo.setDefaultApplication();
        }
	}

	private void doExecute(final int allJobPackSize, final int oneJobPackSize)
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Calendar currentDate = Calendar.getInstance();
					FPPWriter fppWriter = new FPPWriter(currentDate);
					try
					{
						while (true)
						{
							List<ErmbProfileImpl> profiles = profileService.getSubscribeFeeProfiles(currentDate, allJobPackSize, oneJobPackSize);

							if (CollectionUtils.isEmpty(profiles))
								break;

							for (ErmbProfileImpl profile : profiles)
							{
								try
								{
									ErmbCostCalculator calculator = new ErmbCostCalculator(profile);
									Money cost = calculator.calculate();

									if (BigDecimal.ZERO.compareTo(cost.getDecimal()) == 0)
									{
										updater.shiftChargeDate(profile);
									}
									else
									{
										log.error("BUG082388: Обработка профиля с id=" + profile.getId());
										//записываем профиль в фпп-файл
										fppWriter.writeSubscribeFee(profile, cost);
										//устанавливаем в профиль процесс списания в true и дату выгрузки ФПП
										profile.setFppInProgress(true);
										profile.setFppUnloadDate(currentDate);
									}
									//сохраняем профиль
									log.error("BUG082388: Сохранение профиля с id=" + profile.getId());
									session.saveOrUpdate(profile);
									session.flush();
								}
								catch (Exception e)
								{
									log.error("Не удалось обработать ермб-профиль для выгрузки фпп по списанию абонентской платы профиля id=" + profile.getId(), e);
									//профиль недоступен для списания абонентской платы
									profile.setFppBlocked(true);
									//сохраняем профиль
									session.saveOrUpdate(profile);
									session.flush();
								}
							}
						}
					}
					catch (Exception e)
					{
						fppWriter.rollback();
						throw e;
					}
					fppWriter.commit();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
