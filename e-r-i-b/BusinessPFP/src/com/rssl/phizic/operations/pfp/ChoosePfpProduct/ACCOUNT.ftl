select     
    {accountProduct.*}
from
    PFP_PRODUCTS accountProduct
    left join PFP_SP_TARGET_GROUPS_BUNDLE segment on accountProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    accountProduct.type = 'A'
    and accountProduct.for_complex = 'none'
    and accountProduct.ENABLED = '1'
