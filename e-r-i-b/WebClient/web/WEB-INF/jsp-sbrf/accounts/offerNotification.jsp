<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute/>

<c:set var="isViewOfferNotificationAvalible">${phiz:impliesOperation("ViewOfferNotificationOperation", "ViewOfferNotification")}</c:set>
<c:if test="${isViewOfferNotificationAvalible}">
    <div id="notificationBlocks"></div>
    <script type="text/javascript">
        var emptyNotificationList = false;
        var timoutOffer;
        var loadingBlockOffer = []; // подгружаемое с сервера  уведомление
        var tryShowOffer = null;
        var currShowOffer; // id уведомления, которое сейчас отображается
        var showTimeOffer = new Array();
        var showPreloaderOffer = true;

        function clearTimoutOffer()
        {
            window.clearTimeout(timoutOffer);
        }

        function setTimoutOffer(time)
        {
            timoutOffer = window.setTimeout("nextNotification()", time);
        }

        function addNotification(data, id)
        {
            if(trim(data) == '')
            {
                deleteLoadNotification(id);
                emptyNotificationList = true;
                getNextBunner();
                return;
            }
            $("#advertisingBlocks").empty();
            $("#notificationBlocks").append(data);
            emptyNotificationList = false;

            var img = document.getElementById("notificationImg"+id);
            if (img == undefined)
            {
                if (id == tryShowOffer || id == null && loadingBlockOffer[0] == tryShowOffer)
                {
                    if (id == null)
                        id = tryShowOffer;
                    hideNotification(currShowOffer);
                    showNotification(id);
                }
                deleteLoadNotification(id);
            }
        }

        <%-- Отображает баннер с идентификатором == id --%>
        function showCurrentNotification(id)
        {
            <%-- Если этот баннер не последний из запрашеваемых, то не отображаем--%>
            if (id != tryShowOffer)
            {
                deleteLoadNotification(id);
                return;
            }

            deleteLoadNotification(id);
            if (currShowOffer != undefined)
                hideNotification(currShowOffer);
            showNotification(id);
            $("#notificationImg"+id).unbind();
            if (isIE(6))
                setImgFilter(id);
        }

        <%-- Удаляет уведомление из списка загружаемых --%>
        function deleteLoadNotification(id)
        {
            for (var i = 0; i < loadingBlockOffer.length; i++)
                if (loadingBlockOffer[i] == id)
                    loadingBlockOffer.splice(i,1);
        }

        <%-- Есть ли уведомление в списке загружаемых --%>
        function isLoadingBlockOffer(id)
        {
            for (var i = 0; i < loadingBlockOffer.length; i++)
                if (loadingBlockOffer[i] == id)
                    return true;
            return false;
        }

        function imgFilter(src) {
            return "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale,src='"+src+"')";
        }

        function setImgFilter(id)
        {
            var elem = $("#notificationImg"+id);
            var source =  $(elem).attr('src');
            if ($(elem).css('filter')=='')
                $(elem).css({filter:imgFilter(source), width:$(elem).width(), height:$(elem).height()}).attr({src:'${imagePath}/pixel.gif'});
        }

        function hideNotification(id)
        {
            <%-- Скрываем прелоадер --%>
            $("#notificationContent" + id).hide();
            $("#notification"+id).hide();
        }

        function showNotification(id)
        {
            currShowOffer = id;
            $("#notification"+id).show();
        }

        function showNotificationPreloader(id)
        {
            var preloader = $("#notificationContent" + id);
            var notification = $("#notification" + id);
            var h = $("#notification" + id + " .navigation").offset().top - notification.offset().top;
            preloader.css("width", notification.width());
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

        function getNextNotification(id)
        {
            <%-- Отображаем прелоадер, если баннер еще не загружен на странице и пользователь переключился между баннерами --%>
            if (id != null && showTimeOffer[id] == null && showPreloaderOffer)
                showNotificationPreloader(currShowOffer);
            showPreloaderOffer = true;

            <%-- Необходимо не дублировать загрузку баннера --%>
            if (isLoadingBlockOffer(id))
                return;

            tryShowOffer = id;
            if (showTimeOffer[id] == null)
            {
                <%-- Помещаем ид баннера в список загружаемых --%>
                if (id != null)
                    loadingBlockOffer.push(id);
                var url = "${phiz:calculateActionURL(pageContext,'/private/async/notification')}";
                var params ="";
                if (id != '')
                    params = "currentNotificationId="+id;
                ajaxQuery(params, url,  function(data) {addNotification(data, id);}, null, false);
            }
            else
            {
                if (id == currShowOffer)
                    return;

                hideNotification(currShowOffer);
                showNotification(id);

                currShowOffer = id;
                clearTimoutOffer();
                setTimoutOffer(showTimeOffer[id]);
            }
        }

        function nextNotification()
        {
            if (loadingBlockOffer.length == 0)
            {
                var currButton = $("#navigationGreen"+currShowOffer);
                var nextButton = $(currButton).next();
                <%-- Отключаем прелоадер --%>
                showPreloaderOffer = false;
                if ($(nextButton).length)
                {
                    $(nextButton).click();
                }
                else
                {
                    $('#navigationGray'+currShowOffer).first().click();
                }
            }
        }

        <c:if test="${not form.needShowPreRegistrationWindow}">
            doOnLoad(function()
            {
                <c:choose>
                    <c:when test="${form.showBanner}">
                        getNextNotification();
                    </c:when>
                    <c:otherwise>
                        setTimeout(function()
                        {
                            getNextNotification();
                        }, ${form.waitingTime});
                        getNextBunner();
                    </c:otherwise>
                </c:choose>
            });
        </c:if>
    </script>
</c:if>
