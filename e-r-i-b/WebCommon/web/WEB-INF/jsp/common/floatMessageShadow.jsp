<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
 id - id блока
 text - текст подсказки
 data - дополнительные данные
 dataClass - класс для дива с данными
 hintClass - класс для "верхнего" дива
 showHintImg - отображать картинку подсказки
 floatClassSyffix - особые настройки отображения подсказки.
 --%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

<script type="text/javascript">
    $(document).ready(function(){
        $('#floatMessage${id}').hover(function(){
            $('#floatMessageHint${id}').show();
            }, function() {
            $('#floatMessageHint${id}').hide();
        });
    });
</script>

<span id="floatMessage${id}" class="${float_} ${hintClass}" style="text-align:left;">
    <c:if test="${showHintImg}"><a class="imgHintBlock save-template-hint"></a></c:if>
    <c:if test="${not empty data}"><span class="${dataClass}">${data}</span></c:if>

    <c:if test="${needClear}"><div class="clear"></div></c:if>
    <div id="floatMessageHint${id}" class="floatMessage${floatClassSyffix} orangeFloatMessage" style="display: none;">
        <div class="floatMessageTop${floatClassSyffix} r-top"></div>
        <div class="floatMessageHint">
           ${text}
        </div>
    </div>
</span>