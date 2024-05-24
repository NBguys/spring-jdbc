package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void save() throws SQLException {
        Member member = new Member("memberD", 10000);
        repository.save(member);

        Member findMember = repository.findById(member.getMemberId());
        assertThat(member).isEqualTo(findMember);

        findMember.setMoney(20000);
        repository.update(findMember);

        Member updateMember = repository.findById(findMember.getMemberId());
        assertThat(findMember.getMoney()).isEqualTo(updateMember.getMoney());

        repository.delete(updateMember.getMemberId());

        Assertions.assertThatThrownBy(() -> repository.findById(updateMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}