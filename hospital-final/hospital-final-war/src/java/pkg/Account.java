/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import helper.*;
import static helper.HtmlGen.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author artour
 */
public class Account extends HttpServlet {

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
            Vals.myVals(request);
            HttpSession session = request.getSession();
            int permission = Vals.permission(session);
            String x = "";

            if (permission < 0) {//logged out
                /*
                x += tagSeries("tr",
                         wrapTag(wrapTag("Choose An Option", "h1"), "td"),
                         wrapTag(btnLink("Login", "LoginPage"), "td")
                //,wrapTag(btnLink("Signup","SignupPage"),"td")
                );
                */
                response.sendRedirect("LoginPage");

            } else {
                //*
                if (permission == 0) {//patient
                    Patient y=patientFacade.find(session.getAttribute("patientid"));
                    x+=wrapTags("Patient:"+y.getUsername(),true,"tr","td");
                }
                if (permission == 1) {//doctor
                    Doctor y=doctorFacade.find(session.getAttribute("doctorid"));
                    x+=wrapTags("Doctor:"+y.getUsername(),true,"tr","td");
                }
                if (permission > 1) {//admin
                    Admin y=adminFacade.find(session.getAttribute("adminid"));
                    x+=wrapTags("Admin:"+y.getUsername(),true,"tr","td");
                }
                //*/
                x+=wrapTags(btnLink("Logout", "Logout"),true,"tr","td");
                x=wrapTag(x,"table");
            out.println(template(session,x, "Account"));
            }
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
