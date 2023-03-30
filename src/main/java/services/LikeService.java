package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.LikeConverter;
import actions.views.LikeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Like;

/**
 * 日報テーブルの操作に関わる処理を行うクラス
 */
public class LikeService extends ServiceBase {
    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<LikeView> getMinePerPage(ReportView report, int page) {

        List<Like> likes = em.createNamedQuery(JpaConst.Q_LIKE_GET_ALL_MINE, Like.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return LikeConverter.toViewList(likes);
    }
    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<LikeView> getAllPerPage(int page) {

        List<Like> likes = em.createNamedQuery(JpaConst.Q_LIKE_GET_ALL, Like.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return LikeConverter.toViewList(likes);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long likes_count = (long) em.createNamedQuery(JpaConst.Q_LIKE_COUNT, Long.class)
                .getSingleResult();
        return likes_count;
    }

    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param lv 日報の登録内容
     * @return
     * @return
     */
    public  void create(LikeView lv) {
        LocalDateTime ldt = LocalDateTime.now();
        lv.setCreatedAt(ldt);
        lv.setUpdatedAt(ldt);
        createInternal(lv);

    }

    /**
     * 日報データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(LikeView lv) {

        em.getTransaction().begin();
        em.persist(LikeConverter.toModel(lv));
        em.getTransaction().commit();
    }



    /**
     * 指定した従業員が作成した日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
     */
    public long countAllMine(ReportView report) {

        long count = (long) em.createNamedQuery(JpaConst.Q_LIKE_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .getSingleResult();

        return count;
    }

    /**
     * 対象の日報とログインしている従業員のいいねレコードの件数を取得する
     * @param reportView 対象の日報
     * @param loginEmployee ログインしている従業員
     * @return 対象の日報とログインしている従業員のいいねレコードの件数
     */
    public long countLikesByReportAndEmployee(ReportView report, EmployeeView Employee) {
        long count = (long) em.createNamedQuery(JpaConst.Q_LIKE_COUNT_BY_REPORT_AND_EMPLOYEE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(Employee))
                .getSingleResult();
        return count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public LikeView findOne(int id) {
        return LikeConverter.toView(findOneInternal(id));
    }

    private Like findOneInternal(int id) {
        return em.find(Like.class, id);
    }




}