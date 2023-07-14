<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>


<%--
   skinsList - Список стилей
   actionUrl - экшена, ответственного за обработку запросов о переключении текущего скина

--%>
<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>
<div data-dojo-type="widget.SkinSelector">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <div class="button-skin-selector left-button-skin-selector">
        <img src='${commonImagePath}/widget-catalog-to-left.gif'/>
    </div>
    <tiles:insert definition="scrollable-data" flush="false">
        <tiles:put name="className" value="previewSkins"/>
        <tiles:put name="data">
            <table>
                <tr>
                    <c:forEach items="${skinsList}" var="skin">
                        <c:set var="className" value="${skin == form.currentSkin ? 'activeSkin' : ''}"/>
                        <c:set var="temp">
                            <c:out value="${skin.name}"/>
                        </c:set>
                        <td>
                            <div class="skin">
                                <a class="${className}" skinId="${skin.id}">
                                    <img src="${phiz:updateSkinPath(skin.url)}/images/preview.jpg" alt="${temp}"
                                         onerror="onImgError(this)" border="0"/>
                                    <br/>
                                    ${temp}
                                </a>
                            </div>
                        </td>
                    </c:forEach>
                </tr>
            </table>
        </tiles:put>
    </tiles:insert>
    <div class="button-skin-selector right-button-skin-selector">
        <img src='${commonImagePath}/widget-catalog-to-right.gif'/>
    </div>
    <div class="editSkins">
        Для смены стиля приложения выберите изображение нужного стиля и нажмите на кнопку «Сохранить»
    </div>
</div>
