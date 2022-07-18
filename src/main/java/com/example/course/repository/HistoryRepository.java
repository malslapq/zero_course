package com.example.course.repository;

import com.example.course.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findTopByMemberUserIdOrderByLoginDateDesc(String memberUserId);

    List<History> findAllByMemberUserIdOrderByLoginDateDesc(String memberUserId);

}
