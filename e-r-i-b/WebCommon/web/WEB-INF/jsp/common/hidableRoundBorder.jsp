<%--
  Created by IntelliJ IDEA.
  User: kichinova
  Date: 11.07.2013
  Time: 15:47:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<%--
    компонент для раскрывающегося блока
    data - данные
    title - заголовок блока

--%>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".hideProducts").hide();
    });

    var hideProductsFlag = false;
    function showHideBlockedCards()
    {
        if ($(".hideProducts").css("display")=="none")
        {
            $(this).addClass("hide");
            $('.hideProducts').show();
            if (!hideProductsFlag)
            {
                initProductTitle();
                hideProductsFlag = true;
            }
        }
        else
        {
            $(this).removeClass("hide");
            $('.hideProducts').hide();
        }
    }
</script>

<%-- оборачиваем title для фиксации и обрезания лишнего --%>
<c:if test="${not empty title}">
    <c:set var="title">
        <div class="titleControl float" onclick="showHideBlockedCards();">
            <span>${title}</span>
        </div>
        <div class="clear"></div>
    </c:set>
</c:if>

<div class="workspace-box blockedProducts">
    <div class="mainTitle">
        ${title}
    </div>
    <div class="clear"></div>

    <div class="hideProducts">
        <div class="RC r-content">
            ${data}
            <div class="clear"></div>
        </div>
    </div>
</div>




