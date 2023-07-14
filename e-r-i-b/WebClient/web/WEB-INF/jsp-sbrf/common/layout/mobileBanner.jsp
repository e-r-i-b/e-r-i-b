<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<c:set var ="blocks" value="${phiz:getQuickPaymentPanel()}"/>
<c:if test="${phiz:size(blocks) > 0}">
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/jquery.ifixpng.js"></script>
    <script type="text/javascript">

        var MIN_WIDTH = 1170;
        var PADDING = 18;
        var MIN_BLOCK_WIDTH = 92;
        function initMobileBanner()
        {
            var width = document.body.clientWidth;
            if (width < MIN_WIDTH)
            {
                $('#mobileBannerArrow').css('display','');
                $('#mobileBanner .greenBannerArea').css('cursor','pointer');
                $('#mobileBanner .greenBannerArea').bind('click', openMobileBanner);
                openMobileBanner();
            }

            var bunners = $('#mobileBanner .operators');

            var max_width = bunners.find('.operatorTitle:first span.nowrapWhiteSpace').width();
            bunners.find('.operatorTitle span.nowrapWhiteSpace').each(function(){
                if( max_width < $(this).width() )
                    max_width = $(this).width();
            });
            max_width = (max_width < MIN_BLOCK_WIDTH) ? MIN_BLOCK_WIDTH : max_width;
            $('.box').css('width', max_width + PADDING);
        }

        function openMobileBanner()
        {
            var w = $('#mobileBanner .box').width();
            var l = parseInt($('#mobileBanner').css('left'));
            if (l != 0)
            {
                $('#mobileBanner').css('left','0px');
                $('#mobileBannerArrow').css('background-position','bottom');
            }
            else
            {
                $('#mobileBanner').css('left','-'+w+'px');
                $('#mobileBannerArrow').css('background-position','top');
            }
        }

        $(document).ready(function()
        {
            initMobileBanner();
            $('#mobileBanner .greenBannerArea').ifixpng();

            $('#mobileBanner .operator').hover(function(){
                $(this).addClass('hover');
            }, function() {
                $(this).removeClass('hover');
            });
        });
    </script>

    <c:set var="commonImgPath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/payments/servicesPayments/edit')}"/>

    <div id="mobileBanner">
        <div class="box">
            <div class="mobileBannerBCTop"></div>
            <div class="operators">
                <c:forEach items="${blocks}" var="block">
                    <c:set var="providerId"    value="${block.providerId}"/>
                    <c:set var="providerAlias" value="${block.providerName}"/>
                    <c:set var="showProviderName" value="${block.showName}"/>
                    <c:set var="imageData"       value="${block.image}"/>
                    <c:set var="height"        value="${showProviderName ? '40px' : '55px'}"/>
                    <c:choose>
                        <c:when test="${not empty imageData}">
                            <c:set var="imageURL" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                            <c:if test="${empty imageURL}">
                                <c:set var="imageURL" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg"/>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="imageURL" value="${globalUrl}/images/logotips/IQWave/IQWave-other.jpg"/>
                        </c:otherwise>
                    </c:choose>
                    <div class="operator">
                        <c:set var="params" value=""/>
                        <c:if test="${not empty block.providerFieldName}">
                            <c:set var="params" value="phoneFieldParam=field(${block.providerFieldName})&field(${block.providerFieldAmount})=${block.summ}"/>
                            <c:set var="providerAlias" value="${phiz:getCutPhoneNumberForQPP()}"/>
                        </c:if>
                        <a onclick="panelRequest(${block.id}, '${url}?recipient=${providerId}&fromResource=&${params}')">
                            <div class="operatorIcon" <c:if test="${empty providerAlias && showProviderName}">style="margin: -1px -7px 9px -7px;"</c:if>><img src="${imageURL}" alt='<c:out value="${providerAlias}"/>' height="${height}" width="55px"></div>
                            <c:if test="${showProviderName}">
                                <div class="operatorTitle greenTitle">
                                <span class="nowrapWhiteSpace" >
                                    <c:out value="${providerAlias}"/>
                                </span>
                                </div>
                            </c:if>
                        </a>
                    </div>
                </c:forEach>
            </div>
            <div class="mobileBannerBC"></div>
        </div>
        <div class="greenBannerArea">
            <div id="mobileBannerArrow" style="display: none"></div>
        </div>
        <div class="clear"></div>
    </div>
</c:if>