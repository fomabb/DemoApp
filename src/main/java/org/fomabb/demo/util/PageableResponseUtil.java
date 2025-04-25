package org.fomabb.demo.util;

import org.fomabb.demo.dto.PaginationInfo;
import org.fomabb.demo.dto.response.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableResponseUtil {

    public <T, R extends PageableResponse<T>> R buildPageableResponse(
            List<T> content, Page<?> page, R response
    ) {
        response.setContent(content);
        response.setPaginationInfo(
                PaginationInfo.builder()
                        .currentPage(page.getNumber() + 1)
                        .itemsPerPage(page.getNumberOfElements())
                        .totalPages(page.getTotalPages())
                        .totalItems(page.getTotalElements())
                        .isFirst(page.isFirst())
                        .isLast(page.isLast())
                        .isEmpty(page.isEmpty())
                        .build());

        return response;
    }
}
