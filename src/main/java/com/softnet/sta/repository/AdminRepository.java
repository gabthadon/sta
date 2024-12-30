package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    //Get count of enrolled students
    @Query(value = "SELECT COUNT(id) FROM learner WHERE is_enrolled = true", nativeQuery = true)
    Long countIsEnrolled();

//Get count of students that has completed training
    @Query(value = "SELECT COUNT(id) FROM learner WHERE has_completed_training = true", nativeQuery = true)
    Long countHasCompletedTraining();

//Get count of students still undergoing training
    @Query(value = "SELECT COUNT(ln.id) FROM learner ln INNER JOIN cohort ch ON ch.is_active = true AND ch.name = ln.cohort_name ", nativeQuery = true)
    Long countCandidateInTraining();

    //Get count of full time students
    @Query(value = "SELECT COUNT(id) FROM learner WHERE availability = 'Full-Time'", nativeQuery = true)
    Long countAvailabilityFullTime();

    //Get count of part time students
    @Query(value = "SELECT COUNT(id) FROM learner WHERE availability = 'Part-Time'", nativeQuery = true)
    Long countAvailabilityPartTime();


    //Count Of Self Sponsored
    @Query(value = "SELECT COUNT(id) FROM learner WHERE organization_rep_id = 'STA-SS'", nativeQuery = true)
    Long countSelfSponsored();

    //Count Of Group Sponsored
    @Query(value = "SELECT COUNT(id) FROM learner WHERE organization_rep_id != 'STA-SS'", nativeQuery = true)
    Long countGroupSponsored();


    //Get count of learners by categories  to be use for Bar chart of candidate distribution by Category
    @Query(value = """
        SELECT cat.name AS category_name, COUNT(DISTINCT ln.id) AS learner_count
        FROM learner ln
        JOIN packages pk ON ln.packages_id = pk.id
        JOIN course_package cp ON pk.id = cp.package_id
        JOIN course c ON cp.course_id = c.id
        JOIN category cat ON c.category_id = cat.id
        GROUP BY cat.name
        """, nativeQuery = true)
    List<Object[]> countLearnersByCategory();




    //Get count of learners distribution by package to be use for Pie chart of candidate distribution by Package
    @Query(value = """
        SELECT pk.package_name AS package_name, COUNT(DISTINCT ln.id) AS learner_count
        FROM learner ln
        JOIN packages pk ON ln.packages_id = pk.id
        GROUP BY pk.package_name
        """, nativeQuery = true)
    List<Object[]> countLearnersByPackage();


//Get count of learners by cohort name, to be used for Bar chart of candidates by cohort
    @Query(value = """
        SELECT ln.cohort_name AS cohort_name, COUNT(DISTINCT ln.id) AS learner_count
        FROM learner ln
        GROUP BY ln.cohort_name
        """, nativeQuery = true)
    List<Object[]> countLearnersByCohort();


    //Get A count of Total Amount Received
    @Query(value = """
        SELECT COALESCE(SUM(st.amount), 0) AS total_amount_received
        FROM sta_transaction st
        WHERE st.status = 'SUCCESS'
        """, nativeQuery = true)
    BigDecimal getTotalAmountReceived();


    //Get count of total amount collected for active cohort
    @Query(value = """
        SELECT COALESCE(SUM(st.amount), 0) AS total_amount_collected
        FROM sta_transaction st
        JOIN learner l ON st.enrollment_id = l.enrollment_id
        JOIN cohort c ON l.cohort_name = c.name
        WHERE st.status = 'SUCCESS'
          AND c.is_active = true
        """, nativeQuery = true)
    BigDecimal getTotalAmountCollectedForActiveCohort();


    //Get count of total  amount received by group enrollment
    @Query(value = """
      SELECT COALESCE(SUM(st.amount), 0) AS total_amount_collected
                FROM sta_transaction st
                JOIN learner l ON st.enrollment_id = l.enrollment_id
                WHERE st.status = 'SUCCESS'
                  AND ( l.organization_rep_id != 'STA-SS')  
        """, nativeQuery = true)
    List<Object[]> getTotalAmountReceivedByGroupEnrollment();


//Get count of total  amount received by individual enrollment
    @Query(value = """
                 SELECT COALESCE(SUM(st.amount), 0) AS total_amount_collected
                FROM sta_transaction st
                JOIN learner l ON st.enrollment_id = l.enrollment_id
                WHERE st.status = 'SUCCESS'
                  AND (l.organization_rep_id IS NULL OR l.organization_rep_id = 'STA-SS')             
        """, nativeQuery = true)
    List<Object[]> getTotalAmountReceivedByIndividualEnrollment();


}
