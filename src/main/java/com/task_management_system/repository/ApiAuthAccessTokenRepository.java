package com.task_management_system.repository;

import com.task_management_system.entity.ApiAuthAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApiAuthAccessTokenRepository extends JpaRepository<ApiAuthAccessToken, String> {

    @Query("SELECT t FROM ApiAuthAccessToken t WHERE t.apiUserDetail.id = (:userDetailId)")
    ApiAuthAccessToken findByUser(@Param("userDetailId") String userDetailId);

    @Query("SELECT t FROM ApiAuthAccessToken t WHERE t.token = (:token)")
    ApiAuthAccessToken findByToken(@Param("token") String token);

    @Modifying
    @Query("UPDATE ApiAuthAccessToken SET active = false WHERE id = (:id)")
    void delete(@Param("id") String id);
}
