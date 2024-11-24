package com.bwslc.locationsuggestion.applayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bwslc.locationsuggestion.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT l FROM Location l WHERE l.name LIKE :searchName%")
    List<Location> findLocationByNameContaining(@Param("searchName") String name);
}