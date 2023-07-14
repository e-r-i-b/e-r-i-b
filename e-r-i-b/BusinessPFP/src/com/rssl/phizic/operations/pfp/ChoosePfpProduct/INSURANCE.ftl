select
    {insuranceProduct.*}
from
    PFP_INSURANCE_PRODUCTS insuranceProduct
    left join PFP_IP_TARGET_GROUPS_BUNDLE segment on insuranceProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    insuranceProduct.for_complex = '0'
    and insuranceProduct.ENABLED = '1'    
    <#if clientAge?has_content>
        and (insuranceProduct.MAX_AGE is NULL or insuranceProduct.MAX_AGE >= :clientAge)
        and (insuranceProduct.MIN_AGE is NULL or insuranceProduct.MIN_AGE <= :clientAge)
    </#if>

