<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.MonitoringESRequestCollector" %>
<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESRequestInfo" %>
<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESSystemInfo" %>
<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.MonitoringESRequestServlet" %>
<%@ page import="com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESResult" %>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Мониторинг запросов к ВС</title>
        <style>

            .message {
                display: block;
                color: #ff0000;
                padding: 10px;
            }

            .monitoringState {
                display: block;
                padding: 10px;
            }

            .buttons {
                padding: 10px;
            }

            .monitoring-line-system
            {
                background-color: #d3d3d3;
            }

            .monitoring-line-1
            {
                background-color: #f3f3f3;
            }

            .monitoring-line-item {
                padding: 0 5px;
            }

            .monitoring-line-item-system {
                width: 150px;
            }

            .monitoring-line-item-count {
                text-align: center;
                width: 50px;
            }

            .monitoring-line-item-count-for-application {
                text-align: center;
                width: 75px;
            }

            .monitoring-line-item-avg {
                text-align: center;
                width: 50px;
            }

        </style>
    </head>

    <body>
        <h3>Мониторинг запросов к ВС</h3>

        <span class="message">
            <c:out value="${sessionScope.message}"/>
            <c:set var="message" value="" scope="session"/>
        </span>

        <form>
            <% MonitoringESRequestCollector.State state = MonitoringESRequestCollector.getInstance().getState(); %>
            <% boolean isStarted = MonitoringESRequestServlet.isStarted(); %>

            <% if (isStarted) { %>
                <span class="monitoringState">MONITORING_STATE: <%= state.name() %></span>

                <div class="buttons">
                    <button type="submit"
                            formaction="/PhizIC-test/externalSystem/monitoring"
                            name="operation"
                            value="start"
                            <%=state == MonitoringESRequestCollector.State.processed? "disabled='disabled'": ""%>
                            >
                        start
                    </button>
                    <button type="submit"
                            formaction="/PhizIC-test/externalSystem/monitoring"
                            name="operation"
                            value="stop"
                            <%=state != MonitoringESRequestCollector.State.processed? "disabled='disabled'": ""%>
                            >
                        stop
                    </button>
                    <button type="submit"
                            formaction="/PhizIC-test/externalSystem/monitoring"
                            name="operation"
                            value="clear">
                        clear
                    </button>
                    <button type="submit"
                            formaction="/PhizIC-test/externalSystem/monitoring"
                            name="operation"
                            value="unload">
                        unload
                    </button>
                    <button type="submit"
                            formaction="/PhizIC-test/externalSystem/monitoring"
                            name="operation"
                            value="stopThreads">
                        stopThreads
                    </button>
                </div>

                <% MonitoringESResult monitoringESResult = MonitoringESRequestCollector.getInstance().buildInfo();%>
                <% int applicationsLength = monitoringESResult.getApplications().length;%>

                <table class="monitoring-info">
                    <tr class="monitoring-line">
                        <td class="monitoring-line-item monitoring-line-item-system">Система</td>
                        <td class="monitoring-line-item monitoring-line-item-message">Тип сообщения</td>
                        <td class="monitoring-line-item monitoring-line-item-avg">Среднее время обработки</td>
                        <td class="monitoring-line-item monitoring-line-item-count">Общее количество запросов</td>
                        <% for (int i = 0; i < applicationsLength; i++) { %>
                            <td class="monitoring-line-item monitoring-line-item-count-for-application"><%= monitoringESResult.getApplications()[i] %></td>
                        <% } %>
                    </tr>
                    <% for (com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESSystemInfo systemInfo : monitoringESResult.getSystemInformationList()){%>
                        <% if (systemInfo.getCount() == 0) continue;%>
                        <tr class="monitoring-line monitoring-line-system">
                            <td class="monitoring-line-item monitoring-line-item-system"><%=systemInfo.getSystem().name()%></td>
                            <td class="monitoring-line-item monitoring-line-item-message">&mdash;</td>
                            <td class="monitoring-line-item monitoring-line-item-avg">&mdash;</td>
                            <td class="monitoring-line-item monitoring-line-item-count"><%=systemInfo.getCount()%></td>
                            <% for (int i = 0; i < applicationsLength; i++) { %>
                                <td class="monitoring-line-item monitoring-line-item-count-for-application"><%= systemInfo.getCountByApplication()[i] %></td>
                            <% } %>
                        </tr>
                        <% int j = 0;%>
                        <% for (com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESRequestInfo requestInfo : systemInfo.getRequests()){%>
                            <tr class="monitoring-line monitoring-line-<%= j%2 %>">
                                <td class="monitoring-line-item monitoring-line-item-system"><%=systemInfo.getSystem()%></td>
                                <td class="monitoring-line-item monitoring-line-item-message"><%=requestInfo.getMessageType()%></td>
                                <td class="monitoring-line-item monitoring-line-item-avg"><%=requestInfo.getAvgTime()%></td>
                                <td class="monitoring-line-item monitoring-line-item-count"><%=requestInfo.getCount()%></td>
                                <% for (int i = 0; i < applicationsLength; i++) { %>
                                    <td class="monitoring-line-item monitoring-line-item-count-for-application"><%= requestInfo.getCountByApplication()[i] %></td>
                                <% } %>
                            </tr>
                            <% j++;%>
                        <%}%>
                    <%}%>
                </table>
            <% } else {%>
                <span class="monitoringState">Мониторинг не запущен.</span>
            <% }%>
        </form>
    </body>
</html>
