import java.sql.Timestamp;

import com.jdbc.model.dto.AllArgsConstructor;
import com.jdbc.model.dto.Builder;
import com.jdbc.model.dto.Data;
import com.jdbc.model.dto.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {
    private Integer reviewSeq;
    private String reviewTitle;
    private String reviewContents;
    private Timestamp reviewCreateTime;
    private Integer reviewRate;
    private Timestamp reviewDeleteTime;
    private Timestamp reviewEditTime;
    private Integer userSeq;
    private Integer bookSeq;
}
