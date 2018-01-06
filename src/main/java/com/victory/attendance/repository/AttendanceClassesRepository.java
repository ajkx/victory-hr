package com.victory.attendance.repository;

import com.victory.attendance.entity.AttendanceClasses;
import com.victory.common.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by ajkx
 * Date: 2017/8/1.
 * Time:10:25
 */
@Repository
public interface AttendanceClassesRepository extends BaseRepository<AttendanceClasses,Long> {

    AttendanceClasses findByName(String name);

    long countByName(String name);
    /**
     * 删除不为休息和class_id为空的孤岛数据
     */
    @Modifying
    @Query("delete from AttendanceClassesDetail a where a.classes is null and not EXISTS(select 1 from AttendanceClasses b where b.restTime = a.id)")
    void deleteClassDetailOnClassesIsNull();
}
