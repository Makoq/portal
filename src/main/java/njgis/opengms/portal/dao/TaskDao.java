package njgis.opengms.portal.dao;

import njgis.opengms.portal.entity.Task;
import njgis.opengms.portal.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TaskDao extends MongoRepository<Task,String> {

    Task findFirstByTaskId(String taskId);

    Page<Task> findByUserId(String userId,Pageable pageable);

    List<Task> findAllByUserIdAndStatus(String userId, int status);

    Page<Task> findByComputableNameContainsIgnoreCaseAndUserId(String name,String author,Pageable pageable);

    Task findFirstByOid(String oid);
}
