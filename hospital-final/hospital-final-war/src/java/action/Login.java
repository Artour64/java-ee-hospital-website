/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pkg.Admin;
import pkg.AdminFacadeLocal;
import pkg.Doctor;
import pkg.DoctorFacadeLocal;
import pkg.Patient;
import pkg.PatientFacadeLocal;

/**
 *
 * @author artour
 */
public class Login extends HttpServlet {

    @EJB
    private PatientFacadeLocal patientFacade;

    @EJB
    private DoctorFacadeLocal doctorFacade;

    @EJB
    private AdminFacadeLocal adminFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            HttpSession session=request.getSession();
            {
                List<Patient> x=patientFacade.findAll();
                for(Patient c:x){
                    if(c.getUsername().equals(username)&&c.getPassword().equals(password)&&c.isActive()){
                        session.setAttribute("patientid", c.getId());
                        break;
                    }
                }
            }
            {
                List<Doctor> x=doctorFacade.findAll();
                for(Doctor c:x){
                    if(c.getUsername().equals(username)&&c.getPassword().equals(password)&&c.isActive()){
                        session.setAttribute("doctorid", c.getId());
                        break;
                    }
                }
            }
            {
                List<Admin> x=adminFacade.findAll();
                for(Admin c:x){
                    if(c.getUsername().equals(username)&&c.getPassword().equals(password)&&c.isActive()){
                        session.setAttribute("adminid", c.getId());
                        break;
                    }
                }
            }
            
            response.sendRedirect("home");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
