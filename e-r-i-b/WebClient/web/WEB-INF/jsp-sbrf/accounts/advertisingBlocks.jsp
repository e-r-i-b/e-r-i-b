<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute/>

<c:set var="isViewAdvertisingBlockAvalible">${phiz:impliesOperation("ViewAdvertisingBlockOperation", "ViewAdvertisingBlock")}</c:set>

<c:if test="${isViewAdvertisingBlockAvalible}">
    <div id="advertisingBlocks"></div>
    <script type="text/javascript">
        var timout;
        var loadingBlock = []; // ������������ ������ � �������
        var tryShowBlock = null;
        var currShowBlock; // id �������, ������� ������ ������������
        var showTime = new Array();
        var showPreloader = true;

        function clearTimout()
        {
            window.clearTimeout(timout);
        }

        function setTimout(time)
        {
            timout = window.setTimeout("nextBunner()", time);
        }

        function addBunner(data, id)
        {
            if(trim(data) == '')
            {
                deleteLoadBunner(id);
                return;
            }

            $("#advertisingBlocks").append(data);

            var img = document.getElementById("advertisingBlockImg"+id);
            if (img == undefined)
            {
                if (id == tryShowBlock || id == null && loadingBlock[0] == tryShowBlock)
                {
                    if (id == null)
                        id = tryShowBlock;
                    hideBunner(currShowBlock);
                    showBunner(id);
                }
                deleteLoadBunner(id);
            }
        }

        <%-- ���������� ������ � ��������������� == id --%>
        function showCurrentBunner(id)
        {
            <%-- ���� ���� ������ �� ��������� �� �������������, �� �� ����������--%>
            if (id != tryShowBlock)
            {
                deleteLoadBunner(id);
                return;
            }

            deleteLoadBunner(id);
            if (currShowBlock != undefined)
                hideBunner(currShowBlock);
            showBunner(id);
            $("#advertisingBlockImg"+id).unbind();
             if (isIE(6))
                setImgFilter(id);
        }

        <%-- ������� ������ �� ������ ����������� --%>
        function deleteLoadBunner(id)
        {
            for (var i = 0; i < loadingBlock.length; i++)
                if (loadingBlock[i] == id)
                    loadingBlock.splice(i,1);
        }

        <%-- ���� �� ������ � ������ ����������� --%>
        function isLoadingBlock(id)
        {
            for (var i = 0; i < loadingBlock.length; i++)
                if (loadingBlock[i] == id)
                    return true;
            return false;
        }

        function imgFilter(src) {
            return "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale,src='"+src+"')";
        }

        function setImgFilter(id)
        {
            var elem = $("#advertisingBlockImg"+id);
            var source =  $(elem).attr('src');
            if ($(elem).css('filter')=='')
                $(elem).css({filter:imgFilter(source), width:$(elem).width(), height:$(elem).height()}).attr({src:'${imagePath}/pixel.gif'});
        }

        function hideBunner(bunnerId)
        {
            <%-- �������� ��������� --%>
            $("#advertisingBlockContent" + bunnerId).hide();
            $("#advertiingBlock"+bunnerId).hide();
        }

        function showBunner(bunnerId)
        {
            currShowBlock = bunnerId;
            $("#advertiingBlock"+bunnerId).show();
        }

        function showBunnerPreloader(bunnerId)
        {
            var preloader = $("#advertisingBlockContent" + bunnerId);
            var bunner = $("#advertiingBlock" + bunnerId);
            var h = $("#advertiingBlock" + bunnerId + " .navigation").offset().top - bunner.offset().top;
            preloader.css("width", bunner.width());
            if (h/2 > 12)
            {
                preloader.css("height", h/2 + 12);
                preloader.css("padding-top", h/2 - 12);
            }
            else
                preloader.css("height", h);
            preloader.css("zIndex", 10);
            preloader.css("display", "");
        }

        function getNextBunner(id)
        {
            <%-- ���������� ���������, ���� ������ ��� �� �������� �� �������� � ������������ ������������ ����� ��������� --%>
            if (id != null && showTime[id] == null && showPreloader)
                showBunnerPreloader(currShowBlock);
            showPreloader = true;

            <%-- ���������� �� ����������� �������� ������� --%>
            if (isLoadingBlock(id))
                return;

            tryShowBlock = id;
            if (showTime[id] == null)
            {
                <%-- �������� �� ������� � ������ ����������� --%>
                if (id != null)
                    loadingBlock.push(id);
                var url = "${phiz:calculateActionURL(pageContext,'/private/async/advertising')}";
                var params ="";
                if (id != '')
                   params = "currentBannerId="+id;
                ajaxQuery(params, url,  function(data) {addBunner(data, id);}, null, false);
            }
            else
            {
                if (id == currShowBlock)
                   return;

                hideBunner(currShowBlock);
                showBunner(id);

                currShowBlock = id;
                clearTimout();
                setTimout(showTime[id]);
            }
        }

        function nextBunner()
        {
            if (loadingBlock.length == 0)
            {
                var currButton = $("#navigationGreen"+currShowBlock);
                var nextButton = $(currButton).next();
                <%-- ��������� ��������� --%>
                showPreloader = false;
                if ($(nextButton).length)
                {
                    $(nextButton).click();
                }
                else
                {
                    $('#navigationGray'+currShowBlock).first().click();
                }
            }
        }

        <c:if test="${not form.needShowPreRegistrationWindow}">
            if (typeof emptyNotificationList == "undefined" || emptyNotificationList == true )
                doOnLoad(function(){ getNextBunner(); });
        </c:if>
    </script>
</c:if>