<%--
    компонент для раскрывающегося блока архивов-автоплатежей
    data - данные
    title - заголовок блока
    blockId - id блока
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<tiles:importAttribute/>

<script type="text/javascript">
    $(document).ready(function(){
        $(".hideArchivePayments").hide();
    });

     function showHideArchivePayments(blockId)
    {
        if ($("#payments"+blockId).css("display")=="none")
        {
            $("#titleControl"+blockId).addClass("hide");
            $("#payments"+blockId).show();
        }
        else
        {
            $("#titleControl"+blockId).removeClass("hide");
            $("#payments"+blockId).hide();
        }
    }
</script>

<div class="archivePayments">
    <div class="titleControl titleArchivePayments float" id="titleControl${blockId}" onclick="showHideArchivePayments('${blockId}');">
        <span>${title}</span>
    </div>
    <div class="clear"></div>
    <div class="hideArchivePayments" id="payments${blockId}">
        ${data}
    </div>
</div>




