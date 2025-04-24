package org.fomabb.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для формирования пагинации")
public class PaginationInfo {

    @Schema(description = "Текущая страница", example = "1")
    private int currentPage;

    @Schema(description = "Страница элементов", example = "3")
    private int itemsPerPage;

    @Schema(description = "Всего страниц", example = "1")
    private int totalPages;

    @Schema(description = "Совокупные элементы", example = "3")
    private long totalItems;

    @Schema(description = "Является ли текущая страница первой", example = "true")
    private boolean isFirst;

    @Schema(description = "Является ли текущая страница последней", example = "false")
    private boolean isLast;

    @Schema(description = "Является ли текущая страница пустой", example = "false")
    private boolean isEmpty;
}
