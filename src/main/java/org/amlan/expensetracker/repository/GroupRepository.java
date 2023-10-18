package org.amlan.expensetracker.repository;

import org.amlan.expensetracker.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
