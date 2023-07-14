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
            <c:set var="transferSubjectText" value="����� ������ �������"/>
        </c:when>
        <c:otherwise>
            <c:set var="transferSubjectText" value="�������� ���������"/>
        </c:otherwise>
    </c:choose>
    <div class="content">
        <div class="firstRow">
            <span class="bold">�������!</span> ������ ���������� �������� ��� ��������� ${transferSubjectText}.
        </div>
        <div class="clear"></div>
        <div class="secondRow">
            <img class="icon" alt="" src="${globalUrl}/commonSkin/images/iconPmntList_LongOffer.jpg">
            <a onclick="createCommandButton('button.makeAutoTransfer', '').click('', false);">���������� ���������� ����� ������</a>
        </div>
    </div>
    <a class="closeIcon" title="�������" onclick="closeP2PBanner()"></a>
</div>
<div class="clear"></div>