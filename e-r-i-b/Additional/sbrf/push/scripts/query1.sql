SELECT COUNT(*) FROM PUSH_DEVICES_STATES_LOG
WHERE
CREATION_DATE >= to_date(:fromDate,'dd.mm.yyyy')
AND CREATION_DATE < to_date(:todate,'dd.mm.yyyy') + 1
AND TYPE = 'A'