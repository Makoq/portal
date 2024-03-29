package njgis.opengms.portal.dto.dataItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import njgis.opengms.portal.entity.Comments;
import njgis.opengms.portal.entity.support.AuthorInfo;
import njgis.opengms.portal.entity.support.DataItemMeta;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * @ClassName DataItemAddDTO
 * @Description todo
 * @Author sun_liber
 * @Date 2019/2/13
 * @Version 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataItemAddDTO {

    String name;
    String type;
    String description;
    String detail;
    String author;

    String reference;

    List<String> keywords;
    List<String> classifications;
    List<String> displays;
    List<String> contributers;
    List<AuthorInfo> authorship;



    int shareCount=0;
    int viewCount=0;
    int thumbsUpCount=0;

    DataItemMeta meta;

    List<Comments> comments;
}
