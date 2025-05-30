package com.servlet;
import com.dao.HospitalDAO;
import com.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/AddPatientServlet")
public class AddPatientServlet extends HttpServlet {

	private HospitalDAO dao;

    @Override
    public void init() {
        dao = new HospitalDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int patientID = Integer.parseInt(request.getParameter("patientID"));
            String name = request.getParameter("patientName");
            int age = Integer.parseInt(request.getParameter("age"));
            String gender = request.getParameter("gender");
            String admissionDateStr = request.getParameter("admissionDate"); // Format: yyyy-mm-dd
            String ailment = request.getParameter("ailment");
            String assignedDoctor = request.getParameter("assignedDoctor");

            // Convert String to java.sql.Date
            Date admissionDate = Date.valueOf(admissionDateStr);

            // Set patient data
            Patient patient = new Patient();
            patient.setPatientID(patientID);
            patient.setPatientName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setAdmissionDate(admissionDateStr); // Still storing as String in model
            patient.setAilment(ailment);
            patient.setAssignedDoctor(assignedDoctor);

            // Add patient using DAO
            boolean added = dao.addPatient(patient, admissionDate);

            if (added) {
                response.sendRedirect("patientadd.jsp?msg=Patient added successfully");
            } else {
                response.sendRedirect("patientadd.jsp?msg=Failed to add patient");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("patientadd.jsp?msg=Error: " + e.getMessage());
        }
    }
}
