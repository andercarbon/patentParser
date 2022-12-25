package com.basf.parser.patentParser.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basf.parser.patentParser.entity.Patent;

public interface PatentRepository extends JpaRepository<Patent, Long> {
	Patent findBytitle(String patentTitle);
}
