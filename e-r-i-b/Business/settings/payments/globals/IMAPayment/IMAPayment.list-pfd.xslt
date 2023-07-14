<?xml version="1.0" encoding="windows-1251"?>
<payment-list xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="../PaymentList.xsd"
		title="Список переводов переводов с использованием ОМС">

	<filter>
        <fields>
            <field/>
        </fields>
    </filter>
    <hql-query>
		<returnKey property="id"/>
		<return    property="dateCreated"/>
		<![CDATA[
			select payment
			from com.rssl.phizic.business.documents.InternalTransfer as payment
			where payment.formName = 'IMAPayment'
			    and payment.owner.id = :loginId
				and (:state is null           or :state = '' or payment.state.code = :state)
				and (:fromDate is null        or payment.dateCreated >= :fromDate)
				and (:toDate is null          or payment.dateCreated <= :toDate  )
            order by payment.dateCreated asc, payment.id desc
	    ]]>
    </hql-query>

</payment-list>