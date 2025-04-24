package org.fomabb.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fomabb.demo.dto.PaginationInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Общий ответ с пагинацией")
public class PageableResponse<T> {
    @Schema(description = "Список данных", example = "data[]")
    private List<T> content;

    @Schema(description = "Информация о пагинации", example = """
            "paginationInfo": {
                    "currentPage": 1,
                    "itemsPerPage": 3,
                    "totalPages": 1,
                    "totalItems": 3
                }
            """)
    private PaginationInfo paginationInfo;

}
