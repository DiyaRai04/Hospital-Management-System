package com.servlet;
import com.dao.HospitalDAO;
import com.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/UpdatePatientServlet")
public class UpdatePatientServlet extends HttpServlet {

	private HospitalDAO dao;

    @Override
    public void init() {
        dao = new HospitalDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int patientID = Integer.parseInt(request.getParameter("patientID"));
            // Fetch existing patient details to update selectively
            Patient existing = dao.getPatientById(patientID);
            if (existing == null) {
                response.sendRedirect("patientupdate.jsp?msg=Patient not found");
                return;
            }

            String name = request.getParameter("patientName");
            String ageStr = request.getParameter("age");
            String gender = request.getParameter("gender");
            String admissionDate = request.getParameter("admissionDate");
            String ailment = request.getParameter("ailment");
            String assignedDoctor = request.getParameter("assignedDoctor");

            if (name != null && !name.isEmpty()) existing.setPatientName(name);
            if (ageStr != null && !ageStr.isEmpty()) existing.setAge(Integer.parseInt(ageStr));
            if (gender != null && !gender.isEmpty()) existing.setGender(gender);
            if (admissionDate != null && !admissionDate.isEmpty()) existing.setAdmissionDate(admissionDate);
            if (ailment != null && !ailment.isEmpty()) existing.setAilment(ailment);
            if (assignedDoctor != null && !assignedDoctor.isEmpty()) existing.setAssignedDoctor(assignedDoctor);

            boolean updated = dao.updatePatient(existing);

            if (updated) {
                response.sendRedirect("patientupdate.jsp?msg=Patient updated successfully");
            } else {
                response.sendRedirect("patientupdate.jsp?msg=Failed to update patient");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("patientupdate.jsp?msg=Error: " + e.getMessage());
        }
    }
	}

