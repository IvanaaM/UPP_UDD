package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.CoauthorOther;

@Repository
public interface CoauthorOtherRepository extends JpaRepository<CoauthorOther, Long> {

}
