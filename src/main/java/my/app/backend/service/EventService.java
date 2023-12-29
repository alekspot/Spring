package my.app.backend.service;

import lombok.AllArgsConstructor;
import my.app.backend.entity.Event;
import my.app.backend.repo.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EventService {
    private final EventRepository repository;
    // GET ONE
    public Event getEventById(Long id) {
        return repository.findById(id).get();
    }

    // GET ALL
    public List<Event> getEvents() {
        return repository.findAll();
    }

    public List<Event> getEventsByUserId(Long id) {
        return repository.findAllByUserId(id);
    }

    // CREATE
    public Event add(Event event){
        return repository.save(event);
    }

    // UPDATE
    public Event update(Event event){
        return repository.save(event);
    }

    // DELETE
    public void delete(Long id){
        repository.deleteById(id);
    }

    // SEARCH
    public Page<Event> findByParams(String text, Long userId, Long categoryId, Date dateFrom, Date dateTo, PageRequest paging) {
        return repository.findByParams(text, userId, categoryId, dateFrom, dateTo, paging);
    }
}
