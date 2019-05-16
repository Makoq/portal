package njgis.opengms.portal.dao;

import njgis.opengms.portal.entity.Classification;
import njgis.opengms.portal.entity.Concept;
import njgis.opengms.portal.entity.SpatialReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @ClassName ClassificationDao
 * @Description todo
 * @Author Kai
 * @Date 2019/2/21
 * @Version 1.0.0
 * TODO
 */
public interface SpatialReferenceDao extends MongoRepository<SpatialReference,String> {
    SpatialReference findByOid(String oid);

    Page<SpatialReference> findByParentId(String parentId, Pageable pageable);

    Page<SpatialReference> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<SpatialReference> findByParentIdIn(List<String> parentIds, Pageable pageable);
}