/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author artour
 */
@Local
public interface VisitFacadeLocal {

    void create(Visit visit);

    void edit(Visit visit);

    void remove(Visit visit);

    Visit find(Object id);

    List<Visit> findAll();

    List<Visit> findRange(int[] range);

    int count();
    
}
