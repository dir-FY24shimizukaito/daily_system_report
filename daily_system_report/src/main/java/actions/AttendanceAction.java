package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AttendanceView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.AttendanceService;
import java.time.LocalDate;
import actions.views.EmployeeView;
import constants.MessageConst;

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
    
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        
        AttendanceView av = new AttendanceView();
        av.setAttendanceDate(LocalDate.now());
        putRequestScope(AttributeConst.ATTENDANCE, av); 

        //新規登録画面を表示
        forward(ForwardConst.FW_ATT_NEW);

    }
    
    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.ATT_DATE) == null
                    || getRequestParam(AttributeConst.ATT_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.ATT_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //パラメータの値をもとに日報情報のインスタンスを作成する
            AttendanceView av = new AttendanceView(
                    null,
                    ev,
                    day,
                    null);

            //日報情報登録
            List<String> errors = service.create(av);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, av);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_ATT_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_ATT, ForwardConst.CMD_INDEX);
            }
        }
    }
    
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に勤怠データを取得する
        AttendanceView av = service.findOne(toNumber(getRequestParam(AttributeConst.ATT_ID)));

   

            putRequestScope(AttributeConst.ATTENDANCE, av); //取得した勤怠データ

            //詳細画面を表示
            forward(ForwardConst.FW_ATT_SHOW);
        
    }
}


