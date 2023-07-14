select
    {imaProduct.*}
from
    PFP_PRODUCTS imaProduct
    left join PFP_SP_TARGET_GROUPS_BUNDLE segment on imaProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    imaProduct.type = 'M'
    and imaProduct.for_complex = 'none'
    and imaProduct.ENABLED = '1'