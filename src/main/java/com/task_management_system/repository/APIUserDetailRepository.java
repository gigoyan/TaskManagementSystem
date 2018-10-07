package com.task_management_system.repository;

import com.task_management_system.entity.APIUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserDetailRepository extends JpaRepository<APIUserDetail, String> {

    APIUserDetail findByUsernameAndDeletedFalse(String username);

    APIUserDetail findByUser_IdAndDeletedFalse(String userId);

}
