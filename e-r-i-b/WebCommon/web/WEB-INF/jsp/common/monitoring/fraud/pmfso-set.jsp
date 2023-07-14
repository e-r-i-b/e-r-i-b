<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${phiz:isFSOTokenValid()}">

    <div id=pmfso-set-div></div>

    <c:set var="FSOTokenValue"   value="${phiz:getFSOTokenValue()}"/>

    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/rsa/AC_OETags.js"></script>
    <script language="JavaScript" type="text/javascript">

        // -----------------------------------------------------------------------------
        // Globals
        // Major version of Flash required
        var requiredMajorVersion = 6;
        // Minor version of Flash required
        var requiredMinorVersion = 0;
        // Minor version of Flash required
        var requiredRevision = 0;
        // -----------------------------------------------------------------------------

        // Version check based upon the values defined in globals
        var hasReqestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
        if (hasReqestedVersion)
        {
            var d = new Date().getTime();
            var out = "";
            out = out + "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='1' height='1'>" + "\n";
            out = out + "<param name='movie' value='${globalUrl}/images/svf/pmfso.swf?nocache=" + d + "'>" + "\n";
            out = out + "<param name='quality' value='high'>" + "\n";
            out = out + "<param name='bgcolor' value=#FFFFFF>" + "\n";
            out = out + "<param name='allowScriptAccess' value='sameDomain'>" + "\n";
            out = out + "<param name='FlashVars' value='pmdata=${FSOTokenValue}&browserType=" + BrowserDetect.browser + "'>" + "\n";
            out = out + "<embed src='${globalUrl}/images/svf/pmfso.swf?nocache=" + d + "'" + "\n";
            out = out + "FlashVars='pmdata=${FSOTokenValue}&browserType=" + BrowserDetect.browser + "'" + "\n";
            out = out + "allowScriptAccess='sameDomain'" + "\n";
            out = out + "quality='low' bgcolor='#FFFFFF' width='1' height='1'" + "\n";
            out = out + "type='application/x-shockwave-flash'>" + "\n";
            out = out + "<noembed></noembed>" + "\n";
            out = out + "<noobject></noobject>" + "\n";
            out = out + "</embed>" + "\n";
            out = out + "<noobject></noobject>" + "\n";
            out = out + "</object>" + "\n";
            <%--
                убрала document.write, т.к. иначе не работает открытие вклада из кредитной заявки с помощью ajax-запроса,
                потому что данный скрипт выполняется после формирования DOM.
                Вызов document.write после загрузки страницы затирает все данные со страницы
            --%>
            document.getElementById('pmfso-set-div').innerHTML = out;
       }
    </script>
    <noscript>
        <object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='1' height='1'>
            <param name='movie'     value='${globalUrl}/images/svf/pmfso.swf'>
            <param name='quality'   value='high'>
            <param name='bgcolor'   value=#FFFFFF>
            <param name='FlashVars' value='pmdata=${FSOTokenValue}'>
            <embed src='${globalUrl}/images/svf/pmfso.swf' FlashVars='pmdata=${FSOTokenValue}' quality='low' bgcolor='#FFFFFF' width='1' height='1' type='application/x-shockwave-flash'>
                <noembed></noembed>
                <noobject></noobject>
            </embed>
            <noobject></noobject>
        </object>
    </noscript>
</c:if>