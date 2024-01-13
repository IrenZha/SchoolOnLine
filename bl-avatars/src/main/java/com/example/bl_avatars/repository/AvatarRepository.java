package com.example.bl_avatars.repository;

import com.example.bl_avatars.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar,String> {
}
