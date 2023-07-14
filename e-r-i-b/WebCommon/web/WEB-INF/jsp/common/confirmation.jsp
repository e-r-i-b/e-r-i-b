<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<tiles:importAttribute/>
<%--
  ��������� ��� ������������� ��������
    winId                       - ������������� ���� �������������.
    title                       - ���������.
    message                     - ��������� ������������ ������������.
                                  (��������� � ������ ���� �� ������ getConfirmMessageFunction).
    currentBundle               - bundle � ������� ������ textKey � helpKey
                                  ��� ����� ������(������ �� �����, � ��� ������ �� ����� ���� �������������).
    confirmKey                  - ���� ��� ������ �� ������� ����������� ����������� ����, ���� �� �����, �� ������������ confirmCommandKey
    confirmCommandKey           - ���� ��� ������ �������������.
    confirmCommandTextKey       - ���� ��� �������� ������ �������������.
    validationFunction          - javascript ������� (true - ���� ������������� ���������� ������������
                                                      false - �� ���������� ����).
    getConfirmMessageFunction   - javascript �������. ������, ������� ��� �����,
                                             ����� �������������� ������ message.
    buttonViewType              - ������� ��� ������(�� ��������� buttonGrey)
    image                       - �������� ������
    iconPosition                - ������� ������
    urlImageHover               - �������� ������ ��� ���������
    afterConfirmFunction        - ���������� ������� ������������� ��� �������������, ���� �� ������ ������� �� ������ �������.
	isDefault             	    - true/false ������ �� ��������� (�� �������� ����������� �� ������� Enter)
	openElementId               - id �������� ��� ����� �� ������� ����������� ����, ���� �� ����� - �������� ���������� ������
--%>

<c:if test="${empty confirmKey}">
    <c:set var="confirmKey" value="${confirmCommandKey}"/>
</c:if>

<c:if test="${empty openElementId}">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="${confirmKey}"/>
        <tiles:put name="commandHelpKey" value="${confirmKey}"/>
        <tiles:put name="bundle"         value="${currentBundle}"/>
        <tiles:put name="viewType"       value="${buttonViewType}"/>
        <tiles:put name="image"          value="${buttonImage}"/>
        <tiles:put name="imagePosition"          value="${iconPosition}"/>
        <tiles:put name="imageHover"          value="${urlImageHover}"/>
        <tiles:put name="onclick"        value="${winId}_confirmation();"/>
        <tiles:put name="isDefault"      value="${isDefault}"/>
    </tiles:insert>
</c:if>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="window" flush="false">
        <tiles:put name="id" value="${winId}"/>
        <tiles:put name="styleClass" value="confirmWidow"/>
        <tiles:put name="data">
            <div class="confirmWindowTitle">
                <h2>
                    ${title}
                </h2>
            </div>

            <div id="message_${winId}" class="confirmWindowMessage">
            </div>

            <div class="buttonsArea">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="onclick" value="win.close('${winId}');"/>
                </tiles:insert>
                <c:choose>
                    <c:when test="${not empty afterConfirmFunction}">
                        <tiles:insert definition="clientButton" flush="false">
                            <c:choose>
                                <c:when test="${not empty confirmCommandTextKey }">
                                    <tiles:put name="commandTextKey" value="${confirmCommandTextKey}"/>
                                    <tiles:put name="commandHelpKey" value="${confirmCommandTextKey}.help"/>
                                    <tiles:put name="bundle" value="${currentBundle}"/>
                                </c:when>
                                <c:otherwise>
                                    <tiles:put name="commandTextKey" value="button.confirm"/>
                                    <tiles:put name="commandHelpKey" value="button.confirm.help"/>
                                    <tiles:put name="bundle" value="commonBundle"/>
                                </c:otherwise>
                            </c:choose>
                            <tiles:put name="viewType" value="buttonGreen"/>
                            <tiles:put name="onclick" value="${afterConfirmFunction};${winId}_close();"/>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="${confirmCommandKey}"/>
                            <tiles:put name="commandHelpKey" value="${confirmCommandKey}.help"/>
                            <tiles:put name="commandTextKey" value="${confirmCommandTextKey}"/>
                            <tiles:put name="bundle" value="${currentBundle}"/>
                            <tiles:put name="viewType" value="buttonGreen"/>
                            <tiles:put name="validationFunction">${winId}_close();</tiles:put>
                        </tiles:insert>
                    </c:otherwise>
                </c:choose>
                <div class="clear"></div>
            </div>
        </tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        function ${winId}_confirmation()
        {
            var  info = "${message}";
            var valid = true;
            <c:if test="${!empty validationFunction}">
                var r = ${validationFunction};
                valid = (typeof r == 'function') ? r() : r;
            </c:if>
            <c:if test="${!empty getConfirmMessageFunction}">
                info = ${getConfirmMessageFunction};
            </c:if>
            if(valid)
            {
                ${winId}_setMessage(info);
                win.open('${winId}');
            }
        }

        function ${winId}_setMessage(info)
        {
            info = "<table class=\"confirmWindowTable\"><tr><td class=\"align-center\">"+info+"</td></tr></table>";
            $("#message_${winId}").html(info);
        }

        function ${winId}_close()
        {
            win.close('${winId}');
            return true;
        }

        <c:if test="${not empty openElementId}">
            $(function(){
                $("#${openElementId}").click(function(){
                    ${winId}_confirmation();
                });
            });
        </c:if>
    </script>