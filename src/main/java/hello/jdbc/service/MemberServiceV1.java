package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 repository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        Member fromMember = repository.findById(fromId);
        Member toMember = repository.findById(toId);

        fromMember.setMoney(fromMember.getMoney() - money);
        toMember.setMoney(toMember.getMoney() + money);

        repository.update(fromMember);
        vaildation(toMember);
        repository.update(toMember);
    }

    private static void vaildation(Member toMember) {
        if (toMember.getMemberId().equals("memberEX")) {
            throw new IllegalStateException();
        }
    }

}
