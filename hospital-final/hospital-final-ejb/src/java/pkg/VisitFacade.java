/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author artour
 */
@Stateless
public class VisitFacade extends AbstractFacade<Visit> implements VisitFacadeLocal {

    @PersistenceContext(unitName = "hospital-final-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitFacade() {
        super(Visit.class);
    }
    
}
