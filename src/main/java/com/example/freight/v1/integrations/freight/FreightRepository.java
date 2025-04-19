package com.example.freight.v1.integrations.freight;

import com.example.freight.v1.integrations.freight.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FreightRepository extends JpaRepository<Freight, Long> {
}
