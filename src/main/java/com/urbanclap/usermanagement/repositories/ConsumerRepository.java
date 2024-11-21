package com.urbanclap.usermanagement.repositories;

import com.urbanclap.usermanagement.entity.Consumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {

    Optional<Consumer> findByEmail(String email);
}
