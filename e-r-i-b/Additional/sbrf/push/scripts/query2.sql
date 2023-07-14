SELECT type1, type2, type3, type45 FROM

	(SELECT count(distinct login_ID) as type1 FROM USER_NOTIFICATION_LOG 
		WHERE value = 'push'
		AND ADDITION_DATE >= to_date(:fromDate,'dd.mm.yyyy') AND ADDITION_DATE < to_date(:todate,'dd.mm.yyyy') + 1 
		AND type = 'loginNotification') count1, 

	(SELECT count(distinct login_ID) as type2 FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date(:fromDate,'dd.mm.yyyy') AND ADDITION_DATE < to_date(:todate,'dd.mm.yyyy') + 1 
		AND type = 'mailNotification') count2, 

	(SELECT count(distinct login_ID) as type3 FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date(:fromDate,'dd.mm.yyyy') AND ADDITION_DATE < to_date(:todate,'dd.mm.yyyy') + 1 
		AND type = 'operationNotification') count3, 

	(SELECT count(distinct login_ID) as type45 FROM USER_NOTIFICATION_LOG 
		where value = 'push'
		AND ADDITION_DATE >= to_date(:fromDate,'dd.mm.yyyy') AND ADDITION_DATE < to_date(:todate,'dd.mm.yyyy') + 1 
		AND type = 'operationConfirm') count45