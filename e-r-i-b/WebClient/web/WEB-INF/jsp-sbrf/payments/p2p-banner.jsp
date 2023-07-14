<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    function closeP2PBanner()
    {
        $('.p2p-promo').hide();
    }
</script>
<div class="p2p-promo">
    <c:choose>
        <c:when test="${metadataFormName == 'InternalPayment'}">
            <c:set var="transferSubjectText" value="между своими картами"/>
        </c:when>
        <c:otherwise>
            <c:set var="transferSubjectText" value="клиентам Сбербанка"/>
        </c:otherwise>
    </c:choose>
    <div class="content">
        <div class="firstRow">
            <span class="bold">Новинка!</span> Теперь автоплатеж доступен для переводов ${transferSubjectText}.
        </div>
        <div class="clear"></div>
        <div class="secondRow">
            <img class="icon" alt="" src="${globalUrl}/commonSkin/images/iconPmntList_LongOffer.jpg">
            <a onclick="createCommandButton('button.makeAutoTransfer', '').click('', false);">Подключить автоплатеж прямо сейчас</a>
        </div>
    </div>
    <a class="closeIcon" title="Закрыть" onclick="closeP2PBanner()"></a>
</div>
<div class="clear"></div>