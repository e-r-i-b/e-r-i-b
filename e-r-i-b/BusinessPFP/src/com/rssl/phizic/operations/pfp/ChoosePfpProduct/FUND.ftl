select
    {fundProduct.*}
from
    PFP_PRODUCTS fundProduct
    left join PFP_SP_TARGET_GROUPS_BUNDLE segment on fundProduct.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    fundProduct.type = 'F'
    and fundProduct.for_complex = 'none'
    and fundProduct.ENABLED = '1'