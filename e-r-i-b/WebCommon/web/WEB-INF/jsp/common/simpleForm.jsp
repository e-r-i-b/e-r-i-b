<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<%--
Простая форма с обрамлением.

	id          -   имя формы
	name        -   название
	description -   описание
	data        -   данные
    alignTable  -   это центровка формы в рабочей области (центр, право, лево)
    additionalStyle - дополнительный стиль для внешней рамки окна.

--%>
<div class="paymentForms width700">
    <div class="pageTitle">${name}</div>

    <c:if test="${not empty description}">
        <table class="paymentHeader">
            <tr>
                <td>
                    <div class="clear"></div>
                        <%--Необходим для получения заголовка формы, при сохранении страницы в избранное--%>
                    <input type="hidden" id="formName" name="formName" value="${name}">
                    <c:if test="${not empty description}">
                        ${description}
                    </c:if>
                </td>
            </tr>
        </table>
    </c:if>

    <div class="pmntData" onkeypress="onEnterKey(event);">
        ${data}
    </div>

</div>