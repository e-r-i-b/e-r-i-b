select
    {trustManaging.*}
from
    PFP_PRODUCTS trustManaging
    left join PFP_SP_TARGET_GROUPS_BUNDLE segment on trustManaging.ID = segment.PRODUCT_ID
where
    segment.SEGMENT_CODE = :clientSegment and
    trustManaging.type = 'T'
    and trustManaging.ENABLED = '1'
