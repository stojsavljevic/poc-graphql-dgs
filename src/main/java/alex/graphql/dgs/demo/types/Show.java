package alex.graphql.dgs.demo.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Show {
	
    private String title;
    private Integer releaseYear;
    private String authorId;

}