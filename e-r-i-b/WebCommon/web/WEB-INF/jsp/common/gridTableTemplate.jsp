<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%--
    Имитация таблицы грида

    id      - идентификатор таблицы
    name    - имя таблицы
    style   - стиль
    data    - данные
--%>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<table id="${id}" cellspacing="0" cellpadding="0" style="${style}" width="100%">
    <tr>
        <td class="tblNeedRightStyle">
            <table width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="100%" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table id="${id}Data" width="100%" cellspacing="0" cellpadding="0" class="standartTable">
                                        ${data}
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

</table>