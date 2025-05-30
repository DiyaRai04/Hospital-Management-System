<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%-- Added for best practice --%>
<%
    String type = request.getParameter("type");
    if (type == null) type = ""; // Keep original null check
%>
<!DOCTYPE html>
<html>
<head>
    <title>Report Criteria</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <h2>Report Criteria</h2>
    <%-- Added contextPath to form action for robustness --%>
    <form action="<%= request.getContextPath() %>/ReportServlet" method="get">
        <input type="hidden" name="type" value="<%= type %>"/>
        <%
            switch(type) {
                case "dateRange":
        %>
                    From Date: <input type="date" name="fromDate" required><br>
                    To Date: <input type="date" name="toDate" required><br>
        <%
                    break;
                case "ailment":
        %>
                    Ailment: <input type="text" name="ailment" required><br>
        <%
                    break;
                case "doctor":
        %>
                    Doctor Name: <input type="text" name="doctor" required><br>
        <%
                    break;
                default:
                    // It's unusual to have a 'return;' statement directly in JSP scriptlet like this
                    // for flow control that terminates the page.
                    // If 'type' is invalid, the form shouldn't even show.
                    // This part might be better handled by redirecting from reports.jsp
                    // or by the ReportServlet if an invalid type reaches it.
                    // However, keeping the logic as per "don't change the code".
        %>
                    <p>Invalid report type selected. Please go <a href="<%= request.getContextPath() %>/reports.jsp">back to reports</a> and select a valid type.</p>
        <%
                    // The return statement here will stop further processing of the JSP from this point.
                    // So, the "Generate Report" button and the "Back to Reports" link below
                    // will NOT be rendered if type is invalid.
                    return;
            }
        %>
        <input type="submit" value="Generate Report">
    </form>
    <%-- This link will only be shown if 'type' was valid and the 'default' case's 'return' wasn't hit --%>
    <p><a href="<%= request.getContextPath() %>/reports.jsp">Back to Reports</a></p> <%-- Added contextPath --%>
</body>
</html>