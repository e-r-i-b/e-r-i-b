package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.gate.dictionaries.MembersGateService;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.ContactMember;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Iterator;

/**
 * User: novikov_a
 * Сервис для получения списка Контактовских точек  из внешней системы
 * Date: 17.09.2009
 * Time: 16:04:56
 */
public class GateInterfaceMembersReplicaSource implements ReplicaSource<ContactMember>
{
    private MembersGateService service;

	public void initialize(GateFactory factory) throws GateException
	{
		service = factory.service(MembersGateService.class);
	}

	public Iterator<ContactMember> iterator() throws GateException
	{
		return new ReplicationServiceIterator<ContactMember>(service);
	}

	public void close() {}
}
