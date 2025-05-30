<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reports</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <h2>Reports</h2>

    <%-- Display any message passed back to this page (e.g., from ReportServlet) --%>
    <%
        String msg = (String) request.getAttribute("msg");
        if (msg == null) {
            msg = request.getParameter("msg"); // Fallback for direct parameter
        }
        if (msg != null && !msg.isEmpty()) {
    %>
        <p class="error-message"><b><%= msg %></b></p> <%-- Assuming .error-message is styled in style.css --%>
    <%
        }
    %>

    <ul>
        <%-- Added contextPath to links for robustness --%>
        <li><a href="<%= request.getContextPath() %>/report_form.jsp?type=dateRange">Patients Admitted in Date Range</a></li>
        <li><a href="<%= request.getContextPath() %>/report_form.jsp?type=ailment">Patients with Specific Ailment</a></li>
        <li><a href="<%= request.getContextPath() %>/report_form.jsp?type=doctor">Patients Assigned to Specific Doctor</a></li>
    </ul>
    <%-- Added contextPath to link for robustness --%>
    <p><a href="<%= request.getContextPath() %>/index.jsp">Back to Home</a></p>
</body>
</html>