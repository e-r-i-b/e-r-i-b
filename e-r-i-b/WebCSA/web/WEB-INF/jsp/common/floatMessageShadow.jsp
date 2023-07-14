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
<c:set var="imagePath" value="${skinUrl}/skins/sbrf/images"/>

<script type="text/javascript">
    $(document).ready(function(){
        $('#floatMessage${id} .dataHint').click(function(){
            $('#floatMessageHint${id}').toggle();
        });
    });
</script>

<div id="floatMessage${id}" class="float ${hintClass}" style="text-align:left;">
    <c:if test="${showHintImg}"><a class="imgHintBlock save-template-hint"></a></c:if>
    <c:if test="${not empty data}"><div class="${dataClass}">${data}</div></c:if>

    <div id="floatMessageHint${id}" class="floatMessage${floatClassSyffix} orangeFloatMessage" style="display: none;">
        <div class="floatMessageTop${floatClassSyffix} r-top"></div>
        <div class="floatMessageHint">
            <div class="floatMessageHintRT r-top">
                <div class="floatMessageHintLT r-top-left">
                    <div class="floatMessageHintRT r-top-right">
                        <div class="floatMessageHintCT r-top-center"></div>
                    </div>
                </div>
            </div>
            <div class="floatMessageHintLC r-center-left">
                <div class="floatMessageHintCR r-center-right">
                    <div class="floatMessageHintCC r-content">${text}</div>
                </div>
            </div>
            <div class="floatMessageHintLB r-bottom-left">
                <div class="floatMessageHintRB r-bottom-right">
                    <div class="floatMessageHintCB r-bottom-center"></div>
                </div>
            </div>
            <img class="floatMessageClose" onclick="$('#floatMessageHint${id}').hide();" alt="закрыть" src="${imagePath}/csa/close_orange.gif" width="9" height="9"/>
        </div>
    </div>
</div>