<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<%--
��������� ��� ����������� ���������� "��������"
id - ������������� �����, ���������� ��� ����������, ���� ����� ����������� �� �������� ���������
xMinValue - ����������� �������� �� ��� �
xMaxValue - ������������ �������� �� ��� �
yMinValue - ����������� �������� �� ��� y
yMaxValue - ������������ �������� �� ��� y
xUnit - ������� ��������� ��� ��� x
yUnit - ������� ��������� ��� ��� y
xCurrValue - ������� �������� �� ��� �
yCurrValue - ������� �������� �� ��� y
xFieldName - �������� ����, � ������� ���������� �������� �������� �� ��� �
yFieldName - �������� ����, � ������� ���������� �������� �������� �� ��� y
xInputSize - ������ input'� ��� ��� x
yInputSize - ������ input'� ��� ��� y
totalFieldName -  ���� ��� �������� ���� � ������� dd/MM/yyyy
yScrollType - ��� ������������ ����� (��������� ��������: quarter (�������), month (�����) )
callback - ������� ����������� ��� ����� �������� ��������
yMinPossibleVal - ���������� ��������� �������� Y ��� ����������� X
yMaxPossibleVal - ����������� ��������� �������� Y ��� ������������ X
inputHint - ���������, ������������ ������ �� �����
--%>

<c:if test="${yScrollType == 'quarter'}">
    <c:set var="yMinValue" value="1"/>
    <c:set var="yMaxValue" value="4"/>
</c:if>
<c:if test="${yScrollType == 'month'}">
    <c:set var="yMinValue" value="1"/>
    <c:set var="yMaxValue" value="12"/>
</c:if>

<c:set var="xFieldValue"><bean:write name="org.apache.struts.taglib.html.BEAN" property="${xFieldName}"/></c:set>
<c:if test="${not empty xFieldValue}"><%-- ���� �������� � ���� �� �����, �� ���������� ���--%>
    <c:set var="xCurrValue" value="${xFieldValue}"/>
</c:if>
<c:if test="${empty xCurrValue}">
    <c:set var="xCurrValue" value="${xMinValue}"/>
</c:if>
<c:if test="${xCurrValue gt xMaxValue}">
    <c:set var="xCurrValue" value="${xMaxValue}"/>
</c:if>
<c:if test="${xCurrValue lt xMinValue}">
    <c:set var="xCurrValue" value="${xMinValue}"/>
</c:if>

<c:set var="yFieldValue"><bean:write name="org.apache.struts.taglib.html.BEAN" property="${yFieldName}"/></c:set>
<c:if test="${not empty yFieldValue}"><%-- ���� �������� � ���� �� �����, �� ���������� ���--%>
    <c:set var="yCurrValue" value="${yFieldValue}"/>
</c:if>
<c:if test="${empty yCurrValue}">
    <c:set var="yCurrValue" value="${yMinValue}"/>
</c:if>
<c:if test="${yCurrValue gt yMaxValue}">
    <c:set var="yCurrValue" value="${yMaxValue}"/>
</c:if>
<c:if test="${yCurrValue lt yMinValue}">
    <c:set var="yCurrValue" value="${yMinValue}"/>
</c:if>

<c:if test="${empty yMinPossibleVal}">
    <c:set var="yMinPossibleVal" value="${yMinValue}"/>
</c:if>
<c:if test="${empty yMaxPossibleVal}">
    <c:set var="yMaxPossibleVal" value="${yMaxValue}"/>
</c:if>

<script type="text/javascript">

    var horizDrag${id};
    var vertDrag${id};

    <%-- �������������� ������
         xMinValue - ����������� �������� �� ��� �
         xMaxValue - ������������ �������� �� ��� �
         yMinPossibleVal - ���������� ��������� �������� Y ��� ����������� X 
         yMaxPossibleVal - ����������� ��������� �������� Y ��� ������������ X
         xCurrValue - ������� �������� �� ��� �
         yCurrValue - ������� �������� �� ��� y --%>

    function reinitScroll_${id}(xMinValue, xMaxValue, yMinPossibleVal, yMaxPossibleVal, xCurrValue, yCurrValue){
        horizDrag${id}.xStep = xMaxValue - xMinValue + 1;
        horizDrag${id}.minValue = xMinValue;
        horizDrag${id}.maxValue = xMaxValue;

        vertDrag${id}.xStep = xMaxValue - xMinValue + 1;
        vertDrag${id}.yMinPossibleVal = yMinPossibleVal;
        vertDrag${id}.yMaxPossibleVal = yMaxPossibleVal;
        initScrolls('${id}', horizDrag${id}, vertDrag${id}, xCurrValue, yCurrValue);
        $('#xMinValueLabelContainer${id}').html(xMinValue);
        $('#xMaxValueLabelContainer${id}').html(xMaxValue);
    }

    $(document).ready(function(){
        horizDrag${id} = new Dragdealer('horizScroll${id}',
        {
            snap: true,
            xStep: ${xMaxValue} - ${xMinValue} + 1,
            fieldName: '${xFieldName}',
            minValue:   ${xMinValue},
            maxValue:   ${xMaxValue}
        });

        vertDrag${id} = new Dragdealer('vertScroll${id}',
        {
            horizontal: true,
            vertical: true,
            fieldName: '${yFieldName}',
            snap: true,
            xStep: ${xMaxValue} - ${xMinValue} + 1,
            yStep: ${yMaxValue} - ${yMinValue} + 1,
            minValue: ${yMinValue},
            maxValue: ${yMaxValue},
            yMinPossibleVal: ${yMinPossibleVal},
            yMaxPossibleVal: ${yMaxPossibleVal}
        });

        horizDrag${id}.animationCallback = function(x, y){
            setYScrollPosition('${id}');
            setHorizPosition('${id}', horizDrag${id});
            correctYScrollPosition('${id}', horizDrag${id}, vertDrag${id});
            <c:if test="${not empty totalFieldName}">
                updateTotalField('${xFieldName}','${yFieldName}','${totalFieldName}');
            </c:if>
        };

        horizDrag${id}.callback = function(x, y){
            <c:if test="${not empty callback}">
                ${callback}
            </c:if>
        };

        vertDrag${id}.animationCallback = function(x, y){
            setYScroll('${id}');
            setXScrollPosition('${id}');
            setHorizPosition('${id}', horizDrag${id});
            setVerticalPosition('${id}', '${yFieldName}', y, ${yMinValue}, ${yMaxValue});
            <c:if test="${not empty totalFieldName}">
                updateTotalField('${xFieldName}','${yFieldName}','${totalFieldName}');
            </c:if>
        };

        vertDrag${id}.callback = function(x, y){
            <c:if test="${not empty callback}">
                ${callback}
            </c:if>
        };

        initScrolls('${id}', horizDrag${id}, vertDrag${id}, ${xCurrValue}, ${yCurrValue});

        <c:choose>
            <c:when test="${yScrollType == ''}">
                initYScrollDescrPos('${id}', ${yMinValue}, ${yMaxValue});
            </c:when>
            <c:otherwise>
                initYScrollDescr('${id}', ${yMinValue}, ${yMaxValue}, ARRAY_DESCR_${yScrollType});
            </c:otherwise>
        </c:choose>
    });

    function onScroll2DEnterPress${id}(e)
    {
        var kk = navigator.appName == 'Netscape' ? e.which : e.keyCode;
        if(kk == 13)
        {
            setXScrollVal('${id}', horizDrag${id}, vertDrag${id});
            setYScrollVal('${id}', horizDrag${id}, vertDrag${id});
            ${callback};
            cancelBubbling(e);
        }
    }
</script>

<div class="scrollInput">
    <input type="text" name="${xFieldName}" value="${xCurrValue}" size="${xInputSize}" onchange="setXScrollVal('${id}', horizDrag${id}, vertDrag${id});${callback}"
            onkeydown="onScroll2DEnterPress${id}(event);"/> ${xUnit}
    <input type="text" name="${yFieldName}" value="${yCurrValue}" size="${yInputSize}" onchange="setYScrollVal('${id}', horizDrag${id}, vertDrag${id});${callback}"
            onkeydown="onScroll2DEnterPress${id}(event);"/> ${yUnit}
    <c:if test="${not empty totalFieldName}">
        <input type="hidden" name="${totalFieldName}">
    </c:if>
    ${inputHint}
</div>

<div class="scroll2D ${yScrollType}">
    <div class="scrollDescLeft"><span id="xMinValueLabelContainer${id}">${xMinValue}</span> ${xUnit}</div>

    <div id="vertScroll${id}" class="dragdealer">

        <div id="horizScroll${id}" class="greenScroll">

            <div class="scrollRight"></div>
            <div class="scrollLeft"></div>
            <div class="scrollCenter">
                <div id="Main${id}" class="scrollMain">
                    <div class="innerScroll" id="InnerScroll${id}"></div>
                </div>
            </div>
            <div class="scrollShadow"></div>

            <div id="yScroll${id}" class="yScroll">
                <div class="yScrollShadow">
                    <div class="yScrollCenter">
                        <div class="yInnerScroll" id="yInnerScroll${id}">
                            <div id="yMain${id}" class="yScrollMain"></div>
                        </div>
                    </div>
                    <div class="scrollBottom"></div>
                    <c:forEach begin="${yMinValue + 1}" end="${yMaxValue}" varStatus="item">
                        <div class="yScrollDesc">${item.index} ${yUnit}</div>
                    </c:forEach>
                </div>
            </div>

            <div id="horizScroll${id}Inner" class="scrollInner handle"></div>
        </div>
        <div class="clear"></div>

        <div id="vertScroll${id}Inner" class="yScrollInner handle"></div>
    </div>

    <div class="scrollDescRight"><span id="xMaxValueLabelContainer${id}">${xMaxValue}</span> ${xUnit}</div>
</div>
