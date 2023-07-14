update SERVICE_PROVIDERS sp 
set sp.PLANING_FOR_DEACTIVATE = :planForInactive 
where sp.BILLING_ID = :billingId

