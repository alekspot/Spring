package my.app.backend.controller;

import lombok.AllArgsConstructor;
import my.app.backend.entity.EventCategory;
import my.app.backend.service.EventCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("event-category")
@AllArgsConstructor
public class EventCategoryController {
    private EventCategoryService eventCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<EventCategory>> getCategoryList() {
        List<EventCategory> eventCategories = eventCategoryService.getEventCategories();

        return ResponseEntity.ok(eventCategories);
    }
}
