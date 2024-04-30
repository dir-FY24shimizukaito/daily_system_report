package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.AttendanceConverter;
import actions.views.AttendanceView;
import constants.JpaConst;
import models.Attendance;
import models.validators.AttendanceValidator;

public class AttendanceService extends ServiceBase {
    
    public List<AttendanceView> getMinePerPage(EmployeeView employee, int page){
        
        List<Attendance> attendances = em.createNamedQuery(JpaConst.Q_ATT_GET_ALL_MINE, Attendance.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return AttendanceConverter.toViewList(attendances);
    }
    
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }
    
    public List<AttendanceView> getAllPerPage(int page) {

        List<Attendance> attendances = em.createNamedQuery(JpaConst.Q_ATT_GET_ALL, Attendance.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return AttendanceConverter.toViewList(attendances);
    }
    
    public long countAll() {
        long Attendances_count = (long) em.createNamedQuery(JpaConst.Q_ATT_COUNT, Long.class)
                .getSingleResult();
        return Attendances_count;
    }
    
    public AttendanceView findOne(int id) {
        return AttendanceConverter.toView(findOneInternal(id));
    }
    
    public List<String> create(AttendanceView av) {
        List<String> errors = AttendanceValidator.validate(av);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            av.setCreatedAt(ldt);
            createInternal(av);
        }
        
        return errors;
        
    }
    
    private Attendance findOneInternal(int id) {
        return em.find(Attendance.class, id);
    }
    
    private void createInternal(AttendanceView av) {

        em.getTransaction().begin();
        em.persist(AttendanceConverter.toModel(av));
        em.getTransaction().commit();

    }
    
    


}
