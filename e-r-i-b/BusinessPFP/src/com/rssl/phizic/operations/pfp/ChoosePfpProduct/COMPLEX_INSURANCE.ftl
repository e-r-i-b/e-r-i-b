select
    {complexProduct.*}
from
    PFP_COMPLEX_PRODUCTS complexProduct
    join PFP_PRODUCTS product on complexProduct.account_id = product.id
    left join PFP_CP_TARGET_GROUPS_BUNDLE segment on complexProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    complexProduct.type = 'I'
 and
    (select
        count(ins_product.id)
    from
        PFP_INSURANCE_PRODUCTS ins_product
    join
        PFP_C_INSURANCE_PRODUCTS c_ins_prod on ins_product.ID = c_ins_prod.INSURANCE_ID
    where
        c_ins_prod.C_PRODUCT_ID = complexProduct.id
        <#if clientAge?has_content>
            and (ins_product.MAX_AGE is NULL or ins_product.MAX_AGE >= :clientAge)
            and (ins_product.MIN_AGE is NULL or ins_product.MIN_AGE <= :clientAge)
        </#if>
    )>0