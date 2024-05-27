package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            //트랜잭션 시작

            Member fromMember = repository.findById(fromId);
            Member toMember = repository.findById(toId);

            fromMember.setMoney(fromMember.getMoney() - money);
            toMember.setMoney(toMember.getMoney() + money);

            repository.update(fromMember);
            vaildation(toMember);
            repository.update(toMember);

            transactionManager.commit(status);

        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new IllegalStateException(e);
        }
    }

    private static void vaildation(Member toMember) {
        if (toMember.getMemberId().equals("memberEX")) {
            throw new IllegalStateException();
        }
    }

}
