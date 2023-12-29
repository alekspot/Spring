package my.app.backend.service;

import lombok.AllArgsConstructor;
import my.app.backend.entity.EventCategory;
import my.app.backend.repo.EventCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventCategoryService {
    private final EventCategoryRepository repository;

    public List<EventCategory> getEventCategories() {
        return repository.findAll();
    }
}
