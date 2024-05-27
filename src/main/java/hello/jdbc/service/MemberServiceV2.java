package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Connection con = dataSource.getConnection();

        try {
            //트랜잭션 시작
            con.setAutoCommit(false);

            Member fromMember = repository.findById(con,fromId);
            Member toMember = repository.findById(con,toId);

            fromMember.setMoney(fromMember.getMoney() - money);
            toMember.setMoney(toMember.getMoney() + money);

            repository.update(con, fromMember);
            vaildation(toMember);
            repository.update(con, toMember);

            con.commit();

        } catch (Exception e) {
            con.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    private static void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); //커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private static void vaildation(Member toMember) {
        if (toMember.getMemberId().equals("memberEX")) {
            throw new IllegalStateException();
        }
    }

}
