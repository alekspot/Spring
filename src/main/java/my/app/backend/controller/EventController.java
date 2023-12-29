package my.app.backend.controller;

import lombok.AllArgsConstructor;
import my.app.backend.entity.Event;
import my.app.backend.search.EventSearch;
import my.app.backend.service.EventService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("event")
@AllArgsConstructor
public class EventController {
    public static final String ID_COLUMN = "id"; // имя столбца id
    private EventService eventService;

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getEventList() {
        return ResponseEntity.ok(eventService.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Event event = null;

        try {
            event = eventService.getEventById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(event);
    }

    @PostMapping("/add")
    public ResponseEntity<Event> add(@RequestBody Event event) {
        if (event.getTitle() == null || event.getTitle().trim().length() == 0) {
            return new ResponseEntity("title empty", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(eventService.add(event));
    }

    @PutMapping("/update")
    public ResponseEntity<Event> update(@RequestBody Event category) {
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("id empty", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("title empty", HttpStatus.NOT_ACCEPTABLE);
        }



        return ResponseEntity.ok(eventService.update(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable("id") Long id) {

        try {
            eventService.delete(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("Id" + id + "not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Event>> searchEvents(@RequestBody EventSearch searchValues) throws ParseException {
        String title = searchValues.getTitle();
        Long categoryId = searchValues.getCategoryId();
        Long userId = searchValues.getUserId();

        Date dateFrom = null;
        Date dateTo = null;


        // выставить 00:00 для начальной даты (если она указана)
        if (searchValues.getDateFrom() != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(searchValues.getDateFrom());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
            calendarFrom.set(Calendar.MINUTE, 0);
            calendarFrom.set(Calendar.SECOND, 0);
            calendarFrom.set(Calendar.MILLISECOND, 0);

            dateFrom = calendarFrom.getTime(); // записываем начальную дату с 00:00

        }


        // выставить 23:59 для конечной даты (если она указана)
        if (searchValues.getDateTo() != null) {

            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(searchValues.getDateTo());
            calendarTo.set(Calendar.HOUR_OF_DAY, 23);
            calendarTo.set(Calendar.MINUTE, 59);
            calendarTo.set(Calendar.SECOND, 59);
            calendarTo.set(Calendar.MILLISECOND, 999);

            dateTo = calendarTo.getTime(); // записываем конечную дату с 23:59

        }

        String sortColumn = searchValues.getSortColumn() != null ? searchValues.getSortColumn() : "title";
        String sortDirection = searchValues.getSortDirection() != null ? searchValues.getSortDirection() : null;

        // направление сортировки
        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        /* Вторым полем для сортировки добавляем id, чтобы всегда сохранялся строгий порядок.
            Например, если у 2-х задач одинаковое значение приоритета и мы сортируем по этому полю.
            Порядок следования этих 2-х записей после выполнения запроса может каждый раз меняться, т.к. не указано второе поле сортировки.
            Поэтому и используем ID - тогда все записи с одинаковым значением приоритета будут следовать в одном порядке по ID.
         */

        // объект сортировки, который содержит стобец и направление
        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        Integer pageNumber = searchValues.getPageNumber() != null ? searchValues.getPageNumber() : 0;
        Integer pageSize = searchValues.getPageSize() != null ? searchValues.getPageSize() : 10;


        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // результат запроса с постраничным выводом
        Page<Event> result = eventService.findByParams(title, userId, categoryId, dateFrom, dateTo, pageRequest);


        return ResponseEntity.ok(result);

    }
}
