package com.urbanclap.usermanagement.repositories;

import com.urbanclap.usermanagement.entity.ServiceProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {

    Optional<ServiceProvider> findByEmail(String email);
}
