package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * いいねデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_FOL)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_FOL_GET_ALL_MINE,
            query = JpaConst.Q_FOL_GET_ALL_MINE_DEF),
    @NamedQuery(
            name = JpaConst.Q_FOL_COUNT_ALL_MINE,
            query = JpaConst.Q_FOL_COUNT_ALL_MINE_DEF),
   @NamedQuery(
           name = JpaConst.Q_FOL_COUNT_ALL,
           query = JpaConst.Q_FOL_COUNT_ALL_DEF)

})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity

public class Follow {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.FOL_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * いいねをした従業員
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * いいねをした日報
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.FOL_COL_REP, nullable = false)
    private Employee report;
    /**
     * 登録日時
     */
    @Column(name = JpaConst.FOL_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.FOL_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}