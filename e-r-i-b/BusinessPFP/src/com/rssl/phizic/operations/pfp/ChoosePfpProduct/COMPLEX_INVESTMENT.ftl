select
    {complexProduct.*}
from
    PFP_COMPLEX_PRODUCTS complexProduct
    join PFP_PRODUCTS product on complexProduct.account_id = product.id
    left join PFP_CP_TARGET_GROUPS_BUNDLE segment on complexProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    (complexProduct.type = 'F' or complexProduct.type = 'A')