package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Attendance;


public class AttendanceConverter {
    
    public static Attendance toModel(AttendanceView av) {
        return new Attendance(
                av.getId(),
                EmployeeConverter.toModel(av.getEmployee()),
                av.getAttendanceDate(),
                av.getCreatedAt());
    }
    
    public static AttendanceView toView(Attendance a) {
        
        if (a == null) {
            return null;
        }
        
        return new AttendanceView(
                a.getId(),
                EmployeeConverter.toView(a.getEmployee()),
                a.getAttendanceDate(),
                a.getCreatedAt());
        }
    
    public static List<AttendanceView> toViewList(List<Attendance> list){
        List<AttendanceView> avs = new ArrayList<>();
        
        for (Attendance a : list) {
            avs.add(toView(a));
        }
        
        return avs;
    }
    
    public static void copyViewToModel(Attendance a, AttendanceView av) {
        a.setId(av.getId());
        a.setEmployee(EmployeeConverter.toModel(av.getEmployee()));
        a.setAttendanceDate(av.getAttendanceDate());
        a.setCreatedAt(av.getCreatedAt());
    }
}


