package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import com.rssl.phizic.business.ermb.migration.list.MigrationStatus;
import com.rssl.phizic.business.ermb.migration.list.Segment;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rssl.phizic.business.ermb.migration.list.MigrationStatus.NOT_MIGRATED;
import static com.rssl.phizic.business.ermb.migration.list.Segment.*;

/**
 * Мигрируемый клиент из csv
 * @author Puzikov
 * @ created 20.12.13
 * @ $Author$
 * @ $Revision$
 */

public class Client extends MigrationClient
{
	private EnumSet<Segment> segments = EnumSet.noneOf(Segment.class); //флаги принадлежности к сегментам миграции...
	private MigrationStatus status = NOT_MIGRATED;      //статус мигарции
	private Long migrationBlock;                        //блок приложения, в котором необходимо проверить на возможность миграции в этом блоке
	private boolean UDBO;                               //подписан ли УДБО
	private Set<Phone> phones = new HashSet<Phone>();   //телефоны
	private boolean migrationError;                     //были ли ошибка миграции

	//флаги ддя сегментации
	private boolean cardActivity;                       //Была ли активность по картам в мбк.
	private List<String[]> additionalRegistration;
	//(ОПИСАНИЕ МИГРАЦИИ 12)...И по картам этих клиентов, зарегистрированных в МБК, нет операций в Way4 в последние N месяцев (N-параметр)

	public boolean getSegment_1()
	{
		return segments.contains(SEGMENT_1);
	}

	public void setSegment_1(boolean segment_1)
	{
		if (segment_1)
			segments.add(SEGMENT_1);
		else
			segments.remove(SEGMENT_1);
	}

	public boolean getSegment_1_1()
	{
		return segments.contains(SEGMENT_1_1);
	}

	public void setSegment_1_1(boolean segment_1_1)
	{
		if (segment_1_1)
			segments.add(SEGMENT_1_1);
		else
			segments.remove(SEGMENT_1_1);
	}

	public boolean getSegment_1_2()
	{
		return segments.contains(SEGMENT_1_2);
	}

	public void setSegment_1_2(boolean segment_1_2)
	{
		if (segment_1_2)
			segments.add(SEGMENT_1_2);
		else
			segments.remove(SEGMENT_1_2);
	}

	public boolean getSegment_2_1()
	{
		return segments.contains(SEGMENT_2_1);
	}

	public void setSegment_2_1(boolean segment_2_1)
	{
		if (segment_2_1)
			segments.add(SEGMENT_2_1);
		else
			segments.remove(SEGMENT_2_1);
	}

	public boolean getSegment_2_2()
	{
		return segments.contains(SEGMENT_2_2);
	}

	public void setSegment_2_2(boolean segment_2_2)
	{
		if (segment_2_2)
			segments.add(SEGMENT_2_2);
		else
			segments.remove(SEGMENT_2_2);
	}

	public boolean getSegment_2_2_1()
	{
		return segments.contains(SEGMENT_2_2_1);
	}

	public void setSegment_2_2_1(boolean segment_2_2_1)
	{
		if (segment_2_2_1)
			segments.add(SEGMENT_2_2_1);
		else
			segments.remove(SEGMENT_2_2_1);
	}

	public boolean getSegment_3_1()
	{
		return segments.contains(SEGMENT_3_1);
	}

	public void setSegment_3_1(boolean segment_3_1)
	{
		if (segment_3_1)
			segments.add(SEGMENT_3_1);
		else
			segments.remove(SEGMENT_3_1);
	}

	public boolean getSegment_3_2_1()
	{
		return segments.contains(SEGMENT_3_2_1);
	}

	public void setSegment_3_2_1(boolean segment_3_2_1)
	{
		if (segment_3_2_1)
			segments.add(SEGMENT_3_2_1);
		else
			segments.remove(SEGMENT_3_2_1);
	}

	public boolean getSegment_3_2_2()
	{
		return segments.contains(SEGMENT_3_2_2);
	}

	public void setSegment_3_2_2(boolean segment_3_2_2)
	{
		if (segment_3_2_2)
			segments.add(SEGMENT_3_2_2);
		else
			segments.remove(SEGMENT_3_2_2);
	}

	public boolean getSegment_3_2_3()
	{
		return segments.contains(SEGMENT_3_2_3);
	}

	public void setSegment_3_2_3(boolean segment_3_2_3)
	{
		if (segment_3_2_3)
			segments.add(SEGMENT_3_2_3);
		else
			segments.remove(SEGMENT_3_2_3);
	}

	public boolean getSegment_4()
	{
		return segments.contains(SEGMENT_4);
	}

	public void setSegment_4(boolean segment_4)
	{
		if (segment_4)
			segments.add(SEGMENT_4);
		else
			segments.remove(SEGMENT_4);
	}

	public boolean getSegment_5_1()
	{
		return segments.contains(SEGMENT_5_1);
	}

	public void setSegment_5_1(boolean segment_5_1)
	{
		if (segment_5_1)
			segments.add(SEGMENT_5_1);
		else
			segments.remove(SEGMENT_5_1);
	}

	public boolean getSegment_5_2()
	{
		return segments.contains(SEGMENT_5_2);
	}

	public void setSegment_5_2(boolean segment_5_2)
	{
		if (segment_5_2)
			segments.add(SEGMENT_5_2);
		else
			segments.remove(SEGMENT_5_2);
	}

	public boolean getSegment_5_3()
	{
		return segments.contains(SEGMENT_5_3);
	}

	public void setSegment_5_3(boolean segment_5_3)
	{
		if (segment_5_3)
			segments.add(SEGMENT_5_3);
		else
			segments.remove(SEGMENT_5_3);
	}

	public boolean getSegment_5_4()
	{
		return segments.contains(SEGMENT_5_4);
	}

	public void setSegment_5_4(boolean segment_5_4)
	{
		if (segment_5_4)
			segments.add(SEGMENT_5_4);
		else
			segments.remove(SEGMENT_5_4);
	}

	public boolean getSegment_5_5()
	{
		return segments.contains(SEGMENT_5_5);
	}

	public void setSegment_5_5(boolean segment_5_5)
	{
		if (segment_5_5)
			segments.add(SEGMENT_5_5);
		else
			segments.remove(SEGMENT_5_5);
	}

	/**
	 * @return входит ли клиент в любые подсегменты 5 сегмента
	 */
	public boolean getSegment_5()
	{
		return getSegment_5_1() || getSegment_5_2() || getSegment_5_3() || getSegment_5_4() || getSegment_5_5();
	}

	/**
	 * @return входит ли клиент в любые подсегменты 3 сегмента
	 */
	public boolean getSegment_3()
	{
		return getSegment_3_1() || getSegment_3_2_1() || getSegment_3_2_2()	|| getSegment_3_2_3();
	}

	public Set<Segment> getSegments()
	{
		return segments;
	}

	public MigrationStatus getStatus()
	{
		return status;
	}

	public void setStatus(MigrationStatus status)
	{
		this.status = status;
	}

	public boolean getUDBO()
	{
		return UDBO;
	}

	public void setUDBO(boolean UDBO)
	{
		this.UDBO = UDBO;
	}

	public Long getMigrationBlock()
	{
		return migrationBlock;
	}

	public void setMigrationBlock(Long migrationBlock)
	{
		this.migrationBlock = migrationBlock;
	}

	public boolean isCardActivity()
	{
		return cardActivity;
	}

	public void setCardActivity(boolean cardActivity)
	{
		this.cardActivity = cardActivity;
	}

	public boolean isAdditionalCards()
	{
		for (Phone phone : getPhones())
			if (phone.isHasAdditional())
				return true;
		return false;
	}

	public Set<Phone> getPhones()
	{
		return phones;
	}

	public void setPhones(Set<Phone> phones)
	{
		this.phones = phones;
	}

	public boolean isMigrationError()
	{
		return migrationError;
	}

	public void setMigrationError(boolean migrationError)
	{
		this.migrationError = migrationError;
	}

	public void setAdditionalRegistration(List<String[]> additionalRegistration)
	{
		this.additionalRegistration = additionalRegistration;
	}

	public List<String[]> getAdditionalRegistration()
	{
		return additionalRegistration;
	}
}
