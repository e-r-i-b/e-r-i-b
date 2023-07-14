<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<div class="b-promo collapsed">
    <div class="promo_mini"><tiles:insert attribute="note"/></div>
    <div class="promo_inner">
        <tiles:insert attribute="data"/>
    </div>
    <div class="promo_tail"></div>
    <div class="promo_toggler">
        <span class="dash off"><tiles:insert attribute="dashOff"/></span>
        <span class="dash on"><tiles:insert attribute="dashOn"/></span>
    </div>
    <i class="promo_sh"></i>
    <script type="text/javascript">
        $('.b-promo .promo_toggler').click(function(){
            if($('.b-promo').hasClass('expanded'))
            {
                $('.b-promo').removeClass('expanded').addClass('collapsed');
                $('.promo_inner').hide();
            }
            else
            {
                $('.b-promo').removeClass('collapsed').addClass('expanded');
                $('.promo_inner').show();
            }
        });
    </script>
</div>