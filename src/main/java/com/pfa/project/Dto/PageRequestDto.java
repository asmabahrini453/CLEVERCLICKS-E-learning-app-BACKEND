package com.pfa.project.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDto {
    private Integer pageNo = 0; //page I want to retrieve
    private Integer pageSize = 3 ;//number of category per page

    public Pageable getPageable(PageRequestDto dto) {
        Integer page;
        Integer size;

        if (Objects.nonNull(dto.getPageNo())) {
            page = dto.getPageNo();
        } else {
            page = this.pageNo;
        }

        if (Objects.nonNull(dto.getPageSize())) {
            size = dto.getPageSize();
        } else {
            size = this.pageSize;
        }

        return PageRequest.of(page, size);
    }

}
