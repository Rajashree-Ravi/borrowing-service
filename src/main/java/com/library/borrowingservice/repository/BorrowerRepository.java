package com.library.borrowingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.borrowingservice.entity.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

}
