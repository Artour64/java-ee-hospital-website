/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import static helper.HtmlGen.*;
import helper.Vals;
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
public class visitedit extends HttpServlet {

    @EJB
    private VisitFacadeLocal visitFacade;

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
        HttpSession session = request.getSession();
        Vals.myVals(request);
        try (PrintWriter out = response.getWriter()) {
            Long id = Long.parseLong(request.getParameter("Id"));

            if (Vals.permission(session) > 1) {//admin
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        wrapTag(wrapTag("Edit Visit","h1"),"td",at("colspan","2")),
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        tagSeries("td", "Active:", input("checkbox", "active", "active", strChecked(visitFacade.find(id).isActive()))),
                                        tagSeries("td", "Patient Id:", input("number", "patid", "" + visitFacade.find(id).getIdPatient())),
                                        tagSeries("td", "Doctor Id:", input("number", "docid", "" + visitFacade.find(id).getIdDoctor())),
                                        tagSeries("td", "Details:", input("text", "details", "" + visitFacade.find(id).getDetails())),
                                        wrapTag("<Input type='submit' value='Edit'>", "td", at("colspan", "2"))
                                ) + input("hidden", "Id", id + ""),
                                "form", at2("action", "EditVisit") + at2("method", "post")
                        ),
                        "table");
                x += btnLink("Go Back", "visitlist");
                //System.out.println(x);
                out.print(template(session, x, "Edit Visit"));
            } else if (Vals.permission(session) == 1) {//doctor
                String x = "";
                x += wrapTag(
                        wrapTag(
                                tagSeries("tr",
                                        wrapTag(wrapTag("Edit Visit","h1"),"td",at("colspan","2")),
                                        //tagSeries("td", "Id:", "<Input type='number' name='Id'>"),
                                        //tagSeries("td", "Active:", input("checkbox", "active", "active", strChecked(visitFacade.find(id).isActive()))),
                                        tagSeries("td", "Patient Id:", input("number", "patid", "" + visitFacade.find(id).getIdPatient())),
                                        //tagSeries("td", "Doctor Id:", input("number","docid",""+visitFacade.find(id).getIdDoctor())),
                                        tagSeries("td", "Details:", input("text", "details", "" + visitFacade.find(id).getDetails())),
                                        wrapTag("<Input type='submit' value='Edit'>", "td", at("colspan", "2"))
                                )
                                + input("hidden", "Id", id + "")
                                + input("hidden", "docid", "" + visitFacade.find(id).getIdDoctor())
                                + input("hidden", "active", activeN(visitFacade.find(id).isActive())),
                                "form", at2("action", "EditVisit") + at2("method", "post")
                        ),
                        "table");
                x += btnLink("Go Back", "visitlist");
                //System.out.println(x);
                out.print(template(session, x, "Edit Visit"));
            } else {
                response.sendRedirect("home");
            }
        } catch (Exception e) {
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
