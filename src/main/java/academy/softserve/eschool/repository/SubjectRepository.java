package academy.softserve.eschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import academy.softserve.eschool.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
   
	@Query(value = "SELECT DISTINCT subject.id, subject.name, subject.description FROM class_teacher_subject_link \n" +
			"LEFT JOIN subject ON class_teacher_subject_link.subject_id = subject.id \n" +
            "WHERE teacher_id = :idTeacher", nativeQuery=true)
    List<Subject> findSubjectsByTeacher(@Param("idTeacher") int idTeacher);
    
    /**
     * @param classId
     * @return
     */
    @Query(value = "SELECT DISTINCT subject.id, subject.name, subject.description FROM class_teacher_subject_link \n" +
    		"LEFT JOIN subject ON class_teacher_subject_link.subject_id = subject.id \n" +
            "WHERE clazz_id = :classId", nativeQuery=true)
	List<Subject> findSubjectsByClass(@Param(value="classId") Integer classId);

    @Query(value = "SELECT * FROM subject WHERE id IN (:ids)", nativeQuery =true)
    List<Subject> findSubjectsByIds(@Param("ids") List<Integer> ids);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE subject SET name = :subjectName, description = :subjectDescription WHERE id = :id", nativeQuery=true)
    void editSubject(@Param("id") int id, @Param("subjectName") String name, @Param("subjectDescription") String description);
}
