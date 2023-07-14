<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
    customSelectId - идентификатор выпадающего списка
    customSelectName - name дл€ выбираемых элементов
    customSelectOnclick - действие, которое должно выполнитьс€ после выбора элементов из выпадающего списка
--%>

<tiles:importAttribute/>

<c:set var="onlyOneValue" value="${phiz:size(node.allIdentifiers) == 1}" scope="request"/>

<div id="customSelect${customSelectId}" class="relative customSelectBlock <c:if test="${onlyOneValue}">onlyOneValue</c:if>">
    <input type="hidden" name="${customSelectName}">

    <c:choose>
        <c:when test="${onlyOneValue}">
           <tiles:insert definition="floatMessageShadow" flush="false">
                <tiles:put name="id" value="customSelectHint"/>
                <tiles:put name="data">
                    <div class="customSelect">
                        <div class="customSelectLeft"></div>
                        <div class="customSelectCenter">
                            <div class="customSelectCenterBlock">
                                <span class="customSelectTopText">${customSelectValue}</span>
                            </div>
                            <div class="customSelectHideShadow">
                                <div class="customSelectArrow"></div>
                            </div>
                        </div>
                        <div class="customSelectRight"></div>
                        <div class="clear"></div>
                    </div>
                </tiles:put>
                <tiles:put name="showHintImg" value="false"/>
                <tiles:put name="text">
                    –асходы по карте <span class="customSelectHintText bold">${customSelectValue}</span>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <div class="customSelect" onclick="hideOrShow($('#customSelect${customSelectId} .customSelectList')[0]);">
                <div class="customSelectLeft"></div>
                <div class="customSelectCenter">
                    <div class="customSelectCenterBlock">
                        <span class="customSelectTopText">${customSelectValue}</span>
                    </div>
                    <div class="customSelectHideShadow">
                        <div class="customSelectArrow"></div>
                    </div>
                </div>
                <div class="customSelectRight"></div>
                <div class="clear"></div>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="customSelectList" style="display: none;">
        <c:set var="nodeList" value="${node.list}" scope="request"/>
        <c:set var="level" value="1" scope="request"/>
        <tiles:insert page="/WEB-INF/jsp/common/layout/drawList.jsp" flush="false">
            <tiles:put name="customSelectId" value="${customSelectId}"/>
            <tiles:put name="customSelectName" value="${customSelectName}"/>
            <tiles:put name="customSelectOnclick" value="${customSelectOnclick}"/>
        </tiles:insert>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
    <c:choose>
        <c:when test="${onlyOneValue}">
            $('#customSelect${customSelectId}').bind('mouseleave', function(){
               $(this).find('.customSelectList').hide();
            });
        </c:when>
        <c:otherwise>
            $('#customSelect${customSelectId} .selectListElem').hover(function(){
               $(this).addClass('hover');
               }, function() {
               $(this).removeClass('hover');
            });

            $('#customSelect${customSelectId}').bind('mouseleave', function(){
               $(this).find('.customSelectList').hide();
            });
        </c:otherwise>
    </c:choose>
    });
</script>