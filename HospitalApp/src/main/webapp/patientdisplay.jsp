<%@ page import="java.util.List" %>
<%@ page import="com.model.Patient" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>View Patients</title>
    <%-- Link to the external stylesheet --%>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/style.css">

    <%-- Your existing internal styles --%>
    <style>
        /* These internal styles might be overridden by more specific rules in style.css,
           or they might override general rules from style.css if they are more specific
           or loaded after and have the same specificity.
           The .error-message class defined here will likely take precedence
           if style.css also defines .error-message without !important and has similar specificity.
        */
        .error-message {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <h2>All Patients</h2>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage"); // Using "errorMessage" as per original
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
                // Only show "No patients found" if there isn't a more specific error message
                // and the patients list is null or empty.
                if (patients != null && !patients.isEmpty()) {
                    for (Patient p : patients) {
            %>
                <tr>
                    <td><%= p.getPatientID() %></td>
                    <td><%= p.getPatientName() %></td>
                    <td><%= p.getAge() %></td>
                    <td><%= p.getGender() %></td>
                    <td><%= p.getAdmissionDate() != null ? p.getAdmissionDate().toString() : "" %></td>
                    <td><%= p.getAilment() %></td>
                    <td><%= p.getAssignedDoctor() %></td>
                </tr>
            <%
                    }
                } else if (errorMessage == null || errorMessage.isEmpty()) { // Check isEmpty for robustness
                    // If there was no error message set, and patients list is null or empty
            %>
                <%-- Added class for potential CSS targeting from external sheet --%>
                <tr><td colspan="7" class="no-results-message">No patients found.</td></tr>
            <%
                }
                // If there was an errorMessage, the table might appear empty or you could add a specific row here.
                // The current setup displays the error message above the table.
            %>
        </tbody>
    </table>

    <%-- Added contextPath to the link for robustness --%>
    <p><a href="<%= request.getContextPath() %>/index.jsp">Back to Home</a></p>
</body>
</html>