/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import static helper.HtmlGen.at2;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author artour
 */
import static helper.HtmlGen.input;
import static helper.HtmlGen.tagSeries;
import static helper.HtmlGen.tagSeriesAtt;
import static helper.HtmlGen.template;
import static helper.HtmlGen.wrapTag;
import helper.Vals;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpSession;

public class visitlist extends HttpServlet {

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
        String x = "";
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            Vals.myVals(request);
            x = "";

            //x += "<table>";
            //x += "<tr><th>First Name</th><th>Last Name</th></tr>";
            List<Visit> l = visitFacade.findAll();
            String rows = "";
            String[] colNames;
            ArrayList<String> t = new ArrayList<String>();
            String docid = null;
            Object temp = null;
            temp = session.getAttribute("doctorid");
            if (temp != null) {
                docid = temp + "";
            }
            if (Vals.permission(session) > 1) {//admin
                colNames = new String[]{"Id", "Active", "Patient Id", "Doctor Id", "Details"};
                t.add(tagSeries("th", colNames));
                for (Visit c : l) {
                    t.add(
                            tagSeriesAtt("td", "align='center' style='font-weight:normal'",
                                    c.getId() + "",
                                    c.isActive() + "",
                                    c.getIdPatient() + "",
                                    c.getIdDoctor() + "",
                                    c.getDetails(),
                                    wrapTag(
                                            input("hidden", "Id", c.getId() + "")
                                            + input("submit", null, "Edit"),
                                            "form", at2("action", "visitedit") + at2("method", "post")
                                    ),
                                    wrapTag(
                                            input("hidden", "Id", c.getId() + "")
                                            + input("submit", null, "Delete"),
                                            "form", at2("action", "DeleteVisit") + at2("method", "post")
                                    )
                            )
                    );
                }
            } else if (Vals.permission(session) == 1) {//doctor
                colNames = new String[]{"Id","Patient Id", "Details"};
                t.add(tagSeries("th", colNames));
                for (Visit c : l) {
                    if (c.isActive()) {
                        if (c.getIdDoctor() == Long.parseLong("" + session.getAttribute("doctorid"))) {
                            t.add(
                                    tagSeriesAtt("td", "align='center' style='font-weight:normal'",
                                            c.getId() + "",
                                            //c.isActive() + "",
                                            c.getIdPatient() + "",
                                            c.getDetails(),
                                            wrapTag(
                                                    input("hidden", "Id", c.getId() + "")
                                                    + input("submit", null, "Edit"),
                                                    "form", at2("action", "visitedit") + at2("method", "post")
                                            ),
                                            wrapTag(
                                                    input("hidden", "Id", c.getId() + "")
                                                    + input("submit", null, "Delete"),
                                                    "form", at2("action", "DeleteVisit") + at2("method", "post")
                                            )
                                    )
                            );
                        }
                    }
                }
            } else {
                response.sendRedirect("home");
                return;
            }
            x += "</table>";
            String[] c = new String[t.size()];
            c = (String[]) t.toArray(c);
            x = "";

            x += wrapTag(
                    tagSeries("tr",
                            c
                    ),
                    "table");
            /*
                System.out.println(x);//*/
            out.print(template(session, x, "Visits"));

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
