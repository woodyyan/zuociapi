package com.easystudio.api.zuoci.repository;

import com.easystudio.api.zuoci.entity.ReportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportInfo, Long> {
}
