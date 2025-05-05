package com.example.freight.v1.integrations.freight;

import com.example.freight.v1.integrations.freight.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FreightRepository extends JpaRepository<Freight, Long> {


    Optional<Freight> findFreightByUserIdAndId(String userId, Long id);
}
