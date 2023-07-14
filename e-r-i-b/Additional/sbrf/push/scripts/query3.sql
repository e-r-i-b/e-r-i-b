SELECT COUNT(*) FROM USER_MESSAGES_LOG
WHERE
ADDITION_DATE >= to_date(:fromDate,'dd.mm.yyyy')
AND ADDITION_DATE < to_date(:todate,'dd.mm.yyyy') + 1
AND TYPE = 'push'