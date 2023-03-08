package ru.skypro.homework.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdsComment;


@Repository
public interface AdsCommentRepository extends JpaRepository<AdsComment, Long> {
    
}