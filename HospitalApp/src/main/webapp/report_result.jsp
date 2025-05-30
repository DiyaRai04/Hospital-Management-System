<%@ page import="java.util.List" %>
<%@ page import="com.model.Patient" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Report Results</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">

    <%-- Your existing internal styles --%>
    <style>
        /* These internal styles might be overridden by more specific rules in style.css,
           or they might override general rules from style.css if they are more specific
           or loaded after and have the same specificity.
        */
        body { font-family: sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .error-message { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Report Results</h2>

    <%-- Optional: Display what criteria was used for the report --%>
    <%-- You might need to pass these criteria from the ReportServlet as request attributes --%>
    <%
    /*
    String reportType = (String) request.getAttribute("reportType"); // Example
    if (reportType != null) {
        out.println("<p><strong>Report Type:</strong> " + reportType + "</p>");
        if ("dateRange".equals(reportType)) {
            out.println("<p><strong>From:</strong> " + request.getAttribute("fromDateCrit") +
                        " <strong>To:</strong> " + request.getAttribute("toDateCrit") + "</p>");
        } else if ("ailment".equals(reportType)) {
            out.println("<p><strong>Ailment:</strong> " + request.getAttribute("ailmentCrit") + "</p>");
        } else if ("doctor".equals(reportType)) {
            out.println("<p><strong>Doctor:</strong> " + request.getAttribute("doctorCrit") + "</p>");
        }
    }
    */
    %>


    <%
        String errorMessage = (String) request.getAttribute("msg"); // 'msg' is what ReportServlet uses
        if (errorMessage != null && !errorMessage.isEmpty()) {
    %>
        <p class="error-message"><%= errorMessage %></p>
    <%
        }
    %>

    <%-- The table attributes border, cellpadding, cellspacing might be overridden by CSS if
         your style.css has more specific table styling (e.g., table, th, td selectors)
         that doesn't rely on these HTML attributes. Typically, CSS is preferred for styling. --%>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>Patient ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>Admission Date</th>
                <th>Ailment</th>
                <th>Assigned Doctor</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Patient> patients = (List<Patient>) request.getAttribute("patients");
                if (patients != null && !patients.isEmpty()) {
                    for (Patient p : patients) {
            %>
                <tr>
                    <td><%= p.getPatientID() %></td>
                    <td><%= p.getPatientName() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getGender() %></td>
                    <td><%= p.getAdmissionDate() != null ? p.getAdmissionDate() : "" %></td>
                    <td><%= p.getAilment() %></td>
                    <td><%= p.getAssignedDoctor() %></td>
                </tr>
            <%
                    }
                } else if (errorMessage == null || errorMessage.isEmpty()) {
                    // Only show "No results" if there isn't already an error message from the servlet
            %>
                <%-- Added class for potential CSS targeting from external sheet --%>
                <tr><td colspan="7" class="no-results-message">No patients found matching your criteria.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>

    <p>
        <a href="<%= request.getContextPath() %>/reports.jsp">Back to Reports List</a><br>
        <a href="<%= request.getContextPath() %>/index.jsp">Back to Home</a>
    </p>
</body>
</html>