package com.lionheart15.ideamarket.repository;

import com.lionheart15.ideamarket.domain.entity.Board;
import com.lionheart15.ideamarket.domain.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Page<Board> findByCategory(String category, Pageable pageable);
    Page<Board> findByCategoryAndTitleContains(String category, String title, Pageable pageable);
    Page<Board> findByCategoryAndUserNameContains(String category, String username, Pageable pageable);

    @Query(value = "select b from Board b order by b.goods.size DESC")
    Page<Board> popularList(Pageable pageable);

    Page<Board> findByUserId(Long id,Pageable pageable);
}
