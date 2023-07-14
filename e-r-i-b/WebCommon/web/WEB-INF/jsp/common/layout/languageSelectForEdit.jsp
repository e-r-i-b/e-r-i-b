<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<%--
    Компонент выбора языка для редактирования локалезависимых полей сущности в АРМ сотрудника
    idName  - название идентификатора для сущности(uuid, synchkey) по-умолчанию id
    selectId - идентификатор выпадающего списка
    selectDefaultTitle - заголовок выпадающего списка
    selectTitleOnclick - действие, которое должно выполниться после выбора элементов из выпадающего списка
    styleClass         - дополнительный стиль
    editLanguageURL    - урл для окошка
--%>

<tiles:importAttribute/>

<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="selectValues" value="${phiz:getLocales()}"/>
<c:set var="onlyOneValue" value="${phiz:size(selectValues) == 1}"/>
<c:if test="${empty selectTitleOnclick}">
    <c:set var="selectTitleOnclick" value="openEditLocaleWin(this);"/>
</c:if>
<c:if test="${empty selectDefaultTitle}">
    <c:set var="selectDefaultTitle"><bean:message key="language.fill" bundle="localeBundle"/></c:set>
</c:if>

<c:choose>
    <c:when test="${not empty entityId}">
        <c:if test="${phiz:size(selectValues) > 0}">
            <div class="${styleClass}">
                <div id="languageSelectForEdit${selectId}" class="relative <c:if test="${onlyOneValue}">onlyOneValue</c:if>">

                    <div class="languageSelectForEdit">
                        <div class="languageSelectLeft"></div>
                        <div class="languageSelectCenter">
                            <div class="languageSelectCenterBlock" onclick="${selectTitleOnclick}">
                                <span class="languageSelectTopText">${selectDefaultTitle}</span>
                            </div>
                            <div class="languageSelectDivider" <c:if test="${!onlyOneValue}">onclick="hideOrShow($('#languageSelectForEditList${selectId}')[0]);"</c:if>>
                                <c:set var="imageData" value="${phiz:getImageById(selectValues[0].imageId)}"/>
                                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                                <img class="languageIcon" src="${image}" border="0"/>
                                <html:hidden property="locale_Id" value="${selectValues[0].id}"/>
                                <input type="hidden" name="entity_Id" class="entity_Id" value="${entityId}"/>
                                <input type="hidden" name="select_Id" class="select_Id" value="${selectId}"/>
                                <c:if test="${!onlyOneValue}">
                                    <img class="languageSelectArrow" src="${globalImagePath}/languageSelectForEditArrow.png" border="0"/>
                                </c:if>
                            </div>
                        </div>
                        <div class="languageSelectRight"></div>
                        <div class="clear"></div>
                    </div>

                    <c:if test="${!onlyOneValue}">
                        <div id="languageSelectForEditList${selectId}" class="languageSelectForEditList" style="display: none;">
                            <c:forEach items="${selectValues}" var="selectValue">
                                <c:set var="imageData" value="${phiz:getImageById(selectValue.imageId)}"/>
                                <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>

                                <div class="languageSelectListElem" onclick="chooseLanguage(this)">
                                    <div class="languageSelectListElemName">
                                        <c:out value="${selectValue.name}"/>
                                    </div>
                                    <div class="languageSelectListElemImg">
                                        <img src="${image}" border="0"/>
                                    </div>
                                    <html:hidden property="locale_Id" value="${selectValue.id}"/>
                                    <input type="hidden" name="entity_Id" class="entity_Id" value="${entityId}"/>
                                    <input type="hidden" name="select_Id" class="select_Id" value="${selectId}"/>
                                    <div class="clear"></div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <script type="text/javascript">
                    function chooseLanguage(elem)
                    {
                        var newSrcImage = $(elem).find('img:first').attr('src');
                        var selectId = $(elem).find('.select_Id').val();
                        $('#languageSelectForEdit'+selectId+' .languageIcon:first').attr('src', newSrcImage);
                        $('#languageSelectForEdit'+selectId+' [name=locale_Id]:first').val($(elem).find('[name=locale_Id]:first').val());
                        $('#languageSelectForEditList'+selectId).hide();
                    }

                    function openEditLocaleWin(elem)
                    {
                        <c:if test="${not empty needSaveChangedData}">
                        var change =  ${needSaveChangedData};
                        if(change)
                        {
                            alert('<bean:message key="message.data.changed" bundle="advertisingBlockBundle"/>');
                            return;
                        }
                        </c:if>
                        var selectId = $(elem).parents('.languageSelectCenter').find('.select_Id').val();
                        var localeId = $('#languageSelectForEdit'+selectId+' [name=locale_Id]:first').val();
                        var entityId = $('#languageSelectForEdit'+selectId+' [name=entity_Id]:first').val();
                        ajaxQuery("${idName}="+entityId+"&localeId="+localeId, "${editLanguageURL}",
                                function(data){
                                    $('#editLocaleWindow'+selectId).html(data);
                                    <c:choose>
                                        <c:when test="${multiSelect=='true'}">
                                            win.open('editLanguageWindow'+selectId);
                                        </c:when>
                                        <c:otherwise>
                                            win.open('editLanguageWindow');
                                        </c:otherwise>
                                    </c:choose>
                                }
                                , null, true);
                    }

                    function clearWin()
                    {
                        $('[id^=editLocaleWindow]').html("");
                        return true;
                    }

                    function getEditLocaledResourcesURL()
                    {
                        return "${editLanguageURL}";
                    }
                </script>
            </div>
            <tiles:insert definition="window" flush="false">
                <tiles:put name="id" value="editLanguageWindow${multiSelect=='true' ? selectId : ''}"/>
                <tiles:put name="styleClass" value="editLanguageWindow"/>
                <tiles:put name="closeCallback" value="clearWin"/>
                <tiles:put name="data">
                    <div id="editLocaleWindow${selectId}" data-select-id="${selectId}">
                    </div>
                </tiles:put>
            </tiles:insert>
        </c:if>
    </c:when>
    <c:otherwise>
        <div class="${styleClass}" style="filter: alpha(opacity=60); -moz-opacity:  0.6; -khtml-opacity:  0.6; opacity:  0.6;">
            <div id="languageSelectForEdit${selectId}" class="relative <c:if test="${onlyOneValue}">onlyOneValue</c:if>" >
                <div class="languageSelectForEdit">
                    <div class="languageSelectLeft"></div>
                    <div class="languageSelectCenter">
                        <div class="languageSelectCenterBlock">
                            <span class="languageSelectTopText">${selectDefaultTitle}</span>
                        </div>
                        <div class="languageSelectDivider">
                            <c:set var="imageData" value="${phiz:getImageById(selectValues[0].imageId)}"/>
                            <c:set var="image" value="${phiz:getAddressImage(imageData, pageContext)}"/>
                            <img class="languageIcon" src="${image}" border="0"/>
                            <c:if test="${!onlyOneValue}">
                                <img class="languageSelectArrow" src="${globalImagePath}/languageSelectForEditArrow.png" border="0"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="languageSelectRight"></div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>