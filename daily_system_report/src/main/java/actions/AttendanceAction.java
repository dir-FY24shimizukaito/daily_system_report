package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AttendanceView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.AttendanceService;

public class AttendanceAction extends ActionBase {
    
    private AttendanceService service;
    
    @Override
    public void process() throws ServletException, IOException{
        
        service = new AttendanceService();
        
        invoke();
        service.close();
    }
    
    public void index() throws ServletException, IOException {
    
    int page = getPage();
    List<AttendanceView> attendances = service.getAllPerPage(page);
    
    long attendancesCount = service.countAll();{
    
    putRequestScope(AttributeConst.ATTENDANCES, attendances); 
    putRequestScope(AttributeConst.ATT_COUNT, attendancesCount); 
    putRequestScope(AttributeConst.PAGE, page);
    putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

    }
    
    forward(ForwardConst.FW_ATT_INDEX);
    
    }
}


