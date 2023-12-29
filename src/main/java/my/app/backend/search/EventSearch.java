package my.app.backend.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventSearch {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    private String title;
    private Long categoryId;
    private Long userId;

    private Date dateFrom; // для задания периода по датам
    private Date dateTo;


    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;
}

