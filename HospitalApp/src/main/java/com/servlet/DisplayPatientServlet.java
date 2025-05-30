package com.servlet;

import com.dao.HospitalDAO;
import com.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/DisplayPatientServlet")
public class DisplayPatientServlet extends HttpServlet {

    private HospitalDAO dao;
    private static final Logger LOGGER = Logger.getLogger(DisplayPatientServlet.class.getName());

    @Override
    public void init() throws ServletException {
        try {
            dao = new HospitalDAO();
            LOGGER.info("DisplayPatientServlet initialized successfully with HospitalDAO.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing HospitalDAO in DisplayPatientServlet", e);
            // Throwing ServletException indicates that the servlet cannot be initialized properly
            throw new ServletException("Failed to initialize HospitalDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Patient> patients = null;
        String errorMessage = null;

        try {
            if (dao == null) {
                // This might happen if init() failed silently or was overridden without super.init()
                // and dao wasn't initialized.
                errorMessage = "DAO not initialized. Cannot fetch patient data.";
                LOGGER.severe(errorMessage);
            } else {
                patients = dao.getAllPatient();
                if (patients != null) {
                    LOGGER.info("Retrieved " + patients.size() + " patients from DAO.");
                } else {
                    // This case means DAO explicitly returned null, which might be by design or an error.
                    LOGGER.warning("getAllPatient() returned null. No patients will be displayed.");
                    // Depending on requirements, you might want to treat null as an error or as empty.
                    // For this example, null list will lead to "No patients found" unless an errorMessage is set.
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error retrieving patients from DAO", e);
            errorMessage = "An error occurred while retrieving patient data. Please check server logs.";
            // You could be more specific with e.getMessage() but might expose internal details.
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        request.setAttribute("patients", patients); // 'patients' can be null or empty
        request.getRequestDispatcher("patientdisplay.jsp").forward(request, response);
    }
}