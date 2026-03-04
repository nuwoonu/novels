package com.example.novels.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novels.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    // 구글로그인 접근 여부 판단.
    @EntityGraph(attributePaths = { "roles" }, type = EntityGraphType.LOAD) // 같이 갖고 나오게끔 하는 구문
    Optional<Member> findByEmailAndFromSocial(String email, boolean fromSocial); // JPA가 만들어 주기떄문에 Query문 필요없이 엔티티그래프만
                                                                                 // 있으면 된다.
}
