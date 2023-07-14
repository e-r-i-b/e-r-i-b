<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="informMessageBlock" flush="false">
    <tiles:put name="regionSelector" value="informMessageOldBrowser"/>
    <tiles:put name="color" value="infMesOrange"/>
    <tiles:put name="data">
        <div class="relative">
            <div>Вам недоступны виджеты, поскольку Вы используете устаревшую версию браузера. Для доступа к данному функционалу обновите браузер.</div>
            <div class="closeMessageIcon" onclick="closeOldBrowserMessage();"></div>
        </div>

        <script type="text/javascript">
            function closeOldBrowserMessage()
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/async/closeOldBrowserMessage')}";
                ajaxQuery ("", url, function(data){closeOldBrowserMessageResult(data);});
            }

            function closeOldBrowserMessageResult()
            {
                $("#informMessageOldBrowser").hide();    
            }
        </script>
    </tiles:put>
</tiles:insert>
